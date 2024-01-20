package br.com.fiap.techchallenge.producao.adapters.repository.models;


public class Cliente {

    private String nome;

    public Cliente() {}

    public Cliente(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
