package br.edu.ifsul.derick.tads_springboot.autenticacao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Importante: Torna esta classe um "Bean" gerenciado pelo Spring
public class AutenticacaoService implements UserDetailsService {

    private final AutenticacaoRepository rep;

    // Injeção de dependência do repositório
    public AutenticacaoService(AutenticacaoRepository rep) {
        this.rep = rep;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // O Spring Security chama este método quando alguém tenta fazer login.
        // Estamos dizendo a ele que o "username" é, na verdade, o email.
        UserDetails user = rep.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        return user;
    }
}