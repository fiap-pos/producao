package br.com.fiap.techchallenge.producao.core.usecases;

import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.producao.core.dtos.AtualizaStatusPedidoDTO;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.AtualizaStatusPedidoInputPort;
import br.com.fiap.techchallenge.producao.core.ports.out.pedido.AtualizaStatusPedidoOutputPort;
import br.com.fiap.techchallenge.producao.core.usecases.pedido.AtualizaStatusPedidoUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getAtualizaStatusPedidoDTO;
import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getPedidoDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class AtualizaStatusPedidoUseCaseTest {

    private AtualizaStatusPedidoInputPort atualizaStatusPedidoInputPort;

    @Mock
    AtualizaStatusPedidoOutputPort atualizaStatusPedidoOutputPort;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        atualizaStatusPedidoInputPort = new AtualizaStatusPedidoUseCase(atualizaStatusPedidoOutputPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class buscaTodosPedidosPorStatusUseCase {

        @Test
        void buscaTodosPedidosPorStatus() {
            var id = "1";
            var status = StatusPedidoEnum.RECEBIDO;
            var pedidoDTO = getPedidoDTO();
            AtualizaStatusPedidoDTO atualizaStatusPedidoDTO = getAtualizaStatusPedidoDTO(status);

            when(atualizaStatusPedidoOutputPort.atualizarStatus(anyString(), any(StatusPedidoEnum.class))).thenReturn(pedidoDTO);

            var pedidoAtualizado = atualizaStatusPedidoInputPort.atualizarStatus(id, atualizaStatusPedidoDTO);

            assertThat(pedidoAtualizado).isNotNull();
            assertThat(pedidoAtualizado.itens()).allSatisfy( item -> {
                assertThat(item.nome()).isEqualTo(pedidoDTO.itens().get(0).nome());
                assertThat(item.descricao()).isEqualTo(pedidoDTO.itens().get(0).descricao());
                assertThat(item.quantidade()).isEqualTo(pedidoDTO.itens().get(0).quantidade());
            });
            assertThat(pedidoAtualizado.status()).isEqualTo(pedidoDTO.status());
            assertThat(pedidoAtualizado.dataCriacao()).isEqualTo(pedidoDTO.dataCriacao());

            verify(atualizaStatusPedidoOutputPort, times(1)).atualizarStatus(anyString(), any(StatusPedidoEnum.class));
            verifyNoMoreInteractions(atualizaStatusPedidoOutputPort);
        }
    }

}