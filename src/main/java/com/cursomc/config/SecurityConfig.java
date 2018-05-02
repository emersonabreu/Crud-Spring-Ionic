package com.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cursomc.security.JWTAuthenticationFilter;
import com.cursomc.security.JWTAuthorizationFilter;
import com.cursomc.security.JWTUtil;


/** Classe configuração de Segurança
 * OBS: Toda vez que faz o login essa classe é utilizada gerando um token novo
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private Environment env;
	
	/** Classe que autentica o usuario
	 */
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	/** Variavel que armazena os caminhos que serão liberados
	 */
	private static final String[] PUBLIC_MATCHERS = {
			"/mysql-console/**"
	};

	/** EndPoints GETS que serão Liberados
	 */
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**"
			
	};

	/** A Pessoa que não é Cliente poderá se Cadastrar no banco
	 */
	private static final String[] PUBLIC_MATCHERS_POST = {
		"/clientes/**",
		"/clientes/picture",
		"/auth/forgot/**"
			
	};
	
	/** Metodo que permite a liberação dos caminhos e dos EndPoints 
	 * que serão autorizados a ser acessados
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
            http.headers().frameOptions().disable();
        }
		
		http.cors().and().csrf().disable();
		http.authorizeRequests()
		    .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated();
		/** Faz a autenticação do usuario
		 */
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		/** Faz a autorização do usuario
		 */
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	
	
	/** Disponibiliza o Bean que configura o basico pra aplicação
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	/** Disponibiliza o Bean que criptografa a senha 
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/** Metodo que sobrescreve o configure passando a autenticação 
	 * que recebe o userDetailsService e o tipo de Criptografia
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
}
