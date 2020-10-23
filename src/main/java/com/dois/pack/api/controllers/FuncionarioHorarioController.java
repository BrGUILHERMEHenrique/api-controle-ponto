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

import com.dois.pack.api.models.FuncionarioHorario;
import com.dois.pack.api.services.FuncionarioHorarioService;

@RestController
@RequestMapping({ "/funcionario_horario" })
public class FuncionarioHorarioController {

	@Autowired
	FuncionarioHorarioService funcionarioHorarioService;

	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(funcionarioHorarioService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable Integer id) {
		return ResponseEntity.ok(funcionarioHorarioService.getbyId(id));
	}
	@GetMapping("/idFuncionario/{idFuncionario}")
	public ResponseEntity<?> getWithIdFuncionario(@PathVariable Integer idFuncionario){
		return ResponseEntity.ok(funcionarioHorarioService.getWithIdFuncionario(idFuncionario));
	}
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody FuncionarioHorario funcionarioHorario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioHorarioService.create(funcionarioHorario));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable Integer id, @RequestBody FuncionarioHorario funcionarioHorario) {
		FuncionarioHorario funcionarioHorarioAtualizado = funcionarioHorarioService.update(id, funcionarioHorario);
		return ResponseEntity.ok(funcionarioHorarioAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		boolean response = funcionarioHorarioService.delete(id);
		if(response) {		
			return ResponseEntity.ok("Funcionario apagado com sucesso!");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
