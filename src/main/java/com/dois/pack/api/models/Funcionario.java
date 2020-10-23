package com.dois.pack.api.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 2103677445935061431L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotNull
	@Column(name = "cod_matricula", unique = true, length = 20)
	private String codMatricula;
	
	@NotNull
	@Column(name = "nome", length = 100)
	@Size(min = 1, max = 100)
	private String nome;
	
	@NotNull
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	
	@NotNull
	@Column(name = "cpf", length = 11)
	@Size(min = 11, max = 11)
	private String cpf;

	@NotNull
	@Column(name = "telefone", length = 11)
	private String telefone;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_empresa")
	private Empresa idEmpresa;

	public Funcionario() {
		super();
	}
	

	public Funcionario(Integer id, @NotNull String codMatricula, @NotNull @Size(min = 1, max = 100) String nome,
			@NotNull LocalDate dataNascimento, @NotNull @Size(min = 11, max = 11) String cpf, @NotNull String telefone,
			Empresa idEmpresa) {
		super();
		this.id = id;
		this.codMatricula = codMatricula;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
		this.telefone = telefone;
		this.idEmpresa = idEmpresa;
	}


	public Funcionario(Integer id) {
		super();
		this.id = id;
	}



	public String getCodMatricula() {
		return codMatricula;
	}

	public void setCodMatricula(String codMatricula) {
		this.codMatricula = codMatricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Empresa getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Empresa idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Integer getId() {
		return id;
	}

}
