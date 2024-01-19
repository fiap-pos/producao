package br.com.fiap.techchallenge.producao.config;

import br.com.fiap.techchallenge.producao.core.ports.in.pedido.*;
import br.com.fiap.techchallenge.producao.core.ports.out.pedido.*;
import br.com.fiap.techchallenge.producao.core.usecases.pedido.BuscaPedidosProducaoUseCase;
import br.com.fiap.techchallenge.producao.core.usecases.pedido.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreInjectionConfig {

    @Bean
    CriaPedidoInputPort criarPedido(CriaPedidoOutputPort criaPedidoOutputPort, BuscarPedidoOutputPort buscarPedidoOutputPort) {
        return new CriaPedidoUseCase(criaPedidoOutputPort, buscarPedidoOutputPort);
    }

    @Bean
    AtualizaStatusPedidoInputPort atualizaStatusPedido(AtualizaStatusPedidoOutputPort atualizaStatusPedidoOutputPort){
        return new AtualizaStatusPedidoUseCase(atualizaStatusPedidoOutputPort);
    }

    @Bean
    BuscarPedidoPorIdInputPort buscarPedidoPorId(BuscarPedidoOutputPort buscarPedidoOutputPort){
        return new BuscarPedidoUseCase(buscarPedidoOutputPort);
    }
    @Bean
    BuscaTodosPedidosInputPort buscarTodosPedidos(BuscaPedidosOutputPort buscaPedidosOutputPort) {
        return new BuscaTodosPedidosUseCase(buscaPedidosOutputPort);
    }

    @Bean
    BuscaPedidosProducaoInputPort ordenaPorPrioridade(BuscaPedidosOutputPort buscaPedidosOutputPort) {
        return new BuscaPedidosProducaoUseCase(buscaPedidosOutputPort);
    }
}
