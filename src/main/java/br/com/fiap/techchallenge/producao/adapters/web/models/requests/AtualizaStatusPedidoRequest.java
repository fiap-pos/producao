package br.com.fiap.techchallenge.producao.adapters.web.models.requests;

import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.producao.core.dtos.AtualizaStatusPedidoDTO;
import jakarta.validation.constraints.NotNull;

public class AtualizaStatusPedidoRequest {
    private StatusPedidoEnum status;

    public AtualizaStatusPedidoRequest() {
    }

    public AtualizaStatusPedidoRequest(StatusPedidoEnum status) {
        this.status = status;
    }

    @NotNull(message = "O campo 'status' é obrigatório")
    public StatusPedidoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoEnum status) {
        this.status = status;
    }

    public AtualizaStatusPedidoDTO toAtualizaStatusPedidoDTO() {
        return new AtualizaStatusPedidoDTO(
                status
        );
    }
}
