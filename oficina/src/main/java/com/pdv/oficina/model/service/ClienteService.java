package com.pdv.oficina.model.service;


import com.pdv.oficina.model.entity.Cliente;
import com.pdv.oficina.model.repository.ClientesRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService extends GenericCrudService<Cliente, Long, ClientesRepository>{

}

