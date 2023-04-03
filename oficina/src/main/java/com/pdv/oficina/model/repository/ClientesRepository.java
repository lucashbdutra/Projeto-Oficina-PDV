package com.pdv.oficina.model.repository;


import com.pdv.oficina.model.entity.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientesRepository extends GenericCrudRepository<Cliente, Long> {

}
