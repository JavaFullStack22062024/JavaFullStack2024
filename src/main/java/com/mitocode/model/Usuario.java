package com.mitocode.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	private Integer idUsuario;
	
	@Column(name = "nombre", nullable = false, unique = true)
	private String nombre;
	
	@Column(name = "clave", nullable = false)
	private String clave;
	
	@Column(name = "estado", nullable = false)
	private boolean enabled;
	
	@ManyToMany(fetch = FetchType.EAGER) // por defecto es LAZY 
	@JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name= "id_usuario", referencedColumnName = "idUsuario"), 
									 inverseJoinColumns = @JoinColumn(name= "id_rol", referencedColumnName = "idRol"),
									 uniqueConstraints = @UniqueConstraint(columnNames = {"id_usuario", "id_rol"}))
	private List<Rol> roles;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	
}
