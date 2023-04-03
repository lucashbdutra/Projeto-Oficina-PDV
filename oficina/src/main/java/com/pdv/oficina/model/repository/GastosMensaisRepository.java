package com.pdv.oficina.model.repository;


import com.pdv.oficina.model.entity.GastosMensais;
import org.springframework.stereotype.Repository;

@Repository
public interface GastosMensaisRepository extends GenericCrudRepository<GastosMensais, Long> {
}
