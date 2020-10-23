package com.dois.pack.api.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.dois.pack.api.models.Horario;
import com.dois.pack.api.repositorys.HorarioRepository;
import org.springframework.stereotype.Service;

@Service
public class HorarioService {
	
	@Autowired
	HorarioRepository horarioRepository;
	
	@Transactional
	public Horario create(Horario horario) {
		return horarioRepository.save(horario);
	}
	
	@Transactional
	public List<Horario> getAll() {
		return horarioRepository.findAll();
	}
	
	@Transactional
	public Optional<Horario> getbyId(Integer id) {
		return horarioRepository.findById(id);
	}
	
	@Transactional
	public boolean delete(Integer id) {
		Optional<Horario> horario = horarioRepository.findById(id);
		if(horario.isPresent()) {
			horarioRepository.delete(horario.get());
			return true;
		}else {
			return false;
		}
	}
	
	@Transactional
	public Horario update(Integer id, Horario horario) {
		Optional<Horario> horarioAntigo = horarioRepository.findById(id);
		Horario horarioAtualizado = horarioAntigo.get();
		
		if(!horario.getDescHorario().equals("") && horario.getDescHorario() != null) {
			horarioAtualizado.setDescHorario(horario.getDescHorario());
		}
		
		if(horario.getCodigoHorario() != null) {
			horarioAtualizado.setCodigoHorario(horario.getCodigoHorario());
		}
		
		return horarioRepository.save(horarioAtualizado);
	}

}
