package com.dois.pack.api.exceptions;

public class DiaForaDeVigenciaException extends Exception {

	private static final long serialVersionUID = -6206989941093150191L;

	String msg = "O dia informado está fora da vigência do funcionário";
	

	public DiaForaDeVigenciaException() {
		super();
	}


	public DiaForaDeVigenciaException(String msg) {
		super();
		this.msg = msg;
		
		
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
