package br.senac.devweb.api.product.categoria;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public interface CategoriaRepresentation {

    @Data
    @Getter
    @Setter
    class CreateCategoria {

        @NotNull(message = "O campo descrição não pode ser nulo")
        @Size(max = 30, min = 1, message = "A descrição deve conter de 1 a 30 caracteres")
        private String descricao;
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detail {
        private Long id;
        private String descricao;
        private Categoria.Status status;

        public static Detail from(Categoria categoria) {
            return Detail.builder()
                    .id(categoria.getId())
                    .descricao(categoria.getDescricao())
                    .status(categoria.getStatus())
                    .build();
        }
    }
}
