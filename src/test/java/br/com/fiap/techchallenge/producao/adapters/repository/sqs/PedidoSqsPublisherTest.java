package br.com.fiap.techchallenge.producao.adapters.repository.sqs;

import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.support.GenericMessage;

import java.util.HashMap;
import java.util.UUID;

import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getPedidoDTO;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoSqsPublisherTest {
    @InjectMocks
    private PedidoSqsPublisher pedidoSqsPublisher;

    @Mock
    private SqsTemplate sqsTemplate;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoSqsPublisher = new PedidoSqsPublisher(sqsTemplate);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
    @Test
    void testEnviarParaFilaPagamentos() {
        UUID messageId = UUID.randomUUID();
        GenericMessage<String> message = new GenericMessage<>("Payload", new HashMap<>());
        var pedidoDTO = getPedidoDTO();

        when(sqsTemplate.send(anyString(), anyString()))
                .thenReturn(new SendResult<>(messageId, "https://config.us-east-2.amazonaws.com", message, new HashMap<>()));

        pedidoSqsPublisher.publicaAtualizacaoFilaProducao(pedidoDTO);

        verify(sqsTemplate).send(Mockito.<String>any(), Mockito.<String>any());
    }
}