package io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDTO {

    private String email;
    private String token;
}