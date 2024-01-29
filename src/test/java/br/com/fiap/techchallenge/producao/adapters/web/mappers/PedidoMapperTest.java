package br.com.fiap.techchallenge.producao.adapters.web.mappers;

import br.com.fiap.techchallenge.producao.adapters.web.models.responses.ItemPedidoResponse;
import br.com.fiap.techchallenge.producao.adapters.web.models.responses.PedidoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getListaPedidoDTO;
import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getPedidoDTO;
import static org.assertj.core.api.Assertions.assertThat;

class PedidoMapperTest {

    private PedidoMapper pedidoMapper;

    @BeforeEach
    void setup() {
        this.pedidoMapper = new PedidoMapper();
    }

    @Test
    void dadoPedidoDTO_DeveFazerMapper_RetornarPedidoResponse() {
        // Arrange
        var pedidoDTO = getPedidoDTO();

        // Act
        var pedidoResponse = pedidoMapper.toPedidoResponse(pedidoDTO);

        // Assert
        assertThat(pedidoResponse).isNotNull();
        assertThat(pedidoResponse.getId()).isEqualTo(pedidoDTO.id());
        assertThat(pedidoResponse.getClienteNome()).isEqualTo(pedidoDTO.cliente().nome());
        assertThat(pedidoResponse.getItens()).allSatisfy(item -> {
            assertThat(item).isNotNull().isInstanceOf(ItemPedidoResponse.class);
        });
        assertThat(pedidoResponse.getItens().get(0).getNome()).isEqualTo(pedidoDTO.itens().get(0).nome());
        assertThat(pedidoResponse.getItens().get(0).getDescricao()).isEqualTo(pedidoDTO.itens().get(0).descricao());
        assertThat(pedidoResponse.getItens().get(0).getQuantidade()).isEqualTo(pedidoDTO.itens().get(0).quantidade());
    }

    @Test
    void dadoListaPedidoDTO_DeveFazerMapper_RetornarListaPedidoResponse() {
        // Arrange
        var listaPedidoDTO = getListaPedidoDTO();

        // Act
        var listaPedidoResponse = pedidoMapper.toPedidoListResponse(listaPedidoDTO);

        // Assert
        assertThat(listaPedidoResponse).isNotNull();
        assertThat(listaPedidoResponse).allSatisfy(pedidoResponse -> {
            assertThat(pedidoResponse).isNotNull().isInstanceOf(PedidoResponse.class);
            assertThat(pedidoResponse.getId()).isEqualTo(listaPedidoDTO.get(0).id());
            assertThat(pedidoResponse.getClienteNome()).isEqualTo(listaPedidoDTO.get(0).cliente().nome());
            assertThat(pedidoResponse.getItens()).allSatisfy(item -> {
                assertThat(item).isNotNull().isInstanceOf(ItemPedidoResponse.class);
            });
            assertThat(pedidoResponse.getItens().get(0).getNome()).isEqualTo(listaPedidoDTO.get(0).itens().get(0).nome());
            assertThat(pedidoResponse.getItens().get(0).getDescricao()).isEqualTo(listaPedidoDTO.get(0).itens().get(0).descricao());
            assertThat(pedidoResponse.getItens().get(0).getQuantidade()).isEqualTo(listaPedidoDTO.get(0).itens().get(0).quantidade());
        });
    }
}