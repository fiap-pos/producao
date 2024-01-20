package br.com.fiap.techchallenge.producao.core.domain.entities;

import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String id;
    private Long codigo;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private StatusPedidoEnum status;

    private LocalDateTime dataCriacao;

    public Pedido(Long codigo, String id, List<ItemPedido> itens, StatusPedidoEnum status, LocalDateTime dataCriacao) {
        this.id = id;
        this.itens = List.copyOf(itens);
        this.status = status;
        this.dataCriacao = dataCriacao;
    }
    public Pedido(Long codigo, StatusPedidoEnum status) {
        this.codigo = codigo;
        this.itens = new ArrayList<>();
        this.status = status;
        this.dataCriacao = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItens() {
        return List.copyOf(itens);
    }

    public void addItemPedido(ItemPedido item) {
        itens.add(item);
    }

    public StatusPedidoEnum getStatus() {
        return status;
    }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
}
