package com.example.licitacao.model;

import java.util.List;

public class LicitacaoDetalhesDTO {
    private String numero;
    private String descricao;
    private boolean lida;
    private List<ItemDTO> itens;

    public LicitacaoDetalhesDTO(String numero, String descricao, boolean lida, List<ItemDTO> itens) {
        this.numero = numero;
        this.descricao = descricao;
        this.lida = lida;
        this.itens = itens;
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

    public List<ItemDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemDTO> itens) {
        this.itens = itens;
    }
}
