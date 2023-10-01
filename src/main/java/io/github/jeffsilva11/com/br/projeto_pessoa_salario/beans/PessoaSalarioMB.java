package io.github.jeffsilva11.com.br.projeto_pessoa_salario.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception.EntidadeNaoEncontrada;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Pessoa;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.PessoaSalario;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.CargoRepository;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.PessoaRepository;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.PessoaSalarioRepository;
import lombok.Getter;
import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;

@Named(value = "pessoaSalarioMB")
@ViewScoped
//@Slf4j
public class PessoaSalarioMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private List<Pessoa> pessoas = new ArrayList<>();

    @Getter
    @Setter
    private Cargo cargo = new Cargo(); // Inicialize o objeto cargo aqui

    //@Autowired
    //private RestTemplate restTemplate;

    @Autowired
    private CargoRepository cargoRepository;
    
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaSalarioRepository pessoaSalarioRepository;
    
    public List<Pessoa> listarTodos() {
        return pessoas = pessoaRepository.findAll();
    }       
 
    public void calcularSalario() {
        List<Pessoa> listaPessoa = pessoaRepository.findAll();
        for (Pessoa pessoa : listaPessoa) {
            Long idCargo = pessoa.getCargo().getId();
            if (pessoaSalarioRepository.existsById(pessoa.getId())) {
                continue;
            }
            var cargo = cargoRepository.findById(idCargo)
                    .orElseThrow(() -> new EntidadeNaoEncontrada("Cargo não encontrado"));
            var pessoaSalario = new PessoaSalario();
            pessoaSalario.setId(pessoa.getId());
            pessoaSalario.setNome(pessoa.getNome());
            pessoaSalario.setSalario(cargo.getSalario());
            pessoaSalarioRepository.save(pessoaSalario);
            pessoa.setSalario(cargo.getSalario()); //COMENTAR
            pessoaRepository.save(pessoa); //COMENTAR
        }
    }
}