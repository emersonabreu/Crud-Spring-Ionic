package com.cursomc.services.exceptions;


/** Classe que Trata as exeções caso objeto da requisição vier vazio
 *  A manda uma Mensagem
 */
public class ObjectNotFoundException extends RuntimeException{
	private static final long SerialVersionUID =1L;
	
	
    
	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public ObjectNotFoundException(String message) {
		super(message);
		
	}
	
	


}
