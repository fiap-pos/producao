package br.com.fiap.techchallenge.producao.core.usecases;

import br.com.fiap.techchallenge.producao.core.domain.entities.Pedido;
import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.producao.core.dtos.ClienteDTO;
import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.CriaPedidoInputPort;
import br.com.fiap.techchallenge.producao.core.ports.out.pedido.BuscarPedidoOutputPort;
import br.com.fiap.techchallenge.producao.core.ports.out.pedido.CriaPedidoOutputPort;
import br.com.fiap.techchallenge.producao.core.usecases.pedido.CriaPedidoUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getCriaPedidoDTO;
import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getCriaPedidoDTOSemCliente;
import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getPedidoDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class CriaPedidoUseCaseTest {

    private CriaPedidoInputPort criaPedidoInputPort;

    @Mock
    CriaPedidoOutputPort criaPedidoOutputPort;
    @Mock
    BuscarPedidoOutputPort buscarPedidoOutputPort;

    AutoCloseable mock;

    Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido(1L, StatusPedidoEnum.RECEBIDO);
        mock = MockitoAnnotations.openMocks(this);
        criaPedidoInputPort = new CriaPedidoUseCase(criaPedidoOutputPort, buscarPedidoOutputPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class criaPedidoUseCase {

        @Test
        void criarPedidoComCliente() {
            var pedidoDTO = getPedidoDTO();
            var novoPedido = getCriaPedidoDTO();
            when(criaPedidoOutputPort.criar(any(PedidoDTO.class))).thenReturn(pedidoDTO);

            var pedidoCriado = criaPedidoInputPort.criar(novoPedido);

            assertThat(pedidoCriado).isNotNull();
            assertThat(pedidoCriado.id()).isEqualTo(pedidoDTO.id());
            assertThat(pedidoCriado.getNomeCliente()).isEqualTo(pedidoDTO.getNomeCliente());
            assertThat(pedidoCriado.itens()).allSatisfy(item -> {
               assertThat(item.nome()).isEqualTo(pedidoDTO.itens().get(0).nome());
               assertThat(item.descricao()).isEqualTo(pedidoDTO.itens().get(0).descricao());
               assertThat(item.quantidade()).isEqualTo(pedidoDTO.itens().get(0).quantidade());
            });
            assertThat(pedidoCriado.status()).isEqualTo(pedidoDTO.status());
            assertThat(pedidoCriado.dataCriacao()).isEqualTo(pedidoDTO.dataCriacao());

            verify(criaPedidoOutputPort, times(1)).criar(any(PedidoDTO.class));
            verifyNoMoreInteractions(criaPedidoOutputPort);
        }

        @Test
        void criarPedidoSemCliente() {
            var pedidoDTO = getPedidoDTO();
            var novoPedido = getCriaPedidoDTOSemCliente();

            when(criaPedidoOutputPort.criar(any(PedidoDTO.class))).thenReturn(pedidoDTO);

            var pedidoCriado = criaPedidoInputPort.criar(novoPedido);

            assertThat(pedidoCriado).isNotNull();
            assertThat(pedidoCriado.id()).isEqualTo(pedidoDTO.id());
            assertThat(pedidoCriado.getNomeCliente()).isEqualTo(pedidoDTO.getNomeCliente());
            assertThat(pedidoCriado.itens()).allSatisfy(item -> {
                assertThat(item.nome()).isEqualTo(pedidoDTO.itens().get(0).nome());
                assertThat(item.descricao()).isEqualTo(pedidoDTO.itens().get(0).descricao());
                assertThat(item.quantidade()).isEqualTo(pedidoDTO.itens().get(0).quantidade());
            });
            assertThat(pedidoCriado.status()).isEqualTo(pedidoDTO.status());
            assertThat(pedidoCriado.dataCriacao()).isEqualTo(pedidoDTO.dataCriacao());

            verify(criaPedidoOutputPort, times(1)).criar(any(PedidoDTO.class));
            verifyNoMoreInteractions(criaPedidoOutputPort);
        }
    }
}