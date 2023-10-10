package io.github.jeffsilva11.com.br.projeto_pessoa_salario.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

//import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class PessoaSalario {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private BigDecimal salario;
    
//    @OneToOne
//    @JoinColumn(name = "id")
//    private Pessoa pessoa;
}