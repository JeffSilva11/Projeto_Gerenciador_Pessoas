package io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception;

public class HttpRequestMethodNotSupportedException extends RuntimeException{
	
    private static final long serialVersionUID = 1L;

	public HttpRequestMethodNotSupportedException(String message) {
        super(message);
    }
	
//	public  HttpRequestMethodNotSupportedException(Object id) {
//    	super("Resource not found. Id " + id);
//    }

}
