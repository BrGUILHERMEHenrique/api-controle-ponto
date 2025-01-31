package com.dois.pack.api.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable, Comparable<Empresa> {
	
	private static final long serialVersionUID = 2103677445935061431L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@NotNull
	@Column(name = "cod_empresa", unique = true, length = 20)
	private String codEmpresa;
	
	@NotNull
	@Column(name = "razao_social", length = 100)
	@Size(min = 1, max = 100)
	private String razaoSocial;	
	
	@NotNull
	@Column(name = "cnpj", length = 14)
	@Size(min = 14, max = 14)
	private String cnpj;
	

	public Empresa() {
		super();
	}

	public Empresa(Integer id, @NotNull String codEmpresa, @NotNull @Size(min = 1, max = 100) String razaoSocial,
			@NotNull @Size(min = 14, max = 14) String cnpj) {
		super();
		this.id = id;
		this.codEmpresa = codEmpresa;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
	}

	public Empresa(Integer id) {
		super();
		this.id = id;
	}
	
	@Override
	public int compareTo(Empresa o) {
		return this.razaoSocial.compareTo(o.getRazaoSocial());
	}

	public String getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Integer getId() {
		return id;
	}


	
}
