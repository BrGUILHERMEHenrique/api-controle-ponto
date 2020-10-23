package com.dois.pack.api.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dois.pack.api.models.FuncionarioHorario;
import com.dois.pack.api.repositorys.FuncionarioHorarioRepository;

@Service
public class FuncionarioHorarioService {
	
	@Autowired
	FuncionarioHorarioRepository funcionarioHorarioRepository;
	
	@Transactional
	public FuncionarioHorario create(FuncionarioHorario funcionarioHorario) {
		return funcionarioHorarioRepository.save(funcionarioHorario);
	}
	
	@Transactional
	public List<FuncionarioHorario> getWithIdFuncionario(Integer idFuncionario) {
		return funcionarioHorarioRepository.getWithIdFuncionario(idFuncionario);
	}
	
	@Transactional
	public List<FuncionarioHorario> getAll() {
		return funcionarioHorarioRepository.findAll();
	}
	
	@Transactional
	public Optional<FuncionarioHorario> getbyId(Integer id) {
		return funcionarioHorarioRepository.findById(id);
	}
	
	@Transactional
	public boolean delete(Integer id) {
		Optional<FuncionarioHorario> funcionarioHorario = funcionarioHorarioRepository.findById(id);
		if(funcionarioHorario.isPresent()) {
			funcionarioHorarioRepository.delete(funcionarioHorario.get());
			return true;
		}else {
			return false;
		}
	}
	
	@Transactional
	public FuncionarioHorario update(Integer id, FuncionarioHorario funcionarioHorario) {
		Optional<FuncionarioHorario> funcionarioHorarioAntigo = funcionarioHorarioRepository.findById(id);
		FuncionarioHorario funcionarioHorarioAtualizado = funcionarioHorarioAntigo.get();
		
		if(funcionarioHorario.getCodigoInicial() != null) {
			funcionarioHorarioAtualizado.setCodigoInicial(funcionarioHorario.getCodigoInicial());
		}
		
		if(funcionarioHorario.getIdFuncionario() != null) {
			funcionarioHorarioAtualizado.setIdFuncionario(funcionarioHorario.getIdFuncionario());
		}
		
		if(funcionarioHorario.getIdHorario() != null) {
			funcionarioHorarioAtualizado.setIdHorario(funcionarioHorario.getIdHorario());
		}
		
		if(funcionarioHorario.getVigenciaFinal() != null) {
			funcionarioHorarioAtualizado.setVigenciaFinal(funcionarioHorario.getVigenciaFinal());
		}
		
		if(funcionarioHorario.getVigenciaInicial() != null) {
			funcionarioHorarioAtualizado.setVigenciaInicial(funcionarioHorario.getVigenciaInicial());
		}
		
		return funcionarioHorarioRepository.save(funcionarioHorarioAtualizado);
	}
}
