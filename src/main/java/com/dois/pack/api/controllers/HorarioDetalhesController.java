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

import com.dois.pack.api.exceptions.EmptyHourException;
import com.dois.pack.api.exceptions.WrongTimeException;
import com.dois.pack.api.models.HorarioDetalhes;
import com.dois.pack.api.services.HorarioDetalhesService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({ "/horario_detalhes" })
public class HorarioDetalhesController {

	@Autowired
	HorarioDetalhesService horarioDetalhesService;

	@ApiOperation("Retorna todas as relações Horário-Detalhes")
	@GetMapping(produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<HorarioDetalhes>> getAll() {
		return ResponseEntity.ok(horarioDetalhesService.getAll());
	}

	@ApiOperation("Retorna uma relação Horário-Detalhes, baseando-se em seu id")
	@GetMapping(path="/{id}", produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<HorarioDetalhes> get(@PathVariable Integer id) {
		HorarioDetalhes horarioDetalhes = horarioDetalhesService.getbyId(id).get();
		return ResponseEntity.ok(horarioDetalhes);
	}
	
	@ApiOperation("Retorna uma lista com Detalhes de horário baseando-se no id do horário")
	@GetMapping(path="idHorario/{idHorario}", produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<HorarioDetalhes>> getWithIdHorario (@PathVariable Integer idHorario){
		return ResponseEntity.ok(horarioDetalhesService.getAllIdHorario(idHorario));
	}

	@ApiOperation("Permite cadastrar uma nova relação Horário-Detalhes")
	@PostMapping(consumes= {MediaType.APPLICATION_JSON_VALUE},
						produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<HorarioDetalhes> create(@Valid @RequestBody HorarioDetalhes horarioDetalhes) throws EmptyHourException, WrongTimeException, IllegalArgumentException, IllegalAccessException {
		return ResponseEntity.status(HttpStatus.CREATED).body(horarioDetalhesService.create(horarioDetalhes));
	}

	@ApiOperation("Pemite atualizar uma relação Horário-Detalhes já cadastrada, baseando-se em seu id")
	@PutMapping(path="/{id}", consumes= {MediaType.APPLICATION_JSON_VALUE},
										produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<HorarioDetalhes> put(@PathVariable Integer id, @RequestBody HorarioDetalhes horarioDetalhes) {
		HorarioDetalhes horarioAtualizado = horarioDetalhesService.update(id, horarioDetalhes);
		return ResponseEntity.ok(horarioAtualizado);
	}

	@ApiOperation("Permite apagr uma relação Horário-Detalhes")
	@DeleteMapping(path="/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		boolean response = horarioDetalhesService.delete(id);
		if(response) {		
			return ResponseEntity.ok("Relação Horário-Detalhes apagada com sucesso!");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Relação Horário-Detalhes não encontrada");
		}
	}
}