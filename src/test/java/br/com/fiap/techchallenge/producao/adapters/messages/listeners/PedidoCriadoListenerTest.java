package br.com.fiap.techchallenge.producao.adapters.messages.listeners;

import br.com.fiap.techchallenge.producao.core.dtos.CriaPedidoDTO;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.CriaPedidoInputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.sqs.model.Message;

import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getCriaPedidoDTO;
import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getPedidoDTO;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoCriadoListenerTest {

    private PedidoCriadoListener pedidoCriadoListener;

    @Mock
    private CriaPedidoInputPort criaPedidoInputPort;

    @Mock
    private ObjectMapper objectMapper;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoCriadoListener = new PedidoCriadoListener(criaPedidoInputPort, objectMapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void receberMensagem() throws JsonProcessingException {
        var pedidoDTO = getPedidoDTO();
        var criaPedidoDTO = getCriaPedidoDTO();
        var message = mock(Message.class);
        var criaPedidoJson = objectMapper.writeValueAsString(criaPedidoDTO);

        when(message.body()).thenReturn(criaPedidoJson);
        when(objectMapper.readValue(criaPedidoJson, CriaPedidoDTO.class)).thenReturn(criaPedidoDTO);
        when(criaPedidoInputPort.criar(criaPedidoDTO)).thenReturn(pedidoDTO);

        pedidoCriadoListener.receberMensagem(message);

        verify(criaPedidoInputPort, times(1)).criar(criaPedidoDTO);
    }

}