package br.senac.devweb.api.product.produto;

import br.senac.devweb.api.product.categoria.Categoria;
import br.senac.devweb.api.product.categoria.CategoriaRepresentation;
import br.senac.devweb.api.product.categoria.CategoriaService;
import br.senac.devweb.api.product.exceptions.NotFoundException;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    public Produto salvar(ProdutoRepresentation.CreateOrUpdate createOrUpdate) {

        Categoria categoria = this.categoriaService.getCategoria(createOrUpdate.getCategoria());

        Produto produto = Produto.builder()
                .nome(createOrUpdate.getNome())
                .descricao(createOrUpdate.getDescricao())
                .complemento(Strings.isEmpty(createOrUpdate.getComplemento()) ? "" : createOrUpdate.getComplemento())
                .fabricante(createOrUpdate.getFabricante())
                .fornecedor(Strings.isEmpty(createOrUpdate.getFornecedor()) ? "" : createOrUpdate.getFornecedor())
                .qtde(createOrUpdate.getQtde())
                .valor(createOrUpdate.getValor())
                .unidadeMedida(createOrUpdate.getUnidadeMedida())
                .categoria(categoria)
                .status(Produto.Status.ATIVO)
                .build();

        return this.produtoRepository.save(produto);
    }

    public Produto atualizar(Long id, ProdutoRepresentation.CreateOrUpdate createOrUpdate) {

        Produto produtoAntigo = this.buscarUm(id);

        Categoria categoria = this.categoriaService.getCategoria(createOrUpdate.getCategoria());

        Produto produtoAtualizado = produtoAntigo.toBuilder()
                .nome(createOrUpdate.getNome())
                .descricao(createOrUpdate.getDescricao())
                .complemento(Strings.isEmpty(createOrUpdate.getComplemento()) ? "" : createOrUpdate.getComplemento())
                .fabricante(createOrUpdate.getFabricante())
                .fornecedor(Strings.isEmpty(createOrUpdate.getFornecedor()) ? "" : createOrUpdate.getFornecedor())
                .qtde(createOrUpdate.getQtde())
                .valor(createOrUpdate.getValor())
                .unidadeMedida(createOrUpdate.getUnidadeMedida())
                .categoria(categoria)
                .build();

        return this.produtoRepository.save(produtoAtualizado);

    }

    public List<Produto> buscarTodos(Predicate filter) {
        return this.produtoRepository.findAll(filter);
    }

    public Produto buscarUm(Long id) {
        BooleanExpression filter = QProduto.produto.id.eq(id)
                .and(QProduto.produto.status.eq(Produto.Status.ATIVO));
        return this.produtoRepository.findOne(filter)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));
    }

    public void deletar(Long id) {
        Produto produto = this.buscarUm(id);
        produto.setStatus(Produto.Status.INATIVO);
        this.produtoRepository.save(produto);
    }
}