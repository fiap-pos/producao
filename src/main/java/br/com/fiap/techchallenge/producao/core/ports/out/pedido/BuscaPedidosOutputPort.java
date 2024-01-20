package br.com.fiap.techchallenge.producao.core.ports.out.pedido;

import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import java.util.List;

public interface BuscaPedidosOutputPort {
    List<PedidoDTO> buscarTodos();

    List<PedidoDTO> buscarPedidosPorStatus(List<StatusPedidoEnum> status);
}
