package io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception;

public class EntityNotFoundException extends RuntimeException{
	
    private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String message) {
        super(message);
    }
	
	public  EntityNotFoundException(Object id) {
    	super("Resource not found. Id " + id);
	}
}
