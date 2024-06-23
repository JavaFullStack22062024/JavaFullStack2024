package com.mitocode.repo;

import com.mitocode.model.Consulta;

//@Repository	Ya no es necesario por que hereda a IGenericRepo y este a su vez de JpaRepository
public interface IConsultaRepo extends IGenericRepo<Consulta, Integer> {
	
	

}
