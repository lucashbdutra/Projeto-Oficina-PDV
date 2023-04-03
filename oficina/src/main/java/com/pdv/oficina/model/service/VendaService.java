package com.pdv.oficina.model.service;


import com.pdv.oficina.model.entity.Produto;
import com.pdv.oficina.model.entity.Venda;
import com.pdv.oficina.model.repository.ProdutosRepository;
import com.pdv.oficina.model.repository.VendasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class VendaService extends GenericCrudService<Venda, Long, VendasRepository>{

    @Autowired
    private VendasRepository vendasRepository;
    @Autowired
    private ProdutosRepository produtosRepository;


    public Venda makeSell(Venda venda){

        Date date = new Date();
        Format format = new SimpleDateFormat("MM/yyyy");
        List<Produto> produtos = venda.getProdutos();
        long subtotal = 0;
        BigDecimal total;

        for(Produto produto: produtos){

            Produto alterQuant = produtosRepository.findById(produto.getId())
                    .orElseThrow(IndexOutOfBoundsException::new);

            if(produto.getIsService()){

                subtotal += produto.getValorFinal().floatValue();

            } else {

                alterQuant.setQuantidade(alterQuant.getQuantidade() - produto.getQuantidade());
                produtosRepository.save(alterQuant);

                subtotal += produto.getQuantidade() * produto.getValorFinal().floatValue();
            }

        }

        total = BigDecimal.valueOf(subtotal);
        venda.setData(date);
        venda.setValor(total);
        venda.setMesAno(format.format(date));

        vendasRepository.save(venda);
        return venda;
    }


}
