package br.com.fiap.techchallenge.producao.core.usecases.pedido;

import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.BuscarPedidoPorIdInputPort;
import br.com.fiap.techchallenge.producao.core.ports.out.pedido.BuscarPedidoPorIdOutputPort;

public class BuscarPedidoUseCase implements BuscarPedidoPorIdInputPort {
    private final BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort;
    public BuscarPedidoUseCase(BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort) {
        this.buscarPedidoPorIdOutputPort = buscarPedidoPorIdOutputPort;
    }
    @Override
    public PedidoDTO buscarPorId(String id) {
        return buscarPedidoPorIdOutputPort.buscarPorId(id);
    }
}
