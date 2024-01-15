package br.com.fiap.techchallenge.producao.core.usecases.pedido;

import br.com.fiap.techchallenge.producao.core.domain.entities.Cliente;
import br.com.fiap.techchallenge.producao.core.domain.entities.ItemPedido;
import br.com.fiap.techchallenge.producao.core.domain.entities.Pedido;
import br.com.fiap.techchallenge.producao.core.dtos.*;
import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.CriaPedidoInputPort;
import br.com.fiap.techchallenge.producao.core.ports.out.pedido.CriaPedidoOutputPort;

import java.util.List;

public class CriaPedidoUseCase implements CriaPedidoInputPort {

    private final CriaPedidoOutputPort criaPedidoOutputPort;

    public CriaPedidoUseCase(CriaPedidoOutputPort criaPedidoOutputPort) {
        this.criaPedidoOutputPort = criaPedidoOutputPort;
    }

    @Override
    public PedidoDTO criar(CriaPedidoDTO pedidoIn) {

        var pedido = new Pedido(pedidoIn.codigo(), StatusPedidoEnum.RECEBIDO);
        adicionaItensPedido(pedido, pedidoIn.itens());
        if (pedidoIn.clienteNome() != null) {
            pedido.setCliente(new Cliente(pedidoIn.clienteNome()));
        }

        return criaPedidoOutputPort.criar(new PedidoDTO(pedido));
    }

    private void adicionaItensPedido(Pedido pedido, List<ItemPedidoDTO> itensPedidoIn) {
        itensPedidoIn.forEach(item -> {
            pedido.addItemPedido(
                new ItemPedido(item.nome(), item.descricao(), item.quantidade())
            );
        });
    }
}
