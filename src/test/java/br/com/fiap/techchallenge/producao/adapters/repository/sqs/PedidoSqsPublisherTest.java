package br.com.fiap.techchallenge.producao.adapters.repository.sqs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.support.GenericMessage;

import java.util.HashMap;
import java.util.UUID;

import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getPedidoDTO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoSqsPublisherTest {

    private PedidoSqsPublisher pedidoSqsPublisher;

    @Mock
    private SqsTemplate sqsTemplate;

    @Mock
    private ObjectMapper objectMapper;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoSqsPublisher = new PedidoSqsPublisher(sqsTemplate, objectMapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
    @Test
    void testEnviarParaFilaPagamentos() throws JsonProcessingException {
        UUID messageId = UUID.randomUUID();
        GenericMessage<String> message = new GenericMessage<>("Payload", new HashMap<>());
        var pedidoDTO = getPedidoDTO();

        when(objectMapper.writeValueAsString(pedidoDTO))
                .thenReturn("Payload");
        when(sqsTemplate.send(anyString(), anyString()))
                .thenReturn(new SendResult<>(messageId, "https://config.us-east-2.amazonaws.com", message, new HashMap<>()));

        pedidoSqsPublisher.publicaAtualizacaoFilaProducao(pedidoDTO);

        verify(objectMapper).writeValueAsString(pedidoDTO);
        verify(sqsTemplate).send(Mockito.<String>any(), eq("Payload"));
    }

    @Test
    void testEnviarParaFilaPagamentosThrowsJsonProcessingException() throws JsonProcessingException {
        var pedidoDTO = getPedidoDTO();

        when(objectMapper.writeValueAsString(pedidoDTO))
                .thenThrow(JsonProcessingException.class);

        assertThrows(JsonProcessingException.class, () -> pedidoSqsPublisher.publicaAtualizacaoFilaProducao(pedidoDTO));

        verify(objectMapper).writeValueAsString(pedidoDTO);
    }
}