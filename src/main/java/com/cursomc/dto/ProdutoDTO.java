package com.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Produto;



/** Classe Usada quando precisar trazer só as produtos que estão no banco.
 * Como a model Produto está relacionada com categorias toda vez que for listada atravéz dela
 * serão trazidos todos os dados relacionados a ela. A Classe ProdutoDTO resolve esse problema.
 */
public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	/** Validando os campos com javax.validation.validator
	 */
	@NotEmpty(message="Campo Obrigatorio")
	@Length(min=5,max=80)
	private String nome;
	private Double preco;
	public ProdutoDTO() {
		
		super();
	}
	public ProdutoDTO(Produto produto) {
		super();
		id =produto.getId();
		nome = produto.getNome();
		preco = produto.getPreco();
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
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	
	
    	
		
	
}