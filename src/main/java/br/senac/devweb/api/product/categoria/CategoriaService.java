package br.senac.devweb.api.product.categoria;

import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaService {


    private CategoriaRepository categoriaRepository;

    public Categoria salvar(CategoriaRepresentation.CreateCategoria createCategoria) {

        return this.categoriaRepository.save(Categoria.builder()
                .descricao(createCategoria.getDescricao())
                .status(Categoria.Status.ATIVO)
                .build());

    }

    public List<Categoria> getAllCategoria(Predicate filter) {
      return this.categoriaRepository.findAll(filter);
    }

    public void deleteCategoria(Long id) {
        Categoria categoria = this.categoriaRepository.findById(id).get();

        categoria.setStatus(Categoria.Status.INATIVO);
        this.categoriaRepository.save(categoria);
    }
}
