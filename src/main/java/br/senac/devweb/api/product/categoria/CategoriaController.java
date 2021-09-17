package br.senac.devweb.api.product.categoria;

import com.querydsl.core.types.dsl.BooleanExpression;
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

    @PostMapping
    @RequestMapping("/")
    public ResponseEntity<CategoriaRepresentation.Detail> createCategotia(
           @Valid @RequestBody CategoriaRepresentation.CreateCategoria createCategoria) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoriaRepresentation.Detail.from(this.categoriaService.salvar(createCategoria)));
    }

    @GetMapping
    @RequestMapping("/todos")
    public ResponseEntity<List<CategoriaRepresentation.Lista>> getAll() {

        BooleanExpression filter = QCategoria.categoria.status.eq(Categoria.Status.ATIVO);
        return ResponseEntity.ok(CategoriaRepresentation.Lista.from(this.categoriaService.getAllCategoria(filter)));
    }

    @DeleteMapping
    @RequestMapping("/{id}")
    public ResponseEntity deleteCategoria(@PathVariable("id") Long id) {
        this.categoriaService.deleteCategoria(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
