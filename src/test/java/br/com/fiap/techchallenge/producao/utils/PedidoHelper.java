package br.com.fiap.techchallenge.producao.utils;

import br.com.fiap.techchallenge.producao.adapters.repository.models.ItemPedido;
import br.com.fiap.techchallenge.producao.adapters.repository.models.Pedido;
import br.com.fiap.techchallenge.producao.adapters.web.models.requests.ItemPedidoRequest;
import br.com.fiap.techchallenge.producao.adapters.web.models.responses.ItemPedidoResponse;
import br.com.fiap.techchallenge.producao.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.producao.core.dtos.AtualizaStatusPedidoDTO;
import br.com.fiap.techchallenge.producao.core.dtos.ClienteDTO;
import br.com.fiap.techchallenge.producao.core.dtos.CriaPedidoDTO;
import br.com.fiap.techchallenge.producao.core.dtos.ItemPedidoDTO;
import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;

import java.time.LocalDateTime;
import java.util.List;

import static br.com.fiap.techchallenge.producao.utils.ClienteHelper.getCliente;
import static br.com.fiap.techchallenge.producao.utils.ClienteHelper.getClienteDTO;

public abstract class PedidoHelper {
    private static final String PEDIDO_ID = "1";
    private static final Long CODIGO = 1L;
    private static final ClienteDTO CLIENTE_NOME = getClienteDTO();
    private static final List<ItemPedidoDTO> ITENS = List.of(getItemPedidoDTO());
    private static final StatusPedidoEnum PEDIDO_STATUS = StatusPedidoEnum.RECEBIDO;
    private static final LocalDateTime DATA_CRIACAO = LocalDateTime.parse("2024-01-08T20:31:51.620293057");
    private static final String PRODUTO_NOME = "X TUDO";
    private static final String PRODUTO_DESCRICAO = "X Tudo mostro com hamburger, salcicha, bacon, ovo, salada, queijo";
    private static final int PRODUTO_QUANTIDADE = 2;


    public static PedidoDTO getPedidoDTO() {
        return new PedidoDTO(PEDIDO_ID, CODIGO, CLIENTE_NOME, ITENS, PEDIDO_STATUS, DATA_CRIACAO);
    }
    public static List<PedidoDTO> getListaPedidoDTO() {
        return List.of(getPedidoDTO());
    }

    public static Pedido getPedido() {
        var itens = List.of(getItemPedido());
        return new Pedido( PEDIDO_ID,CODIGO, getCliente(), itens, PEDIDO_STATUS, DATA_CRIACAO);
    }

    public static PedidoResponse getPedidoResponse(){
        return new PedidoResponse(PEDIDO_ID, CODIGO, CLIENTE_NOME.nome(), List.of(getItemPedidoResponse()), PEDIDO_STATUS, DATA_CRIACAO);
    }
    public static List<Pedido> getListaPedido() {
        return List.of(getPedido());
    }

    public static ItemPedidoDTO getItemPedidoDTO() {
        return new ItemPedidoDTO(PRODUTO_NOME,PRODUTO_DESCRICAO, PRODUTO_QUANTIDADE);
    }

    public static ItemPedido getItemPedido() {
        return new ItemPedido(PRODUTO_NOME,PRODUTO_DESCRICAO, PRODUTO_QUANTIDADE);
    }
    public static ItemPedidoResponse getItemPedidoResponse() {
        return new ItemPedidoResponse(PRODUTO_NOME,PRODUTO_DESCRICAO, PRODUTO_QUANTIDADE);
    }

    public static AtualizaStatusPedidoDTO getAtualizaStatusPedidoDTO(StatusPedidoEnum status) {
        return new AtualizaStatusPedidoDTO(status);
    }

    public static List<StatusPedidoEnum> getListaStatusPedido(){
        return List.of(PEDIDO_STATUS);
    }
}
