package br.edu.ifsul.derick.tads_springboot.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDtoPut(
        @NotBlank(message = "O nome não pode ser nulo ou vazio")
        @Size(min = 2, max = 100, message = "Tamanho mínimo de 2 e máximo de 100")
        String name,

        @NotBlank(message = "O email não pode ser nulo ou vazio")
        @Email(message = "Formato de email inválido")
        String email,

        @NotBlank(message = "A role não pode ser nula ou vazia")
        String role
) {}