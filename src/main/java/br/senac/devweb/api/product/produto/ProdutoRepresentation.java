package br.senac.devweb.api.product.produto;

import br.senac.devweb.api.product.categoria.Categoria;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface ProdutoRepresentation {

    @Data
    @Getter
    @Setter
    class CreateOrUpdate {

        @NotNull(message = "O campo nome não pode ser nulo")
        @Size(min = 1, max = 30, message = "O campo nome deve conter entre 1 e 30 caracteres")
        private String nome;

        @NotNull(message = "O campo descricao não pode ser nulo")
        @Size(min = 1, max = 255, message = "O campo descrição deve conter entre 1 e 255 caracteres")
        private String descricao;

        private String complemento;

        @NotNull(message = "O campo valor não pode ser nulo")
        @NotEmpty(message = "O campo valor de medida não pode ser vazio")
        private Double valor;

        @NotNull(message = "O campo unidade de medida não pode ser nulo")
        @NotEmpty(message = "O campo unidade de medida não pode ser vazio")
        private Produto.UnidadeMedida unidadeMedida;

        @NotNull(message = "O campo quantidade não pode ser nulo")
        @NotEmpty(message = "O campo quantidade não pode ser vazio")
        private Double qtde;

        @NotNull(message = "O campo fabricante não pode ser nulo")
        @Size(min = 1, max = 255, message = "O campo fabricante deve conter entre 1 e 255 caracteres")
        private String fabricante;

        private String fornecedor;

        @NotNull(message = "A categoria é obrigatória")
        private Long categoria;
    }
}
