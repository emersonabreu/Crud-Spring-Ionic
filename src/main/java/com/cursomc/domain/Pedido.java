package com.cursomc.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

	/** 
	* Classe Pedido que recebe um Pagamento
	*  
	*/
	@Entity
	public class Pedido implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
		private Integer id;	
		private Date Instante;
		
		/**
		 * Faz o Mapeamento bidirecional garantindo que o id do pagamento vai ser o mesmo 
		 * que o id do pedido
		 */
		@OneToOne(cascade=CascadeType.ALL,mappedBy="pedido")
		private Pagamento Pagamento;	
		
		
		/** 
		 * Relacionamento de 1 pra Muitos de pedido pra cliente O pedido está ligado
		 * a 1 cliente Usa o @JsonBackReference falando que já trouxe as referencias 
		 * Para evitar a referencia ciclica
		 */
		@JsonBackReference
		@ManyToOne
		@JoinColumn(name = "cliente_id")
		private Cliente cliente;
		
		@ManyToOne
		@JoinColumn(name = "enderecoDeEntrega_id")
		private Endereco enderecoDeEntrega;

		public Pedido() {
			
		}
		
		

		public Pedido(Integer id, Date instante, Cliente cliente,
				Endereco enderecoDeEntrega) {
			super();
			this.id = id;
			Instante = instante;
			this.cliente = cliente;
			this.enderecoDeEntrega = enderecoDeEntrega;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Date getInstante() {
			return Instante;
		}

		public void setInstante(Date instante) {
			Instante = instante;
		}

		public Pagamento getPagamento() {
			return Pagamento;
		}

		public void setPagamento(Pagamento pagamento) {
			Pagamento = pagamento;
		}

		public Cliente getCliente() {
			return cliente;
		}

		public void setCliente(Cliente cliente) {
			this.cliente = cliente;
		}

		public Endereco getEnderecoDeEntrega() {
			return enderecoDeEntrega;
		}

		public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
			this.enderecoDeEntrega = enderecoDeEntrega;
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
			Pedido other = (Pedido) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}

		
		
		
		
		}
