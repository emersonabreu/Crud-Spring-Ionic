package com.cursomc.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.cursomc.domain.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/** 
* Classe Cliente que recebe um enum tipocliente no construtor
*  
*/
@Entity
public class Cliente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String email;
	private String cpfOuCnpj;
	private int tipo;
	
	
	/**
	 * Relacionamento de Muitos pra 1 de Cliente pra Endereco.
	 * Um Cliente tem vários enderecos e proteçao contra serialização ciclica @JsonManagedReference
	 */
	@JsonManagedReference
    @OneToMany(mappedBy="cliente")
	private List<Pedido> enderecos=new ArrayList<>();
	
	
	/**
	 * Relacionamento de Muitos pra 1 de Cliente pra Pedido.
	 * Um Cliente pode ter vários pedidos e 
	 * proteçao contra serialização ciclica @JsonManagedReference
	 */
	@JsonManagedReference
    @OneToMany(mappedBy="cliente")
	private List<Pedido> pedidos=new ArrayList<>();
    
    
    /**
	 * Cria uma Entidade Fraca telefone no banco com a JPA
	 */
    @ElementCollection
    @CollectionTable(name="telefone")
    private Set<String>telefones=new HashSet<>();

	public Cliente() {
		
	}
	


	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = tipo.getCod();
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}



	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}



	public int getTipo() {
		return tipo;
	}



	public void setTipo(int tipo) {
		this.tipo = tipo;
	}



	public List<Pedido> getEnderecos() {
		return enderecos;
	}



	public void setEnderecos(List<Pedido> enderecos) {
		this.enderecos = enderecos;
	}



	public List<Pedido> getPedidos() {
		return pedidos;
	}



	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}



	public Set<String> getTelefones() {
		return telefones;
	}



	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	

}
