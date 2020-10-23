package com.dois.pack.api.repositorys;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dois.pack.api.models.HorarioDetalhes;

@Repository
public interface HorarioDetalhesRepository extends JpaRepository<HorarioDetalhes, Integer>{

}
