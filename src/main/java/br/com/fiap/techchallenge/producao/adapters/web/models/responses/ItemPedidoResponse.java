package br.com.fiap.techchallenge.producao.adapters.web.models.responses;

public class ItemPedidoResponse {
    private String nome;
    private String descricao;
    private Integer quantidade;

    public ItemPedidoResponse(String nome, String descricao, Integer quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
