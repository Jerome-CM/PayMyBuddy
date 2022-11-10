package fr.cm.paymybuddy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class PaymybuddyApplication {

	private static final Logger logger = LogManager.getLogger(PaymybuddyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyApplication.class, args);

		logger.trace("#-#-#-#-#-#-#--- APPLICATION READY ---#-#-#-#-#-#-#");
	}



}
