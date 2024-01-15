package br.com.fiap.techchallenge.producao.adapters.web.models.responses;

import java.math.BigDecimal;

public class ItemPedidoResponse {
    private String produtoNome;
    private String produtoDescricao;
    private Integer quantidade;

    public ItemPedidoResponse(String produtoNome, String produtoDescricao, Integer quantidade) {
        this.produtoNome = produtoNome;
        this.produtoDescricao = produtoDescricao;
        this.quantidade = quantidade;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public String getProdutoDescricao() {
        return produtoDescricao;
    }

    public void setProdutoDescricao(String produtoDescricao) {
        this.produtoDescricao = produtoDescricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
