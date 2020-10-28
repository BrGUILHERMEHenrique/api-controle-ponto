package com.dois.pack.api.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.HOURS;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dois.pack.api.models.Apontamento;
import com.dois.pack.api.models.FuncionarioHorario;
import com.dois.pack.api.models.HorarioDetalhes;
import com.dois.pack.api.repositorys.ApontamentoRepository;
import com.dois.pack.api.repositorys.FuncionarioHorarioRepository;
import com.dois.pack.api.repositorys.HorarioDetalhesRepository;

@Service
public class ApontamentoService {

	@Autowired
	ApontamentoRepository apontamentoRepository;
	
	@Autowired
	FuncionarioHorarioRepository funcionarioHorarioRepository;
	
	@Autowired 
	HorarioDetalhesRepository horarioDetalhesReposotory;
	
	@Transactional
	public List<Apontamento> getAll (){
		return apontamentoRepository.findAll();
	}
	
	@Transactional
	public Apontamento getById (Integer id) {
		Apontamento apontamento = apontamentoRepository.findById(id).get();
		return apontamento;
	}
	
	@Transactional
	public Apontamento create (Apontamento apontamento) {
		System.out.println(apontamento.getData());
		System.out.println("idFuncionario apontamento ," + apontamento.getIdFuncionario().getId());
		HorarioDetalhes horarioDetalhes;
		Integer idHorario = funcionarioHorarioRepository.getIdHorario(
				apontamento.getIdFuncionario().getId(), 
				apontamento.getData());
		System.out.println("idHorario ," + idHorario);
		
		if(idHorario != null) {
			Integer qtdHorarios = horarioDetalhesReposotory.getCount(idHorario);
			FuncionarioHorario funcionarioHorario = funcionarioHorarioRepository.getFuncionario(
					apontamento.getIdFuncionario().getId(), idHorario);
			System.out.println("FuncionarioHorario: " + funcionarioHorario.getVigenciaInicial());
			System.out.println("Quanti Horarios: " + qtdHorarios);
			LocalDate vigenciaInicial = LocalDate.of(funcionarioHorario.getVigenciaInicial().getYear(),
														funcionarioHorario.getVigenciaInicial().getMonth(),
														funcionarioHorario.getVigenciaInicial().getDayOfMonth());
			
			Period period = Period.between(vigenciaInicial, apontamento.getData());
			System.out.println("Periodo: " + period.getDays());
			
			Integer result = (period.getDays() % qtdHorarios) + funcionarioHorario.getCodigoInicial() - 1;
			
			System.out.println("conta: " + result);
			if(result == 0) {
				result = qtdHorarios;
			}
			if(result > qtdHorarios) {
				result -= qtdHorarios;
			}
			
			HorarioDetalhes idHorarioDetalhe = horarioDetalhesReposotory.getWithCodigoDia(result);
			
			apontamento.setIdHorarioDetalhes(idHorarioDetalhe);
			
//			Long hora1 = HOURS.between(apontamento.getEntrada1(), apontamento.getSaida1());
//			
//			Long hora2 = HOURS.between(apontamento.getEntrada2(), apontamento.getSaida2());
//			
//			Long hora1HD = HOURS.between(idHorarioDetalhe.getEntrada1(), idHorarioDetalhe.getSaida1());
//			
//			Long hora2HD = HOURS.between(idHorarioDetalhe.getEntrada2(), idHorarioDetalhe.getSaida2());
//			
//			System.out.println((hora1 + hora2) - (hora1HD + hora2HD));
			
		}
		
		return apontamento;
	}
	
	
}
