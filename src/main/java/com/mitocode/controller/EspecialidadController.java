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
import com.mitocode.model.Especialidad;
import com.mitocode.service.IEspecialidadService;

import jakarta.validation.Valid;

/**
 * Para consumir un servicio desde el mismo java se utiliza RestTemplate
 * Medainte peticiones http , como si fuese POSTMAN 
 */

@RestController
@RequestMapping("/especialidades")
public class EspecialidadController {
	
	@Autowired
	private IEspecialidadService service;
	
	@GetMapping
	public ResponseEntity<List<Especialidad>> listar() throws Exception {
		List<Especialidad> Especialidads = service.listar();
		return new ResponseEntity<List<Especialidad>>(Especialidads, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Especialidad> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Especialidad Especialidad = service.listarPorId(id);
		if(Especialidad == null) {
			throw new ModelNotFounException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Especialidad>(Especialidad, HttpStatus.OK);
	}
	
	/** Modelo de Madurez de Richardzon nivel 3
	 *  El body agrega indormacion de como se obtuvo el registro dela peticion
	 *  
	 * @param id
	 * @return 
	 */
	@GetMapping("/hateoas/{id}")
	public EntityModel<Especialidad> listarPorIdHateoas(@PathVariable("id") Integer id) throws Exception {
		Especialidad Especialidad = service.listarPorId(id);
		if(Especialidad.getIdEspecialidad() == null) {
			throw new ModelNotFounException("ID NO ENCONTRADO " + id);
		}
		
		// localhost:8080/Especialidads/{id}
		EntityModel<Especialidad> recurso = EntityModel.of(Especialidad);

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));

		recurso.add(linkTo.withRel("Especialidad-recurso"));

		return recurso;
	}
	
/*	@PostMapping
	public ResponseEntity<Especialidad> registrar(@Valid @RequestBody Especialidad Especialidad) {
		Especialidad EspecialidadRet = service.registrar(Especialidad);
		return new ResponseEntity<Especialidad>(EspecialidadRet, HttpStatus.CREATED);
	} */
	
	/******* Modelo de Madurez de Richardzon nivel 2 **********************************
	 * En la respuesta del POSTMAN Headers/Location = http://localhost:8080/Especialidads/5
	 * @param Especialidad
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Especialidad> registrar(@Valid @RequestBody Especialidad Especialidad) throws Exception {
		Especialidad EspecialidadRet = service.registrar(Especialidad);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(EspecialidadRet.getIdEspecialidad()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Especialidad> modificar(@Valid @RequestBody Especialidad Especialidad) throws Exception {
		Especialidad EspecialidadRet = service.modificar(Especialidad);
		return new ResponseEntity<Especialidad>(EspecialidadRet, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Especialidad Especialidad = service.listarPorId(id);
		if(Especialidad == null) {
			throw new ModelNotFounException("EL REGISTRO CON ID "+id+" QUE QUIERES ELIMINAR, NO SE ENCONTRO");
		}
		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	

}
