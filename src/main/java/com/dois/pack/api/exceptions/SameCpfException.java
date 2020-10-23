package com.dois.pack.api.exceptions;

public class SameCpfException extends Exception {

	private static final long serialVersionUID = 655622958141040870L;
	
	private String codMatricula;
	private String msg = "O cpf indicado já existe, por favor verifique seus dados";

	public SameCpfException(String codMatricula) {
		super();
		this.codMatricula = codMatricula;
	}

	public SameCpfException() {
		super();
	}

	public String getCodMatricula() {
		return codMatricula;
	}

	public String getMsg() {
		return msg;
	}

}
