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
import com.mitocode.model.Medico;
import com.mitocode.service.IMedicoService;

import jakarta.validation.Valid;

/**
 * Para consumir un servicio desde el mismo java se utiliza RestTemplate
 * Medainte peticiones http , como si fuese POSTMAN 
 */

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private IMedicoService service;
	
	@GetMapping
	public ResponseEntity<List<Medico>> listar() throws Exception {
		List<Medico> Medicos = service.listar();
		return new ResponseEntity<List<Medico>>(Medicos, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Medico> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Medico Medico = service.listarPorId(id);
		if(Medico == null) {
			throw new ModelNotFounException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Medico>(Medico, HttpStatus.OK);
	}
	
	/** Modelo de Madurez de Richardzon nivel 3
	 *  El body agrega indormacion de como se obtuvo el registro dela peticion
	 *  
	 * @param id
	 * @return 
	 */
	@GetMapping("/hateoas/{id}")
	public EntityModel<Medico> listarPorIdHateoas(@PathVariable("id") Integer id) throws Exception {
		Medico Medico = service.listarPorId(id);
		if(Medico.getIdMedico() == null) {
			throw new ModelNotFounException("ID NO ENCONTRADO " + id);
		}
		
		// localhost:8080/Medicos/{id}
		EntityModel<Medico> recurso = EntityModel.of(Medico);

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));

		recurso.add(linkTo.withRel("Medico-recurso"));

		return recurso;
	}
	
/*	@PostMapping
	public ResponseEntity<Medico> registrar(@Valid @RequestBody Medico Medico) {
		Medico MedicoRet = service.registrar(Medico);
		return new ResponseEntity<Medico>(MedicoRet, HttpStatus.CREATED);
	} */
	
	/******* Modelo de Madurez de Richardzon nivel 2 **********************************
	 * En la respuesta del POSTMAN Headers/Location = http://localhost:8080/Medicos/5
	 * @param Medico
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Medico> registrar(@Valid @RequestBody Medico Medico) throws Exception {
		Medico MedicoRet = service.registrar(Medico);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(MedicoRet.getIdMedico()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Medico> modificar(@Valid @RequestBody Medico Medico) throws Exception {
		Medico MedicoRet = service.modificar(Medico);
		return new ResponseEntity<Medico>(MedicoRet, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Medico Medico = service.listarPorId(id);
		if(Medico == null) {
			throw new ModelNotFounException("EL REGISTRO CON ID "+id+" QUE QUIERES ELIMINAR, NO SE ENCONTRO");
		}
		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	

}
