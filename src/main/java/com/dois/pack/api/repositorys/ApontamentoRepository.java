package com.dois.pack.api.repositorys;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dois.pack.api.models.Apontamento;

@Repository
public interface ApontamentoRepository extends JpaRepository<Apontamento, Integer> {

	@Query(value="SELECT * FROM apontamento WHERE id_funcionario = :idFuncionario AND data BETWEEN :primaryDate AND :secundaryDate", 
			nativeQuery=true)
	List<Apontamento> getByIdAndDate(Integer idFuncionario, LocalDate primaryDate, LocalDate secundaryDate);
	
	@Query(value="SELECT * FROM apontamento WHERE id_funcionario  = :idFuncionario", 
			nativeQuery=true)
	List<Apontamento> getbyIdFuncionario(Integer idFuncionario);
}
