package com.dois.pack.api.repositorys;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dois.pack.api.models.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer>{

	@Query(value = "FROM funcionario where cod_matricula = :codMatricula",
			nativeQuery = true)
	Funcionario getWithMatricula(String codMatricula);
	
	@Query(value = "SELECT F.id,"
			+ "F.cod_matricula as cod,"
			+ "F.nome as nome,"
			+ "F.cpf,"
			+ "F.data_nascimento,"
			+ "F.telefone,"
			+ "F.id_empresa,"
			+"H.codigo_horario,"
			+ "HD.entrada_1, HD.saida_1,"
			+ "HD.entrada_2, HD.saida_2 "
			+ "FROM funcionario F Inner Join funcionario_horario FH on (FH.id_funcionario = F.id)"
								+ "Inner Join horario H on (FH.id_horario = H.id)"
								+ "Inner Join horario_detalhes HD on (HD.id_horario = H.id) ",
								nativeQuery=true)
	List<?> getInnerJoin();
}
