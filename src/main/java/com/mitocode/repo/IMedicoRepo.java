package com.mitocode.repo;

import com.mitocode.model.Medico;

//@Repository	Ya no es necesario por que hereda a IGenericRepo y este a su vez de JpaRepository
public interface IMedicoRepo extends IGenericRepo<Medico, Integer> {

}
