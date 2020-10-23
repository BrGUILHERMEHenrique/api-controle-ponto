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

import com.dois.pack.api.models.Horario;
import com.dois.pack.api.services.HorarioService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({ "/horario" })
public class HorarioController {

	@Autowired
	HorarioService horarioService;

	@ApiOperation("Retorna todos os horários cadastrados")
	@GetMapping(produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Horario>> getAll() {
		return ResponseEntity.ok(horarioService.getAll());
	}

	@ApiOperation("Retorna um horário cadastrado, baseando-se em seu id")
	@GetMapping(path="/{id}", produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Horario> get(@PathVariable Integer id) {
		Horario horario = horarioService.getbyId(id).get();
		return ResponseEntity.ok(horario);
	}

	@ApiOperation("Permite cadastrar um novo horário")
	@PostMapping(consumes= {MediaType.APPLICATION_JSON_VALUE},
						produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Horario> create(@Valid @RequestBody Horario horario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(horarioService.create(horario));
	}

	@ApiOperation("Permite atualizar um horário já existente, baseando-se em seu id")
	@PutMapping(path="/{id}",consumes= {MediaType.APPLICATION_JSON_VALUE},
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Horario> put(@PathVariable Integer id, @RequestBody Horario horario) {
		Horario horarioAtualizado = horarioService.update(id, horario);
		return ResponseEntity.ok(horarioAtualizado);
	}

	@ApiOperation("Permite apagar um horário já existente, baseando-se em seu id")
	@DeleteMapping(path="/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		boolean response = horarioService.delete(id);
		if(response) {		
			return ResponseEntity.ok("Horário apagado com sucesso!");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Horário não encontrado");
		}
	}
}
