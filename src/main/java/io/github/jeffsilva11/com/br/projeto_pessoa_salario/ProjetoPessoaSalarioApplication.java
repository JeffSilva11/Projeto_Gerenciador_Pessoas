package io.github.jeffsilva11.com.br.projeto_pessoa_salario;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjetoPessoaSalarioApplication {

	@Bean
	public ModelMapper modelMapper(){
			return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetoPessoaSalarioApplication.class, args);
		System.out.println("PROGRAMA INICIADO");
		
	}

}
