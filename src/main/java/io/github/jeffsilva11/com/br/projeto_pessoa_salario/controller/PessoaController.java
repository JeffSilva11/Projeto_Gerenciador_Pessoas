package io.github.jeffsilva11.com.br.projeto_pessoa_salario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.request.PessoaRequestDTO;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.response.PessoaResponseDTO;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Pessoa;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.services.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarPessoa(@RequestBody PessoaRequestDTO pessoaRequestDTO) {
        pessoaService.cadastrarPessoa(pessoaRequestDTO);
    }

    @GetMapping
    public List<Pessoa> listar() {
        return pessoaService.listarPessoas();
    }

    @GetMapping("/{id}")
    public PessoaResponseDTO buscarIdPessoa(@PathVariable Long id) {
        return pessoaService.buscarIdPessoa(id);
    }

    @PutMapping("/calcular")
    public void calcularSalario() {
        pessoaService.calcularSalario();
    }

    @PutMapping("/atualizar/{id}")
    public void atualizarPessoa(@PathVariable Long id, @RequestBody PessoaRequestDTO pessoaRequestDTO){
        pessoaService.atualizarPessoa(id, pessoaRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void excluirPessoa(@PathVariable Long id) {
        pessoaService.excluirPessoa(id);
    }
}