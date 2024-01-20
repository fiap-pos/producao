package br.com.fiap.techchallenge.producao.core.dtos;

import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;

public record AtualizaStatusPedidoDTO(StatusPedidoEnum status) {
}
