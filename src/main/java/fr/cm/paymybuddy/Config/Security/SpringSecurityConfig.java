package fr.cm.paymybuddy.Config.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	private UserDetailsServiceImpl userDetailsService;


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Defining the passwordEncoder here, to avoid plain-text manipulations
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests() //
				.antMatchers("/CSS/**").permitAll() // Allow CSS to be loaded by everyone
				.antMatchers("/home").permitAll() // Allow CSS to be loaded by everyone
				.antMatchers("/login").permitAll() // Allow CSS to be loaded by everyone
				.antMatchers("/contact").permitAll() // Allow CSS to be loaded by everyone
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated() // Every others pages must be accessed with valid credentials
				.and()
				.formLogin().loginPage("/login")//.defaultSuccessUrl("/transfert").failureUrl("/login?error=true")
				.loginProcessingUrl("/loginFormControl").permitAll() // Trop de redirection sans permitAll = bug de localhost
				.usernameParameter("mail")
				.passwordParameter("password");
    }


	// Utilise le hash BCrypt
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
