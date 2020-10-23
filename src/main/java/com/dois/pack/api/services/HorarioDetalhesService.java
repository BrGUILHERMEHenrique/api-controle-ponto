package com.dois.pack.api.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dois.pack.api.models.HorarioDetalhes;
import com.dois.pack.api.repositorys.HorarioDetalhesRepository;

@Service
public class HorarioDetalhesService {
	
	@Autowired
	HorarioDetalhesRepository horarioDetalhesRepository;
	
	@Transactional
	public HorarioDetalhes create(HorarioDetalhes horarioDetalhes) {
		return horarioDetalhesRepository.save(horarioDetalhes);
	}
	
	@Transactional
	public List<HorarioDetalhes> getAll() {
		return horarioDetalhesRepository.findAll();
	}
	
	@Transactional
	public Optional<HorarioDetalhes> getbyId(Integer id) {
		return horarioDetalhesRepository.findById(id);
	}
	
	@Transactional
	public boolean delete(Integer id) {
		Optional<HorarioDetalhes> horarioDetalhes = horarioDetalhesRepository.findById(id);
		if(horarioDetalhes.isPresent()) {
			horarioDetalhesRepository.delete(horarioDetalhes.get());
			return true;
		}else {
			return false;
		}
	}
	
	@Transactional
	public HorarioDetalhes update(Integer id, HorarioDetalhes horarioDetalhes) {
		Optional<HorarioDetalhes> horarioDetalhesAntigo = horarioDetalhesRepository.findById(id);
		HorarioDetalhes horarioDetalhesAtualizado = horarioDetalhesAntigo.get();
		
		if(horarioDetalhes.getFolga() != null) {
			horarioDetalhesAtualizado.setFolga(horarioDetalhes.getFolga());
		}
		
		if(horarioDetalhes.getEntrada1() != null) {
			horarioDetalhesAtualizado.setEntrada1(horarioDetalhes.getEntrada1());
		}
		
		if(horarioDetalhes.getEntrada2() != null) {
			horarioDetalhesAtualizado.setEntrada2(horarioDetalhes.getEntrada2());
		}
		
		if(horarioDetalhes.getSaida1() != null) {
			horarioDetalhesAtualizado.setSaida1(horarioDetalhes.getSaida1());
		}
		
		if(horarioDetalhes.getSaida2() != null) {
			horarioDetalhesAtualizado.setSaida2(horarioDetalhes.getSaida2());
		}
		
		if(horarioDetalhes.getCodigoDia() != null) {
			horarioDetalhesAtualizado.setCodigoDia(horarioDetalhes.getCodigoDia());
		}
		
		return horarioDetalhesRepository.save(horarioDetalhesAtualizado);
	}
}
