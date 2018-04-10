package com.cursomc.services.exceptions;


/** Classe que Trata as exeções caso objeto da requisição vier vazio
 *  A manda uma Mensagem
 */
public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}

