package com.pdv.oficina.controller.security;


import com.pdv.oficina.model.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    public final LoginRepository loginRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> loginRepository.findByUsername(username)
                .orElse(null);
    }

    /**
     * *É O responsável por passar quem é o userDetailsService e qual o passwordEncoder, essa é uma das suas implementações
     * * o DaoAuthenticationProvider (DAO = Data Access Object)

     * *Caso necessário buscar usuários em lugares diferentes tem de haver outras implementações desse bean pra esse propósito.
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService()); //* O bean responsavel pro buscar o usuario no banco
        authProvider.setPasswordEncoder(passwordEncoder()); //* O bean responsável pro encriptar o password.
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
