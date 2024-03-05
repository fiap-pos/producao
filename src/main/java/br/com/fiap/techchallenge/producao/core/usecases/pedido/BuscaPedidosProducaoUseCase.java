package br.com.fiap.techchallenge.producao.core.usecases.pedido;

import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.BuscaPedidosProducaoInputPort;
import br.com.fiap.techchallenge.producao.core.ports.out.pedido.BuscaPedidosOutputPort;

import java.util.ArrayList;
import java.util.List;

public class BuscaPedidosProducaoUseCase implements BuscaPedidosProducaoInputPort {

    private final BuscaPedidosOutputPort buscaPedidosOutputPort;

    public BuscaPedidosProducaoUseCase(BuscaPedidosOutputPort buscaPedidosOutputPort) {
        this.buscaPedidosOutputPort = buscaPedidosOutputPort;
    }

    @Override
    public List<PedidoDTO> buscarPedidosProducao() {
        var statusList = List.of(StatusPedidoEnum.RECEBIDO, StatusPedidoEnum.EM_PREPARACAO, StatusPedidoEnum.PRONTO);
        return ordenaPedidosPorPrioridade(buscaPedidosOutputPort.buscarPedidosPorStatus(statusList));
    }

    private static List<PedidoDTO> ordenaPedidosPorPrioridade(List<PedidoDTO> pedidos) {
        List<PedidoDTO> pedidosOrdenadosPorPrioridade = new ArrayList<>();

        pedidos.forEach(
                pedidoOut -> {
                    if (pedidoOut.status().equals(StatusPedidoEnum.PRONTO)) {
                        pedidosOrdenadosPorPrioridade.add(pedidoOut);
                    }
                }
        );

        pedidos.forEach(
                pedidoOut -> {
                    if (pedidoOut.status().equals(StatusPedidoEnum.EM_PREPARACAO)) {
                        pedidosOrdenadosPorPrioridade.add(pedidoOut);
                    }
                }
        );

        pedidos.forEach(
                pedidoOut -> {
                    if (pedidoOut.status().equals(StatusPedidoEnum.RECEBIDO)) {
                        pedidosOrdenadosPorPrioridade.add(pedidoOut);
                    }
                }
        );

        return pedidosOrdenadosPorPrioridade;
    }
}
