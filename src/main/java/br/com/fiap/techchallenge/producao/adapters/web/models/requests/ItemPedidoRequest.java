package br.com.fiap.techchallenge.producao.adapters.web.models.requests;

import jakarta.validation.constraints.NotNull;

public class ItemPedidoRequest {

    private String nome;
    private String descricao;
    private Integer quantidade;

    public ItemPedidoRequest() {
    }

    public ItemPedidoRequest(String nome, String descricao, Integer quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    @NotNull(message = "O campo 'nome' é obrigatório")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NotNull(message = "O campo 'descricao' é obrigatório")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @NotNull(message = "O campo 'quantidade' é obrigatório")
    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
