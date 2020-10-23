package com.dois.pack.api.repositorys;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dois.pack.api.models.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer>{

}
