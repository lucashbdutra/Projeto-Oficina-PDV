package com.pdv.oficina.model.DTO;

import com.pdv.oficina.model.entity.Funcionario;
import com.pdv.oficina.model.entity.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendasPorFuncionarioDTO {

    private Funcionario funcionario;
    private List<Venda> vendas;
    private String mes;
    private BigDecimal valorTotal;
}
