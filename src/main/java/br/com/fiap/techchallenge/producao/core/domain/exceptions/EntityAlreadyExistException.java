package br.com.fiap.techchallenge.producao.core.domain.exceptions;

public class EntityAlreadyExistException extends RuntimeException {

    public EntityAlreadyExistException(String message) {
        super(message);
    }
}
