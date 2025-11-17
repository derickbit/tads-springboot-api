package br.edu.ifsul.derick.tads_springboot.infra.security;

// Importa o repositório de autenticação que criamos
import br.edu.ifsul.derick.tads_springboot.autenticacao.AutenticacaoRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Indica ao Spring que esta é uma classe de componente
public class SecurityFilter extends OncePerRequestFilter {

    // Injeta os dois serviços que precisamos
    private final TokenService tokenService;
    private final AutenticacaoRepository repository;

    public SecurityFilter(TokenService tokenService, AutenticacaoRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Recupera o token do cabeçalho
        var tokenJWT = recuperarToken(request);

        // 2. Se um token foi enviado
        if (tokenJWT != null) {
            // 3. Valida o token e pega o "subject" (o email)
            var subject = tokenService.getSubject(tokenJWT);

            // 4. Busca o usuário no banco de dados pelo email
            var usuario = repository.findByEmail(subject);

            // 5. "Força" a autenticação do usuário no Spring Security
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 6. Continua a cadeia de filtros
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var autorizationHeader = request.getHeader("Authorization");
        if (autorizationHeader != null) {
            // Remove o prefixo "Bearer " para sobrar só o token
            return autorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}