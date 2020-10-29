package com.dois.pack.api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dois.pack.api.models.Apontamento;

@Repository
public interface ApontamentoRepository extends JpaRepository<Apontamento, Integer> {

}
