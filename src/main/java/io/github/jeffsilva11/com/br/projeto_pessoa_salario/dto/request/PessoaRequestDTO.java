package io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PessoaRequestDTO {


    @NotEmpty(message = "Campo NOME é obrigatório")
    private String nome;

    @NotEmpty(message = "Campo CIDADE é obrigatório")
    private String cidade;

    @NotEmpty(message = "Campo E-MAIL é obrigatório")
    @Email(message = "Digite um endereço de email válido.")
    private String email;

    @NotEmpty(message = "Campo CEP é obrigatório")
    private String cep;

    @NotEmpty(message = "Campo ENDEREÇOO é obrigatório")
    private String endereco;

    @NotEmpty(message = "Campo PAÍS é obrigatório")
    private String pais;

    @NotEmpty(message = "Campo NOME DE USUÁRIO é obrigatório")
    private String nomeUsuario;

    @NotEmpty(message = "Campo TELEFONE é obrigatório")
    private String telefone;

    @Past(message = "Data inválida")
    @NotNull(message = "Campo DATA DE NASCIMENTO é obrigatório")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate dataNascimento;

    @NotNull(message = "Campo CARGO é obrigatório")
    private Long idCargo;
}