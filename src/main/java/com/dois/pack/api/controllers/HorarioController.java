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

import com.dois.pack.api.models.Horario;
import com.dois.pack.api.services.HorarioService;

@RestController
@RequestMapping({ "/horario" })
public class HorarioController {

	@Autowired
	HorarioService horarioService;

	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(horarioService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable Integer id) {
		return ResponseEntity.ok(horarioService.getbyId(id));
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Horario horario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(horarioService.create(horario));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable Integer id, @RequestBody Horario horario) {
		Horario horarioAtualizado = horarioService.update(id, horario);
		return ResponseEntity.ok(horarioAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		boolean response = horarioService.delete(id);
		if(response) {		
			return ResponseEntity.ok("Horario apagado com sucesso!");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
