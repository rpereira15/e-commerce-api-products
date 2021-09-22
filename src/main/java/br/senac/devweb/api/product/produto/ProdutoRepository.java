package br.senac.devweb.api.product.produto;

import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProdutoRepository extends CrudRepository<Produto, Long>,
        QuerydslPredicateExecutor<Produto> {

    List<Produto> findAll(Predicate filter);
}
