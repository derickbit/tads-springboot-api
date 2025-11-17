package br.edu.ifsul.derick.tads_springboot.infra.exception;

public class TokenInvalidoException extends RuntimeException{
    public TokenInvalidoException(String mensagem) {
        super(mensagem);
    }
}