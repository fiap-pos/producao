package br.com.fiap.techchallenge.producao.utils;

import br.com.fiap.techchallenge.producao.adapters.repository.models.Cliente;
import br.com.fiap.techchallenge.producao.core.dtos.ClienteDTO;

import java.util.List;

public abstract class ClienteHelper {

    private static final String NOME_1 = "Cliente 1";

    public static ClienteDTO getClienteDTO() {
        return new ClienteDTO(NOME_1);
    }

    public static Cliente getCliente() {
        return new Cliente(NOME_1);
    }
}