package ua.com.company.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import ua.com.company.basicAuthSecurity.SecurityConfig;

@Configuration
@EnableWebMvc
@ComponentScans({@ComponentScan("ua.com.company.controller")})
@Import(value = {DataBaseConfig.class , SecurityConfig.class , ThymeLeafConfig.class, MessageConverter.class})
public class AplicationConfig extends WebMvcConfigurerAdapter {

	private static final String UTF8 = "UTF-8";

	@Bean
	public MultipartResolver resolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(10000000);
		multipartResolver.setMaxUploadSizePerFile(1000000);

		return multipartResolver;
	}

	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		source.setBasenames("classpath:messages/messages");
		source.setDefaultEncoding(UTF8);
		source.setUseCodeAsDefaultMessage(true);

		return source;
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		return localeResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("WEB-INF/css/");
		registry.addResourceHandler("/f/**").addResourceLocations("WEB-INF/files/");
		registry.addResourceHandler("/script/**").addResourceLocations("WEB-INF/js/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("PUT","GET","POST", "DELETE", "HESD", "PATCH");
		
	}
	
	

}
