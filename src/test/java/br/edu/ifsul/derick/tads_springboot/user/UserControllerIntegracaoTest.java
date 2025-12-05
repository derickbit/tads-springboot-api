package br.edu.ifsul.derick.tads_springboot.user;

import br.edu.ifsul.derick.tads_springboot.BaseAPIIntegracaoTest;
import br.edu.ifsul.derick.tads_springboot.TadsSpringbootDerickApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TadsSpringbootDerickApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegracaoTest extends BaseAPIIntegracaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Helper para pegar lista
    private ResponseEntity<List<UserDtoResponse>> getUsersList(String url) {
        return rest.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(getHeaders()),
                new ParameterizedTypeReference<>() {}
        );
    }

    @BeforeEach
    void popularBanco() {
        // 1. Limpa o banco para garantir estado zerado
        jdbcTemplate.execute("DELETE FROM users_perfis");
        jdbcTemplate.execute("DELETE FROM users");
        jdbcTemplate.execute("DELETE FROM perfis");

        // 2. Inserts dos Perfis (IDs manuais 10 e 20)
        jdbcTemplate.execute("INSERT INTO perfis(id, nome) VALUES (10, 'ROLE_ADMIN')");
        jdbcTemplate.execute("INSERT INTO perfis(id, nome) VALUES (20, 'ROLE_USER')");

        // 3. Inserts dos Usuários
        // Senha 'admin' criptografada: $2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00dmxs.AQiy3.a
        jdbcTemplate.execute("INSERT INTO users (id, name, email, password_hash, email_verified_at) VALUES (10, 'Admin Teste', 'admin@email.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00dmxs.AQiy3.a', CURRENT_TIMESTAMP)");

        // Senha '123456' criptografada
        jdbcTemplate.execute("INSERT INTO users (id, name, email, password_hash, email_verified_at) VALUES (20, 'User Teste', 'user@email.com', '$2a$10$Bz6jpv_3rf-Nl5UVXBVDTEqr1DYhWQF0que0IFrurls', CURRENT_TIMESTAMP)");

        // 4. Ligações
        jdbcTemplate.execute("INSERT INTO users_perfis(user_id, perfil_id) VALUES (10, 10)");
        jdbcTemplate.execute("INSERT INTO users_perfis(user_id, perfil_id) VALUES (20, 20)");
    }

    @Test
    void findAll_DeveriaRetornarListaDeUsuarios() {
        var response = getUsersList("/api/v1/users");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size()); // Agora vai bater!
    }

    @Test
    void findById_DeveriaRetornarUsuarioCorreto() {
        var response = get("/api/v1/users/20", User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("User Teste", response.getBody().getName());
    }

    @Test
    void insert_DeveriaCriarNovoUsuario() {
        UserDtoPost novoUser = new UserDtoPost("Novo Teste", "teste.novo@email.com", "123456", "ROLE_USER");
        var response = post("/api/v1/users", novoUser, Void.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
    }

    @Test
    void update_DeveriaAlterarUsuario() {
        UserDtoPut userAtualizado = new UserDtoPut("Nome Alterado", "email.alterado@email.com", "ROLE_ADMIN");
        var response = put("/api/v1/users/20", userAtualizado, UserDtoResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Nome Alterado", response.getBody().name());
    }

    @Test
    void delete_DeveriaApagarUsuario() {
        var response = delete("/api/v1/users/20", Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        var checkResponse = get("/api/v1/users/20", Object.class);
        assertEquals(HttpStatus.NOT_FOUND, checkResponse.getStatusCode());
    }
}