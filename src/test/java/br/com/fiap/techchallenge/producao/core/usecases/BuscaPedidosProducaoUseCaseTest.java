package br.com.fiap.techchallenge.producao.core.usecases;


import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.producao.core.ports.out.pedido.BuscaPedidosOutputPort;
import br.com.fiap.techchallenge.producao.core.usecases.pedido.BuscaPedidosProducaoUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getListaPedidoDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BuscaPedidosProducaoUseCaseTest {

    @Mock
    private BuscaPedidosOutputPort buscaPedidosOutputPort;

    private BuscaPedidosProducaoUseCase buscaPedidosProducaoUseCase;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        buscaPedidosProducaoUseCase = new BuscaPedidosProducaoUseCase(buscaPedidosOutputPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class buscaPedidosPorPrioridadeUseCase {

        @Test
        void buscaPedidoPorPrioridade() {
            var pedidosDTO = getListaPedidoDTO();
            var statusList = List.of(StatusPedidoEnum.RECEBIDO, StatusPedidoEnum.EM_PREPARACAO, StatusPedidoEnum.PRONTO);

            when(buscaPedidosOutputPort.buscarPedidosPorStatus(statusList)).thenReturn(pedidosDTO);

            var listaPedidosBuscados = buscaPedidosProducaoUseCase.buscarPedidosProducao();

            assertThat(listaPedidosBuscados)
                    .isNotNull()
                    .isNotEmpty()
                    .allSatisfy(pedidoBuscado -> {
                        assertThat(pedidoBuscado.id()).isEqualTo(pedidosDTO.get(0).id());
                        assertThat(pedidoBuscado.itens()).allSatisfy( item -> {
                            assertThat(item.nome()).isEqualTo(pedidosDTO.get(0).itens().get(0).nome());
                            assertThat(item.descricao()).isEqualTo(pedidosDTO.get(0).itens().get(0).descricao());
                            assertThat(item.quantidade()).isEqualTo(pedidosDTO.get(0).itens().get(0).quantidade());
                        });
                        assertThat(pedidoBuscado.status()).isEqualTo(pedidosDTO.get(0).status());
                        assertThat(pedidoBuscado.dataCriacao()).isEqualTo(pedidosDTO.get(0).dataCriacao());
                    });

            verify(buscaPedidosOutputPort, times(1)).buscarPedidosPorStatus(statusList);
        }
    }

}