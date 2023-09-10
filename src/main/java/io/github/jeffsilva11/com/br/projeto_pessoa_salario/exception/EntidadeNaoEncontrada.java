package io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception;

public class EntidadeNaoEncontrada extends RuntimeException{
	
    private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontrada(String message) {
        super(message);
    }
}