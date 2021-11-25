package br.senac.devweb.api.product.categoria;

import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoriaRepository extends PagingAndSortingRepository<Categoria, Long>,
        QuerydslPredicateExecutor<Categoria> {
    /**
     * Método responsável por retornar uma lista de categorias
     * com a oportunidade de informar filtros
     * @param filter
     * @return
     */
    List<Categoria> findAll(Predicate filter);


    /**
     *Método responsável por retornar uma lista de categorias
     * sem a oportunidade de informar filtros
     * @return
     */
    List<Categoria> findAll();

}
