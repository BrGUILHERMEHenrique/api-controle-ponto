package com.dois.pack.api.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="apontamento", indexes = {
		@Index(name="A_mesma_data_não_pode_se_repetir", columnList = "id_funcionario, data", unique=true)
})
public class Apontamento implements Serializable, Comparable<Apontamento> {

	private static final long serialVersionUID = 8321879883486681092L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="id_funcionario", foreignKey = @ForeignKey(name="Não_pode_Excluir_o_Funcionário_Antes_De_Excluir_as_relações_do_mesmo"))
	private Funcionario funcionario;

	
	@NotNull
	@OneToOne
	@JoinColumn(name="id_horarioDetalhes", foreignKey = @ForeignKey(name="Não_pode_Excluir_os_Detalhes_de_Horário_Antes_De_Excluir_as_relações"))
	private HorarioDetalhes horarioDetalhes;
	
	@NotNull
	@Column(name="data")
	private LocalDate data;
	
	@Column(name="entrada1")
	private LocalTime entrada1;
	
	@Column(name="saida1")
	private LocalTime saida1;
	
	@Column(name="entrada2")
	private LocalTime entrada2;
	
	@Column(name="saida2")
	private LocalTime saida2;
	
	@Column(name="total_trabalhado")
	private LocalTime totalTrabalhado;
	
	@Column(name="saldo_he")
	private LocalTime saldoHe;
	
	@Column(name="saldo_atraso")
	private LocalTime saldoAtraso;
	
	public boolean DataExiste(List<Apontamento> apontamentos, LocalDate data) {
		System.out.println("Data Existe: " + apontamentos.size());
		for(Apontamento apontamento : apontamentos) {
			System.out.println("Data apontamento: " + apontamento.getData());
			if(apontamento.getData().getDayOfMonth() == data.getDayOfMonth()) {
				return true;
			} 
		}
		return false;
	}
	public List<Apontamento> dataAntesOuDepois(List<Apontamento> apontamentos, LocalDate dataInicial, LocalDate dataFinal) {
		List<Apontamento> aux = new ArrayList<Apontamento>();
		for(Apontamento apontamento : apontamentos) {
			if(apontamento.getData().isAfter(dataInicial) && apontamento.getData().isBefore(dataFinal)) {
				aux.add(apontamento);
				System.out.println("Apontamento: " + apontamento.getData());
			}
			if(apontamento.getData().isEqual(dataInicial) || apontamento.getData().isEqual(dataFinal)) {
				aux.add(apontamento);
			}
		}
		return aux;
	}
	@Override
	public int compareTo(Apontamento o) {
		if(this.data != o.getData()) {
			return this.data.getDayOfMonth() - o.getData().getDayOfMonth();
		}
		return this.data.compareTo(o.getData());
	}
	public Apontamento() {
		super();
		this.funcionario = new Funcionario();
		this.horarioDetalhes = new HorarioDetalhes();
		this.data = LocalDate.of(1900, 01, 01);
		this.entrada1 = LocalTime.of(0, 0);
		this.saida1 = LocalTime.of(0, 0);
		this.entrada2 = LocalTime.of(0, 0);
		this.saida2 = LocalTime.of(0, 0);
		this.totalTrabalhado = LocalTime.of(0, 0);
		this.saldoHe = LocalTime.of(0, 0);
		this.saldoAtraso = LocalTime.of(0, 0);
	}

	public Apontamento(@NotNull Funcionario idFuncionario, @NotNull HorarioDetalhes idHorarioDetalhes,
			@NotNull LocalDate data, LocalTime entrada1, LocalTime saida1, LocalTime entrada2, LocalTime saida2,
			LocalTime totalTrabalhado, LocalTime saldoHe, LocalTime saldoAtraso) {
		super();
		this.funcionario = idFuncionario;
		this.horarioDetalhes = idHorarioDetalhes;
		this.data = data;
		this.entrada1 = entrada1;
		this.saida1 = saida1;
		this.entrada2 = entrada2;
		this.saida2 = saida2;
		this.totalTrabalhado = totalTrabalhado;
		this.saldoHe = saldoHe;
		this.saldoAtraso = saldoAtraso;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario idFuncionario) {
		this.funcionario = idFuncionario;
	}

	public HorarioDetalhes getHorarioDetalhes() {
		return horarioDetalhes;
	}

	public void setHorarioDetalhes(HorarioDetalhes idHorarioDetalhes) {
		this.horarioDetalhes = idHorarioDetalhes;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getEntrada1() {
		return entrada1;
	}

	public void setEntrada1(LocalTime entrada1) {
		this.entrada1 = entrada1;
	}

	public LocalTime getSaida1() {
		return saida1;
	}

	public void setSaida1(LocalTime saida1) {
		this.saida1 = saida1;
	}

	public LocalTime getEntrada2() {
		return entrada2;
	}

	public void setEntrada2(LocalTime entrada2) {
		this.entrada2 = entrada2;
	}

	public LocalTime getSaida2() {
		return saida2;
	}

	public void setSaida2(LocalTime saida2) {
		this.saida2 = saida2;
	}

	public LocalTime getTotalTrabalhado() {
		return totalTrabalhado;
	}

	public void setTotalTrabalhado(LocalTime totalTrabalhado) {
		this.totalTrabalhado = totalTrabalhado;
	}

	public LocalTime getSaldoHe() {
		return saldoHe;
	}

	public void setSaldoHe(LocalTime saldoHe) {
		this.saldoHe = saldoHe;
	}

	public LocalTime getSaldoAtraso() {
		return saldoAtraso;
	}

	public void setSaldoAtraso(LocalTime saldoAtraso) {
		this.saldoAtraso = saldoAtraso;
	}
	

	
	
}
