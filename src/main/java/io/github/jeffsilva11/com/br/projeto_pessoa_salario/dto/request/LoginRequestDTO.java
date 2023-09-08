package io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    private String email;

    private String senha;
}