package br.com.fiap.techchallenge.producao.core.ports.out.pedido;

import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;

public interface AtualizaStatusPedidoOutputPort {
    PedidoDTO atualizarStatus(String id, StatusPedidoEnum status);
}
