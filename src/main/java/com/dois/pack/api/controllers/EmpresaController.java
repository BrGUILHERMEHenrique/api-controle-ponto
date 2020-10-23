package com.dois.pack.api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.dois.pack.api.exceptions.SameCnpjException;
import com.dois.pack.api.models.Empresa;
import com.dois.pack.api.services.EmpresaService;

@RestController
@RequestMapping({ "/empresa" })
public class EmpresaController {

	@Autowired
	EmpresaService empresaService;

	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(empresaService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable Integer id) {
		return ResponseEntity.ok(empresaService.getbyId(id));
	}
	
	@GetMapping("cnpj/{cnpj}")
	public ResponseEntity<?> getByCnpj(@PathVariable String cnpj){
		return ResponseEntity.ok(empresaService.getByCnpj(cnpj));
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Empresa empresa) throws SameCnpjException {
		return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.create(empresa));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable Integer id, @RequestBody Empresa empresa) {
		Empresa empresaAtualizada = empresaService.update(id, empresa);
		return ResponseEntity.ok(empresaAtualizada);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		boolean response = empresaService.delete(id);
		if(response) {		
			return ResponseEntity.ok("Empresa apagada com sucesso.");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
