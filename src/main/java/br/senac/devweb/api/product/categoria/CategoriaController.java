package br.senac.devweb.api.product.categoria;

import com.querydsl.core.types.dsl.BooleanExpression;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categoria")
@AllArgsConstructor
public class CategoriaController {

    private CategoriaService categoriaService;

    @PostMapping("/")
    public ResponseEntity<CategoriaRepresentation.Detail> createCategoria(
           @Valid @RequestBody CategoriaRepresentation.CreateOrUpdateCategoria createOrUpdateCategoria) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoriaRepresentation.Detail.from(this.categoriaService.salvar(createOrUpdateCategoria)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaRepresentation.Detail> atualizaCategoria(@PathVariable("id") Long id,
          @Valid @RequestBody CategoriaRepresentation.CreateOrUpdateCategoria createOrUpdateCategoria) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CategoriaRepresentation.Detail.from(this.categoriaService.update(id, createOrUpdateCategoria)));
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoriaRepresentation.Lista>> getAll() {

        BooleanExpression filter = QCategoria.categoria.status.eq(Categoria.Status.ATIVO);

        return ResponseEntity.ok(CategoriaRepresentation.Lista
                .from(this.categoriaService.getAllCategoria(filter)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaRepresentation.Detail> getOneCategoria(@PathVariable("id") Long id) {
        return ResponseEntity.ok(CategoriaRepresentation.Detail.from(this.categoriaService.getCategoria(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategoria(@PathVariable("id") Long id) {
        this.categoriaService.deleteCategoria(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
