package io.melakuera.fileuploaddownloaddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Configuration
@EnableWebSecurity
public class DemoApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/*private final String CLIENT_SECRET;

	public DemoApplication(@Value("${app.client-secret}") String CLIENT_SECRET ) {
		this.CLIENT_SECRET = CLIENT_SECRET ;
	}*/

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/webjars/**")
				.addResourceLocations("/webjars/").resourceChain(false);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
				.allowedHeaders("*")
				.maxAge(3600);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

		http
				.authorizeRequests()
				.antMatchers("/user").hasAnyRole("USER", "ADMIN")
				.antMatchers("/admin").hasRole("ADMIN")
				.antMatchers("/anonymous").permitAll()
				.and()
				.csrf().disable()
				.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter);
		return http.build();
	}

	/*private ClientRegistration clientRegistration1() {
		return CommonOAuth2Provider.GITHUB.getBuilder("github")
				.clientId("6a3a0764dd29ce230029")
				.clientSecret(CLIENT_SECRET)
				.build();
	}*/

	/*private ClientRegistration clientRegistration2() {
		return ClientRegistration.withRegistrationId("github")
				.clientId("6a3a0764dd29ce230029")
				.clientSecret(CLIENT_SECRET)
				.scope("read:user")
				.authorizationUri("https://github.com/login/oauth/authorize")
				.tokenUri("https://github.com/login/oauth/access_token")
				.userInfoUri("https://api.github.com/user")
				.userNameAttributeName("id").clientName("GitHub")
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.redirectUriTemplate("{baseUrl}/{action}/oauth2/code/{registrationId}")
				.build();
	}*/

	/*@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		return new InMemoryClientRegistrationRepository(clientRegistration1());
	}*/
}
