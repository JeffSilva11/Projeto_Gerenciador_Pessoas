package io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception;

public class BadRequestException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public BadRequestException(String message) {
        super(message);
    }
}