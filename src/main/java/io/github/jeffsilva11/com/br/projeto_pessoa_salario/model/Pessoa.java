package io.github.jeffsilva11.com.br.projeto_pessoa_salario.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cidade;

    private String email;

    private String cep;

    private String endereco;

    private String pais;

    private String nomeUsuario;

    private String telefone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate dataNascimento;

    private BigDecimal salario;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

	
}