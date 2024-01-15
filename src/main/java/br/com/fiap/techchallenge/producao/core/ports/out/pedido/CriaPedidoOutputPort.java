package br.com.fiap.techchallenge.producao.core.ports.out.pedido;

import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;

public interface CriaPedidoOutputPort {
    PedidoDTO criar(PedidoDTO criaPedidoIn);
}
