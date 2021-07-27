package com.aocp.apirestaocp.repository;

import com.aocp.apirestaocp.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeIgnoreCase(String nome);

}
