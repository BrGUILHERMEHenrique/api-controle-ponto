package com.dois.pack.api.exceptions;

public class EmptyHourException extends Exception {

	private static final long serialVersionUID = -4708413705221516670L;

	private String msg = "Por favor caso não seja um dia de folga, informe os horários";

	public EmptyHourException() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
