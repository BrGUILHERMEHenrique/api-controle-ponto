package com.dois.pack.api.repositorys;


import java.time.LocalDate;
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
	
	@Query(value="SELECT id_horario FROM funcionario_horario WHERE funcionario_horario.id_funcionario = :idFuncionario AND :data >=  vigencia_inicial AND :data <= vigencia_final", 
				nativeQuery=true)
	Integer getIdHorario(Integer idFuncionario, LocalDate data);
	
	@Query(value="SELECT * FROM funcionario_horario WHERE id_funcionario = :idFuncionario AND id_horario = :idHorario AND :data >= vigencia_inicial AND :data <= vigencia_final", 
				nativeQuery=true)
	FuncionarioHorario getFuncionario(Integer idFuncionario, Integer idHorario, LocalDate data);
}
