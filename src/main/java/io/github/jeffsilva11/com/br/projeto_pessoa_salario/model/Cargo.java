package io.github.jeffsilva11.com.br.projeto_pessoa_salario.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCargo;

    private BigDecimal salario;

    @OneToMany(mappedBy = "cargo")
    private List<Pessoa> pessoas = new ArrayList<>();
}