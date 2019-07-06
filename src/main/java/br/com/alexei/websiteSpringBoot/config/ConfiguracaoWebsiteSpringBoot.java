package br.com.alexei.websiteSpringBoot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import br.com.alexei.websiteSpringBoot.converter.StringToEnumConverterFactory;
import br.com.alexei.websiteSpringBoot.interceptor.AutorizadorInterceptor;

@Configuration
public class ConfiguracaoWebsiteSpringBoot implements WebMvcConfigurer {

	@Bean
	public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver resolver) {
	   SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	   templateEngine.setTemplateResolver(resolver);
	   return templateEngine;
	}
	
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
    }
	
	public void addViewControllers(ViewControllerRegistry registry) {
	   registry.addViewController("/").setViewName("home");
	   registry.addViewController("/home").setViewName("home");
	}
	
	public void addFormatters(FormatterRegistry registry) {
	    registry.addConverterFactory(new StringToEnumConverterFactory());
	}
	
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(new AutorizadorInterceptor());
	}
}
