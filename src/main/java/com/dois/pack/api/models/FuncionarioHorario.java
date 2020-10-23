package com.dois.pack.api.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "funcionario_horario")
public class FuncionarioHorario implements Serializable {
	
	private static final long serialVersionUID = 2103677445935061431L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
 
    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario idFuncionario;
 
    @ManyToOne
    @JoinColumn(name = "id_horario")
    private Horario idHorario; 
    
    @NotNull
    @Column(name = "codigo_inicial", length = 15)
    private Integer codigoInicial;
    
    @NotNull
    @Column (name = "vigencia_inicial")
    private LocalDateTime vigenciaInicial;
    
    @NotNull
    @Column (name = "vigencia_final")
    private LocalDateTime vigenciaFinal;

	public Funcionario getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Funcionario idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Horario getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(Horario idHorario) {
		this.idHorario = idHorario;
	}

	public Integer getCodigoInicial() {
		return codigoInicial;
	}

	public void setCodigoInicial(Integer codigoInicial) {
		this.codigoInicial = codigoInicial;
	}

	public LocalDateTime getVigenciaInicial() {
		return vigenciaInicial;
	}

	public void setVigenciaInicial(LocalDateTime vigenciaInicial) {
		this.vigenciaInicial = vigenciaInicial;
	}

	public LocalDateTime getVigenciaFinal() {
		return vigenciaFinal;
	}

	public void setVigenciaFinal(LocalDateTime vigenciaFinal) {
		this.vigenciaFinal = vigenciaFinal;
	}

	public Integer getId() {
		return id;
	}

}
