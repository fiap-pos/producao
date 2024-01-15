package br.com.fiap.techchallenge.producao.core.usecases.pedido;

import br.com.fiap.techchallenge.producao.core.dtos.AtualizaStatusPedidoDTO;
import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.AtualizaStatusPedidoInputPort;
import br.com.fiap.techchallenge.producao.core.ports.out.pedido.AtualizaStatusPedidoOutputPort;

public class AtualizaStatusPedidoUseCase implements AtualizaStatusPedidoInputPort {
    private final AtualizaStatusPedidoOutputPort atualizaStatusPedidoOutputPort;

    public AtualizaStatusPedidoUseCase(AtualizaStatusPedidoOutputPort atualizaStatusPedidoOutputPort) {
        this.atualizaStatusPedidoOutputPort = atualizaStatusPedidoOutputPort;
    }

    @Override
    public PedidoDTO atualizarStatus(String id, AtualizaStatusPedidoDTO pedidoStatusIn) {
        return atualizaStatusPedidoOutputPort.atualizarStatus(id, pedidoStatusIn.status());
    }
}
