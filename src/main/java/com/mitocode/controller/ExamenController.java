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
import com.mitocode.model.Examen;
import com.mitocode.service.IExamenService;

import jakarta.validation.Valid;

/**
 * Para consumir un servicio desde el mismo java se utiliza RestTemplate
 * Medainte peticiones http , como si fuese POSTMAN 
 */

@RestController
@RequestMapping("/examenes")
public class ExamenController {
	
	@Autowired
	private IExamenService service;
	
	@GetMapping
	public ResponseEntity<List<Examen>> listar() throws Exception {
		List<Examen> Examens = service.listar();
		return new ResponseEntity<List<Examen>>(Examens, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Examen> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Examen Examen = service.listarPorId(id);
		if(Examen == null) {
			throw new ModelNotFounException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Examen>(Examen, HttpStatus.OK);
	}
	
	/** Modelo de Madurez de Richardzon nivel 3
	 *  El body agrega indormacion de como se obtuvo el registro dela peticion
	 *  
	 * @param id
	 * @return 
	 */
	@GetMapping("/hateoas/{id}")
	public EntityModel<Examen> listarPorIdHateoas(@PathVariable("id") Integer id) throws Exception {
		Examen Examen = service.listarPorId(id);
		if(Examen.getIdExamen() == null) {
			throw new ModelNotFounException("ID NO ENCONTRADO " + id);
		}
		
		// localhost:8080/Examens/{id}
		EntityModel<Examen> recurso = EntityModel.of(Examen);

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));

		recurso.add(linkTo.withRel("Examen-recurso"));

		return recurso;
	}
	
/*	@PostMapping
	public ResponseEntity<Examen> registrar(@Valid @RequestBody Examen Examen) {
		Examen ExamenRet = service.registrar(Examen);
		return new ResponseEntity<Examen>(ExamenRet, HttpStatus.CREATED);
	} */
	
	/******* Modelo de Madurez de Richardzon nivel 2 **********************************
	 * En la respuesta del POSTMAN Headers/Location = http://localhost:8080/Examens/5
	 * @param Examen
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Examen> registrar(@Valid @RequestBody Examen Examen) throws Exception {
		Examen ExamenRet = service.registrar(Examen);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ExamenRet.getIdExamen()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Examen> modificar(@Valid @RequestBody Examen Examen) throws Exception {
		Examen ExamenRet = service.modificar(Examen);
		return new ResponseEntity<Examen>(ExamenRet, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Examen Examen = service.listarPorId(id);
		if(Examen == null) {
			throw new ModelNotFounException("EL REGISTRO CON ID "+id+" QUE QUIERES ELIMINAR, NO SE ENCONTRO");
		}
		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	

}
