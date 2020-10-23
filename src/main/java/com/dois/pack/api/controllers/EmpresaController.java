package com.dois.pack.api.controllers;

import java.util.List;

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
	public ResponseEntity<List<Empresa>> getAll() {
		return ResponseEntity.ok(empresaService.getAll());
	}

	@ApiOperation("Retorna uma empresa baseada em seu id")
	@GetMapping(path="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE} )
	public ResponseEntity<Empresa> get(@PathVariable Integer id) {
		Empresa empresa = empresaService.getbyId(id).get();
		return ResponseEntity.ok(empresa);
	}
	
	@ApiOperation("Retorna uma empresa baseada em seu CNPJ")
	@GetMapping(path="cnpj/{cnpj}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Empresa> getByCnpj(@PathVariable String cnpj){
		return ResponseEntity.ok(empresaService.getByCnpj(cnpj));
	}

	@ApiOperation("Permite cadastrar uma nova empresa")
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
						produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Empresa> create(@Valid @RequestBody Empresa empresa) throws SameCnpjException {
		return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.create(empresa));
	}

	@ApiOperation("Permite atualizar uma empresa já existente, baseando-se em seu id")
	@PutMapping(path="/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE},
									produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Empresa> put(@PathVariable Integer id, @RequestBody Empresa empresa) {
		Empresa empresaAtualizada = empresaService.update(id, empresa);
		return ResponseEntity.ok(empresaAtualizada);
	}

	@ApiOperation("Permite apagar uma empresa, baseando-se em seu id")
	@DeleteMapping(path="/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		boolean response = empresaService.delete(id);
		if(response) {		
			return ResponseEntity.ok("Empresa apagada com sucesso.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa não encontrada");
		}
	}
}
