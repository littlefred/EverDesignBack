package co.simplon.config;


import org.springframework.context.annotation.Configuration;

@Configuration
// @EnableWebSecurity
public class SecurityConfiguration /*extends WebSecurityConfigurerAdapter*/ {
	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
		.antMatcher("/**").authorizeRequests() // défini le périmètre des règles de sécurité qui vont suivre
		.antMatchers("/", "/categories", "/items/**", "/users/**", "/error**").permitAll() // autorise l'accès pour les url racine, categories, et erreur
		.anyRequest().authenticated(); // demande d'être authentifié pour les autres requêtes;
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://192.168.1.194:4200"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}*/

}
