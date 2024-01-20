package br.com.fiap.techchallenge.producao.adapters.repository.mongo;

import br.com.fiap.techchallenge.producao.adapters.repository.models.Pedido;
import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoMongoRepository extends MongoRepository<Pedido, String> {
    List<Pedido> findAllByStatusIn(List<StatusPedidoEnum> status);

    Optional<Pedido> findByCodigo(Long codigo);
}
