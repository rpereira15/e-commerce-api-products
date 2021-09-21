package br.senac.devweb.api.product.categoria;

import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoriaRepository extends CrudRepository<Categoria, Long>,
        QuerydslPredicateExecutor<Categoria> {

    List<Categoria> findAll(Predicate filter);
}
