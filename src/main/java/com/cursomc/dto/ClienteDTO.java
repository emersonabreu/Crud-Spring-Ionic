package com.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;

import com.cursomc.domain.Cliente;
import com.cursomc.services.validation.ClienteInsert;
import com.cursomc.services.validation.ClienteUpdate;




/** Classe Usada quando precisar trazer só os clientes que estão no banco.
 * Como a model Cliente está relacionada com uma Lista de Enderecos, Telefone e a Enum TipoCliente:Fisica ou Juridica
 *  atravéz da ClienteDTO são trazidos só os dados do Cliente. A Classe ClienteDTO resolve esse problema.
 * OBS: A anotação @ClienteInsert, chama a interface que aciona a classe ClienteUpdateValidator que faz a validação
 */
@ClienteUpdate
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	/** Validando os campos com javax.validation.validator
	 */
	@NotEmpty(message="O Campo Nome é Obrigatorio")
	@Length(min=5,max=80)
	private String nome;
	@NotEmpty(message="O Campo email é Obrigatorio")
	@Length(min=5,max=80)
	private String email;
	public ClienteDTO() {
		
		super();
	}
	
	
    	
	public ClienteDTO(Cliente cliente ) {
		super();
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email= cliente.getEmail();
	
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



		
	
	
}