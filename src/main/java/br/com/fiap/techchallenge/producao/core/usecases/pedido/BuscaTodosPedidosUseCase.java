package br.com.fiap.techchallenge.producao.core.usecases.pedido;

import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.BuscaTodosPedidosInputPort;
import br.com.fiap.techchallenge.producao.core.ports.out.pedido.BuscaPedidosOutputPort;

import java.util.List;

public class BuscaTodosPedidosUseCase implements BuscaTodosPedidosInputPort {

    private final BuscaPedidosOutputPort buscaPedidosOutputPort;

    public BuscaTodosPedidosUseCase(BuscaPedidosOutputPort buscaPedidosOutputPort) {
        this.buscaPedidosOutputPort = buscaPedidosOutputPort;
    }

    @Override
    public List<PedidoDTO> buscarTodos() {
        return buscaPedidosOutputPort.buscarTodos();
    }
}
