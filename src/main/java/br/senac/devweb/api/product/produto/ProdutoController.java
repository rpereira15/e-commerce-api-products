package br.senac.devweb.api.product.produto;

import br.senac.devweb.api.product.categoria.Categoria;
import br.senac.devweb.api.product.categoria.CategoriaService;
import br.senac.devweb.api.product.util.Paginacao;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor
@CrossOrigin("*")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoRepository produtoRepository;
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

    @GetMapping("/")
    public ResponseEntity<Paginacao> buscarTodosProdutos(
            // Filtro com predicate utilizado para filtrar com qualquer atributo
            @QuerydslPredicate(root = Produto.class) Predicate filtroURI,
            @RequestParam(name = "tamanhoPagina", defaultValue = "30") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0")int pagina){
        // Criando filtro, validando se tem algum filtro informado na URI
        // Se for nulo filtra apenas por ativo se nao usa o ativo e o filtro da URI
        BooleanExpression filtro = Objects.isNull(filtroURI) ?
                QProduto.produto.status.eq(Produto.Status.ATIVO) :
                QProduto.produto.status.eq(Produto.Status.ATIVO).and(filtroURI);
        // paremetros para montar paginacao
        Pageable paginaDesejada = PageRequest.of(pagina, tamanhoPagina);
        // passando para o repository, procurar pelo filtro e paginacao
        Page<Produto> listaProduto = this.produtoRepository.findAll(filtro, paginaDesejada);
        // monta estrutura de retorno da paginação no JSON
        Paginacao paginacao = Paginacao.builder()
                .paginaSelecionada(pagina)
                .tamanhoPagina(tamanhoPagina)
                .proximaPagina(listaProduto.hasNext())
                .conteudo(ProdutoRepresentation.Detalhes
                        .from(listaProduto.getContent()))
                .build();
        return ResponseEntity.ok(paginacao);
    }

}
