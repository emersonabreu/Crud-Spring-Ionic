package com.cursomc.services.exceptions;

/**  Classe que retorna a Exceção na inclusão do arquivo
 */
public class FileException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public FileException(String msg) {
		super(msg);
	}
	
	public FileException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
