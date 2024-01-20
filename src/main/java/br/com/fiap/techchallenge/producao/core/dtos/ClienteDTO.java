package br.com.fiap.techchallenge.producao.core.dtos;

import br.com.fiap.techchallenge.producao.core.domain.entities.Cliente;

public record ClienteDTO(String nome) {
    public ClienteDTO(Cliente cliente) {
        this(cliente.nome());
    }
}
