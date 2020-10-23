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

import com.dois.pack.api.models.FuncionarioHorario;
import com.dois.pack.api.services.FuncionarioHorarioService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({ "/funcionario_horario" })
public class FuncionarioHorarioController {

	@Autowired
	FuncionarioHorarioService funcionarioHorarioService;

	@ApiOperation("Retorna todas as relações Funcionário-Horário")
	@GetMapping(produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<FuncionarioHorario>> getAll() {
		return ResponseEntity.ok(funcionarioHorarioService.getAll());
	}

	@ApiOperation("Retorna uma relação Funcionário-Horário, baseando-se em seu id")
	@GetMapping(path="/{id}", produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<FuncionarioHorario> get(@PathVariable Integer id) {
		FuncionarioHorario funcionarioHorario = funcionarioHorarioService.getbyId(id).get();
		return ResponseEntity.ok(funcionarioHorario);
	}
	
	@ApiOperation("Retorna relações Funcionário-Horário, baseando-se em seu id")
	@GetMapping(path="/idFuncionario/{idFuncionario}", produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<FuncionarioHorario>> getWithIdFuncionario(@PathVariable Integer idFuncionario){
		return ResponseEntity.ok(funcionarioHorarioService.getWithIdFuncionario(idFuncionario));
	}
	
	@ApiOperation("Permite cadastrar uma nova relação Funcionário-Horário")
	@PostMapping(consumes= {MediaType.APPLICATION_JSON_VALUE},
						produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<FuncionarioHorario> create(@Valid @RequestBody FuncionarioHorario funcionarioHorario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioHorarioService.create(funcionarioHorario));
	}

	@ApiOperation("Permite atualizar uma relação Funcionário-Horário, baseando-se em seu id")
	@PutMapping(path="/{id}", consumes= {MediaType.APPLICATION_JSON_VALUE},
									produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<FuncionarioHorario> put(@PathVariable Integer id, @RequestBody FuncionarioHorario funcionarioHorario) {
		FuncionarioHorario funcionarioHorarioAtualizado = funcionarioHorarioService.update(id, funcionarioHorario);
		return ResponseEntity.ok(funcionarioHorarioAtualizado);
	}

	@ApiOperation("Permite apagar uma relação Funcionário-Horário")
	@DeleteMapping(path="/{id}", consumes= {MediaType.APPLICATION_JSON_VALUE},
										produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		boolean response = funcionarioHorarioService.delete(id);
		if(response) {		
			return ResponseEntity.ok("Funcionário apagado com sucesso!");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado");
		}
	}
}
