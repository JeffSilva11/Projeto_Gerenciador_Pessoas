package io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PessoaResponseDTO {

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal salario;

    private CargoResponseDTO cargo;
}