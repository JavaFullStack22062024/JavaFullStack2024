package com.mitocode.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "consulta")
public class Consulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idConsulta;
	
//	JPQL --> Orientado a Objetos --> Objeto.atributo  "se ahorra los innerjoin" --> Consulta c WHERE c.paciente.nombres = ''
//	private Integer idPaciente; En este caso seria con innerjoin porque no serian objetos.
//	CASO * --> 1  | Many to One |  Un Paciente puede tener muchas consultas.
	
//	@OneToMany se usa para el caso de maestro-detalle. 
//	Ademas se asocia a una lista ejemplo en poniendo esto en la clase Paciente 
//	@OneToMany
//	private List<Consulta> Consulta
//	Referencia Curso | Spring DATA JPA (Mapeo de tablas) - 23:37
	
	@ManyToOne	//Para hacer referencia a llaves foraneas FK.
//	No hace referencia a la columna id_paciente de Paciente.
//	mas bien se crea una columna id_paciente en esta tabla de consulta.
//	Esta sera la llave Foranea, por eso no acepta nulos.
//	Como las FK se relacionan con las PK no es necesario utilizar 'referencedColumnName'
	@JoinColumn(name = "id_paciente", nullable = false, foreignKey = @ForeignKey(name = "FK_consulta_paciente"))
	private Paciente paciente;
	
	@ManyToOne
	@JoinColumn(name = "id_medico", nullable = false, foreignKey = @ForeignKey(name = "FK_consulta_medico"))
	private  Medico medico;
	
	@ManyToOne
	@JoinColumn(name = "id_especialidad", nullable = false, foreignKey = @ForeignKey(name = "FK_consulta_especialidad"))
	private  Especialidad especialidad;
	
	@Column(name = "num_consultorio", length = 3, nullable = true)
	private  String numConsultorio;
	
//	Antes de Java8
//	@Temporal(TemporalType.DATE)
//	@Column(name = "fecha")
//	Ademas SpringBoot 2 trabaja con Java8 y posteriores por lo tanto asume que se tiene que usa LocalDateTime o LocalDate
//	Para SpringBoot 1.5 hay que agregar la dependencia jsr 310 para usar LocalDateTiem o LocalDate
//	LocalDateTime usa el formato  ISODate --> YYYY-MM-DDTHH:MM:SS  (año-mes-diaThora:minutos:segundos) 
	@Column(name = "fecha", nullable = false)
	private  LocalDateTime fecha;	// A parti de java8
	
//	En mappedBy = "consulta" --> no hace referencia a la entidad de la clase consulta mas bien
//	es el atributo llamado consulta que enlaza en la otra tabla de DetalleConsulta
//	CascadeType.ALL. Si se elimina el registro en consulta se eliminan todos los registros dependientes en detalle consulta
//	orphanRemoval = true. Para permitir eliminar registros de la tabla detalle_consulta
	@OneToMany(mappedBy = "consulta", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<DetalleConsulta> detalleConsulta;
	

	public Integer getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(Integer idConsulta) {
		this.idConsulta = idConsulta;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public String getNumConsultorio() {
		return numConsultorio;
	}

	public void setNumConsultorio(String numConsultorio) {
		this.numConsultorio = numConsultorio;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public List<DetalleConsulta> getDetalleConsulta() {
		return detalleConsulta;
	}

	public void setDetalleConsulta(List<DetalleConsulta> detalleConsulta) {
		this.detalleConsulta = detalleConsulta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idConsulta == null) ? 0 : idConsulta.hashCode());
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
		Consulta other = (Consulta) obj;
		if (idConsulta == null) {
			if (other.idConsulta != null)
				return false;
		} else if (!idConsulta.equals(other.idConsulta))
			return false;
		return true;
	}
	
	
	
}
