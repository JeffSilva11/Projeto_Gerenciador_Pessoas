package io.github.jeffsilva11.com.br.projeto_pessoa_salario.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.request.PessoaRequestDTO;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Pessoa;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.CargoRepository;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.PessoaRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Named(value = "pessoaMB")
@ViewScoped
@Slf4j
public class PessoaMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private List<Pessoa> pessoas = new ArrayList<>();

    @Getter
    @Setter
    private Pessoa pessoa;

    @Getter
    @Setter
    private Cargo cargo;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CargoRepository cargoRepository;


    @PostConstruct
    public void init() {
        pessoa = new Pessoa();
        listarCargos();
        listarTodos();
    }

    @Getter
    @Setter
    private List<Cargo> listaCargos = new ArrayList<>();

    public void listarCargos() {
        listaCargos = cargoRepository.findAll();
    }

    public List<Pessoa> listarTodos() {
        return pessoas = pessoaRepository.findAll();
    }


    @Getter
    @Setter
    private PessoaRequestDTO pessoaRequestDTO;

    public PessoaMB() {
        pessoaRequestDTO = new PessoaRequestDTO();
    }

    public void calcularSalario() {
        restTemplate.put("http://localhost:8081/pessoas/calcular", null);
        listarTodos();
        log.info("Executado com sucesso");
    }

    public void recalcularSalario() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put("http://localhost:8081/pessoas/recalcular", null);
        log.info("Executado com sucesso");
    }

    public Integer getTamanhoDaLista() {
        return pessoas.size();
    }
}