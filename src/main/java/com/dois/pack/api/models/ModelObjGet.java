package com.dois.pack.api.models;

import java.time.LocalDate;

public class ModelObjGet {

	private Integer idFuncionario;
	private LocalDate primaryDate;
	private LocalDate secundaryDate;
	
	
	public ModelObjGet() {
		super();
	}


	public ModelObjGet(Integer idFuncionario, LocalDate primaryDate, LocalDate secundaryDate) {
		super();
		this.idFuncionario = idFuncionario;
		this.primaryDate = primaryDate;
		this.secundaryDate = secundaryDate;
	}


	public Integer getIdFuncionario() {
		return idFuncionario;
	}


	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}


	public LocalDate getPrimaryDate() {
		return primaryDate;
	}


	public void setPrimaryDate(LocalDate primaryDate) {
		this.primaryDate = primaryDate;
	}


	public LocalDate getSecundaryDate() {
		return secundaryDate;
	}


	public void setSecundaryDate(LocalDate secundaryDate) {
		this.secundaryDate = secundaryDate;
	}
	
	
	
}
