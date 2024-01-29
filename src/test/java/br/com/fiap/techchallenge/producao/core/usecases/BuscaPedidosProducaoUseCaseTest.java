package br.com.fiap.techchallenge.producao.core.usecases;


import br.com.fiap.techchallenge.producao.core.ports.in.pedido.BuscaPedidosProducaoInputPort;
import br.com.fiap.techchallenge.producao.core.ports.out.pedido.BuscaPedidosOutputPort;
import br.com.fiap.techchallenge.producao.core.usecases.pedido.BuscaPedidosProducaoUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getListaPedidoDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BuscaPedidosProducaoUseCaseTest {

    private BuscaPedidosProducaoInputPort buscaPedidosProducaoInputPort;

    @Mock
    BuscaPedidosOutputPort buscaTodosPedidosOutputPort;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        buscaPedidosProducaoInputPort = new BuscaPedidosProducaoUseCase(buscaTodosPedidosOutputPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class buscaPedidosPorPrioridadeUseCase {
        @Test
        void testBuscarPedidosProducaoListaVazia() {
            when(buscaTodosPedidosOutputPort.buscarPedidosPorStatus(anyList()))
                    .thenReturn(new ArrayList<>());

            var actualBuscarPedidosProducaoResult = buscaPedidosProducaoInputPort.buscarPedidosProducao();

            verify(buscaTodosPedidosOutputPort).buscarPedidosPorStatus(anyList());
            assertTrue(actualBuscarPedidosProducaoResult.isEmpty());
        }

        @Test
        void testBuscarPedidosProducaoListaComPedidos() {
            var pedidosDTO = getListaPedidoDTO();
            when(buscaTodosPedidosOutputPort.buscarPedidosPorStatus(anyList()))
                    .thenReturn(pedidosDTO);

            var listaPedidosBuscados = buscaPedidosProducaoInputPort.buscarPedidosProducao();

            assertThat(listaPedidosBuscados).isNotNull();
            assertThat(listaPedidosBuscados).allSatisfy( pedidoBuscado -> {
                assertThat(pedidoBuscado.id()).isEqualTo(pedidosDTO.get(0).id());
                assertThat(pedidoBuscado.itens()).allSatisfy( item -> {
                    assertThat(item.nome()).isEqualTo(pedidosDTO.get(0).itens().get(0).nome());
                    assertThat(item.descricao()).isEqualTo(pedidosDTO.get(0).itens().get(0).descricao());
                    assertThat(item.quantidade()).isEqualTo(pedidosDTO.get(0).itens().get(0).quantidade());
                });
                assertThat(pedidoBuscado.status()).isEqualTo(pedidosDTO.get(0).status());
                assertThat(pedidoBuscado.dataCriacao()).isEqualTo(pedidosDTO.get(0).dataCriacao());
            });
            assertEquals(1, listaPedidosBuscados.size());

            verify(buscaTodosPedidosOutputPort, times(1)).buscarPedidosPorStatus(anyList());
        }
    }
}