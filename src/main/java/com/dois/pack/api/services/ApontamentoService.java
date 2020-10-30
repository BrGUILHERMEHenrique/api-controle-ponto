package com.dois.pack.api.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dois.pack.api.models.Apontamento;
import com.dois.pack.api.models.Funcionario;
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
	HorarioDetalhesRepository horarioDetalhesRepository;

	@Transactional
	public List<Apontamento> getAll() {
		return apontamentoRepository.findAll();
	}

	@Transactional
	public List<Apontamento> getAllDays(ModelObjGet modelObj) {
		Integer idFuncionario = modelObj.getIdFuncionario();
		LocalDate dataInicial = modelObj.getDataInicial();
		LocalDate dataFinal = modelObj.getDataFinal();
		List<Apontamento> apontamentosApurados = apontamentoRepository.getbyIdFuncionario(idFuncionario);
		Apontamento apontamento = new Apontamento();
		Apontamento apontamento2;
		LocalDate data;
		HorarioDetalhes horarioDetalhe;
		Integer result;
		FuncionarioHorario funcionarioHorario;
		Integer qtdHorarios;
		Period period;
		Integer idHorario;
		List<Apontamento> teste = new ArrayList<Apontamento>();
		for (int dias = 1; dias <= dataFinal.getDayOfMonth(); dias++) {
			data = LocalDate.of(dataFinal.getYear(), dataFinal.getMonth(), dias);
			if (!apontamento.DataExiste(apontamentosApurados, data)) {

				idHorario = funcionarioHorarioRepository.getIdHorario(idFuncionario, data);
				System.out.println("idHorario: " + idHorario);
				apontamento2 = new Apontamento();
				apontamento2.setFuncionario(new Funcionario(idFuncionario));
				apontamento2.setData(data);
				if (idHorario != null) {
					qtdHorarios = horarioDetalhesRepository.getCount(idHorario);
					funcionarioHorario = funcionarioHorarioRepository.getFuncionario(idFuncionario, idHorario, data);
					System.out.println("idHorario: " + idHorario);

					period = Period.between(funcionarioHorario.getVigenciaInicial(), apontamento2.getData());

					System.out.println("periodo: " + period);
					result = calculateResult(period.getDays(), funcionarioHorario.getCodigoInicial(), qtdHorarios);
					System.out.println("Result: " + result);
					horarioDetalhe = horarioDetalhesRepository.getWithCodigoDia(result, idHorario);

					System.out.println("Horario Detalhes: " + horarioDetalhe.getHorario().getDescHorario());
					apontamento2.setHorarioDetalhes(horarioDetalhe);

					calculateHours(apontamento2, horarioDetalhe);
				}
				apontamentosApurados.add(apontamento2);
			}
		}
		teste = apontamento.dataAntesOuDepois(apontamentosApurados, dataInicial, dataFinal);
		Collections.sort(teste);
		return teste;
	}

	@Transactional
	public Apontamento getById(Integer id) {
		Apontamento apontamento = apontamentoRepository.findById(id).get();
		return apontamento;
	}

	@Transactional
	public List<Apontamento> getByIdFuncionarioAndDate(ModelObjGet modelObj) {
		return apontamentoRepository.getByIdAndDate(modelObj.getIdFuncionario(), modelObj.getDataInicial(),
				modelObj.getDataFinal());
	}

	@Transactional
	public List<Apontamento> getByIdFuncionario(Integer idFuncionario) {
		return apontamentoRepository.getbyIdFuncionario(idFuncionario);
	}

	@Transactional
	public Apontamento create(Apontamento apontamento) {
		System.out.println("Apontamento: " + apontamento);
		Integer idHorario = funcionarioHorarioRepository.getIdHorario(apontamento.getFuncionario().getId(),
				apontamento.getData());
		System.out.println("Horario: " + idHorario);
		Integer idFuncionario = apontamento.getFuncionario().getId();
		System.out.println("ID do funcionario" + idFuncionario);
		if (idHorario != null) {
			Integer qtdHorarios = horarioDetalhesRepository.getCount(idHorario);
			FuncionarioHorario funcionarioHorario = funcionarioHorarioRepository.getFuncionario(idFuncionario,
					idHorario, apontamento.getData());

			LocalDate vigenciaInicial = LocalDate.of(funcionarioHorario.getVigenciaInicial().getYear(),
					funcionarioHorario.getVigenciaInicial().getMonth(),
					funcionarioHorario.getVigenciaInicial().getDayOfMonth());

			Period period = Period.between(vigenciaInicial, apontamento.getData());

			Integer result = calculateResult(period.getDays(), funcionarioHorario.getCodigoInicial(), qtdHorarios);

			HorarioDetalhes horarioDetalhe = horarioDetalhesRepository.getWithCodigoDia(result, idHorario);


			apontamento.setHorarioDetalhes(horarioDetalhe);

			calculateHours(apontamento, horarioDetalhe);

		}

		return apontamentoRepository.save(apontamento);
	}

	private Integer calculateResult(Integer days, Integer codigoInicial, Integer qtdHorarios) {

		Integer result = (((days + 1) % qtdHorarios) + codigoInicial) - 1;

		System.out.println("quantidade Horarios: " + qtdHorarios);

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
		
		int hora;
		int minuto;
		LocalTime horaApurada;

		if (horas < 0) {
			horas = Math.abs(horas);
			hora = horas / 60;
			minuto = horas % 60;
			System.out.println("Horas atraso: " + hora);
			System.out.println("minuto atraso: " + minuto);
			horaApurada = LocalTime.of(hora, minuto);
			System.out.println(horaApurada);
			apontamento.setSaldoAtraso(horaApurada);
			apontamento.setSaldoHe(LocalTime.of(0, 0));

		} else {
			hora = horas / 60;
			minuto = horas % 60;
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
		System.out.println("apontantamento: " + apontamento.getEntrada1());
		LocalTime dataZerada = LocalTime.of(0, 0);
		if (apontamentoAtualizado != null) {
			if (apontamento.getEntrada1() != dataZerada) {
				apontamentoAtualizado.setEntrada1(apontamento.getEntrada1());
				System.out.println("apontamento atualizado: " + apontamento.getEntrada1());
			}
			if (apontamento.getEntrada2() != dataZerada) {
				apontamentoAtualizado.setEntrada2(apontamento.getEntrada2());
			}
			if (apontamento.getSaida1() != dataZerada) {
				apontamentoAtualizado.setSaida1(apontamento.getSaida1());
			}
			if (apontamento.getSaida2() != dataZerada) {
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
