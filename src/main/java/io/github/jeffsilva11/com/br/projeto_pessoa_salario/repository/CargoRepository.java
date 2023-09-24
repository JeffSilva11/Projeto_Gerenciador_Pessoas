package io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Pessoa;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

	//List<Cargo> listarCargos();
	List<Cargo> findAll();

}