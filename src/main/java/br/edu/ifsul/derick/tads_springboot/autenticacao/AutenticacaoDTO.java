package br.edu.ifsul.derick.tads_springboot.autenticacao;

// Este é o "formulário" que o usuário envia para /api/v1/login
public record AutenticacaoDTO(
        String email,
        String senha) {
}