package io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
        super(message);
	}
        
    public ResourceNotFoundException(Object id) {
    	super("Resource not found. Id " + id);
    }
}
