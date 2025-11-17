package br.edu.ifsul.derick.tads_springboot.infra.security;

import br.edu.ifsul.derick.tads_springboot.infra.exception.TokenInvalidoException;
import br.edu.ifsul.derick.tads_springboot.user.User; // Importe o seu User
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {

    // Vamos definir essa propriedade no application.properties
    @Value("${api.security.token.secret}")
    private String secret;

    // Mudei o nome do "Emissor" para o nome do seu projeto
    private final String ISSUER = "API CardNest";

    // Método para GERAR o token
    public String geraToken(User user){ // Adaptado para a sua classe User
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getUsername()) // Pega o email do seu User
                    .withIssuedAt(Instant.now()) // Data de geração
                    .withExpiresAt(Instant.now().plus(Duration.ofHours(2))) // Expira em 2h
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar o token JWT.", exception);
        }
    }

    // Método para VALIDAR o token
    public String getSubject(String tokenJWT){
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject(); // Retorna o "assunto" (o email do usuário)
        } catch (JWTVerificationException exception){
            throw new TokenInvalidoException("Token JWT inválido ou expirado.");
        }
    }
}