package com.usabilidade.oficina.model.service;

import com.usabilidade.oficina.model.entity.Funcionario;
import com.usabilidade.oficina.model.entity.GastosMensais;
import com.usabilidade.oficina.model.entity.Produto;
import com.usabilidade.oficina.model.entity.Venda;
import com.usabilidade.oficina.model.repository.FuncionariosRepository;
import com.usabilidade.oficina.model.repository.GastosMensaisRepository;
import com.usabilidade.oficina.model.repository.ProdutosRepository;
import com.usabilidade.oficina.model.repository.VendasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class GastosMensaisService extends GenericCrudService<GastosMensais, Long, GastosMensaisRepository>{

    @Autowired
    private GastosMensaisRepository gastosRepository;
    @Autowired
    private FuncionariosRepository funcionariosRepository;
    @Autowired
    private VendasRepository vendasRepository;
    @Autowired
    private ProdutosRepository produtosRepository;

    public GastosMensais gastoMensal(GastosMensais gastos){

        BigDecimal somaSalario = new BigDecimal("0");
        BigDecimal somaVendas = new BigDecimal("0");
        BigDecimal somaCustoProdutos = new BigDecimal("0");
        BigDecimal total = new BigDecimal("0");

        Class<GastosMensais> gastosMensais = GastosMensais.class;

        List<Funcionario> funcionarios = funcionariosRepository.findAll();
        List<Venda> vendas = vendasRepository.findByMesAno(gastos.getData());
        List<Produto> produtos = produtosRepository.findByData(gastos.getData());

        for(Venda venda: vendas){
            somaVendas = somaVendas.add(venda.getValor());
        }

        for(Funcionario funcionario : funcionarios){
            somaSalario = somaSalario.add(funcionario.getSalario());
        }

        for(Produto produto : produtos){
            somaCustoProdutos = somaCustoProdutos.add(produto.getValorFinal());
        }

        total = total.add(somaCustoProdutos);
        total = total.add(somaSalario);
        total = total.add(gastos.getAgua());
        total = total.add(gastos.getEnergia());
        total = total.add(gastos.getInternet());

        gastos.setVendas(somaVendas);
        gastos.setCustoProdutos(somaCustoProdutos);
        gastos.setLucro(somaVendas.subtract(total));
        gastos.setFuncionarios(somaSalario);


        return gastos;

    }

    public GastosMensais salvarGastos(GastosMensais gastos){

        Date date = new Date();
        Format format = new SimpleDateFormat("MM/yyyy");

        gastos.setData(format.format(date));
        gastos.setFechamento(date);

        gastosRepository.save(gastos);

        return gastos;
    }


}
