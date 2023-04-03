package com.pdv.oficina.controller.rest;


import com.pdv.oficina.model.entity.Funcionario;
import com.pdv.oficina.model.service.FuncionarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest/funcionarios")
public class FuncionarioRest extends GenericCrudRest<Funcionario, Long, FuncionarioService>{
}
