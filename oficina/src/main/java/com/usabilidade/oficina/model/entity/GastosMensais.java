package com.usabilidade.oficina.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

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


}
