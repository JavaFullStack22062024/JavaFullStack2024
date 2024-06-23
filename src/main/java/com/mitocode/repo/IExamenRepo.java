package com.mitocode.repo;

import com.mitocode.model.Examen;

//@Repository	Ya no es necesario por que hereda a IGenericRepo y este a su vez de JpaRepository
public interface IExamenRepo extends IGenericRepo<Examen, Integer> {
	
	

}
