package com.pdv.oficina.controller.rest;


import com.pdv.oficina.model.entity.Venda;
import com.pdv.oficina.model.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest/vendas")
public class VendaRest extends GenericCrudRest<Venda, Long, VendaService>{

    @Autowired
    private VendaService vendaService;

    @PostMapping("/realizarVenda")
    public ResponseEntity<Venda> realizarVenda(@RequestBody Venda venda){

        return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.makeSell(venda));
    }

}
