package com.cursomc.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import com.cursomc.domain.enums.EstadoPagamento;

	/** Obs: Essa Classe é abstrata pra garantir que toda vez que for chamada tenha que Instanciar com o /new 
	 * e passar o tipo de PagamentoComBoleto ou PagamentoComCartao.
	* Classe Pagamento que recebe um enum estadoPagamento no construtor
	*  Como ela é uma SuperClasse de PagamentoComBoleto e PagamentoComCartao
	*  o @Inheritance(strategy=InheritanceType.JOINED) fala que as 
	*  tabelas PagamentoComBoleto e PagamentoComCartao vão ser criados separadamente.
	*  Se fosse escolhido o (strategy=InheritanceType.SINGLE_TABLE), iria criar uma unica 
	*  tabela contendo todos os campos de PagamentoComBoleto e PagamentoComCartao.
	*  Ex: Se fosse escolhido o PagamentoComBoleto, todos os dados de PagamentoComCartao iram ser 
	*  gerados nulos/null. 
	*/
	@Entity
	@Inheritance(strategy=InheritanceType.JOINED)
	public abstract class Pagamento implements Serializable {
			
		private static final long serialVersionUID = 1L;
		
		/**
		 * Não precisa gerar o @GeneratedValue(strategy=GenerationType.IDENTITY)
		 * Pois o id do pagamento é o mesmo do pedido ou seja herda o id de Pedido
		 * O @MapsId é o responsável por fazer isso
		 */
		@Id
		private Integer id;	
		private int estadoPagamento;
		
		@OneToOne
		@JoinColumn(name = "pedido_id")
		@MapsId
		private Pedido pedido;
		
		public Pagamento() {

		}

		public Pagamento(Integer id, EstadoPagamento estadoPagamento, Pedido pedido) {
			super();
			this.id = id;
			this.estadoPagamento = estadoPagamento.getCod();
			this.pedido = pedido;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public int getEstadoPagamento() {
			return estadoPagamento;
		}

		public void setEstadoPagamento(EstadoPagamento estadoPagamento) {
			this.estadoPagamento = estadoPagamento.getCod();
		}

		public Pedido getPedido() {
			return pedido;
		}

		public void setPedido(Pedido pedido) {
			this.pedido = pedido;
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
			Pagamento other = (Pagamento) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}



		
		
		
		}
