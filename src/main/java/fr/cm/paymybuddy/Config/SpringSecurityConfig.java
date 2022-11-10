package fr.cm.paymybuddy.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	@Autowired
	UserDetailsService userDetailsService;
	 
	@Autowired
	PasswordEncoder passwordEncoder;	  

 	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) -> {
			try {
				authz
		    		.antMatchers("/admin").hasRole("ADMIN")
					.antMatchers("/user").hasRole("USER")
					.anyRequest().authenticated()
					.and()
					.formLogin();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        );
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

 	
 	@Bean
 	  public WebSecurityCustomizer webSecurityCustomizer() {
 	    return (web) -> web.ignoring().antMatchers("/js/**", "/images/**"); 
 	  }
 	
 	@Bean
 	  public DaoAuthenticationProvider authenticationProvider() {
 		
 	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
 	       
 	      authProvider.setUserDetailsService(userDetailsService);
 	      authProvider.setPasswordEncoder(passwordEncoder);
 	   
 	      return authProvider;
 	  }
 	
 	@Bean
 	  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
 	    return authConfiguration.getAuthenticationManager();
 	  }
	
}
