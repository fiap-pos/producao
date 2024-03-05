package br.com.fiap.techchallenge.producao.core.domain.exceptions;

public class UnexpectedDomainException extends RuntimeException {

    public UnexpectedDomainException(String message) {
        super(message);
    }

}
