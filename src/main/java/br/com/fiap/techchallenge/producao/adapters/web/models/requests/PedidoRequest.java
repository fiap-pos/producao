package br.com.fiap.techchallenge.producao.adapters.web.models.requests;

import br.com.fiap.techchallenge.producao.core.dtos.ItemPedidoDTO;
import br.com.fiap.techchallenge.producao.core.dtos.CriaPedidoDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PedidoRequest{

    private Long codigo;

    private List<ItemPedidoRequest> itens;

    public PedidoRequest() {
    }
    @NotNull(message = "O campo 'codigo' é obrigatório")
    public Long getCodigo() {
        return codigo;
    }

    @NotNull(message = "O campo 'itens' é obrigatório")
    public List<ItemPedidoRequest> getItens() {
        return itens;
    }

    public PedidoRequest(List<ItemPedidoRequest> itens) {
        this.itens = itens;
    }

    private List<ItemPedidoDTO> mapItens() {
        return itens.stream().map(item -> new ItemPedidoDTO(item.getNome(), item.getDescricao(), item.getQuantidade()) ).toList();
    }

    public CriaPedidoDTO toCriaPedidoDTO() {
        return new CriaPedidoDTO(
                codigo,
                mapItens()
        );
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public void setItens(List<ItemPedidoRequest> itens) {
        this.itens = itens;
    }
}
