package com.mitocode.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable		//Para que las definiciones de las FK's puedan ser eferenciadas por las PK en ConsultaExamen
public class ConsultaExamenPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
//	DEfinicion de llaves Foraneas
	
	@ManyToOne
	@JoinColumn(name = "id_consulta", nullable = false)
	private Consulta consulta;
	
	@ManyToOne
	@JoinColumn(name = "id_examen", nullable = false)
	private Examen examen;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consulta == null) ? 0 : consulta.hashCode());
		result = prime * result + ((examen == null) ? 0 : examen.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConsultaExamenPK other = (ConsultaExamenPK) obj;
		if (consulta == null) {
			if (other.consulta != null)
				return false;
		} else if (!consulta.equals(other.consulta))
			return false;
		if (examen == null) {
			if (other.examen != null)
				return false;
		} else if (!examen.equals(other.examen))
			return false;
		return true;
	}
	

}
