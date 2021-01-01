package com.roberto.hrapigatewayzuul.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private JwtTokenStore tokenStore;
	
	//rotas publicas
	private static final String[] PUBLIC = { "/hr-oauth/oauth/token" };
	
	//rotas autorização de operador(qualque rota)
	private static final String[] OPERATOR = { "/hr-worker/**" };
	
	//rotas autorização de Admin(/** qualquer rota)
	private static final String[] ADMIN = { "/hr-payroll/**"
			, "/hr-user/**"
			, "/actuator/**"
			,"/hr-worker/actuator/**"
			, "/hr-oauth/actuator/**" };
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		//Consegue ler o token
		resources.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// Configura as autorizações
		http.authorizeRequests()
		.antMatchers(PUBLIC).permitAll()
		.antMatchers(HttpMethod.GET, OPERATOR).hasAnyRole("OPERATOR", "ADMIN")
		.antMatchers(ADMIN).hasRole("ADMIN")
		.anyRequest().authenticated();
		
		http.cors().configurationSource(corsConfigurationSource());
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		//quais as origens que vai poder acessar o sistema
		corsConfig.setAllowedOrigins(Arrays.asList("*"));
		//quais os metodos http vou permitir
		corsConfig.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE","PATCH"));
		//vou permitir credenciais
		corsConfig.setAllowCredentials(true);
		//quais cabeçalhos vou permitir
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		//todos os caminhos
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
		
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean 
			= new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
	

}