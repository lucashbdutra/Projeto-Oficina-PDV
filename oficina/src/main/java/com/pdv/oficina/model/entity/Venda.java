package com.pdv.oficina.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Venda extends GenericEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date data;
    private BigDecimal valor;
    private String mesAno;

    @ManyToMany
    @JoinColumn(name = "fk_produto_id")
    private List<Produto> produtos;

    @ManyToOne
    @JoinColumn(name = "fk_cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "fk_funcionario_id")
    private Funcionario funcionario;
}
