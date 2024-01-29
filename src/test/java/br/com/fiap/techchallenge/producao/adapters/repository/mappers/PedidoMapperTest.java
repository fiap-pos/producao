package br.com.fiap.techchallenge.producao.adapters.repository.mappers;

import br.com.fiap.techchallenge.producao.adapters.repository.models.Pedido;
import br.com.fiap.techchallenge.producao.core.dtos.ItemPedidoDTO;
import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.producao.adapters.repository.models.ItemPedido;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getPedido;
import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getPedidoDTO;
import static org.assertj.core.api.Assertions.assertThat;

class PedidoMapperTest {

    private PedidoMapper pedidoMapper;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        this.pedidoMapper = new PedidoMapper();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void dadoPedidoDTO_DeveFazerMapper_RetornarPedido() {
        var pedidoDTO = getPedidoDTO();

        var pedido = pedidoMapper.toPedido(pedidoDTO);

        assertThat(pedido).isNotNull().isInstanceOf(Pedido.class);
        assertThat(pedido.getCodigo()).isEqualTo(pedidoDTO.codigo());
        assertThat(pedido.getCliente().getNome()).isEqualTo(pedidoDTO.cliente().nome());
        assertThat(pedido.getStatus()).isEqualTo(pedidoDTO.status());

        assertThat(pedido.getItens()).allSatisfy(item -> {
            assertThat(item).isNotNull().isInstanceOf(ItemPedido.class);
        });
        assertThat(pedido.getItens().get(0).getNome()).isEqualTo(pedidoDTO.itens().get(0).nome());
        assertThat(pedido.getItens().get(0).getDescricao()).isEqualTo(pedidoDTO.itens().get(0).descricao());
        assertThat(pedido.getItens().get(0).getQuantidade()).isEqualTo(pedidoDTO.itens().get(0).quantidade());
    }

    @Test
    void dadoPedido_DeveFazerMapper_RetornarPedidoDTO() {
        var pedido = getPedido();

        var pedidoDTO = pedidoMapper.toPedidoDTO(pedido);

        assertThat(pedidoDTO).isNotNull().isInstanceOf(PedidoDTO.class);
        assertThat(pedidoDTO.id()).isEqualTo(pedido.getId());
        assertThat(pedidoDTO.codigo()).isEqualTo(pedido.getCodigo());
        assertThat(pedidoDTO.cliente().nome()).isEqualTo(pedido.getCliente().getNome());
        assertThat(pedidoDTO.status()).isEqualTo(pedido.getStatus());

        assertThat(pedidoDTO.itens()).allSatisfy(item -> {
            assertThat(item).isNotNull().isInstanceOf(ItemPedidoDTO.class);
        });
        assertThat(pedidoDTO.itens().get(0).nome()).isEqualTo(pedido.getItens().get(0).getNome());
        assertThat(pedidoDTO.itens().get(0).descricao()).isEqualTo(pedido.getItens().get(0).getDescricao());
        assertThat(pedidoDTO.itens().get(0).quantidade()).isEqualTo(pedido.getItens().get(0).getQuantidade());
    }
}