package com.example.licitacao.service;

import com.example.licitacao.model.Licitacao;
import com.example.licitacao.repository.LicitacaoRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LicitacaoScraperService {

    @Autowired
    private LicitacaoRepository licitacaoRepository;
    private int idPagina = 1;

    private static final String BASE_URL = "http://comprasnet.gov.br/ConsultaLicitacoes/ConsLicitacaoDia.asp?";
    private static final int TIMEOUT_SECONDS = 240; // 3 * 60 seconds

    public List<Licitacao> capturarLicitacoes() {
        List<Licitacao> licitacoes = new ArrayList<>();

        try {
            Document document = Jsoup.connect(getPageUrl(idPagina)).timeout(TIMEOUT_SECONDS * 1000).get();

            while (document.toString().contains("Proximas")) {
                licitacoes.addAll(scrapeLicitacoesFromPage(document));
                idPagina++;
                document = Jsoup.connect(getPageUrl(idPagina)).timeout(TIMEOUT_SECONDS * 1000).get();
            }
        } catch (IOException e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }

        return licitacoes;
    }

    private List<Licitacao> scrapeLicitacoesFromPage(Document document) {
        List<Licitacao> licitacoes = new ArrayList<>();
        Elements rows = document.select("body > table.tex3 > tbody > tr:nth-child(1) tbody");

        for (Element row : rows) {
            Licitacao licitacao = new Licitacao();
            licitacao.setNumero(row.select("tbody > tr:nth-child(1)").text());

            String descricaoHtml = row.select("tbody > tr:nth-child(2)").html();
            String descricaoLimpa = Jsoup.parse(descricaoHtml).text(); // Remove HTML e obtém texto
            descricaoLimpa = formatarDescricao(descricaoLimpa); // Adiciona formatação, se necessário
            licitacao.setDescricao(descricaoLimpa);

            String codUasg = extractValueFromHtml(descricaoHtml, "Código da UASG: ");
            String numPrp = extractValueFromHtml(descricaoHtml, "Pregão Eletrônico Nº ").replaceAll("/", "");

            licitacao.setCodUasg(codUasg);
            licitacao.setModPrp("5");
            licitacao.setNumPrp(numPrp);

            licitacoes.add(licitacao);

            //licitacaoRepository.save(licitacao);
        }

        return licitacoes;
    }

    private String extractValueFromHtml(String html, String prefix) {
        int startIndex = html.indexOf(prefix) + prefix.length();
        int endIndex = html.indexOf("<", startIndex);
        if (startIndex > -1 && endIndex > -1) {
            return html.substring(startIndex, endIndex).trim();
        }
        return "";
    }

    private String formatarDescricao(String descricao) {
        return descricao.replaceAll("\\n+", "\n").trim();
    }

    private String getPageUrl(int pageNumber) {
        return BASE_URL + "pagina=" + pageNumber;
    }
}
