package io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByLoginAndSenha(String login, String senha);
  
}