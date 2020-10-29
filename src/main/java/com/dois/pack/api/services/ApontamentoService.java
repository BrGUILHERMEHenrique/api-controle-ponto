package com.dois.pack.api.services;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dois.pack.api.models.Apontamento;
import com.dois.pack.api.models.FuncionarioHorario;
import com.dois.pack.api.models.HorarioDetalhes;
import com.dois.pack.api.models.ModelObjGet;
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
	public List<Apontamento> getAll() {
		return apontamentoRepository.findAll();
	}

	@Transactional
	public Apontamento getById(Integer id) {
		Apontamento apontamento = apontamentoRepository.findById(id).get();
		return apontamento;
	}
	
	@Transactional
	public List<Apontamento> getByIdFuncionarioAndDate (ModelObjGet modelObj){
		return apontamentoRepository.getByIdAndDate(modelObj.getIdFuncionario(), modelObj.getPrimaryDate(), modelObj.getSecundaryDate());
	}
	
	@Transactional
	public List<Apontamento> getByIdFuncionario (Integer idFuncionario) {
		return apontamentoRepository.getbyIdFuncionario(idFuncionario);
	}

	@Transactional
	public Apontamento create(Apontamento apontamento) {
		System.out.println("Apontamento: " + apontamento);
		Integer idHorario = funcionarioHorarioRepository.getIdHorario(apontamento.getFuncionario().getId(),
				apontamento.getData());
		System.out.println("Horario: " + idHorario);
		Integer idFuncionario = apontamento.getFuncionario().getId();

		if (idHorario != null) {
			Integer qtdHorarios = horarioDetalhesReposotory.getCount(idHorario);
			FuncionarioHorario funcionarioHorario = funcionarioHorarioRepository.getFuncionario(idFuncionario,
					idHorario);

			LocalDate vigenciaInicial = LocalDate.of(funcionarioHorario.getVigenciaInicial().getYear(),
					funcionarioHorario.getVigenciaInicial().getMonth(),
					funcionarioHorario.getVigenciaInicial().getDayOfMonth());

			Period period = Period.between(vigenciaInicial, apontamento.getData());

			Integer result = calculateResult(period.getDays(), funcionarioHorario.getCodigoInicial(), qtdHorarios);

			HorarioDetalhes horarioDetalhe = horarioDetalhesReposotory.getWithCodigoDia(result);

			apontamento.setHorarioDetalhes(horarioDetalhe);

			calculateHours(apontamento, horarioDetalhe);

		}

		return apontamentoRepository.save(apontamento);
	}

	private Integer calculateResult(Integer days, Integer codigoInicial, Integer qtdHorarios) {

		Integer result = ((days + 1 % qtdHorarios) + codigoInicial) - 1;

		if (result == 0) {
			result = qtdHorarios;
		}
		if (result > qtdHorarios) {
			result -= qtdHorarios;
		}
		return result;
	}

	private Integer calculateAccount(Apontamento apontamento, HorarioDetalhes horarioDetalhe) {
		int durationHours = getHour(apontamento.getEntrada1(), apontamento.getSaida1());
		int durationMinutes = getMinute(apontamento.getEntrada1(), apontamento.getSaida1());
		int hoursToMinutes = hoursToMinutes(durationHours, durationMinutes);
		System.out.println("Horas * 60: " + (hoursToMinutes + durationMinutes));

		int durationHour2 = getHour(apontamento.getEntrada2(), apontamento.getSaida2());
		int durationMinutes2 = getMinute(apontamento.getEntrada2(), apontamento.getSaida2());
		int hoursToMinutes2 = hoursToMinutes(durationHour2, durationMinutes2);
		System.out.println("Horas * 60 2 : " + (hoursToMinutes2 + durationMinutes2));

		int durationHourHD = getHour(horarioDetalhe.getEntrada1(), horarioDetalhe.getSaida1());
		int durationMinutesHD = getMinute(horarioDetalhe.getEntrada1(), horarioDetalhe.getSaida1());
		int hoursToMinutesHD = hoursToMinutes(durationHourHD, durationMinutesHD);
		System.out.println("Horas * 60 3 : " + (hoursToMinutesHD + durationMinutesHD));

		int durationHourHD2 = getHour(horarioDetalhe.getEntrada2(), horarioDetalhe.getSaida2());
		int durationMinutesHD2 = getMinute(horarioDetalhe.getEntrada2(), horarioDetalhe.getSaida2());
		int hoursToMinutesHD2 = hoursToMinutes(durationHourHD2, durationMinutesHD2);
		System.out.println("Horas * 60 4: " + (hoursToMinutesHD2 + durationMinutesHD2));

		int horas = sumDurations(hoursToMinutes, hoursToMinutes2);
		int horasHD = sumDurations(hoursToMinutesHD, hoursToMinutesHD2);

		int hora = horas / 60;
		int minuto = horas % 60;
		apontamento.setTotalTrabalhado(LocalTime.of(hora, minuto));

		return (horas - horasHD);
	}

	private void calculateHours(Apontamento apontamento, HorarioDetalhes horarioDetalhe) {

		if (verifyFolga(horarioDetalhe, apontamento))
			return;

		int contaHoras = calculateAccount(apontamento, horarioDetalhe);

		verifyHours(apontamento, contaHoras);

	}

	private int getMinute(LocalTime entrada, LocalTime saida) {
		return saida.getMinute() - entrada.getMinute();
	}

	private int getHour(LocalTime entrada, LocalTime saida) {
		return saida.getHour() - entrada.getHour();
	}

	private int sumDurations(int duration1, int duration2) {
		return duration1 + duration2;
	}

	private int hoursToMinutes(int hours, int minutes) {
		return (hours * 60) + minutes;
	}

	private void verifyHours(Apontamento apontamento, int horas) {
		System.out.println("Horas: " + horas);

		LocalTime horaApurada;

		if (horas < 0) {
			horas = Math.abs(horas);
			int hora = horas / 60;
			int minuto = horas % 60;
			System.out.println("Horas atraso: " + hora);
			System.out.println("minuto atraso: " + minuto);
			horaApurada = LocalTime.of(hora, minuto);
			System.out.println(horaApurada);
			apontamento.setSaldoAtraso(horaApurada);
			apontamento.setSaldoHe(LocalTime.of(0, 0));

		} else {
			int hora = horas / 60;
			int minuto = horas % 60;
			System.out.println("Horas HE: " + hora);
			System.out.println("minuto HE: " + minuto);
			horaApurada = LocalTime.of(hora, minuto);
			apontamento.setSaldoHe(horaApurada);
			apontamento.setSaldoAtraso(LocalTime.of(0, 0));

		}

	}

	public boolean verifyFolga(HorarioDetalhes horarioDetalhe, Apontamento apontamento) {
		int durationHours = getHour(apontamento.getEntrada1(), apontamento.getSaida1());
		int durationMinutes = getMinute(apontamento.getEntrada1(), apontamento.getSaida1());
		int hoursToMinutes = hoursToMinutes(durationHours, durationMinutes);

		int durationHours2 = getHour(apontamento.getEntrada2(), apontamento.getSaida2());
		int durationMinutes2 = getMinute(apontamento.getEntrada2(), apontamento.getSaida2());
		int hoursToMinutes2 = hoursToMinutes(durationHours2, durationMinutes2);

		int horas = sumDurations(hoursToMinutes, hoursToMinutes2);
		if (horarioDetalhe.getFolga()) {
			int hora = horas / 60;
			int minuto = horas % 60;
			LocalTime horaTrabalhadaFolga = LocalTime.of(hora, minuto);
			apontamento.setSaldoHe(horaTrabalhadaFolga);
			apontamento.setTotalTrabalhado(horaTrabalhadaFolga);
			apontamento.setSaldoAtraso(LocalTime.of(0, 0));
			return true;
		}
		return false;
	}

	@Transactional
	public Apontamento update(Apontamento apontamento, Integer id) {
		Apontamento apontamentoAtualizado = apontamentoRepository.findById(id).get();
		if (apontamentoAtualizado != null) {
			if (apontamento.getEntrada1() != null) {
				apontamentoAtualizado.setEntrada1(apontamento.getEntrada1());
			}
			if (apontamento.getEntrada2() != null) {
				apontamentoAtualizado.setEntrada2(apontamento.getEntrada2());
			}
			if (apontamento.getSaida1() != null) {
				apontamentoAtualizado.setSaida1(apontamento.getSaida1());
			}
			if (apontamento.getSaida2() != null) {
				apontamentoAtualizado.setSaida2(apontamento.getSaida2());
			}
			Integer contaHoras = calculateAccount(apontamentoAtualizado, apontamentoAtualizado.getHorarioDetalhes());
			System.out.println("Conta horas put: " + contaHoras);
			verifyHours(apontamentoAtualizado, contaHoras);

		}
		return apontamentoRepository.save(apontamentoAtualizado);
	}

	@Transactional
	public boolean delete(Integer id) {
		Optional<Apontamento> apontamento = apontamentoRepository.findById(id);
		if (apontamento.isPresent()) {
			apontamentoRepository.delete(apontamento.get());
			return true;
		} else {
			return false;
		}
	}
}
