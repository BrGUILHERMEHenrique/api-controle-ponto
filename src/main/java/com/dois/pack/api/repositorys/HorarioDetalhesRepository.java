package com.dois.pack.api.repositorys;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dois.pack.api.models.HorarioDetalhes;

@Repository
public interface HorarioDetalhesRepository extends JpaRepository<HorarioDetalhes, Integer>{

	@Query(value = "SELECT * FROM horario_detalhes where id_horario = :idHorario", 
				nativeQuery=true)
	List<HorarioDetalhes> getAllWithIdHorario(Integer idHorario);
	
	@Query(value="SELECT COUNT(*) FROM horario_detalhes WHERE id_horario = :idHorario", nativeQuery=true)
	Integer getCount(Integer idHorario);
	
	@Query(value="SELECT * FROM horario_detalhes WHERE codigo_dia = :codigoDia", nativeQuery=true)
	HorarioDetalhes getWithCodigoDia(Integer codigoDia);
}
