package br.senac.devweb.api.product.produto;

import br.senac.devweb.api.product.categoria.Categoria;
import br.senac.devweb.api.product.categoria.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;

    @PostMapping("/")
    public ResponseEntity<ProdutoRepresentation.Detalhes> cadastrarProduto(
            @Valid @RequestBody ProdutoRepresentation.CreateOrUpdate createOrUpdate) {

        Categoria categoria = this.categoriaService.getCategoria(createOrUpdate.getCategoria());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProdutoRepresentation
                        .Detalhes.from(this.produtoService.salvar(createOrUpdate, categoria)));

    }

    @PutMapping ("/{id}")
    public ResponseEntity<ProdutoRepresentation.Detalhes> atualizarProduto(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProdutoRepresentation.CreateOrUpdate createOrUpdate) {

        Categoria categoria = this.categoriaService.getCategoria(createOrUpdate.getCategoria());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ProdutoRepresentation
                        .Detalhes.from(this.produtoService.atualizar(id, createOrUpdate, categoria)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProdutoRepresentation.Detalhes> deletarProduto(@PathVariable("id") Long id) {
        this.produtoService.deletar(id);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

}
