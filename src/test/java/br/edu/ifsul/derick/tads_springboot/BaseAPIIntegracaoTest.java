package br.edu.ifsul.derick.tads_springboot;

import br.edu.ifsul.derick.tads_springboot.user.User;
import br.edu.ifsul.derick.tads_springboot.user.UserRepository;
import br.edu.ifsul.derick.tads_springboot.infra.security.TokenService;

import br.edu.ifsul.derick.tads_springboot.infra.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // Força o uso do application-test.properties
public abstract class BaseAPIIntegracaoTest {

    @Autowired
    protected TestRestTemplate rest;

    @Autowired
    private UserRepository userRepository; // Adaptado para seu repositório

    @Autowired
    private TokenService tokenService;

    private String jwtToken = "";

    @BeforeEach
    public void setupTest() {
        // Busca o Admin que inserimos no data.sql
        User admin = userRepository.findByEmail("admin@email.com");
        assertNotNull(admin, "O Admin de teste não foi encontrado no banco H2. Verifique o data.sql");

        // Gera token
        jwtToken = tokenService.geraToken(admin);
        assertNotNull(jwtToken);
    }

    protected HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }

    protected <T> ResponseEntity<T> post(String url, Object body, Class<T> responseType) {
        return rest.exchange(url, POST, new HttpEntity<>(body, getHeaders()), responseType);
    }

    protected <T> ResponseEntity<T> put(String url, Object body, Class<T> responseType) {
        return rest.exchange(url, PUT, new HttpEntity<>(body, getHeaders()), responseType);
    }

    protected <T> ResponseEntity<T> get(String url, Class<T> responseType) {
        return rest.exchange(url, GET, new HttpEntity<>(getHeaders()), responseType);
    }

    protected <T> ResponseEntity<T> delete(String url, Class<T> responseType) {
        return rest.exchange(url, DELETE, new HttpEntity<>(getHeaders()), responseType);
    }
}