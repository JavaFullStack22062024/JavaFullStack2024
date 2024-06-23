package com.mitocode.exception;


//@ResponseStatus(HttpStatus.NOT_FOUND) --> YA se definio la Excepcion personalizada.
@SuppressWarnings("serial")
public class ModelNotFounException extends RuntimeException {
	
	public ModelNotFounException(String mensaje) {
		super(mensaje);
	}
	
}
