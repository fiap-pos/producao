package br.com.fiap.techchallenge.producao.adapters.repository.sqs;

import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class PedidoListenerTest {

    Logger logger = LoggerFactory.getLogger(PedidoListenerTest.class);

    @SqsListener(value = "${aws.sqs.queues.producao}")
    public void listen(Message<PedidoDTO> message) {
        logger.info(message.getPayload().toString());
    }


}
