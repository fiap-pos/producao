package br.com.fiap.techchallenge.producao.core.dtos;

import br.com.fiap.techchallenge.producao.core.domain.entities.Pedido;
import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDTO(
        String id,
        Long codigo,
        List<ItemPedidoDTO> itens,
        StatusPedidoEnum status,
        LocalDateTime dataCriacao
) {

    public PedidoDTO(Pedido pedido) {
        this(
            pedido.getId(),
            pedido.getCodigo(),
            pedido.getItens().stream().map(item -> new ItemPedidoDTO(item.nome(), item.descricao(), item.quantidade()) ).toList(),
            pedido.getStatus(),
            pedido.getDataCriacao()
        );
    }
}
