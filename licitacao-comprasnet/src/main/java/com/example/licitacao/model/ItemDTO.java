package com.example.licitacao.model;

public class ItemDTO {
    private String nome;
    private String descricao;

    public ItemDTO(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
