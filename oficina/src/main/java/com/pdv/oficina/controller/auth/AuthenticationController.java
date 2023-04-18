package com.pdv.oficina.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    /**
     * *Em ambos os métodos ele vai registrar ou autenticar um usuário e retornar um token criado para aquele user.
     * @param request
     * @return
     */
    @PostMapping("/registerAdmin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody RegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerAdmin(request));

    }

    @PostMapping("/registerFuncionario/{idFuncionario}")
    public ResponseEntity<AuthenticationResponse> registerFuncionario(@PathVariable Long idFuncionario,@RequestBody RegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerFuncionario(idFuncionario, request));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping
    public boolean isFirstLogin(){
        return service.isFirstLogin();
    }



}
