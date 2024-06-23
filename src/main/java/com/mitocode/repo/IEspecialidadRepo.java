package com.mitocode.repo;

import com.mitocode.model.Especialidad;

//@Repository	Ya no es necesario por que hereda a IGenericRepo y este a su vez de JpaRepository
public interface IEspecialidadRepo extends IGenericRepo<Especialidad, Integer> {
	
	

}
