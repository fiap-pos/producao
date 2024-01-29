package br.com.fiap.techchallenge.producao.core.domain.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

}
