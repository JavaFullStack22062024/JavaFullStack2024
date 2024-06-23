package com.mitocode.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.ConsultaExamen;
import com.mitocode.model.ConsultaExamenPK;

public interface IConsultaExamenRepo extends IGenericRepo<ConsultaExamen, ConsultaExamenPK> {
	
	
//	@Transactional //Si ocurre algun erro hace el rolback pero solo en este ambito de la tabla.
//	Para que sea completamente transacional en el ambito (consulta-detalle)-examen se debe poner a nivel de clase en ConsultaServiceImpl
	@Modifying //Para insertar-modificar-eliminar  Porque el sql nativo regrea un resulset y jpa espera un objeto.
	@Query(value = "INSERT INTO cosulta_examen(id_conaulta, id_examen) VALUES (:idConsulta, :idExamen", nativeQuery = true)
	Integer registrar(@Param("idConsulta") Integer idConsulta, @Param("idExamen") Integer idExamen);
}
