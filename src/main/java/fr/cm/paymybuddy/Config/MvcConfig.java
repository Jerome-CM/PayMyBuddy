package fr.cm.paymybuddy.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                /*.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");*/
                .addResourceHandler("/CSS/**")
                .addResourceLocations("/CSS/");

    }
}
