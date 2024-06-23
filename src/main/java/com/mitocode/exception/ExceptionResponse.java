package com.mitocode.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {
	
	private LocalDateTime fecha;
	private String mensaje;
	private String detalles;
	
	public ExceptionResponse() {}
	
	public ExceptionResponse(LocalDateTime now, String message, String description) {
//		super(String format(format: "%s no fue encontrado con: %s= '%s'", resourceName, fielname, fieldValue));
		super();
		this.fecha = now;
		this.mensaje = message;
		this.detalles = description;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getDetalles() {
		return detalles;
	}
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	
}
