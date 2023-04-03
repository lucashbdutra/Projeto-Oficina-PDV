package com.pdv.oficina.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class GastosMensais extends GenericEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal funcionarios;
    private BigDecimal energia;
    private BigDecimal agua;
    private BigDecimal internet;
    private BigDecimal custoProdutos;
    private BigDecimal vendas;
    private BigDecimal lucro;
    private String data;
    private Date fechamento;


}
