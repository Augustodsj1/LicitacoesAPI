package com.example;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.licitacao.model.Licitacao;
import com.example.licitacao.service.LicitacaoScraperService;

public class LicitacaoScraperServiceTest {

    @InjectMocks
    private LicitacaoScraperService licitacaoScraperService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCapturarLicitacoes() throws IOException {

        List<Licitacao> licitacoes = licitacaoScraperService.capturarLicitacoes();

        assertNotNull(licitacoes);
    }

}
