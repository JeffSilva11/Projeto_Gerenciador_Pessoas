package io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	List<Pessoa> findAll();
	
	Pessoa getReferenceById(Long id);

}