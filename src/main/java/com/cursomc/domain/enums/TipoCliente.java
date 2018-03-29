package com.cursomc.domain.enums;

/**
 *Cria a enumeração com Dois tipos Pessoa fisica e Pessoa Jurídica.
 *Obs: Importante lembrar que na enumeração os atributos não mudam
 *por isso só é preciso criar os /gets/
 */
public enum TipoCliente {
	
	PESSOAFISICA(1, "Pessoa Física"), 
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	private Integer tipo;
	
	private TipoCliente(int cod, String descricao) {
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
	 *Metodo que retorna o cliente a partir do codigo.
	 *Percorre os valores da enumeração e verifica se o cod que veio é igual ao id da 
	 * PESSOAFISICA(1, "Pessoa Física") ou PESSOAJURIDICA(2, "Pessoa Jurídica");
	 */
	public static TipoCliente toEnum(Integer cod) {

		if (cod == null) {
			return null;
		}
		for (TipoCliente x : TipoCliente.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido " + cod);
	}
	

	

	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}
}