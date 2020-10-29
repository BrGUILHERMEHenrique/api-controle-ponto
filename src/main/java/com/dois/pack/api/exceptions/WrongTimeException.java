package com.dois.pack.api.exceptions;

public class WrongTimeException extends Exception {

	private static final long serialVersionUID = -8980748718690973139L;

	private String msg = "Os horários devem ser informados corretamente";

	public String getMsg() {
		return msg;
	}

	public WrongTimeException() {
		super();
	}
		
}