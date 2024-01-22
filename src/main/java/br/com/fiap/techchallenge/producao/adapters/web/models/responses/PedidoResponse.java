package br.com.fiap.techchallenge.producao.adapters.web.models.responses;

import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponse {
    private String id;
    private Long codigo;
    private String clienteNome;
    private List<ItemPedidoResponse> itens;
    private StatusPedidoEnum status;
    private BigDecimal valorTotal;
    private LocalDateTime data;

    public PedidoResponse(String id, Long codigo, String clienteNome, List<ItemPedidoResponse> itens, StatusPedidoEnum status, LocalDateTime data) {
        this.id = id;
        this.codigo = codigo;
        this.clienteNome = clienteNome;
        this.itens = itens;
        this.status = status;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public List<ItemPedidoResponse> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoResponse> itens) {
        this.itens = itens;
    }

    public StatusPedidoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoEnum status) {
        this.status = status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
