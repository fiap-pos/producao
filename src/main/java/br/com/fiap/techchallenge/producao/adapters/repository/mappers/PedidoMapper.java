package br.com.fiap.techchallenge.producao.adapters.repository.mappers;

import br.com.fiap.techchallenge.producao.adapters.repository.models.ItemPedido;
import br.com.fiap.techchallenge.producao.adapters.repository.models.Pedido;
import br.com.fiap.techchallenge.producao.adapters.repository.models.Cliente;
import br.com.fiap.techchallenge.producao.core.dtos.ItemPedidoDTO;
import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public Pedido toPedido(PedidoDTO pedidoIn){

        Cliente cliente = null;
        if (pedidoIn.getNomeCliente() != null) {
            cliente = new Cliente(pedidoIn.getNomeCliente());
        }

        var itens = mapItens(pedidoIn.itens());

        return new Pedido(
          pedidoIn.codigo(),
            cliente,
            itens,
            pedidoIn.status(),
            pedidoIn.dataCriacao()
        );

    }

    private List<ItemPedido> mapItens(List<ItemPedidoDTO> itens) {
        return itens.stream().map(item -> new ItemPedido(item.nome(), item.descricao(), item.quantidade())).toList();
    }

    public PedidoDTO toPedidoDTO(Pedido pedido) {
        return null;
    }
}
