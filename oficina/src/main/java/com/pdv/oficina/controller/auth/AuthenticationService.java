package com.pdv.oficina.controller.auth;

import com.pdv.oficina.controller.security.JwtService;
import com.pdv.oficina.controller.security.Role;
import com.pdv.oficina.model.entity.Login;
import com.pdv.oficina.model.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final LoginRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * !Adcionar parâmetros no RegisterRequest para identificar se é Cliente ou Diarista
     * @param request
     * @return
     */
    public AuthenticationResponse register(RegisterRequest request) {

        long cadastros = repository.count();
        boolean isFuncionario = request.isFuncionario();

        if(cadastros == 0 || !isFuncionario){

            var user = Login.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ADMIN)
                    .build();

            repository.save(user);

            var jwtToken = jwtService.generateToken(user);

            return AuthenticationResponse.builder() //* Vai retornar o token gerado para o usuário
                    .token(jwtToken)
                    .build();

        }

        var user = Login.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.FUNCIONARIO)
                .build();

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder() //* Vai retornar o token gerado para o usuário
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        //*Vai fazer o trabalho de autenticação por mim, caso não esteja autenticado vai lançar uma exception
        //*Caso passe desse ponto significa que o username e password estão corretos.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var user = repository.findByUsername(request.getUsername()).orElse(null);

        //*Ambos os métodos dessa classe vão gerar um novo token ao criar e ao autenticar um usuário.
        var jwtToken = jwtService.generateToken(user);


//        Role role = user.getRole();
//        if(role == Role.DIARISTA){
//            return AuthenticationResponse.builder()
//                    .token(jwtToken)
//                    .build();
//        }

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
