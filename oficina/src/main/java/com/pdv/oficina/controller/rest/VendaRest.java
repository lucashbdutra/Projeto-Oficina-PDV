package com.pdv.oficina.controller.rest;


import com.pdv.oficina.OficinaApplication;
import com.pdv.oficina.model.DTO.VendasPorFuncionarioDTO;
import com.pdv.oficina.model.entity.Venda;
import com.pdv.oficina.model.service.VendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest/vendas")
public class VendaRest extends GenericCrudRest<Venda, Long, VendaService>{

    private static final Logger logger = LoggerFactory.getLogger(OficinaApplication.class);

    @Autowired
    private VendaService vendaService;

    @PostMapping("/realizarVenda/{username}")
    public ResponseEntity<Venda> realizarVenda(@RequestBody Venda venda, @PathVariable String username){

        logger.debug("Realizando a chamada do controller: " + this.getClass().getName()
                + " ). Realizando a chamada do service: " + this.service.getClass().getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.makeSell(venda, username));
    }

    @GetMapping("/vendasPorFuncionario/{username}/{mes}")
    public ResponseEntity<VendasPorFuncionarioDTO> vendasPorFuncionario(@PathVariable String username, @PathVariable String mes){

        logger.debug("Realizando a chamada do controller: " + this.getClass().getName()
                + " ). Realizando a chamada do service: " + this.service.getClass().getName());

        return ResponseEntity.ok().body(vendaService.vendasPorFuncionario(username, mes));
    }


}
