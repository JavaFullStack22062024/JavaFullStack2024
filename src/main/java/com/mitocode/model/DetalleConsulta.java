package com.mitocode.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**	JSON para agregar datos desde POSTMAN
 		{
			"paciente" : {
				"idPaciente" : 1
			},
			"medico" : {
				"idMedico" : 1
			},
			"especialidad" : {
				"idEspecialidad" : 1
			},
			"numConsultorio" : "C1",
			"fecha" : "2022-06-25T12:08:00.000Z",
			"detalleConsulta" : [
				{"diagnostico" : "FIEBRE", "tratamiento" : "PARACETAMOL"},
				{"diagnostico" : "AMIGDALITIS", "tratamiento" : "ANTIBIOTICOS"}
			]
		}
 */

@Entity
@Table(name = "detalle_consulta")
public class DetalleConsulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDetalle;
	
//	Ignorara el atributo en Json mas no hacia JPA Para evitar el ciclo: Consulta-->DetalleConsulta
//	Para insertar el registro Consulta y consutaDetalle al mismo tiempo
//	Referencia curso: Spring Hateoas, Refactoring, Transacciones en Spring  1:10:00
	@JsonIgnore	
	@ManyToOne
	@JoinColumn(name = "id_consulta", nullable = false, foreignKey = @ForeignKey(name = "FK_consulta_detalle_consulta"))
	private  Consulta consulta; //Este atributo hace el match con el campo 
	
	@Column(name = "diagnostico", nullable = false, length = 70)
	private String diagnostico;
	
	@Column(name = "tratamiento", nullable = false, length = 300)
	private String tratamiento;

	public Integer getIdDealle() {
		return idDetalle;
	}

	public void setIdDealle(Integer idDealle) {
		this.idDetalle = idDealle;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}
	
}
