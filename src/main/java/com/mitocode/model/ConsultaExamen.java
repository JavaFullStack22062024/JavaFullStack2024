package com.mitocode.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * https://www.objectdb.com/api/java/jpa/annotations
 * Esta una tabla de relacion ManyToMany, 
 * por eso se cera esta tabla intermedia 
 */
/** JSON para incertar datos desde POSTMAN - Representada por el Objeto DTO
 	{
		    "consulta" : {
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
		    },
		    "listExamen" : [
		        {"idExamen" : 1},
		        {"idExamen" : 2}
		    ]
		}
 
 */

@Entity
@Table(name = "consulta_examen")
@IdClass(ConsultaExamenPK.class)	// Para indicar donde se encuentran las definiciones de las llaves foraneas
public class ConsultaExamen {
	
//	Lo mas comun seria tener esto: 
//	@Id
//	private Integer idConsulta;
//	@Id
//	private Integer idExamen;
	
//	Aun mas comun seria utilizar la notacion @ManyToMAny
//	pero se esta haciendo asi para conocer el proceso manual.

//	@EmbeddedId tambien se usaria en estos casos de muchos a muchos
	
	@Id
	private Consulta consulta;
	@Id
	private Examen examen;
	
	
	public Consulta getConsulta() {
		return consulta;
	}
	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}
	public Examen getExamen() {
		return examen;
	}
	public void setExamen(Examen examen) {
		this.examen = examen;
	}

}
