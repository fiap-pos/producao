package br.com.fiap.techchallenge.producao.adapters.web;

import br.com.fiap.techchallenge.producao.adapters.web.models.requests.ItemPedidoRequest;
import br.com.fiap.techchallenge.producao.adapters.web.models.requests.PedidoRequest;
import br.com.fiap.techchallenge.producao.core.domain.entities.enums.CategoriaEnum;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class PedidoControllerIT {

    @LocalServerPort
    private int port;

    private String nome = "Nome do Cliente 1";
//    private String cpf = "64406282084";
//    private String email = "email1@email.com";

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void criaUmPedidoComLancheEBebidaComClienteCadastrado() throws Exception {
//        cadastraUmNovoProdutoLanche();
//        cadastraUmNovoProdutoBebida();
        nome = "Novo Cliente";
//        cpf = "76728342079";
//        email = "novocliente@email.com";
//        cadastraUmNovoCliente();

        var itemPedidoRequest1 = new ItemPedidoRequest("Item", "Item 1", 2);
        var itemPedidoRequest2 = new ItemPedidoRequest("Item2", "Item 2", 1);
        var pedidoRequest = new PedidoRequest(nome, List.of(itemPedidoRequest1, itemPedidoRequest2));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pedidoRequest)
        .when()
                .post("/pedidos")
        .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("schemas/pedido.schema.json"));
    }

    @Test
    void criaUmPedidoComLancheEBebidaSemClienteCadastrado() throws Exception {
//        cadastraUmNovoProdutoLanche();
//        cadastraUmNovoProdutoBebida();

        var itemPedidoRequest1 = new ItemPedidoRequest("Item", "Item 1", 2);
        var itemPedidoRequest2 = new ItemPedidoRequest("Item2", "Item 2", 1);
        var pedidoRequest = new PedidoRequest(List.of(itemPedidoRequest1, itemPedidoRequest2));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pedidoRequest)
        .when()
                .post("/pedidos")
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
//        .when()
//                .post("/clientes")
//        .then()
//                .log().all()
//                .statusCode(HttpStatus.CREATED.value())
//                .body(matchesJsonSchemaInClasspath("schemas/cliente.schema.json"));
//    }

//    @Test
//    void cadastraUmNovoProdutoLanche() throws Exception {
//        var produtoRequest = new ProdutoRequest(
//                "HAMBURGER ANGUS",
//                CategoriaEnum.LANCHE,
//                BigDecimal.valueOf(35.90),
//                "Hamburger Angus 200mg de carne"
//        );
//
//        given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(produtoRequest)
//        .when()
//                .post("/produtos")
//        .then()
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
//        .when()
//                .post("/produtos")
//        .then()
//                .log().all()
//                .statusCode(HttpStatus.CREATED.value())
//                .body(matchesJsonSchemaInClasspath("schemas/produto.schema.json"));
//
//    }
<<<<<<< HEAD
}
=======
}
>>>>>>> bc91fd6 (Adiciona testes)
