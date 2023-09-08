package io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoRequestDTO {

    @NotEmpty(message = "Campo NOME DO CARGO é obrigatório")
    private String nomeCargo;

    @NotNull(message = "Campo SALARIO é obrigatório")
    private BigDecimal salario;
}