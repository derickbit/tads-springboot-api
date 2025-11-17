package br.edu.ifsul.derick.tads_springboot.user;

import java.io.Serializable;

public record UserDtoResponse(
        Long id,
        String name,
        String email,
        String role // Vamos preencher isso com o nome do primeiro perfil
) implements Serializable {

    // SUBSTITUA O CONSTRUTOR ANTIGO POR ESTE
    public UserDtoResponse(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                // Pega o nome do primeiro perfil da lista, se existir
                (user.getPerfis() != null && !user.getPerfis().isEmpty())
                        ? user.getPerfis().get(0).getNome()
                        : null
        );
    }
}