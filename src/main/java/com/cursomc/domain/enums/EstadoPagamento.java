package com.cursomc.domain.enums;

/**
 *Cria a enumeração com Tres estados Pendente, Quitado ou Cancelado
 *Obs: Importante lembrar que na enumeração os atributos não mudam
 *por isso só é preciso criar os /gets/
 */
public enum EstadoPagamento {
	
	PENDENTE(1, "Pendente"), 
	QUITADO(2, "Quitado"),
	CANCELADO(2, "Cancelado");

	
	private int cod;
	private String descricao;
	
	
	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	/**
	 *Metodo que retorna o EstadoPagamento a partir do codigo.
	 *Percorre os valores da enumeração e verifica se o cod que veio é igual ao id da 
	 * PENDENTE(1, "Pendente"), QUITADO(2, "Quitado") ou CANCELADO(2, "Cancelado");
	 */
	public static EstadoPagamento toEnum(Integer cod) {

		if (cod == null) {
			return null;
		}
		for (EstadoPagamento x : EstadoPagamento.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido " + cod);
	}
	

	

}