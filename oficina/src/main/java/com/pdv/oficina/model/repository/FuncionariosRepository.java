package com.pdv.oficina.model.repository;


import com.pdv.oficina.model.entity.Funcionario;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionariosRepository extends GenericCrudRepository<Funcionario, Long> {
}
