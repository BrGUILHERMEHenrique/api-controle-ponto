package com.dois.pack.api.repositorys;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dois.pack.api.models.FuncionarioHorario;

@Repository
public interface FuncionarioHorarioRepository extends JpaRepository<FuncionarioHorario, Integer>{

	@Query(value="SELECT * FROM funcionario_horario WHERE id_funcionario = :idFuncionario", 
			nativeQuery=true)
	List<FuncionarioHorario> getWithIdFuncionario(Integer idFuncionario);
}
