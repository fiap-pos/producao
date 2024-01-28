package br.com.fiap.techchallenge.producao.adapters.web;

import br.com.fiap.techchallenge.producao.adapters.web.models.requests.PedidoRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static br.com.fiap.techchallenge.producao.utils.PedidoHelper.getItemPedidoRequest;
import static io.restassured.RestAssured.given;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
class PedidoControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    private String nome = "Nome do Cliente 1";

//    @Test
//    void criaUmPedidoComLancheEBebidaComClienteCadastrado() throws Exception {
//        cadastraUmNovoProdutoLanche();
//        cadastraUmNovoProdutoBebida();
//        nome = "Novo Cliente";
//        cadastraUmNovoCliente();
//
//        var itemPedidoRequest1 = getItemPedidoRequest();
//        var pedidoRequest = new PedidoRequest(nome, List.of(itemPedidoRequest1));
//
//        given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(pedidoRequest)
//                .when()
//                .post("/producao")
//                .then()
//                .log().all()
//                .statusCode(HttpStatus.CREATED.value())
//                .body(matchesJsonSchemaInClasspath("schemas/pedido-com-cliente.schema.json"));
//    }

    @Test
    void criaUmPedidoComLancheEBebidaSemClienteCadastrado() throws Exception {
//        cadastraUmNovoProdutoLanche();
//        cadastraUmNovoProdutoBebida();

        var itemPedidoRequest1 = getItemPedidoRequest();
        var pedidoRequest = new PedidoRequest("Nome Cliente",List.of(itemPedidoRequest1));
        pedidoRequest.setCodigo(10L);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pedidoRequest)
                .when()
                .post("/producao")
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

//    @Test
//    void cadastraUmNovoCliente() {
//        var cliente = getClienteRequest(nome, cpf, email);
//
//        given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(cliente)
//                .when()
//                .post("/clientes")
//                .then()
//                .log().all()
//                .statusCode(HttpStatus.CREATED.value())
//                .body(matchesJsonSchemaInClasspath("schemas/cliente.schema.json"));
//    }
//
//    @Test
//    void cadastraUmNovoProdutoLanche() throws Exception {
//        var produtoRequest = getProdutoRequest(
//                "HAMBURGER ANGUS",
//                CategoriaEnum.LANCHE,
//                BigDecimal.valueOf(35.90),
//                "Hamburger Angus 200mg de carne"
//        );
//
//        given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(produtoRequest)
//                .when()
//                .post("/produtos")
//                .then()
//                .log().all()
//                .statusCode(HttpStatus.CREATED.value())
//                .body(matchesJsonSchemaInClasspath("schemas/produto.schema.json"));
//
//    }
//
//    @Test
//    void cadastraUmNovoProdutoBebida() throws Exception {
//        var produtoRequest = getProdutoRequest(
//                "COCA-COLA LATA",
//                CategoriaEnum.BEBIDA,
//                BigDecimal.valueOf(7.50),
//                "Coca-Cola Lata 350 ml"
//        );
//
//        given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(produtoRequest)
//                .when()
//                .post("/produtos")
//                .then()
//                .log().all()
//                .statusCode(HttpStatus.CREATED.value())
//                .body(matchesJsonSchemaInClasspath("schemas/produto.schema.json"));
//
//    }
}