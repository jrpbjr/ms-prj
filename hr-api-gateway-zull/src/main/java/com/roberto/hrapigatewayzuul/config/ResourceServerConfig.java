package com.roberto.hrapigatewayzuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

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
	private static final String[] ADMIN = { "/hr-payroll/**", "/hr-user/**" };
	
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
	}

}
