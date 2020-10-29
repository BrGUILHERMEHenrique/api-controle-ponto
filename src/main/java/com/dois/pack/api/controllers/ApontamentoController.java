package com.dois.pack.api.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dois.pack.api.models.Apontamento;
import com.dois.pack.api.models.ModelObjGet;
import com.dois.pack.api.repositorys.ApontamentoRepository;
import com.dois.pack.api.services.ApontamentoService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({ "/apontamento" })
public class ApontamentoController {

	@Autowired
	ApontamentoService apontamentoService;

	@ApiOperation("Permite Cadastrar um novo apontamento")
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, 
				produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Apontamento> create(@RequestBody Apontamento apontamento) {
		return ResponseEntity.status(HttpStatus.CREATED).body(apontamentoService.create(apontamento));
	}
	
	@ApiOperation("Retorna uma lista de apontamentos baseando-se no id de funcionário e as datas informadas")
	@GetMapping(path="/idDate", consumes = { MediaType.APPLICATION_JSON_VALUE }, 
				produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Apontamento>> getByIdFuncionarioAndDate(@RequestBody ModelObjGet modelObj){
		return ResponseEntity.ok(apontamentoService.getByIdFuncionarioAndDate(modelObj));
	}
	
	@ApiOperation("Retorna uma lista de apontamentos baseando-se no id de funcionário")
	@GetMapping(path="/idFuncionario/{idFuncionario}", 
					produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Apontamento>> getByIdFuncionario(@PathVariable Integer idFuncionario){
		return ResponseEntity.ok(apontamentoService.getByIdFuncionario(idFuncionario));
	}

	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE },
				produces = {MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Apontamento> update(@RequestBody Apontamento apontamento, @PathVariable Integer id) {
		return ResponseEntity.status(HttpStatus.OK).body(apontamentoService.update(apontamento, id));
	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Apontamento> getById(@PathVariable Integer id) {
		return ResponseEntity.ok(apontamentoService.getById(id));
	}

	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Apontamento>> getAll() {
		return ResponseEntity.ok(apontamentoService.getAll());
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		if (apontamentoService.delete(id)) {
			return ResponseEntity.ok("Apontamento apagado com sucesso");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
