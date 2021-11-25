package br.senac.devweb.api.product.produto;

import br.senac.devweb.api.product.categoria.Categoria;
import br.senac.devweb.api.product.categoria.CategoriaRepresentation;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
        private Double valor;

        @NotNull(message = "O campo unidade de medida não pode ser nulo")
        private Produto.UnidadeMedida unidadeMedida;

        @NotNull(message = "O campo quantidade não pode ser nulo")
        private Double qtde;

        @NotNull(message = "O campo fabricante não pode ser nulo")
        @Size(min = 1, max = 255, message = "O campo fabricante deve conter entre 1 e 255 caracteres")
        private String fabricante;

        private String fornecedor;

        @NotNull(message = "A categoria é obrigatória")
        private Long categoria;
    }



    @Data
    @Getter
    @Setter
    @Builder
    class Detalhes {

        private Long id;
        private String nome;
        private String descricao;
        private String complemento;
        private Double valor;
        private Produto.UnidadeMedida unidadeMedida;
        private Double qtde;
        private String fabricante;
        private String fornecedor;
        private CategoriaRepresentation.Detail categoria;

        public static Detalhes from(Produto produto) {
            return Detalhes.builder()
                    .id(produto.getId())
                    .nome(produto.getNome())
                    .descricao(produto.getDescricao())
                    .complemento(produto.getComplemento())
                    .valor(produto.getValor())
                    .unidadeMedida(produto.getUnidadeMedida())
                    .qtde(produto.getQtde())
                    .fabricante(produto.getFabricante())
                    .fornecedor(produto.getFornecedor())
                    .categoria(CategoriaRepresentation.Detail.from(produto.getCategoria()))
                    .build();
        }
    }
}
