package io.github.jeffsilva11.com.br.projeto_pessoa_salario.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PessoaSalario {

    @Id
    private Long id;

    private String nome;

    private BigDecimal salario;
}