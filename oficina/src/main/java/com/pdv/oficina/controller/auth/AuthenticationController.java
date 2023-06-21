package com.pdv.oficina.controller.auth;

import com.pdv.oficina.OficinaApplication;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(OficinaApplication.class);

    private final AuthenticationService service;

    /**
     * *Em ambos os métodos ele vai registrar ou autenticar um usuário e retornar um token criado para aquele user.
     * @param request
     * @return
     */
    @PostMapping("/registerAdmin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody RegisterRequest request){

        logger.debug("Realizando a chamada do controller: " + this.getClass().getName()
                + " ). Realizando a chamada do service: " + this.service.getClass().getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerAdmin(request));

    }

    @PostMapping("/registerFuncionario/{idFuncionario}")
    public ResponseEntity<AuthenticationResponse> registerFuncionario(@PathVariable Long idFuncionario,@RequestBody RegisterRequest request){

        logger.debug("Realizando a chamada do controller: " + this.getClass().getName()
                + " ). Realizando a chamada do service: " + this.service.getClass().getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerFuncionario(idFuncionario, request));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){

        logger.debug("Realizando a chamada do controller: " + this.getClass().getName()
                + " ). Realizando a chamada do service: " + this.service.getClass().getName());

        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping
    public boolean isFirstLogin(){

        logger.debug("Realizando a chamada do controller: " + this.getClass().getName()
                + " ). Realizando a chamada do service: " + this.service.getClass().getName());

        return service.isFirstLogin();
    }



}
