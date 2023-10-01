package io.github.jeffsilva11.com.br.projeto_pessoa_salario.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
//import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
//@EqualsAndHashCode(exclude = {"nomeCargo", "salario"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class Cargo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	//public Long id;
   
    private String nomeCargo;

    private BigDecimal salario;

    @OneToMany(mappedBy = "cargo")
    private List<Pessoa> pessoas = new ArrayList<>();

}