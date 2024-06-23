package com.mitocode.repo;

import com.mitocode.model.Paciente;

//@Repository	Ya no es necesario por que hereda a IGenericRepo y este a su vez de JpaRepository
public interface IPacienteRepo extends IGenericRepo<Paciente, Integer> {
	
	

}
