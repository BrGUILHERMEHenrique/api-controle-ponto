package com.dois.pack.api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dois.pack.api.models.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer>{

	@Query(value="SELECT * FROM empresa WHERE cnpj = :cnpj",
			nativeQuery=true)
	Empresa getWithCnpj(String cnpj);
}