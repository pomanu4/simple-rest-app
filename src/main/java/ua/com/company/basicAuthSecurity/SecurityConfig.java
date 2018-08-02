package ua.com.company.basicAuthSecurity;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import ua.com.company.security.entryPoint.CustomAuthEntryPoint;
import ua.com.company.service.CustomUserDetaolService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final String realm_name = "BASIC_AUTH_REALM_NAME";

	@Autowired
	private CustomUserDetaolService cuds;

	@Autowired
	public void globalConfigure(AuthenticationManagerBuilder managerBuilder, AuthenticationProvider provider) {
		try {
			configurer().withUser("admin").password("root").roles("ADMIN").and().configure(managerBuilder);
			configurer().withUser("user").password("user").roles("USER").and().configure(managerBuilder);
			managerBuilder.authenticationProvider(provider);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(cuds);

		return provider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.cors();
		http.authorizeRequests().antMatchers("/admin*").hasRole("ADMIN")
		.and().authorizeRequests().antMatchers("/ref/*").hasRole("USER")
		.and().authorizeRequests().antMatchers("/", "/params").permitAll()
		.and().exceptionHandling()
		.and().httpBasic().authenticationEntryPoint(geEntryPoint())
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Bean("corsConfigurationSource")
	CorsConfigurationSource getCorsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("PUT", "GET", "POST", "DELETE", "HEAD", "PATCH"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public CustomAuthEntryPoint geEntryPoint() {
		return new CustomAuthEntryPoint();
	}

	@Bean
	public SimpleUrlAuthenticationFailureHandler myFailureHandler() {
		return new SimpleUrlAuthenticationFailureHandler();
	}

	private InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> configurer() {

		return new InMemoryUserDetailsManagerConfigurer<>();
	}

}
