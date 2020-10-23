package com.dois.pack.api.models;

import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "horario")
public class Horario implements Serializable{
	
	private static final long serialVersionUID = 2103677445935061431L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@NotNull
	@Column(name = "codigo_horario", unique = true)
	private Integer codigoHorario;
	
	@NotNull
	@Column(name = "desc_horario", unique = true, length = 50)
	@Size(min = 1, max = 50)
	private String descHorario;

	public Horario() {
		super();
	}
	
	

	public Horario(Integer id, Integer codigoHorario, String descHorario) {
		super();
		this.id = id;
		this.codigoHorario = codigoHorario;
		this.descHorario = descHorario;
	}
	

	public Horario(Integer id) {
		super();
		this.id = id;
	}



	public Integer getCodigoHorario() {
		return codigoHorario;
	}

	public void setCodigoHorario(Integer codigoHorario) {
		this.codigoHorario = codigoHorario;
	}

	public String getDescHorario() {
		return descHorario;
	}

	public void setDescHorario(String descHorario) {
		this.descHorario = descHorario;
	}

	public Integer getId() {
		return id;
	}	
	
}
