package com.example.licitacao.repository;

import com.example.licitacao.model.Licitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicitacaoRepository extends JpaRepository<Licitacao, Long> {
    List<Licitacao> findByLidaTrue();
    List<Licitacao> findByLidaFalse();}
