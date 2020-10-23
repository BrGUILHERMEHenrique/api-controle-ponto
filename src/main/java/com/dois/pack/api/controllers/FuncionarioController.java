package com.dois.pack.api.controllers;


import java.util.List;
import java.util.Optional;

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

import com.dois.pack.api.exceptions.SameCpfException;
import com.dois.pack.api.models.Funcionario;
import com.dois.pack.api.services.FuncionarioService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({ "/funcionario" })
public class FuncionarioController {

	@Autowired
	FuncionarioService funcionarioService;

	@ApiOperation("Retorna todos os funcionários cadastrados")
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Funcionario>> getAll() {
		return ResponseEntity.ok(funcionarioService.getAll());
	}
	
	@ApiOperation("Retorna um funcionário baseado no seu código de matricula")
	@GetMapping(path="/cod/{codMatricula}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Funcionario getMatricula(@PathVariable String codMatricula){
		return funcionarioService.getByMatricula(codMatricula);
	}
	
	@ApiOperation("Retorna um funcionário baseado em seu id")
	@GetMapping(path="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Funcionario> get(@PathVariable Integer id) {
		Funcionario funcionario = funcionarioService.getbyId(id).get();
		return ResponseEntity.ok(funcionario);
	}

	@ApiOperation("Permite cadastrar um novo funcionário")
	@PostMapping(consumes= {MediaType.APPLICATION_JSON_VALUE},
									produces= {MediaType.APPLICATION_JSON_VALUE} )
	public ResponseEntity<Funcionario> create(@Valid @RequestBody Funcionario funcionario) throws SameCpfException {
		return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.create(funcionario));
	}

	@PutMapping(path="/{id}", consumes= {MediaType.APPLICATION_JSON_VALUE},
										produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Funcionario> put(@PathVariable Integer id, @RequestBody Funcionario funcionario) {
		Funcionario funcionarioAtualizado = funcionarioService.update(id, funcionario);
		return ResponseEntity.ok(funcionarioAtualizado);
	}

	@DeleteMapping(path="/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		boolean response = funcionarioService.delete(id);
		if(response) {		
			return ResponseEntity.ok("Funcionário apagado com sucesso!");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado");
		}
	}
}
