package io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.PessoaSalario;

@Repository
public interface PessoaSalarioRepository extends JpaRepository<PessoaSalario, Long> {

}