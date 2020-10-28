package com.dois.pack.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dois.pack.api.models.Apontamento;
import com.dois.pack.api.services.ApontamentoService;

@RestController
@RequestMapping({"/teste"})
public class ApontamentoController {

	@Autowired
	ApontamentoService apontamentoService;
	
	@PostMapping
	public Apontamento teste(@RequestBody Apontamento apontamento) {
		return apontamentoService.create(apontamento);
	}
}
