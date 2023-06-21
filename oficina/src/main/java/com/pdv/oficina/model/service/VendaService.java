package com.pdv.oficina.model.service;


import com.pdv.oficina.OficinaApplication;
import com.pdv.oficina.model.DTO.VendasPorFuncionarioDTO;
import com.pdv.oficina.model.entity.Funcionario;
import com.pdv.oficina.model.entity.Login;
import com.pdv.oficina.model.entity.Produto;
import com.pdv.oficina.model.entity.Venda;
import com.pdv.oficina.model.repository.LoginRepository;
import com.pdv.oficina.model.repository.ProdutosRepository;
import com.pdv.oficina.model.repository.VendasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class VendaService extends GenericCrudService<Venda, Long, VendasRepository>{

    private static final Logger logger = LoggerFactory.getLogger(OficinaApplication.class);

    @Autowired
    private VendasRepository vendasRepository;
    @Autowired
    private ProdutosRepository produtosRepository;
    @Autowired
    private LoginRepository loginRepository;


    public Venda makeSell(Venda venda, String username){

        Date date = new Date();
        Format format = new SimpleDateFormat("MM/yyyy");
        List<Produto> produtos = venda.getProdutos();
        long subtotal = 0;
        BigDecimal total;
        Login usuario = loginRepository.findByUsername(username).orElse(null);

        for(Produto produto: produtos){

            Produto alterQuant = produtosRepository.findById(produto.getId())
                    .orElseThrow(IndexOutOfBoundsException::new);

            if(produto.getIsService()){

                subtotal += produto.getValorFinal().floatValue();

            } else {

                alterQuant.setQuantidade(alterQuant.getQuantidade() - produto.getQuantidade());
                subtotal += produto.getQuantidade() * produto.getValorCusto().floatValue();

                alterQuant.setValorFinal(alterQuant.getValorFinal().subtract(BigDecimal.valueOf(subtotal)));
                produtosRepository.save(alterQuant);
            }

        }

        total = BigDecimal.valueOf(subtotal);
        venda.setData(date);
        venda.setValor(total);
        venda.setMesAno(format.format(date));
        venda.setLogin(usuario);

        vendasRepository.save(venda);
        logger.info("Venda realizada no valor de: " + venda.getValor() + ", pelo funcionáirio: " + username + ", na data: " + date);
        return venda;
    }

    public VendasPorFuncionarioDTO vendasPorFuncionario(String username, String mes){
        List<Venda> vendas = vendasRepository.findAllByUsernameAndMonth(username, mes);

        if(vendas.isEmpty()){
            Login user = loginRepository.findByUsername(username).orElse(null);
            Funcionario funcionario = user.getFuncionario();
            BigDecimal valorTotalVendas = this.valorTotalVendas(vendas);

            VendasPorFuncionarioDTO vendasFuncionarioDTO = new VendasPorFuncionarioDTO();
            vendasFuncionarioDTO.setFuncionario(funcionario);
            vendasFuncionarioDTO.setVendas(vendas);
            vendasFuncionarioDTO.setMes(mes);
            vendasFuncionarioDTO.setValorTotal(valorTotalVendas);

            return vendasFuncionarioDTO;
        }

        return null;

    }

    private BigDecimal valorTotalVendas(List<Venda> vendas) {
        BigDecimal somaVendas = new BigDecimal(0);
        for(Venda venda: vendas){
            somaVendas = somaVendas.add(venda.getValor());
        }

        return somaVendas;
    }


}
