package br.com.fiap.techchallenge.producao.adapters.messages.listeners;

import br.com.fiap.techchallenge.producao.core.dtos.CriaPedidoDTO;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.CriaPedidoInputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.sqs.model.Message;

@Component
public class PedidoCriadoListener {

    private static Logger logger = LoggerFactory.getLogger(PedidoCriadoListener.class);

    private final CriaPedidoInputPort criaPedidoInputPort;

    private final ObjectMapper objectMapper;

    public PedidoCriadoListener(CriaPedidoInputPort criaPedidoInputPort, ObjectMapper objectMapper) {
        this.criaPedidoInputPort = criaPedidoInputPort;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @SqsListener("${aws.sqs.queues.pedido-criado}")
    public void receberMensagem(Message mensagem) throws JsonProcessingException {
        logger.info("Recebendo mensagem: {}", mensagem);

        var criaPedidoDTO = objectMapper.readValue(mensagem.body(), CriaPedidoDTO.class);
        criaPedidoInputPort.criar(criaPedidoDTO);
    }
}
