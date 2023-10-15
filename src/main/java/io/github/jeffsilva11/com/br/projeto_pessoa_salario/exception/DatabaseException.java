package io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception;

public class DatabaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatabaseException(String msg) {
		super(msg);
	}
}