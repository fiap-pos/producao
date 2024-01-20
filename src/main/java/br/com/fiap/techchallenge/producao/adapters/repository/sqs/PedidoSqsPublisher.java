package br.com.fiap.techchallenge.producao.adapters.repository.sqs;

import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PedidoSqsPublisher {

    @Value("${aws.sqs.queues.producao}")
    private String filaProducao;

    private SqsTemplate sqsTemplate;

    public PedidoSqsPublisher(SqsTemplate sqsTemplate){
        this.sqsTemplate = sqsTemplate;
    }

    public void publicaAtualizacaoFilaProducao(PedidoDTO pedidoDTO) {
        sqsTemplate.send(filaProducao, pedidoDTO);
    }

}
