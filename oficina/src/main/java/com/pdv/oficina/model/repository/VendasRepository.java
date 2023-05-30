package com.pdv.oficina.model.repository;


import com.pdv.oficina.model.entity.Venda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendasRepository extends GenericCrudRepository<Venda, Long> {

    public List<Venda> findByMesAno(String mesAno);

    @Query("select v from Venda v where v.login.username = ?1 and month(v.data) = ?2")
    List<Venda> findAllByUsernameAndMonth(String username, String month);

}
