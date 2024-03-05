package br.com.fiap.techchallenge.producao.adapters.repository;

import br.com.fiap.techchallenge.producao.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.producao.adapters.repository.models.Pedido;
import br.com.fiap.techchallenge.producao.adapters.repository.mongo.PedidoMongoRepository;
import br.com.fiap.techchallenge.producao.adapters.repository.sqs.PedidoSqsPublisher;
import br.com.fiap.techchallenge.producao.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.producao.core.domain.exceptions.UnexpectedDomainException;
import br.com.fiap.techchallenge.producao.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.producao.core.dtos.PedidoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getListaPedido;
import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getListaStatusPedido;
import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getPedido;
import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getPedidoDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PedidoRepositoryTest {

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
        pedidoRepository = new PedidoRepository(pedidoMongoRepository, pedidoMapper, pedidoSqsPublisher);
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

        assertThat(pedidosBuscados)
                .isNotNull()
                .isNotEmpty()
                .allSatisfy( pedidoBuscado -> {
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

        assertThat(pedidosBuscados)
                .isNotNull()
                .isNotEmpty()
                .allSatisfy( pedidoBuscado -> {
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
    }

    @Test
    void buscarPorIdInexistente() {
        when(pedidoMongoRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pedidoRepository.buscarPorId("1"));
        verify(pedidoMongoRepository).findById(anyString());
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
        assertThat(pedidoBuscado.codigo()).isEqualTo(pedidoDTO.codigo());
    }
    @Test
    void buscarPorCodigoInexistente() {
        when(pedidoMongoRepository.findByCodigo(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pedidoRepository.buscarPorCodigo(1L));
        verify(pedidoMongoRepository).findByCodigo(anyLong());
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
        verify(pedidoSqsPublisher, times(1)).publicaAtualizacaoFilaProducao(pedidoDTO);

        assertThat(pedidoAtualizado).isNotNull().isEqualTo(pedidoDTO);
        assertThat(pedidoAtualizado.id()).isEqualTo(pedidoDTO.id());
        assertThat(pedidoAtualizado.itens()).isEqualTo(pedidoDTO.itens());
        assertThat(pedidoAtualizado.status()).isEqualTo(pedidoDTO.status());
    }

    @Test
    void atualizaStatusJsonException() throws JsonProcessingException {
        var id = "1";
        var status = StatusPedidoEnum.RECEBIDO;
        var pedidoDTO = getPedidoDTO();
        var pedido = getPedido();

        when(pedidoMongoRepository.findById(anyString())).thenReturn(Optional.of(pedido));
        when(pedidoMongoRepository.save(any(Pedido.class))).thenReturn(pedido);
        when(pedidoMapper.toPedidoDTO(any(Pedido.class))).thenReturn(pedidoDTO);

        doThrow(JsonProcessingException.class).when(pedidoSqsPublisher).publicaAtualizacaoFilaProducao(pedidoDTO);


        assertThrows(UnexpectedDomainException.class, () -> pedidoRepository.atualizarStatus(id, status));

        verify(pedidoMongoRepository, times(1)).save(any(Pedido.class));
        verify(pedidoMapper, times(1)).toPedidoDTO(any(Pedido.class));
        verify(pedidoSqsPublisher, times(1)).publicaAtualizacaoFilaProducao(pedidoDTO);
    }
}