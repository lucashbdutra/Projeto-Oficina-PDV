package com.pdv.oficina.controller.rest;


import com.pdv.oficina.OficinaApplication;
import com.pdv.oficina.model.entity.GastosMensais;
import com.pdv.oficina.model.service.GastosMensaisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest/gastos")
public class GastosMensaisRest extends GenericCrudRest<GastosMensais, Long, GastosMensaisService>{

    private static final Logger logger = LoggerFactory.getLogger(OficinaApplication.class);

    @Autowired
    private GastosMensaisService gastosService;

    @PostMapping("/gerarGastos")
    public ResponseEntity<GastosMensais> gerarGastos(@RequestBody GastosMensais gastos){

        logger.debug("Realizando a chamada do controller: " + this.getClass().getName()
                + " ). Realizando a chamada do service: " + this.service.getClass().getName());

        return ResponseEntity.ok().body(gastosService.gastoMensal(gastos));
    }

    @PostMapping("/fechamento")
    public ResponseEntity<GastosMensais> salvarFechamento(@RequestBody GastosMensais gastos){

        logger.debug("Realizando a chamada do controller: " + this.getClass().getName()
                + " ). Realizando a chamada do service: " + this.service.getClass().getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(gastosService.salvarGastos(gastos));
    }
}
