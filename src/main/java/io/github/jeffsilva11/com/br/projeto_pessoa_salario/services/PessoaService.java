package io.github.jeffsilva11.com.br.projeto_pessoa_salario.services;

import java.util.List;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.request.PessoaRequestDTO;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.response.PessoaResponseDTO;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Pessoa;

public interface PessoaService {

    void cadastrarPessoa(PessoaRequestDTO pessoaRequestDTO);

    void calcularSalario();

    PessoaResponseDTO buscarIdPessoa(Long id);

    void atualizarPessoa(Long idPessoa, PessoaRequestDTO pessoaRequestDTO);

    void excluirPessoa(Long id);

    List<Pessoa> listarPessoas();
}