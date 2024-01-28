package br.com.fiap.techchallenge.producao.adapters.web;

import br.com.fiap.techchallenge.producao.adapters.web.mappers.PedidoMapper;
import br.com.fiap.techchallenge.producao.adapters.web.models.requests.AtualizaStatusPedidoRequest;
import br.com.fiap.techchallenge.producao.adapters.web.models.requests.ItemPedidoRequest;
import br.com.fiap.techchallenge.producao.adapters.web.models.requests.PedidoRequest;
import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.producao.core.dtos.AtualizaStatusPedidoDTO;
import br.com.fiap.techchallenge.producao.core.dtos.CriaPedidoDTO;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.AtualizaStatusPedidoInputPort;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.BuscaPedidosProducaoInputPort;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.BuscaTodosPedidosInputPort;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.BuscarPedidoPorIdInputPort;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.CriaPedidoInputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static br.com.fiap.techchallenge.producao.utils.JsonToStringHelper.asJsonString;
import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getPedidoDTO;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PedidoControllerTest {

    private MockMvc mockMvc;
    @Mock
    CriaPedidoInputPort criaPedidoInputPort;

    @Mock
    AtualizaStatusPedidoInputPort atualizaStatusPedidoInputPort;

    @Mock
    BuscaTodosPedidosInputPort buscaTodosPedidosInputPort;

    @Mock
    BuscaPedidosProducaoInputPort buscaPedidosProducaoInputPort;

    @Mock
    BuscarPedidoPorIdInputPort buscarPedidoPorIdInputPort;
    PedidoMapper pedidoMapper;

    AutoCloseable mock;

    PedidoRequest pedidoRequest = new PedidoRequest();

    ItemPedidoRequest itemPedidoRequest = new ItemPedidoRequest();

    AtualizaStatusPedidoRequest atualizaStatusPedidoRequest = new AtualizaStatusPedidoRequest();

    @BeforeEach
    void setUp() {
        itemPedidoRequest.setQuantidade(10);
        pedidoRequest.setCodigo(1L);
        pedidoRequest.setClienteNome("Cliente Nome");
        pedidoRequest.setItens(Collections.singletonList(itemPedidoRequest));

        atualizaStatusPedidoRequest.setStatus(StatusPedidoEnum.FINALIZADO);

        this.pedidoMapper = new PedidoMapper();
        mock = MockitoAnnotations.openMocks(this);
        PedidoController pedidoController = new PedidoController(
                criaPedidoInputPort,
                atualizaStatusPedidoInputPort,
                buscaTodosPedidosInputPort,
                buscaPedidosProducaoInputPort,
                buscarPedidoPorIdInputPort,
                pedidoMapper
        );

        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class testaPedidoController {

        @Test
        void buscarTodosOsPedidos() throws Exception {
            var pedidoDTO = getPedidoDTO();

            when(buscaTodosPedidosInputPort.buscarTodos()).thenReturn(Collections.singletonList(pedidoDTO));

            ResultActions result = mockMvc.perform(get("/producao")
                    .contentType(MediaType.APPLICATION_JSON)
            );

            result.andExpect(status().isOk());

            verify(buscaTodosPedidosInputPort, times(1)).buscarTodos();
            verifyNoMoreInteractions(buscaTodosPedidosInputPort);
        }

        @Test
        void buscarTodosPedidosPorPrioridade() throws Exception {
            var pedidoDTO = getPedidoDTO();

            when(buscaPedidosProducaoInputPort.buscarPedidosProducao()).thenReturn(Collections.singletonList(pedidoDTO));

            ResultActions result = mockMvc.perform(get("/producao/fila")
                    .contentType(MediaType.APPLICATION_JSON)
            );

            result.andExpect(status().isOk());

            verify(buscaPedidosProducaoInputPort, times(1)).buscarPedidosProducao();
            verifyNoMoreInteractions(buscaPedidosProducaoInputPort);
        }

        @Test
        void buscarPedidoPorId() throws Exception {
            var id = 1L;
            var pedidoDTO = getPedidoDTO();

            when(buscarPedidoPorIdInputPort.buscarPorId(anyString())).thenReturn(pedidoDTO);

            ResultActions result = mockMvc.perform(get("/producao/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            result.andExpect(status().isOk());

            verify(buscarPedidoPorIdInputPort, times(1)).buscarPorId(anyString());
            verifyNoMoreInteractions(buscarPedidoPorIdInputPort);
        }

        @Test
        void criaUmPedido() throws Exception {
            var pedidoDTO = getPedidoDTO();

            when(criaPedidoInputPort.criar(any(CriaPedidoDTO.class))).thenReturn(pedidoDTO);

            ResultActions result = mockMvc.perform(post("/producao")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(pedidoRequest))
            );

            result.andExpect(status().isCreated());

            verify(criaPedidoInputPort, times(1)).criar(any(CriaPedidoDTO.class));
            verifyNoMoreInteractions(criaPedidoInputPort);
        }

        @Test
        void atualizaStatusDoPedido() throws Exception {
            var pedidoDTO = getPedidoDTO();
            var id = 1L;

            when(atualizaStatusPedidoInputPort.atualizarStatus(anyString(), any(AtualizaStatusPedidoDTO.class))).thenReturn(pedidoDTO);

            ResultActions result = mockMvc.perform(patch("/producao/{id}/status", id, atualizaStatusPedidoRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(atualizaStatusPedidoRequest))
            );

            result.andExpect(status().isCreated());

            verify(atualizaStatusPedidoInputPort, times(1)).atualizarStatus(anyString(), any(AtualizaStatusPedidoDTO.class));
            verifyNoMoreInteractions(atualizaStatusPedidoInputPort);
        }

//        @Test
//        void buscarTodosPedidosPorStatus() throws Exception {
//            var status = "PENDENTE_DE_PAGAMENTO";
//            var pedidoDTO = getPedidoDTO();
//
//            when(buscaTodosPedidosInputPort.buscarTodos(any(StatusPedidoEnum.class))).thenReturn(Collections.singletonList(pedidoDTO));
//
//            ResultActions result = mockMvc.perform(get("/producao/status/{status}", status)
//                    .contentType(MediaType.APPLICATION_JSON)
//            );
//
//            result.andExpect(status().isOk());
//
//            verify(buscaTodosPedidosInputPort, times(1)).buscarTodosStatus(any(StatusPedidoEnum.class));
//            verifyNoMoreInteractions(buscaTodosPedidosInputPort);
//        }
//
//        @Test
//        void buscarCobrancaPorPedidoId() throws Exception {
//            var id = 1L;
//            var cobrancaDTO = getCobrancaDTO();
//
//            when(buscaCobrancaPorPedidoIdInputPort.buscarPorPedidoId(any(Long.class))).thenReturn(cobrancaDTO);
//
//            ResultActions result = mockMvc.perform(get("/producao/{id}/cobranca", id)
//                    .contentType(MediaType.APPLICATION_JSON)
//            );
//
//            result.andExpect(status().isOk());
//
//            verify(buscaCobrancaPorPedidoIdInputPort, times(1)).buscarPorPedidoId(any(Long.class));
//            verifyNoMoreInteractions(buscaCobrancaPorPedidoIdInputPort);
//        }
    }
}