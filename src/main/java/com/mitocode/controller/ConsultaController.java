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

import com.mitocode.dto.ConsultaListaExamenDTO;
import com.mitocode.exception.ModelNotFounException;
import com.mitocode.model.Consulta;
import com.mitocode.service.IConsultaService;

import jakarta.validation.Valid;

/**
 * Para consumir un servicio desde el mismo java se utiliza RestTemplate
 * Medainte peticiones http , como si fuese POSTMAN 
 */

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
	
	@Autowired
	private IConsultaService service;
	
	@GetMapping
	public ResponseEntity<List<Consulta>> listar() throws Exception {
		List<Consulta> consultas = service.listar();
		return new ResponseEntity<List<Consulta>>(consultas, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Consulta> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Consulta consulta = service.listarPorId(id);
		if(consulta == null) {
			throw new ModelNotFounException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Consulta>(consulta, HttpStatus.OK);
	}
	
	/** Modelo de Madurez de Richardzon nivel 3
	 *  El body agrega indormacion de como se obtuvo el registro dela peticion
	 *  
	 * @param id
	 * @return 
	 */
	@GetMapping("/hateoas/{id}")
	public EntityModel<Consulta> listarPorIdHateoas(@PathVariable("id") Integer id) throws Exception {
		Consulta consulta = service.listarPorId(id);
		if(consulta.getIdConsulta() == null) {
			throw new ModelNotFounException("ID NO ENCONTRADO " + id);
		}
		
		// localhost:8080/pacientes/{id}
		EntityModel<Consulta> recurso = EntityModel.of(consulta);

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));

		recurso.add(linkTo.withRel("paciente-recurso"));

		return recurso;
	}
	
/*	@PostMapping
	public ResponseEntity<Consulta> registrar(@Valid @RequestBody Consulta paciente) {
		Consulta pacienteRet = service.registrar(paciente);
		return new ResponseEntity<Consulta>(pacienteRet, HttpStatus.CREATED);
	} */
	
	/******* Modelo de Madurez de Richardzon nivel 2 **********************************
	 * En la respuesta del POSTMAN Headers/Location = http://localhost:8080/pacientes/5
	 */
	@PostMapping
	public ResponseEntity<Consulta> registrar(@Valid @RequestBody ConsultaListaExamenDTO dto) throws Exception {
		Consulta consultaRet = service.registrarTransaccional(dto);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(consultaRet.getIdConsulta()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Consulta> modificar(@Valid @RequestBody Consulta paciente) throws Exception {
		Consulta consultaRet = service.modificar(paciente);
		return new ResponseEntity<Consulta>(consultaRet, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Consulta consulta = service.listarPorId(id);
		if(consulta == null) {
			throw new ModelNotFounException("EL REGISTRO CON ID "+id+" QUE QUIERES ELIMINAR, NO SE ENCONTRO");
		}
		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	

}
