package com.dois.pack.api.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable, Comparable<Funcionario> {

	private static final long serialVersionUID = 2103677445935061431L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotNull
	@Column(name = "pis", unique = true, length = 11)
	private String pis;
	
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
	
	@OneToOne
	@JoinColumn(name = "id_empresa", foreignKey = @ForeignKey(name="Não_pode_Excluir_a_Empresa_Antes_De_Excluir_as_Relações"))
	@Cascade(value = {CascadeType.MERGE})
	private Empresa idEmpresa;

	public Funcionario() {
		super();
	}
	

	public Funcionario(Integer id, @NotNull String pis, @NotNull @Size(min = 1, max = 100) String nome,
			@NotNull LocalDate dataNascimento, @NotNull @Size(min = 11, max = 11) String cpf, @NotNull String telefone,
			Empresa idEmpresa) {
		super();
		this.id = id;
		this.pis = pis;
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
	
	@Override
	public int compareTo(Funcionario o) {
		return this.nome.compareTo(o.getNome());
	}



	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
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
