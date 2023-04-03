package com.pdv.oficina.controller.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    private static final String SECRET_KEY = "28472B4B6250655368566D597133743677397A244226452948404D635166546A"; //! Usado para assinar digitalmente o jwt.

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); //O subject seria o username
    }


    /**
     * *Extrai uma única reinvindicação
     * @param token
     * @param claimsResolver
     * @return
     * @param <T>
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    /**
     * *Extrai todas as reinvindicações da requisição
     * @param token
     * @return
     */
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey()) //senha usada pra assinar digitalmente o jwt(jason web token)
                .build()
                .parseClaimsJws(token) //Verificar diferença entre jwt e jws
                .getBody();
    }



    /**
     * *Esse método vai decodificar a chave e retorna-la ao método acima
     * @return
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


   //*Método para geração de tokens sem claims (APENAS COM AS INFORMAÇÕES DO USUÁRIO, SEM EXTRA CLAIMS)
    public String generateToken(UserDetails userDetails){

        return generateToken(new HashMap<>(), userDetails);
    }


   //*Método para geração de tokens com claims (COMPLETO)
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){

        return Jwts
                .builder()
                .setClaims(extraClaims) //Qualquer claim extra que eu queira adcionar
                .setSubject(userDetails.getUsername()) //A quem pertence o token
                .setIssuedAt(new Date(System.currentTimeMillis())) //Data que o token foi criado
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) //vai ser válido por 24h
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // A chave pra assinatura e qual algorítimo vai ser utilizado
                .compact();

    }

    /**
     * *Verifica se o token ainda é válido, ou seja,
     * *se o nome do sujeito é o mesmo do usuário e se o token não está expirado.
     * @param token
     * @param userDetails
     * @return
     */
    public boolean isTokenValid(String token, UserDetails userDetails){ //
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }
}
