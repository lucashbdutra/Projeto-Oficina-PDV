package com.pdv.oficina.controller.rest;


import com.pdv.oficina.model.entity.Produto;
import com.pdv.oficina.model.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest/produtos")
public class ProdutoRest extends GenericCrudRest<Produto, Long, ProdutoService>{

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/create")
    public ResponseEntity<Produto> create(@RequestBody Produto produto){

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.cadastrar(produto));
    }

}
