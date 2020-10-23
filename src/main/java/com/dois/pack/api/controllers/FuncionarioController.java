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

import com.dois.pack.api.exceptions.SameCpfException;
import com.dois.pack.api.models.Funcionario;
import com.dois.pack.api.services.FuncionarioService;

@RestController
@RequestMapping({ "/funcionario" })
public class FuncionarioController {

	@Autowired
	FuncionarioService funcionarioService;

	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(funcionarioService.getAll());
	}
	
	@GetMapping("/inner")
	public ResponseEntity<?> getInner(){
		return ResponseEntity.ok(funcionarioService.getInnerJoin());
	}
	
	@GetMapping("/cod/{codMatricula}")
	public Funcionario getMatricula(@PathVariable String codMatricula){
		return funcionarioService.getByMatricula(codMatricula);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable Integer id) {
		return ResponseEntity.ok(funcionarioService.getbyId(id));
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Funcionario funcionario) throws SameCpfException {
		return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.create(funcionario));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable Integer id, @RequestBody Funcionario funcionario) {
		Funcionario funcionarioAtualizado = funcionarioService.update(id, funcionario);
		return ResponseEntity.ok(funcionarioAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		boolean response = funcionarioService.delete(id);
		if(response) {		
			return ResponseEntity.ok("Funcionario apagado com sucesso!");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
