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
    @InjectMocks
    PedidoRepository pedidoRepository;

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
        var pedidoDTO = getPedidoDTO();
        when(pedidoMongoRepository.findAll()).thenReturn(pedidos);

        when(pedidoMapper.toPedidoDTO(any(Pedido.class))).thenReturn(pedidoDTO);

        var pedidosBuscados = pedidoRepository.buscarTodos();

        assertThat(pedidosBuscados).isNotNull();
        assertThat(pedidosBuscados).allSatisfy( pedidoBuscado -> {
            assertThat(pedidoBuscado.id()).isEqualTo(pedidos.get(0).getId());
            assertThat(pedidoBuscado.codigo()).isEqualTo(pedidos.get(0).getCodigo());
            assertThat(pedidoBuscado.itens()).allSatisfy( item -> {
                assertThat(item.nome()).isEqualTo(pedidos.get(0).getItens().get(0).getNome());
                assertThat(item.descricao()).isEqualTo(pedidos.get(0).getItens().get(0).getDescricao());
                assertThat(item.quantidade()).isEqualTo(pedidos.get(0).getItens().get(0).getQuantidade());
            });
            assertThat(pedidoBuscado.status()).isEqualTo(pedidos.get(0).getStatus());
            assertThat(pedidoBuscado.dataCriacao()).isEqualTo(pedidos.get(0).getData());
        });

        verify(pedidoMongoRepository, times(1)).findAll();
        verifyNoMoreInteractions(pedidoMongoRepository);

    }

    @Test
    void buscarPedidosPorStatus() {
        var pedidos = getListaPedido();
        var listaStatus = getListaStatusPedido();
        var pedidoDTO = getPedidoDTO();

        when(pedidoMongoRepository.findAllByStatusIn(anyList())).thenReturn(pedidos);
        when(pedidoMapper.toPedidoDTO(any(Pedido.class))).thenReturn(pedidoDTO);

        var pedidosBuscados = pedidoRepository.buscarPedidosPorStatus(listaStatus);

        assertThat(pedidosBuscados).isNotNull();
        assertThat(pedidosBuscados).allSatisfy( pedidoBuscado -> {
            assertThat(pedidoBuscado.id()).isEqualTo(pedidos.get(0).getId());
            assertThat(pedidoBuscado.codigo()).isEqualTo(pedidos.get(0).getCodigo());
            assertThat(pedidoBuscado.itens()).allSatisfy( item -> {
                assertThat(item.nome()).isEqualTo(pedidos.get(0).getItens().get(0).getNome());
                assertThat(item.descricao()).isEqualTo(pedidos.get(0).getItens().get(0).getDescricao());
                assertThat(item.quantidade()).isEqualTo(pedidos.get(0).getItens().get(0).getQuantidade());
            });
            assertThat(pedidoBuscado.status()).isEqualTo(pedidos.get(0).getStatus());
            assertThat(pedidoBuscado.dataCriacao()).isEqualTo(pedidos.get(0).getData());
        });

        verify(pedidoMongoRepository, times(1)).findAllByStatusIn(anyList());
        verifyNoMoreInteractions(pedidoMongoRepository);
    }

    @Test
    void criar() {
        var pedido = getPedido();
        var pedidoDTO = getPedidoDTO();

        when(pedidoMapper.toPedido(any(PedidoDTO.class))).thenReturn(pedido);
        when(pedidoMongoRepository.save(any(Pedido.class))).thenReturn(pedido);
        when(pedidoMapper.toPedidoDTO(any(Pedido.class))).thenReturn(pedidoDTO);


        var pedidoSalvo = pedidoRepository.criar(pedidoDTO);
        verify(pedidoMongoRepository, times(1)).save(pedido);

        assertThat(pedidoSalvo).isNotNull().isEqualTo(pedidoDTO);
        assertThat(pedidoSalvo.id()).isEqualTo(pedidoDTO.id());
        assertThat(pedidoSalvo.status()).isEqualTo(pedidoDTO.status());
        assertThat(pedidoSalvo.cliente()).isEqualTo(pedidoDTO.cliente());
        assertThat(pedidoSalvo.codigo()).isEqualTo(pedidoDTO.codigo());
        assertThat(pedidoSalvo.itens()).isEqualTo(pedidoDTO.itens());

        verifyNoMoreInteractions(pedidoMongoRepository);
    }
    @Test
    void buscarPorId() {
        var pedido = getPedido();
        var pedidoDTO = getPedidoDTO();
        when(pedidoMongoRepository.findById(anyString())).thenReturn(Optional.of(pedido));
        when(pedidoMapper.toPedidoDTO(any(Pedido.class))).thenReturn(pedidoDTO);

        var pedidoBuscado = pedidoRepository.buscarPorId(pedido.getId());
        verify(pedidoMongoRepository, times(1)).findById(pedido.getId());

        assertThat(pedidoBuscado).isNotNull().isEqualTo(pedidoDTO);

        assertThat(pedidoBuscado.id()).isEqualTo(pedidoDTO.id());
        assertThat(pedidoBuscado.status()).isEqualTo(pedidoDTO.status());
        assertThat(pedidoBuscado.codigo()).isEqualTo(pedidoDTO.codigo());
        assertThat(pedidoBuscado.cliente()).isEqualTo(pedidoDTO.cliente());
    }

    @Test
    void buscarPorIdInexistente() {
        var id = "7";
        when(pedidoMongoRepository.findById(id)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> pedidoRepository.buscarPorId(id),"Cobrança com o id " + id + " não existe");
    }

    @Test
    void buscarPorCodigo() {
        var pedido = getPedido();
        var pedidoDTO = getPedidoDTO();

        when(pedidoMongoRepository.findByCodigo(anyLong())).thenReturn(Optional.of(pedido));
        when(pedidoMapper.toPedidoDTO(any(Pedido.class))).thenReturn(pedidoDTO);

        var pedidoBuscado = pedidoRepository.buscarPorCodigo(pedido.getCodigo());
        verify(pedidoMongoRepository, times(1)).findByCodigo(pedido.getCodigo());

        assertThat(pedidoBuscado).isNotNull().isEqualTo(pedidoDTO);

        assertThat(pedidoBuscado.id()).isEqualTo(pedidoDTO.id());
        assertThat(pedidoBuscado.status()).isEqualTo(pedidoDTO.status());
        assertThat(pedidoBuscado.cliente()).isEqualTo(pedidoDTO.cliente());
        assertThat(pedidoBuscado.codigo()).isEqualTo(pedidoDTO.codigo());
    }
    @Test
    void buscarPorCodigoInexistente() {
        var codigo = 7L;
        when(pedidoMongoRepository.findByCodigo(codigo)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> pedidoRepository.buscarPorCodigo(codigo),"Pedido com codigo " + codigo + " não encontrado");
    }

    @Test
    void atualizaStatus() throws JsonProcessingException {
        var id = "1";
        var status = StatusPedidoEnum.RECEBIDO;
        var pedidoDTO = getPedidoDTO();
        var pedido = getPedido();

        when(pedidoMongoRepository.findById(anyString())).thenReturn(Optional.of(pedido));
        when(pedidoMongoRepository.save(any(Pedido.class))).thenReturn(pedido);
        when(pedidoMapper.toPedidoDTO(any(Pedido.class))).thenReturn(pedidoDTO);

        var pedidoAtualizado = pedidoRepository.atualizarStatus(id, status);

        verify(pedidoMongoRepository, times(1)).save(any(Pedido.class));
        verify(pedidoMapper, times(1)).toPedidoDTO(any(Pedido.class));

        assertThat(pedidoAtualizado).isNotNull().isEqualTo(pedidoDTO);
        assertThat(pedidoAtualizado.id()).isEqualTo(pedidoDTO.id());
        assertThat(pedidoAtualizado.itens()).isEqualTo(pedidoDTO.itens());
        assertThat(pedidoAtualizado.status()).isEqualTo(pedidoDTO.status());
        assertThat(pedidoAtualizado.cliente()).isEqualTo(pedidoDTO.cliente());
    }
}