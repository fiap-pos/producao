package br.com.fiap.techchallenge.producao.core.usecases.pedido;

import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.BuscarPedidoPorIdInputPort;
import br.com.fiap.techchallenge.producao.core.ports.out.pedido.BuscarPedidoOutputPort;

public class BuscarPedidoUseCase implements BuscarPedidoPorIdInputPort {
    private final BuscarPedidoOutputPort buscarPedidoOutputPort;
    public BuscarPedidoUseCase(BuscarPedidoOutputPort buscarPedidoOutputPort) {
        this.buscarPedidoOutputPort = buscarPedidoOutputPort;
    }
    @Override
    public PedidoDTO buscarPorId(String id) {
        return buscarPedidoOutputPort.buscarPorId(id);
    }
}
