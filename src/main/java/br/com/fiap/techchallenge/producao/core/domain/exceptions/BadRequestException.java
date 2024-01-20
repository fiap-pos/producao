package br.com.fiap.techchallenge.producao.core.domain.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BadRequestException(Throwable throwable) {
        super(throwable);
    }

}
