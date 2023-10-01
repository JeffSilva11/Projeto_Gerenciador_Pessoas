package io.github.jeffsilva11.com.br.projeto_pessoa_salario.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_id_seq")
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;
    //public Long id;

    private String nome;

    private String cidade;

    private String email;

    private String cep;

    private String endereco;

    private String pais;

    private String nomeUsuario;

    private String telefone;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate dataNascimento;
    //private Date dataNascimento;

    @ManyToOne (cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;
    //private Cargo cargoId;
    //private Long cargo;

    private BigDecimal salario;

}