package com.mitocode.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.exception.ModelNotFounException;
import com.mitocode.model.Paciente;
import com.mitocode.service.IPacienteService;

import jakarta.validation.Valid;

/**
 * Para consumir un servicio desde el mismo java se utiliza RestTemplate
 * Medainte peticiones http , como si fuese POSTMAN 
 */

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
	
	@Autowired
	private IPacienteService service;
	
	@GetMapping
	public ResponseEntity<List<Paciente>> listar() throws Exception {
		List<Paciente> pacientes = service.listar();
		return new ResponseEntity<List<Paciente>>(pacientes, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Paciente> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Paciente paciente = service.listarPorId(id);
		if(paciente == null) {
			throw new ModelNotFounException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Paciente>(paciente, HttpStatus.OK);
	}
	
	/** Modelo de Madurez de Richardzon nivel 3
	 *  El body agrega indormacion de como se obtuvo el registro dela peticion
	 *  
	 * @param id
	 * @return 
	 */
	@GetMapping("/hateoas/{id}")
	public EntityModel<Paciente> listarPorIdHateoas(@PathVariable("id") Integer id) throws Exception {
		Paciente paciente = service.listarPorId(id);
		if(paciente.getIdPaciente() == null) {
			throw new ModelNotFounException("ID NO ENCONTRADO " + id);
		}
		
		// localhost:8080/pacientes/{id}
		EntityModel<Paciente> recurso = EntityModel.of(paciente);

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));

		recurso.add(linkTo.withRel("paciente-recurso"));

		return recurso;
	}
	
/*	@PostMapping
	public ResponseEntity<Paciente> registrar(@Valid @RequestBody Paciente paciente) {
		Paciente pacienteRet = service.registrar(paciente);
		return new ResponseEntity<Paciente>(pacienteRet, HttpStatus.CREATED);
	} */
	
	/******* Modelo de Madurez de Richardzon nivel 2 **********************************
	 * En la respuesta del POSTMAN Headers/Location = http://localhost:8080/pacientes/5
	 * @param paciente
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Paciente> registrar(@Valid @RequestBody Paciente paciente) throws Exception {
		Paciente pacienteRet = service.registrar(paciente);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pacienteRet.getIdPaciente()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Paciente> modificar(@Valid @RequestBody Paciente paciente) throws Exception {
		Paciente pacienteRet = service.modificar(paciente);
		return new ResponseEntity<Paciente>(pacienteRet, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Paciente paciente = service.listarPorId(id);
		if(paciente == null) {
			throw new ModelNotFounException("EL REGISTRO CON ID "+id+" QUE QUIERES ELIMINAR, NO SE ENCONTRO");
		}
		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	

}
