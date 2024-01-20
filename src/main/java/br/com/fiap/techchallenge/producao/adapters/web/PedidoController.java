package br.com.fiap.techchallenge.producao.adapters.web;

import br.com.fiap.techchallenge.producao.adapters.web.mappers.PedidoMapper;
import br.com.fiap.techchallenge.producao.adapters.web.models.requests.AtualizaStatusPedidoRequest;
import br.com.fiap.techchallenge.producao.adapters.web.models.requests.PedidoRequest;
import br.com.fiap.techchallenge.producao.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.producao.core.ports.in.pedido.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pedido", description = "APIs para gerenciamento dos Pedidos")
@RestController
@RequestMapping("/producao")
public class PedidoController extends ControllerBase{
    private final CriaPedidoInputPort criaPedidoInputPort;
    private final AtualizaStatusPedidoInputPort atualizaStatusPedidoInputPort;
    private final BuscaTodosPedidosInputPort buscaTodosPedidosInputPort;
    private final BuscaPedidosProducaoInputPort buscaPedidosProducaoInputPort;
    private final BuscarPedidoPorIdInputPort buscarPedidoPorIdInputPort;

    private final PedidoMapper pedidoMapper;

    public PedidoController(CriaPedidoInputPort criaPedidoInputPort,
                            AtualizaStatusPedidoInputPort atualizaStatusPedidoInputPort,
                            BuscaTodosPedidosInputPort buscaTodosPedidosInputPort,
                            BuscaPedidosProducaoInputPort buscaPedidosProducaoInputPort,
                            BuscarPedidoPorIdInputPort buscarPedidoPorIdInputPort,
                            PedidoMapper pedidoMapper
    ) {
        this.criaPedidoInputPort = criaPedidoInputPort;
        this.atualizaStatusPedidoInputPort = atualizaStatusPedidoInputPort;
        this.buscaTodosPedidosInputPort = buscaTodosPedidosInputPort;
        this.buscaPedidosProducaoInputPort = buscaPedidosProducaoInputPort;
        this.buscarPedidoPorIdInputPort = buscarPedidoPorIdInputPort;
        this.pedidoMapper = pedidoMapper;
    }

    @Operation(summary = "Busca todos os pedidos")
    @GetMapping
    public ResponseEntity<List<PedidoResponse>> buscarTodos(){
        var pedidosOut = buscaTodosPedidosInputPort.buscarTodos();
        var listPedidoResponse = pedidoMapper.toPedidoListResponse(pedidosOut);
        return ResponseEntity.ok(listPedidoResponse);
    }

    @Operation(summary = "Busca pedidos para serem exibidos na fila de preparação")
    @GetMapping("/fila")
    public ResponseEntity<List<PedidoResponse>> buscarTodosPedidosPorPrioridade(){
        var pedidosOut = buscaPedidosProducaoInputPort.buscarPedidosProducao();
        var listPedidoResponse = pedidoMapper.toPedidoListResponse(pedidosOut);
        return ResponseEntity.ok(listPedidoResponse);
    }

    @Operation(summary = "Busca pedido pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable("id") String id){
        var pedidoOut = buscarPedidoPorIdInputPort.buscarPorId(id);
        var pedidoResponse = pedidoMapper.toPedidoResponse(pedidoOut);
        return ResponseEntity.ok(pedidoResponse);
    }

    @Operation(summary = "Cria um pedido")
    @PostMapping
    public ResponseEntity<PedidoResponse> criarPedido(@Valid @RequestBody PedidoRequest pedidoRequest){
        var pedidoOut = criaPedidoInputPort.criar(pedidoRequest.toCriaPedidoDTO());
        var pedidoResponse = pedidoMapper.toPedidoResponse(pedidoOut);
        var uri = getExpandedCurrentUri("/{id}", pedidoResponse.getId());
        return ResponseEntity.created(uri).body(pedidoResponse);
    }

    @Operation(summary = "Atualiza status de um  pedido")
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponse> atualizaStatus(@PathVariable("id") String id,
                                                         @RequestBody AtualizaStatusPedidoRequest pedidoRequest){
        var pedidoOut = atualizaStatusPedidoInputPort.atualizarStatus(id, pedidoRequest.toAtualizaStatusPedidoDTO());
        var pedidoResponse = pedidoMapper.toPedidoResponse(pedidoOut);
        var uri = getExpandedCurrentUri("/{id}", pedidoResponse.getId());
        return ResponseEntity.created(uri).body(pedidoResponse);
    }

}
