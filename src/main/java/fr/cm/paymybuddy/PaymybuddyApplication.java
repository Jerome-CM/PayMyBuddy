package fr.cm.paymybuddy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class PaymybuddyApplication implements CommandLineRunner { 

	private static final Logger logger = LogManager.getLogger(PaymybuddyApplication.class);
	
	// Utilise le hash BCrypt
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Permet de récuperer des identifiants ou d'autre attributs par DaoAuthenticationProvider
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
	    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	    manager.createUser(User.withUsername("bouteveillejerome@hotmail.fr")
	      .password(passwordEncoder.encode("adminPass"))
	      .roles("ADMIN")
	      .build());
	    return manager;
	}
	
	public static void main(String[] args){
		SpringApplication.run(PaymybuddyApplication.class, args);
	}
	
	
	  @Override
	  public void run(String... args) throws Exception {
	  
		  logger.trace("#-#-#-#-#-#-#--- APPLICATION READY ---#-#-#-#-#-#-#");
	  
	  }
	 


}
