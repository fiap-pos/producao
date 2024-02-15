package br.com.fiap.techchallenge.producao.adapters.repository.sqs;

import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PedidoSqsPublisher {

    private ObjectMapper objectMapper;

    @Value("${aws.sqs.queues.producao}")
    private String filaProducao;

    private SqsTemplate sqsTemplate;

    public PedidoSqsPublisher(SqsTemplate sqsTemplate, ObjectMapper objectMapper){
        this.sqsTemplate = sqsTemplate;
        this.objectMapper = objectMapper;
    }

    public void publicaAtualizacaoFilaProducao(PedidoDTO pedidoDTO) throws JsonProcessingException {
        sqsTemplate.send(filaProducao, objectMapper.writeValueAsString(pedidoDTO));
    }

}
