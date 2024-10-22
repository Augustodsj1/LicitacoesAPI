package com.example.licitacao.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Licitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String descricao;
    private String dataPublicacao;
    private String prazoSubmissao;
    private String orgao;
    private String valorEstimado;
    private boolean lida = false;

    private String codUasg;
    private String modPrp;
    private String numPrp;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "licitacao_id")
    private List<Item> itens;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getPrazoSubmissao() {
        return prazoSubmissao;
    }

    public void setPrazoSubmissao(String prazoSubmissao) {
        this.prazoSubmissao = prazoSubmissao;
    }

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public String getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(String valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    // Getters e Setters para os novos campos
    public String getCodUasg() {
        return codUasg;
    }

    public void setCodUasg(String codUasg) {
        this.codUasg = codUasg;
    }

    public String getModPrp() {
        return modPrp;
    }

    public void setModPrp(String modPrp) {
        this.modPrp = modPrp;
    }

    public String getNumPrp() {
        return numPrp;
    }

    public void setNumPrp(String numPrp) {
        this.numPrp = numPrp;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }
}
