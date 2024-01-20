package br.com.fiap.techchallenge.producao.core.ports.in.pedido;

import br.com.fiap.techchallenge.producao.core.dtos.AtualizaStatusPedidoDTO;
import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;

public interface AtualizaStatusPedidoInputPort {
    PedidoDTO atualizarStatus(String id, AtualizaStatusPedidoDTO pedidoStatusIn);
}
