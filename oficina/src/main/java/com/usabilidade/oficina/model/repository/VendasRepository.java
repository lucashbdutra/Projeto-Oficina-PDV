package com.usabilidade.oficina.model.repository;

import com.usabilidade.oficina.model.entity.Venda;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendasRepository extends GenericCrudRepository<Venda, Long> {

    public List<Venda> findByMesAno(String mesAno);
}
