package com.dois.pack.api.controllers;

import javax.validation.Valid;

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

import com.dois.pack.api.exceptions.SameCnpjException;
import com.dois.pack.api.models.Empresa;
import com.dois.pack.api.services.EmpresaService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({ "/empresa" })
public class EmpresaController {

	@Autowired
	EmpresaService empresaService;

	@ApiOperation("Retorna uma lista com todas as empresas cadastradas")
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE} )
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(empresaService.getAll());
	}

	@ApiOperation("Retorna uma empresa baseada em seu id")
	@GetMapping(path="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE} )
	public ResponseEntity<?> get(@PathVariable Integer id) {
		return ResponseEntity.ok(empresaService.getbyId(id));
	}
	
	@GetMapping(path="cnpj/{cnpj}")
	public ResponseEntity<?> getByCnpj(@PathVariable String cnpj){
		return ResponseEntity.ok(empresaService.getByCnpj(cnpj));
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Empresa empresa) throws SameCnpjException {
		return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.create(empresa));
	}

	@PutMapping(path="/{id}")
	public ResponseEntity<?> put(@PathVariable Integer id, @RequestBody Empresa empresa) {
		Empresa empresaAtualizada = empresaService.update(id, empresa);
		return ResponseEntity.ok(empresaAtualizada);
	}

	@DeleteMapping(path="/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		boolean response = empresaService.delete(id);
		if(response) {		
			return ResponseEntity.ok("Empresa apagada com sucesso.");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
