package com.pdv.oficina.model.repository;


import com.pdv.oficina.model.entity.Produto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutosRepository extends GenericCrudRepository<Produto, Long> {

    public List<Produto> findByData(String data);
}
