package com.example.licitacao.model;

public class LicitacaoDTO {
    private String numero;
    private String descricao;
    private boolean lida;

    public LicitacaoDTO(String numero, String descricao, boolean lida) {
        this.numero = numero;
        this.descricao = descricao;
        this.lida = lida;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }
}
