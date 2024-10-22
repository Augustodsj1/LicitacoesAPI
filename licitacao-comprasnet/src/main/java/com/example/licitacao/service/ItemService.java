package com.example.licitacao.service;

import com.example.licitacao.model.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    public List<Item> extrairItensDoHtml(String html) {
        List<Item> itens = new ArrayList<>();
        Document document = Jsoup.parse(html);

        Elements itemElements = document.select("body > table:nth-child(3) > tbody > tr:nth-child(2) > td > table:nth-child(2) > tbody > tr:nth-child(3) > td.tex3");

        Elements rowsItens = itemElements.select("tr");

        for (Element row : rowsItens) {
            Elements cells = row.select("td");

            if (cells.size() >= 2) {
                Item item = new Item();

                Element nome = cells.get(1);
                String nomeItem = nome.select("span.tex3b").text();

                Element descricao = cells.get(1);
                String descricaoCompleta = descricao.select("span.tex3").html();
                descricaoCompleta = descricaoCompleta.replaceAll("<br>", "\n").trim();

                item.setNome(nomeItem);
                item.setDescricao(descricaoCompleta);
                itens.add(item);
            }
        }

        return itens;
    }
}
