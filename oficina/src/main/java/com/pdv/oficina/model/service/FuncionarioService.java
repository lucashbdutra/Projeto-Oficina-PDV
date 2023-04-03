package com.pdv.oficina.model.service;


import com.pdv.oficina.model.entity.Funcionario;
import com.pdv.oficina.model.repository.FuncionariosRepository;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService extends GenericCrudService<Funcionario, Long, FuncionariosRepository>{
}
