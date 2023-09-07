package io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.PessoaSalario;

public interface PessoaSalarioRepository extends JpaRepository<PessoaSalario, Long> {
}