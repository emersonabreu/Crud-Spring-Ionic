package com.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;

import com.cursomc.domain.Categoria;



/** Classe Usada quando precisar trazer só as categorias que estão no banco.
 * Como a model Categoria está relacionada com produtos toda vez que for listada atravéz dela
 * serão trazidos todos os dados relacionados a ela. A Classe CategoriaDTO resolve esse problema.
 */
public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	/** Validando os campos com hibernate.validator
	 */
	@NotEmpty(message="Campo Obrigatorio")
	@Length(min=5,max=80)
	private String nome;
	public CategoriaDTO() {
		
		super();
	}
	
	
    	
	public CategoriaDTO(Categoria categoria ) {
		super();
		this.id = categoria.getId();
		this.nome = categoria.getNome();
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
	
	
	
}