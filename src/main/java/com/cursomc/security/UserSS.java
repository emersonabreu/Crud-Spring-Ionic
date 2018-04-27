package com.cursomc.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.cursomc.domain.enums.Perfil;

/**
 * Classe que Implemente os metodos da UserDetails trazendo as credenciais de
 * autorização ???OBS: POSTERIORMENTE PODE SER ACRESCENTADO ALGUMA LOGICA NOS
 * MÉTODOS DESSA CLASSE
 */
public class UserSS implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String email;
	private String senha;

	/**
	 * São as autorizações dos usuarios
	 */
	private Collection<? extends GrantedAuthority> authorities;

	public UserSS() {
	}

	public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		/**
		 * Alista de authorities é gerada a partir dos perfis desse cliente EXEMPLO: SE
		 * ele tiver os perfis de ADMIM E CLIENTE Ele terá duas authorities
		 */
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao()))
				.collect(Collectors.toList());
	}

	public Integer getId() {
		return id;
	}

	/**
	 * Retorna as autorizações dos usuarios
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * Retorna a senha do usuario
	 */
	@Override
	public String getPassword() {
		return senha;
	}

	/**
	 * Retorna a email de login do usuario
	 */
	@Override
	public String getUsername() {
		return email;
	}

	/**
	 * Por padrão a conta não está expirada
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Por padrão a conta não está bloqueada
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Por padrão as credenciais não estão bloqueada
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Por padrão o usuario está ativo
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	/**
	 * Retorna as Autorizações do Usuário logado
	 */
	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}
	
}
