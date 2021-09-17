package br.senac.devweb.api.product.categoria;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "CATEGORIA")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo descrição não pode ser nulo")
    @Column(name = "DESCRICAO")
    @Size(max = 30, min = 1, message = "A descrição deve conter de 1 a 30 caracteres")
    private String descricao;

    @NotNull(message = "O campo status não pode ser nulo")
    @Column(name = "STATUS")
    private Status status;

    public enum Status {
        ATIVO,
        INATIVO
    }

}
