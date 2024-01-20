package br.com.fiap.techchallenge.producao.core.ports.out.pedido;

import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;

public interface BuscarPedidoOutputPort {

    PedidoDTO buscarPorId(String id);

    PedidoDTO buscarPorCodigo(Long codigo);
}
