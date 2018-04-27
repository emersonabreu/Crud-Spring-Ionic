package com.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Classe que gera o token
 */
@Component
public class JWTUtil {
	
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;
	
	/**
	 * Método que constroi o token para o usuario
	 * baseado no tempo de expiração e passando o algoritmo que vai assinar o token e o segredo que
	 * foi criado na  application.properties
	 */
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
	
	/** Classe que verifica se o token é válido 
	 * 
	 ***/
	public boolean tokenValido(String token) {
		/** Armazena os dados que vieram no token
		 ***/
		Claims claims = getClaims(token);
		if (claims != null) {
			/** Pega o usuario e a data de expiração dele
			 ***/
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	
	/** Retorna o usuario a partir do token
	 ***/
	public String getUsername(String token) {
		/** O Objeto Claims recebe algumas coisas que vieram do token
		 ***/
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}
	
	/** Retorna o token a partir do segredo em forma de Claims
	 ***/
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}
		catch (Exception e) {
			return null;
		}
	}
	
}
