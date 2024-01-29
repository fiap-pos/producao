package br.com.fiap.techchallenge.producao.core.usecases;

import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.BuscarPedidoPorIdInputPort;
import br.com.fiap.techchallenge.producao.core.ports.out.pedido.BuscarPedidoOutputPort;
import br.com.fiap.techchallenge.producao.core.usecases.pedido.BuscarPedidoUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getPedidoDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
class BuscarPedidoUseCaseTest {

    private BuscarPedidoPorIdInputPort buscarPedidoPorIdInputPort;

    @Mock
    BuscarPedidoOutputPort buscarPedidoOutputPort;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        buscarPedidoPorIdInputPort = new BuscarPedidoUseCase(buscarPedidoOutputPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class buscaPedidoPorIdUseCase {

        @Test
        void buscaPedidoPorId() {
            var id = "1";
            var pedidoDTO = getPedidoDTO();
            when(buscarPedidoOutputPort.buscarPorId(id)).thenReturn(pedidoDTO);

            var pedidoBuscado = buscarPedidoPorIdInputPort.buscarPorId(id);

            assertThat(pedidoBuscado).isNotNull().isInstanceOf(PedidoDTO.class);
            assertThat(pedidoBuscado.id()).isEqualTo(pedidoDTO.id());
            assertThat(pedidoBuscado.itens()).allSatisfy(item -> {
                assertThat(item.nome()).isEqualTo(pedidoDTO.itens().get(0).nome());
                assertThat(item.descricao()).isEqualTo(pedidoDTO.itens().get(0).descricao());
                assertThat(item.quantidade()).isEqualTo(pedidoDTO.itens().get(0).quantidade());
            });
            assertThat(pedidoBuscado.status()).isEqualTo(pedidoDTO.status());
            assertThat(pedidoBuscado.dataCriacao()).isEqualTo(pedidoDTO.dataCriacao());

            verify(buscarPedidoOutputPort, times(1)).buscarPorId(anyString());
            verifyNoMoreInteractions(buscarPedidoOutputPort);
        }
    }

}