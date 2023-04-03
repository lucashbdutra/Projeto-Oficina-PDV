package com.pdv.oficina.model.repository;


import com.pdv.oficina.model.entity.GenericEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericCrudRepository<T extends GenericEntity<I>, I> extends JpaRepository<T, I> {



}