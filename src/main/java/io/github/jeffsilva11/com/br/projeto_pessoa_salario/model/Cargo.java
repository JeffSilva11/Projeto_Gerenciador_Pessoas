package io.github.jeffsilva11.com.br.projeto_pessoa_salario.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cargo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//public Long id;
   
    private String nomeCargo;

    private BigDecimal salario;

    @OneToMany(mappedBy = "cargo")
    private List<Pessoa> pessoas = new ArrayList<>();
    
}