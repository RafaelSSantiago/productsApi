package com.aocp.apirestaocp.controller;

import com.aocp.apirestaocp.model.Produto;
import com.aocp.apirestaocp.repository.ProdutoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@Api(value = "API REST Produtos")
public class ProdutoController {

    @Autowired
    ProdutoRepository RepoProduto;

    @GetMapping("/produtos")
    @ApiOperation(value = "Retorna a listagem de produtos")
    public ResponseEntity<List<Produto>> findAll() {
        List<Produto> list = RepoProduto.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/produtos/nome/{nome}")
    @ApiOperation(value = "Realiza a Busca por nome do produto")
    public ResponseEntity<List<Produto>> findByName(@PathVariable String nome) {
        List<Produto> nomes = RepoProduto.findByNomeIgnoreCase(nome);
        return ResponseEntity.ok(nomes);
    }

    @GetMapping("/produtos/{id}")
    @ApiOperation(value = "Busca o produto por Id")
    public ResponseEntity<Produto> getProdutosById(@PathVariable("id") Long id) {
        Optional<Produto> produtoData = RepoProduto.findById(id);

        if (produtoData.isPresent()) {
            return new ResponseEntity<>(produtoData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/produtos")
    @ApiOperation(value = "Cria um novo produto")
    public ResponseEntity<Produto> createProduct(@RequestBody Produto produto) {
        try {
            return ResponseEntity.ok(RepoProduto.save(produto));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/produtos/{id}")
    @ApiOperation(value = "Edita um produto por Id")
    public ResponseEntity<Produto> updateProduct(@PathVariable("id") long id, @RequestBody Produto produto) {
        Optional<Produto> produtoData = RepoProduto.findById(id);

        if (produtoData.isPresent()) {
            Produto _produto = produtoData.get();
            _produto.setNome(produto.getNome());
            _produto.setPreco(produto.getPreco());
            _produto.setCodigo(produto.getCodigo());
            _produto.setCategoria(produto.getCategoria());
            _produto.setStatus(produto.getStatus());

            return new ResponseEntity<>(RepoProduto.save(_produto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/produtos/{id}")
    @ApiOperation(value = "Deleta um produto por id")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") Long id) {
        try {
            RepoProduto.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/produtos/ids/{ids}")
    @ApiOperation(value = "Deleta vários produtos de uma vez")
    public ResponseEntity<HttpStatus> deleteAllProduct(@PathVariable List<Produto> ids) {
        try {
            RepoProduto.deleteAllInBatch(ids);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
