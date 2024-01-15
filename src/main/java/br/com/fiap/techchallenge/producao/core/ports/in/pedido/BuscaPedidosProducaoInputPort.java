package br.com.fiap.techchallenge.producao.core.ports.in.pedido;

import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;

import java.util.List;

public interface BuscaPedidosProducaoInputPort {

    List<PedidoDTO> buscarPedidosProducao();

}
