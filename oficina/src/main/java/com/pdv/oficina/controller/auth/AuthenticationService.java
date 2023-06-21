package com.pdv.oficina.controller.auth;

import com.pdv.oficina.OficinaApplication;
import com.pdv.oficina.controller.security.JwtService;
import com.pdv.oficina.controller.security.Role;
import com.pdv.oficina.model.entity.Funcionario;
import com.pdv.oficina.model.entity.Login;
import com.pdv.oficina.model.repository.FuncionariosRepository;
import com.pdv.oficina.model.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(OficinaApplication.class);

    private final LoginRepository loginRepository;
    private final FuncionariosRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     *
     * @param request
     * @return
     */
    public AuthenticationResponse registerAdmin(RegisterRequest request) {

        var user = Login.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();

        loginRepository.save(user);
        logger.info("Criando login para: " + request.getUsername() + ", Role: " + Role.ADMIN);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder() //* Vai retornar o token gerado para o usuário
                .token(jwtToken)
                .build();


    }

    public AuthenticationResponse registerFuncionario(Long idFuncionario, RegisterRequest request){
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario).orElse(null);

        var user = Login.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .funcionario(funcionario)
                .role(Role.FUNCIONARIO)
                .build();

        loginRepository.save(user);
        logger.info("Criando login para: " + request.getUsername() + ", Role: " + Role.FUNCIONARIO);

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

        var user = loginRepository.findByUsername(request.getUsername()).orElse(null);

        //*Ambos os métodos dessa classe vão gerar um novo token ao criar e ao autenticar um usuário.
        var jwtToken = jwtService.generateToken(user);


        Role role = user.getRole();
        if(role == Role.FUNCIONARIO){
            return AuthenticationResponse.builder()
                    .username(user.getUsername())
                    .funcionario(true)
                    .token(jwtToken)
                    .build();
        }

        return AuthenticationResponse.builder()
                .username(user.getUsername())
                .funcionario(false)
                .token(jwtToken)
                .build();
    }

    public boolean isFirstLogin(){
        long cadastros = loginRepository.count();
        return cadastros == 0;
    }

}
