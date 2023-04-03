package com.pdv.oficina.model.service;


import com.pdv.oficina.model.DTO.LoginDTO;
import com.pdv.oficina.model.entity.Login;
import com.pdv.oficina.model.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    public final LoginRepository loginRepository;

    @Transactional(rollbackFor = Exception.class)
    public Login createLogin(Login login){
        try {
            String encoder = new BCryptPasswordEncoder().encode(login.getPassword());
            login.setPassword(encoder);

            loginRepository.saveAndFlush(login);


        } catch (Exception e){

            return null;
        }
        return login;
    }

    public LoginDTO authenticateLogin(LoginDTO login){
        Login client = loginRepository.findByUsername(login.getUsername()).get();

        String[] firstName = login.getUsername().split(" ");
        login.setUsername(firstName[0]);

        boolean passTest = new BCryptPasswordEncoder().matches(login.getPassword(), client.getPassword());

        if(passTest){

            return login;
        }
        return null;
    }



}
