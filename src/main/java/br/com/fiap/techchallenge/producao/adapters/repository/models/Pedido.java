package br.com.fiap.techchallenge.producao.adapters.repository.models;

import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@Document("pedidos")
public class Pedido {
    @Id
    private String id;

    @Indexed(unique = true)
    private Long codigo;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private StatusPedidoEnum status;
    private LocalDateTime data;

    public Pedido() {}

    public Pedido(Long codigo, Cliente cliente, List<ItemPedido> itens, StatusPedidoEnum status, LocalDateTime data) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.itens = itens;
        this.status = status;
        this.data = data;
    }

    public Pedido(String id, Long codigo, Cliente cliente, List<ItemPedido> itens, StatusPedidoEnum status, LocalDateTime data) {
        this.id = id;
        this.codigo = codigo;
        this.cliente = cliente;
        this.itens = itens;
        this.status = status;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public StatusPedidoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoEnum status) {
        this.status = status;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
