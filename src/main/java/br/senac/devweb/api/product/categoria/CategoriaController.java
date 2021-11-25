package br.senac.devweb.api.product.categoria;

import br.senac.devweb.api.product.util.Paginacao;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categoria")
@AllArgsConstructor
@CrossOrigin("*")
public class CategoriaController {

    private CategoriaService categoriaService;
    private CategoriaRepository categoriaRepository;

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
    public ResponseEntity<Paginacao> getAll(
            @RequestParam(name = "filtro", required = false, defaultValue = "") String filtro,
            @RequestParam(name = "paginaSelecionada", defaultValue = "0") Integer paginaSelecionada,
            @RequestParam(name = "tamanhoPagina", defaultValue = "20") Integer tamanhoPagina) {

        BooleanExpression filter = Strings.isEmpty(filtro) ? QCategoria.categoria.status.eq(Categoria.Status.ATIVO) :
                QCategoria.categoria.status.eq(Categoria.Status.ATIVO)
                        .and(QCategoria.categoria.descricao.containsIgnoreCase(filtro));

        Pageable pageRequest = PageRequest.of(paginaSelecionada, tamanhoPagina);

        Page<Categoria> categoriaList = this.categoriaRepository.findAll(filter, pageRequest);

        Paginacao paginacao = Paginacao.builder()
                .conteudo(CategoriaRepresentation.Lista
                        .from(categoriaList.getContent()))
                .paginaSelecionada(paginaSelecionada)
                .tamanhoPagina(tamanhoPagina)
                .proximaPagina(categoriaList.hasNext())
                .build();

        return ResponseEntity.ok(paginacao);
    }

    @GetMapping("/full-categoria")
    public ResponseEntity<List<CategoriaRepresentation.Lista>> getAll() {

        BooleanExpression filter = QCategoria.categoria.status.eq(Categoria.Status.ATIVO);

        List<Categoria> all = this.categoriaRepository.findAll(filter);

        return ResponseEntity.ok(CategoriaRepresentation.Lista.from(all));
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
