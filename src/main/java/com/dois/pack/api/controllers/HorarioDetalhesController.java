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

import com.dois.pack.api.models.HorarioDetalhes;
import com.dois.pack.api.services.HorarioDetalhesService;

@RestController
@RequestMapping({ "/horario_detalhes" })
public class HorarioDetalhesController {

	@Autowired
	HorarioDetalhesService horarioDetalhesService;

	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(horarioDetalhesService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable Integer id) {
		return ResponseEntity.ok(horarioDetalhesService.getbyId(id));
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody HorarioDetalhes horarioDetalhes) {
		return ResponseEntity.status(HttpStatus.CREATED).body(horarioDetalhesService.create(horarioDetalhes));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable Integer id, @RequestBody HorarioDetalhes horarioDetalhes) {
		HorarioDetalhes horarioAtualizado = horarioDetalhesService.update(id, horarioDetalhes);
		return ResponseEntity.ok(horarioAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		boolean response = horarioDetalhesService.delete(id);
		if(response) {		
			return ResponseEntity.ok("Horario apagado com sucesso!");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}