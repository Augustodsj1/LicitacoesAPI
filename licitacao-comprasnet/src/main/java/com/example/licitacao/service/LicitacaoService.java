package com.example.licitacao.service;

import com.example.licitacao.model.Licitacao;
import com.example.licitacao.repository.LicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
public class LicitacaoService {

    @Autowired
    private LicitacaoRepository licitacaoRepository;
    @Autowired
    private LicitacaoScraperService licitacaoScraperService;

    public List<Licitacao> listarLicitacoes() throws IOException {
        List<Licitacao> novasLicitacoes = licitacaoScraperService.capturarLicitacoes();
        for (Licitacao licitacao : novasLicitacoes) {
            licitacaoRepository.save(licitacao);
        }
        List<Licitacao> licitacoes = licitacaoRepository.findAll();
        return licitacoes;
    }

    public Licitacao buscarLicitacaoPorId(Long id) {
        return licitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Licitacao não encontrada"));
    }

    public List<Licitacao> listarLicitacoesLidas() {
        return licitacaoRepository.findByLidaTrue();
    }

    public List<Licitacao> listarLicitacoesNaoLidas() {
        return licitacaoRepository.findByLidaFalse();
    }

    public void marcarComoLida(Long id) {
        Licitacao licitacao = licitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Licitacao não encontrada"));
        licitacao.setLida(true);
        licitacaoRepository.save(licitacao);
    }

    public void marcarComoNaoLida(Long id) {
        Licitacao licitacao = licitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Licitacao não encontrada"));
        licitacao.setLida(false);
        licitacaoRepository.save(licitacao);
    }
    public String construirUrlDownload(Licitacao licitacao) {
        return "http://comprasnet.gov.br/ConsultaLicitacoes/download/download_editais_detalhe.asp?" +
                "coduasg=" + licitacao.getCodUasg() +
                "&modprp=" + licitacao.getModPrp() +
                "&numprp=" + licitacao.getNumPrp();
    }

}
