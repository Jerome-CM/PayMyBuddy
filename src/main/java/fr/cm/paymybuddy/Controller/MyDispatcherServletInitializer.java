package fr.cm.paymybuddy.Controller;

import fr.cm.paymybuddy.Config.JSPViewResolver;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { JSPViewResolver.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}