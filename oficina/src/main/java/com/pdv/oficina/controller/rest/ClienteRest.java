package com.pdv.oficina.controller.rest;


import com.pdv.oficina.model.entity.Cliente;
import com.pdv.oficina.model.service.ClienteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest/clientes")
public class ClienteRest extends GenericCrudRest<Cliente, Long, ClienteService> {


}
