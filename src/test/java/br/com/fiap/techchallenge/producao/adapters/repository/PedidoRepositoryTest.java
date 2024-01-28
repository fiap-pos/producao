package br.com.fiap.techchallenge.producao.adapters.repository;

import br.com.fiap.techchallenge.producao.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.producao.adapters.repository.mongo.PedidoMongoRepository;
import br.com.fiap.techchallenge.producao.adapters.repository.sqs.PedidoSqsPublisher;
import br.com.fiap.techchallenge.producao.core.domain.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getListaPedido;
import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getListaStatusPedido;
import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getPedido;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class PedidoRepositoryTest {
    @Mock
    PedidoMongoRepository pedidoMongoRepository;

    @Mock
    PedidoMapper pedidoMapper;

    @Mock
    PedidoSqsPublisher pedidoSqsPublisher;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        var pedidoRepository = new PedidoRepository(pedidoMongoRepository, pedidoMapper, pedidoSqsPublisher);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void buscarTodos() {
        var pedidos = getListaPedido();
        when(pedidoMongoRepository.findAll()).thenReturn(pedidos);

        var pedidosBuscados = pedidoMongoRepository.findAll();

        assertThat(pedidosBuscados).isNotNull();
        assertThat(pedidosBuscados).allSatisfy( pedidoBuscado -> {
            assertThat(pedidoBuscado.getId()).isEqualTo(pedidos.get(0).getId());
            assertThat(pedidoBuscado.getCodigo()).isEqualTo(pedidos.get(0).getCodigo());
            assertThat(pedidoBuscado.getItens()).allSatisfy( item -> {
                assertThat(item.getNome()).isEqualTo(pedidos.get(0).getItens().get(0).getNome());
                assertThat(item.getDescricao()).isEqualTo(pedidos.get(0).getItens().get(0).getDescricao());
                assertThat(item.getQuantidade()).isEqualTo(pedidos.get(0).getItens().get(0).getQuantidade());
            });
            assertThat(pedidoBuscado.getStatus()).isEqualTo(pedidos.get(0).getStatus());
            assertThat(pedidoBuscado.getData()).isEqualTo(pedidos.get(0).getData());
        });

        verify(pedidoMongoRepository, times(1)).findAll();
        verifyNoMoreInteractions(pedidoMongoRepository);

    }

    @Test
    void buscarPedidosPorStatus() {
        var pedidos = getListaPedido();
        var listaStatus = getListaStatusPedido();
        when(pedidoMongoRepository.findAllByStatusIn(anyList())).thenReturn(pedidos);

        var pedidosBuscados = pedidoMongoRepository.findAllByStatusIn(listaStatus);

        assertThat(pedidosBuscados).isNotNull();
        assertThat(pedidosBuscados).allSatisfy( pedidoBuscado -> {
            assertThat(pedidoBuscado.getId()).isEqualTo(pedidos.get(0).getId());
            assertThat(pedidoBuscado.getCodigo()).isEqualTo(pedidos.get(0).getCodigo());
            assertThat(pedidoBuscado.getItens()).allSatisfy( item -> {
                assertThat(item.getNome()).isEqualTo(pedidos.get(0).getItens().get(0).getNome());
                assertThat(item.getDescricao()).isEqualTo(pedidos.get(0).getItens().get(0).getDescricao());
                assertThat(item.getQuantidade()).isEqualTo(pedidos.get(0).getItens().get(0).getQuantidade());
            });
            assertThat(pedidoBuscado.getStatus()).isEqualTo(pedidos.get(0).getStatus());
            assertThat(pedidoBuscado.getData()).isEqualTo(pedidos.get(0).getData());
        });

        verify(pedidoMongoRepository, times(1)).findAllByStatusIn(anyList());
        verifyNoMoreInteractions(pedidoMongoRepository);
    }

    @Test
    void criar() {
        var pedido = getPedido();
        when(pedidoMongoRepository.save(pedido)).thenReturn(pedido);

        var pedidoSalvo = pedidoMongoRepository.save(pedido);
        verify(pedidoMongoRepository, times(1)).save(pedido);

        assertThat(pedidoSalvo).isNotNull().isEqualTo(pedido);
        assertThat(pedidoSalvo.getId()).isEqualTo(pedido.getId());
        assertThat(pedidoSalvo.getStatus()).isEqualTo(pedido.getStatus());
        assertThat(pedidoSalvo.getCliente()).isEqualTo(pedido.getCliente());
        assertThat(pedidoSalvo.getItens()).isEqualTo(pedido.getItens());
    }
    @Test
    void buscarPorId() {
        var pedido = getPedido();
        when(pedidoMongoRepository.findById(anyString())).thenReturn(Optional.of(pedido));

        var pedidoOptional = pedidoMongoRepository.findById(pedido.getId());
        verify(pedidoMongoRepository, times(1)).findById(pedido.getId());

        assertThat(pedidoOptional).isPresent().containsSame(pedido);

        pedidoOptional.ifPresent(pedidoSalva -> {
            assertThat(pedidoSalva.getId()).isEqualTo(pedido.getId());
            assertThat(pedidoSalva.getStatus()).isEqualTo(pedido.getStatus());
            assertThat(pedidoSalva.getStatus()).isEqualTo(pedido.getStatus());
        });
    }

    @Test
    void buscarPorIdInexistente() {
        var id = "7";
        when(pedidoMongoRepository.findById(id)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> pedidoMongoRepository.findById(id),"Cobrança com o id " + id + " não existe");
    }

    @Test
    void buscarPorCodigo() {
        var pedido = getPedido();
        when(pedidoMongoRepository.findByCodigo(anyLong())).thenReturn(Optional.of(pedido));

        var pedidoOptional = pedidoMongoRepository.findByCodigo(pedido.getCodigo());
        verify(pedidoMongoRepository, times(1)).findByCodigo(pedido.getCodigo());

        assertThat(pedidoOptional).isPresent().containsSame(pedido);

        pedidoOptional.ifPresent(pedidoSalva -> {
            assertThat(pedidoSalva.getId()).isEqualTo(pedido.getId());
            assertThat(pedidoSalva.getStatus()).isEqualTo(pedido.getStatus());
            assertThat(pedidoSalva.getStatus()).isEqualTo(pedido.getStatus());
        });
    }
    @Test
    void buscarPorCodigoInexistente() {
        var codigo = 7L;
        when(pedidoMongoRepository.findByCodigo(codigo)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> pedidoMongoRepository.findByCodigo(codigo),"Pedido com codigo " + codigo + " não encontrado");
    }
}