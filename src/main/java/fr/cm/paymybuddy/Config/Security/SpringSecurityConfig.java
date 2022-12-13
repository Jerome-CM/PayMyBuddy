package fr.cm.paymybuddy.Config.Security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
private static final Logger logger = LogManager.getLogger(SpringSecurityConfig.class);

	UserDetailsServiceImpl userDetailsService;

	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests()

				.antMatchers("/register").permitAll()
				.antMatchers(HttpMethod.POST,"/registerUser").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/").permitAll()
				.antMatchers("/contact").permitAll()
				.antMatchers("/contact").permitAll()
				.antMatchers("/admin/").hasAuthority("ADMIN")
				.antMatchers("/accessDenied").permitAll()
				.antMatchers("/CSS/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login")
				.usernameParameter("mail")
				.successHandler((request, response, authentication) -> {
					HttpSession session = request.getSession();
					RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
					if(session.getAttribute("mail") == null){
						String mail = authentication.getName();
						logger.info("Handler Auth mail : {}", mail);
						session.setAttribute("mail", mail);
					}
					redirectStrategy.sendRedirect(request, response, "/");
				})
				.and()
				.rememberMe()
				.and()
				.logout()
				.logoutSuccessUrl("/login?status=disconnected")
				.invalidateHttpSession(true)
				.deleteCookies("remember-me")
				.and()
				.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return new ProviderManager(provider);
	}
}

