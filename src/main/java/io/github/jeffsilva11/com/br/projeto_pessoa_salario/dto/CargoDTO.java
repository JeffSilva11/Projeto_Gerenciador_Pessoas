package io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CargoDTO {
    
	private Long id;
    private String nome;
    private BigDecimal salario;

    public CargoDTO(Long id, String nome, BigDecimal salario) {
        this.id = id;
        this.nome = nome;
        this.salario = salario;
    }
    
}