package br.senac.devweb.api.product.categoria;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface CategoriaRepository extends CrudRepository<Categoria, Long>,
        QuerydslPredicateExecutor<Categoria> {
}
