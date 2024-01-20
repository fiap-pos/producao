package br.com.fiap.techchallenge.producao.core.ports.in.pedido;

import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;

public interface BuscarPedidoPorIdInputPort {
    PedidoDTO buscarPorId(String id);
}
