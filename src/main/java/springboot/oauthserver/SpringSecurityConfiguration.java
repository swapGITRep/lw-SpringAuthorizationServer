package springboot.oauthserver;

import java.util.Arrays;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.SpringAuthorizationEventPublisher;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
	

	@Bean
	SecurityFilterChain configureSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
		.formLogin(Customizer.withDefaults());

		return http.build();
	}
	
	@Bean
	public AuthenticationEventPublisher authenticationEventPublisher
	        (ApplicationEventPublisher applicationEventPublisher) {
	    return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
	}
	
	@Bean
	public AuthorizationEventPublisher authorizationEventPublisher
	        (ApplicationEventPublisher applicationEventPublisher) {
	    return new SpringAuthorizationEventPublisher(applicationEventPublisher);
	}
	
	
	 @Bean
		CorsConfigurationSource corsConfigurationSource() {
			// Very permissive CORS config...
			final var configuration = new CorsConfiguration();
			configuration.setAllowedOrigins(Arrays.asList("*"));
			configuration.setAllowedMethods(Arrays.asList("*"));
			configuration.setAllowedHeaders(Arrays.asList("*"));
			configuration.setExposedHeaders(Arrays.asList("*"));
			
			// Limited to API routes (neither actuator nor Swagger-UI)
			final var source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", configuration);

			return source;
		}
	

	@Bean
	public UserDetailsService users() {
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		UserDetails user = User.withUsername("demo")
				.password(encoder.encode("demo"))
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager(user);
		
	}

}
