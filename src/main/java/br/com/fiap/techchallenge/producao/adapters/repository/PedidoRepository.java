package br.com.fiap.techchallenge.producao.adapters.repository;

import br.com.fiap.techchallenge.producao.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.producao.adapters.repository.mongo.PedidoMongoRepository;
import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.producao.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.producao.core.ports.out.pedido.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PedidoRepository implements CriaPedidoOutputPort, AtualizaStatusPedidoOutputPort,
        BuscaPedidosOutputPort, BuscarPedidoPorIdOutputPort {

    private final PedidoMongoRepository pedidoMongoRepository;
    private final PedidoMapper pedidoMapper;

    public PedidoRepository(PedidoMongoRepository pedidoMongoRepository, PedidoMapper pedidoMapper) {
        this.pedidoMongoRepository = pedidoMongoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public List<PedidoDTO> buscarTodos() {
        var listaPedidos = pedidoMongoRepository.findAll();
        return listaPedidos.stream().map(pedidoMapper::toPedidoDTO).toList();
    }

    @Override
    public List<PedidoDTO> buscarPedidosPorStatus(List<StatusPedidoEnum> status) {
        var listaPedidos = pedidoMongoRepository.findAllByStatusIn(status);
        return listaPedidos.stream().map(pedidoMapper::toPedidoDTO).toList();
    }

    @Override
    public PedidoDTO criar(PedidoDTO pedidoIn) {
        var pedido = pedidoMapper.toPedido(pedidoIn);
        var pedidoSalvo = pedidoMongoRepository.save(pedido);
        return pedidoMapper.toPedidoDTO(pedidoSalvo);
    }

    @Override
    public PedidoDTO atualizarStatus(String id, StatusPedidoEnum status) {
        var pedidoBuscado = buscarPedidoPorId(id);
        pedidoBuscado.setStatus(status);
        var pedido = pedidoMongoRepository.save(pedidoBuscado);
        return pedidoMapper.toPedidoDTO(pedido);
    }

    @Override
    public PedidoDTO buscarPorId(String id) {
        var pedidoBuscado = buscarPedidoPorId(id);
        return pedidoMapper.toPedidoDTO(pedidoBuscado);
    }

    private br.com.fiap.techchallenge.producao.adapters.repository.models.Pedido buscarPedidoPorId(String id){
        return pedidoMongoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido " + id + " não encontrado"));
    }

}
