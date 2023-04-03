package com.pdv.oficina.controller.security;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * *Essa classe vai fazer um filtro em cada requisisção feita para o back para verificar se há um token
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); //Aqui vamos extrair o token do header da requisição
        final String jwt;
        final String username;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){ //Os tokens sempre começam com 'Bearer ...'
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); //* Aqui pegamos a string a partir da 7ª posição pois excluimos o 'Bearer '
        username = jwtService.extractUsername(jwt); //Extraio o username do token

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){ //verifica se foi possível extrair o user e se ele ainda não foi autenticado
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); //Tento encontrar esse username no banco.

            if(jwtService.isTokenValid(jwt, userDetails)){ //Verifico se ele não esta expirado e se o subject é o mesmo nome do usuário que eu encontrei
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, //* Necessário para atualizar o Security COntext
                        null, //credentials
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //* É gerado os details a partir do request
                SecurityContextHolder.getContext().setAuthentication(authToken); //* Finalmente atualizamos o Security Context
            }
        }
        filterChain.doFilter(request, response);
    }
}
