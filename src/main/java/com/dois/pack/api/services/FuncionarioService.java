package com.dois.pack.api.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dois.pack.api.exceptions.SameCpfException;
import com.dois.pack.api.models.Funcionario;
import com.dois.pack.api.repositorys.FuncionarioRepository;

@Service
public class FuncionarioService {
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Transactional
	public Funcionario create(Funcionario funcionario) throws SameCpfException {
		Funcionario funcionarioAchado = funcionarioRepository.getWithMatricula(funcionario.getCodMatricula());
		if(funcionarioAchado == null) {
			return funcionarioRepository.save(funcionario);	
		} else {
			throw new SameCpfException(funcionario.getCodMatricula());
		}
	}
	
	@Transactional
	public List<Funcionario> getAll() {
		return funcionarioRepository.findAll();
	}
	
	@Transactional
	public List<?> getInnerJoin(){
		return funcionarioRepository.getInnerJoin();
	}
	
	@Transactional
	public Optional<Funcionario> getbyId(Integer id) {
		return funcionarioRepository.findById(id);
	}
	
	@Transactional
	public Funcionario getByMatricula(String codMatricula) {
		return funcionarioRepository.getWithMatricula(codMatricula);
	}
	
	@Transactional
	public boolean delete(Integer id) {
		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
		if(funcionario.isPresent()) {
			funcionarioRepository.delete(funcionario.get());
			return true;
		}else {
			return false;
		}
	}
	
	@Transactional
	public Funcionario update(Integer id, Funcionario funcionario) {
		Optional<Funcionario> funcionarioAntigo = funcionarioRepository.findById(id);
		Funcionario funcionarioAtualizado = funcionarioAntigo.get();
		
		if(!funcionario.getNome().equals("") && funcionario.getNome() != null) {
			funcionarioAtualizado.setNome(funcionario.getNome());
		}
		
		if(funcionario.getDataNascimento() != null) {
			funcionarioAtualizado.setDataNascimento(funcionario.getDataNascimento());
		}
		
		if(!funcionario.getTelefone().equals("") && funcionario.getTelefone() != null) {
			funcionarioAtualizado.setTelefone(funcionario.getTelefone());
		}
		
		return funcionarioRepository.save(funcionarioAtualizado);
	}
}
