package com.pdv.oficina.controller.rest;


import com.pdv.oficina.model.DTO.LoginDTO;
import com.pdv.oficina.model.entity.Login;
import com.pdv.oficina.model.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest/login")
public class LoginRest {

    @Autowired
    public LoginService loginService;

    @PostMapping
    public ResponseEntity<Login> createUser(@RequestBody Login login){

        return ResponseEntity.status(HttpStatus.CREATED).body(loginService.createLogin(login));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<LoginDTO> authenticate(@RequestBody LoginDTO loginDTO){

        return ResponseEntity.ok().body(loginService.authenticateLogin(loginDTO));
    }
}
