package fr.cm.paymybuddy.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
				// autorise l'url si on a le bon rôle
		    		.antMatchers("/admin").hasRole("ADMIN")
					.antMatchers("/user").hasRole("USER")
				// toutes les requêtes http doivent être authentifiées
					.anyRequest().authenticated()
					.and()
				// Demande une page de formulaire, en précisant laquelle
					.formLogin().loginPage("/login").defaultSuccessUrl("/home").failureUrl("/login?error=true")
					.loginProcessingUrl("/loginFormControl")
					.usernameParameter("mail")
					.passwordParameter("password").permitAll()
					.and().rememberMe();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        );
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

 	// ignore l'authentification sur ces Urls
 	@Bean
 	  public WebSecurityCustomizer webSecurityCustomizer() {
 	    return (web) -> web.ignoring().antMatchers("/js/**", "/images/**", "/**"); 
 	  }
 	
 	// Exploite un UserDetailsService(DAO) afin de rechercher le nom d'utilisateur, le mot de passe et l'adresse GrantedAuthoritys
 	@Bean
 	  public DaoAuthenticationProvider authenticationProvider() {
 		
 	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
 	       
 	      authProvider.setUserDetailsService(userDetailsService);
 	      authProvider.setPasswordEncoder(passwordEncoder);
 	   
 	      return authProvider;
 	  }
 	
 	// Renvoie l'objet AuthenticationManager avec les droits accordés ( ou non ) 
 	@Bean
 	  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
 	    return authConfiguration.getAuthenticationManager();
 	  }
	
}
