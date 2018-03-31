package com.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.cursomc.domain.enums.EstadoPagamento;
/**
 * SubClasse PagamentoComCartao Herda todos os atributos e métodos da Super Classe Pagamento, 
 */

@Entity
public class PagamentoComBoleto extends Pagamento {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date dataVencimento;
	private Date dataPagamento;
	
	
	public PagamentoComBoleto() {
		
	}


	/**
	 * Construtor com Parametros da Super Classe Pagamento: Integer id, EstadoPagamento estadoPagamento
	 * Pedido pedido. Como Pagamento recebe um enum EstadoPagamento estadoPagamento e recebe um Pedido
	 * @param dataVencimento 
	 * @param dataPagamento 
	 */
	public PagamentoComBoleto(Integer id, EstadoPagamento estadoPagamento, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, estadoPagamento, pedido);
		this.dataVencimento=dataVencimento;
		this.dataPagamento=dataPagamento;
		
	}


	public Date getDataVencimento() {
		return dataVencimento;
	}


	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}


	public Date getDataPagamento() {
		return dataPagamento;
	}


	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	
	
}
