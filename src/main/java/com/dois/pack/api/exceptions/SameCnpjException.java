package com.dois.pack.api.exceptions;

public class SameCnpjException extends Exception {

	private static final long serialVersionUID = -6840138218861403331L;
	
	private String msg = "O CNPJ informado já existe, por favor verifique os dados";
	private String cnpj;
	
	public SameCnpjException() {
		super();
	}
	public SameCnpjException(String cnpj) {
		super();
		this.cnpj = cnpj;
	}
	public String getMsg() {
		return msg;
	}
	public String getCnpj() {
		return cnpj;
	}
	
	
	
}
