package br.edu.ifsul.derick.tads_springboot.user;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UserDtoResponse(
        Long id,
        String name,
        String email,
        String role
) implements Serializable {

    // Construtor que converte a Entidade User em um DTO de Resposta
    public UserDtoResponse(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}