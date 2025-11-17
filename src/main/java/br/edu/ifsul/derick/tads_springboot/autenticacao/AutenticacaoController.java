package br.edu.ifsul.derick.tads_springboot.autenticacao;

import br.edu.ifsul.derick.tads_springboot.infra.security.TokenJwtDTO;
import br.edu.ifsul.derick.tads_springboot.infra.security.TokenService;
import br.edu.ifsul.derick.tads_springboot.user.User; // Importe seu User
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/login") // Este é o endpoint que liberamos no SecurityConfig
public class AutenticacaoController {

    // Injeta o "gerente" de autenticação e o "serviço de token"
    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenJwtDTO> efetuaLogin(@RequestBody AutenticacaoDTO data) {
        // 1. Cria o DTO de autenticação do Spring
        var authenticationDTO = new UsernamePasswordAuthenticationToken(data.email(), data.senha());

        // 2. O 'manager' usa o 'AutenticacaoService' para validar o usuário e senha
        var authentication = manager.authenticate(authenticationDTO);

        // 3. Se deu certo, gera um token JWT
        var tokenJWT = tokenService.geraToken((User) authentication.getPrincipal());

        // 4. Retorna o token em um DTO de resposta
        return ResponseEntity.ok(new TokenJwtDTO(tokenJWT));
    }
}