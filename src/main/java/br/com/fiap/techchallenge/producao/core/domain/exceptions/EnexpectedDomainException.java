package br.com.fiap.techchallenge.producao.core.domain.exceptions;

public class EnexpectedDomainException extends RuntimeException {

    public EnexpectedDomainException(String message) {
        super(message);
    }

}
