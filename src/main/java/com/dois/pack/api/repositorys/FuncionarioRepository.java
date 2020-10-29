package com.dois.pack.api.repositorys;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dois.pack.api.models.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer>{

	@Query(value = "SELECT * FROM funcionario where pis = :pis",
			nativeQuery = true)
	Funcionario getWithPis(String pis);
	

}
