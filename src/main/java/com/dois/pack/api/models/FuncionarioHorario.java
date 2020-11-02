package com.dois.pack.api.models;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;





@Entity
@Table(name = "funcionario_horario")
public class FuncionarioHorario implements Serializable {
	
	private static final long serialVersionUID = 2103677445935061431L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Cascade(value = {CascadeType.MERGE})
	private Integer id;
 
    @ManyToOne
    @JoinColumn(name = "id_funcionario", foreignKey = @ForeignKey(name="Não_pode_Excluir_o_Funcionário_Antes_De_Excluir_as_relações_do_mesmo"))
    @Cascade(value = {CascadeType.MERGE})
    private Funcionario idFuncionario;
 
    @ManyToOne
    @JoinColumn(name = "id_horario", foreignKey = @ForeignKey(name="Não_pode_Excluir_o_Horário_Antes_De_Excluir_as_relações"))
    private Horario idHorario; 
    
    @NotNull
    @Column(name = "codigo_inicial", length = 15)
    private Integer codigoInicial;
    
    @NotNull
    @Column (name = "vigencia_inicial")
    private LocalDate vigenciaInicial;
    
    @NotNull
    @Column (name = "vigencia_final")
    private LocalDate vigenciaFinal;

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

	public LocalDate getVigenciaInicial() {
		return vigenciaInicial;
	}

	public void setVigenciaInicial(LocalDate vigenciaInicial) {
		this.vigenciaInicial = vigenciaInicial;
	}

	public LocalDate getVigenciaFinal() {
		return vigenciaFinal;
	}

	public void setVigenciaFinal(LocalDate vigenciaFinal) {
		this.vigenciaFinal = vigenciaFinal;
	}

	public Integer getId() {
		return id;
	}

}
