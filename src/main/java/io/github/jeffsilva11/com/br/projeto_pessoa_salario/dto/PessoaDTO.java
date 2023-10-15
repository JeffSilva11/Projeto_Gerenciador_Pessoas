package io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto;

import lombok.Data;

@Data
public class PessoaDTO {
    
	private Long id;
    private String nome;
    private String cargo;

    public PessoaDTO(Long id, String nome, String string) {
        this.id = id;
        this.nome = nome;
        this.cargo = string;
    }
    
}