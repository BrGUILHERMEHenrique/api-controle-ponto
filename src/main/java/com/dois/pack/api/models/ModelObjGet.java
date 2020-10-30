package com.dois.pack.api.models;

import java.time.LocalDate;

public class ModelObjGet {

	private Integer idFuncionario;
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	
	
	public ModelObjGet() {
		super();
	}


	public ModelObjGet(Integer idFuncionario, LocalDate dataInicial, LocalDate dataFinal) {
		super();
		this.idFuncionario = idFuncionario;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
	}


	public Integer getIdFuncionario() {
		return idFuncionario;
	}


	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}


	public LocalDate getDataInicial() {
		return dataInicial;
	}


	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}


	public LocalDate getDataFinal() {
		return dataFinal;
	}


	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	
	
}
