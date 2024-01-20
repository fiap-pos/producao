package br.com.fiap.techchallenge.producao.core.dtos;

import java.util.List;

public record CriaPedidoDTO(
    Long codigo,
    String clienteNome,
    List<ItemPedidoDTO> itens
)  {
}
