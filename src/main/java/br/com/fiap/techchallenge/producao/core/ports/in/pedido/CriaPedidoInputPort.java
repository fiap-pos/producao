package br.com.fiap.techchallenge.producao.core.ports.in.pedido;

import br.com.fiap.techchallenge.producao.core.dtos.CriaPedidoDTO;
import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;

public interface CriaPedidoInputPort {
    PedidoDTO criar(CriaPedidoDTO pedidoIn);
}
