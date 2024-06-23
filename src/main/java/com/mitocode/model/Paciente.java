package com.mitocode.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 	 NOTAS:
 * - No utilizara LOMBOK para poder ver las relaciones de multiplicidad.
 * - Si el nombre del campo es nombreCompleto y no se tiene @Column, el campo se generara como nombre_completo
 * - En las clases del modelo por lo general implementaban serializable eso era en java enterprice, pero con jpas ya no es necesario
 * 
 *   Para que las validaciones se activen, en los endpoints - metodos del controller se debe agregar @Valid
 *   https://www.baeldung.com/java-validation	| Javax Validation Constraints
 */

@Schema(description = "Paciente Model") //Para la documentacion en Swagger
@Entity
//@Table(name = "tbl_paciente")
public class Paciente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPaciente;
	
	@Schema(description = "Nombres del Paciente") //Para la documentacion en Swagger 
	@Size(min = 3, message = "{nombres.size}")
	@Column(name = "nombres", nullable = false, length = 70)
	private String nombres;
	
	@NotNull
	@Size(min = 3, message = "{apellidos.size}")
	@Column(name = "apellidos", nullable = false, length = 70)
	private String apellidos;
	
	@Size(min = 8, max = 8, message = "DNI debe tener 8 caracteres")
	@Column(name = "dni", nullable = false, length = 8, unique = true)
	private String dni;
	
	@Size(min = 3, max = 150, message = "Direccion debe tener minimo 3 caracteres")
	@Column(name = "direccion", nullable = true, length = 150)
	private String direccion;
	
	@Size(min = 10, max = 10, message = "Telefono debe tener 10 caracteres")
	@Column(name = "telefono", nullable = true, length = 10)
	private String telefono;
	
//	@Pattern(regexp = "expReg")
	@Email(message = "Email no cuple con el formato de correo electronico: correo@sitio.com")
	@Column(name = "email", nullable = false, length = 55)
	private String email;
	
//	@Size Aplica solo a variables del tipo String
//	@Max, @Min Aplica a variables del tipo Integer o int

	public Integer getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Integer idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
