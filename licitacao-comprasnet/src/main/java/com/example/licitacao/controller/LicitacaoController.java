package com.example.licitacao.controller;

import com.example.licitacao.model.*;
import com.example.licitacao.service.ItemService;
import com.example.licitacao.service.LicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/licitacoes")
public class LicitacaoController {

    private final LicitacaoService licitacaoService;
    private final ItemService itemService;

    @Autowired
    public LicitacaoController(LicitacaoService licitacaoService, ItemService itemService) {
        this.licitacaoService = licitacaoService;
        this.itemService = itemService;
    }

    @GetMapping
    public List<LicitacaoDTO> listarLicitacoes() throws IOException {
        List<Licitacao> licitacoes = licitacaoService.listarLicitacoes();
        List<LicitacaoDTO> licitacaoDTOs = licitacoes.stream()
                .map(licitacao -> new LicitacaoDTO(
                        licitacao.getNumero(),
                        licitacao.getDescricao(),
                        licitacao.isLida()
                ))
                .toList();

        return licitacaoDTOs;
    }

    @GetMapping("/lidas")
    public ResponseEntity<?> listarLicitacoesLidas() {
        List<Licitacao> lidas = licitacaoService.listarLicitacoesLidas();

        if (lidas.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Nenhuma licitação lida encontrada.");
            return ResponseEntity.ok(response);
        }

        List<LicitacaoDTO> licitacaoDTOs = lidas.stream()
                .map(licitacao -> new LicitacaoDTO(
                        licitacao.getNumero(),
                        licitacao.getDescricao(),
                        licitacao.isLida()
                ))
                .toList();

        return ResponseEntity.ok(licitacaoDTOs);
    }


    @GetMapping("/naolidas")
    public ResponseEntity<?> listarLicitacoesNaoLidas() {
        List<Licitacao> naoLidas = licitacaoService.listarLicitacoesNaoLidas();

        if (naoLidas.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Nenhuma licitação não lida encontrada.");
            return ResponseEntity.ok(response);
        }

        List<LicitacaoDTO> licitacaoDTOs = naoLidas.stream()
                .map(licitacao -> new LicitacaoDTO(
                        licitacao.getNumero(),
                        licitacao.getDescricao(),
                        licitacao.isLida()
                ))
                .toList();

        return ResponseEntity.ok(licitacaoDTOs);
    }

    @GetMapping("/{id}/detalhes")
    public ResponseEntity<LicitacaoDetalhesDTO> obterLicitacaoDownload(@PathVariable Long id) {
        Licitacao licitacao = licitacaoService.buscarLicitacaoPorId(id);

        String urlDownload = licitacaoService.construirUrlDownload(licitacao);
        RestTemplate restTemplate = new RestTemplate();
        String htmlResponse = restTemplate.getForObject(urlDownload, String.class);

        List<Item> itens = itemService.extrairItensDoHtml(htmlResponse);

        List<ItemDTO> itemDTOs = itens.stream()
                .map(item -> new ItemDTO(item.getNome(), item.getDescricao()))
                .toList();

        LicitacaoDetalhesDTO detalhesDTO = new LicitacaoDetalhesDTO(
                licitacao.getNumero(),
                licitacao.getDescricao(),
                licitacao.isLida(),
                itemDTOs
        );

        return ResponseEntity.ok(detalhesDTO);
    }

    @PostMapping("/{id}/marcarLida")
    public ResponseEntity<String> marcarComoLida(@PathVariable Long id) {
        licitacaoService.marcarComoLida(id);
        return ResponseEntity.ok("Licitacao marcada como lida.");
    }

    @PostMapping("/{id}/marcarNaoLida")
    public ResponseEntity<String> marcarComoNaoLida(@PathVariable Long id) {
        licitacaoService.marcarComoNaoLida(id);
        return ResponseEntity.ok("Licitacao marcada como não lida.");
    }
}
