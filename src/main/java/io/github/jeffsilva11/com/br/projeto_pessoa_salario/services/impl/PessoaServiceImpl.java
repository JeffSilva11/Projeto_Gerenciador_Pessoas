package io.github.jeffsilva11.com.br.projeto_pessoa_salario.services.impl;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.request.PessoaRequestDTO;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.response.PessoaResponseDTO;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception.BadRequestException;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception.EntidadeNaoEncontrada;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Pessoa;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.PessoaSalario;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.CargoRepository;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.PessoaRepository;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.PessoaSalarioRepository;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.services.PessoaService;

@Service
public class PessoaServiceImpl implements PessoaService {

    public static final int IDADE_MINIMA = 18;
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaSalarioRepository pessoaSalarioRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void cadastrarPessoa(PessoaRequestDTO pessoaRequestDTO) {
        var pessoa = fromRequest(pessoaRequestDTO);
        verificarDataCliente(pessoa);
        var cargo = cargoRepository.findById(pessoaRequestDTO.getIdCargo())
                .orElseThrow(() -> new EntidadeNaoEncontrada("Cargo não encontrado"));
        pessoa.setCargo(cargo);
        cargo.getPessoas().add(pessoa);
        pessoaRepository.save(pessoa);
    }

    public PessoaResponseDTO buscarIdPessoa(Long id) {
        return pessoaRepository.findById(id).map(pessoa -> modelMapper.map(pessoa, PessoaResponseDTO.class))
                .orElseThrow(() -> new EntidadeNaoEncontrada("Pessoa não encontrada"));
    }

    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
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
            pessoa.setSalario(cargo.getSalario());
            pessoaRepository.save(pessoa);
        }
    }

    public void excluirPessoa(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new EntidadeNaoEncontrada("Pessoa não encontrada na base de dados");
        }
        pessoaRepository.deleteById(id);
    }

    public void atualizarPessoa(Long idPessoa, PessoaRequestDTO pessoaRequestDTO) {
        pessoaRepository.findById(idPessoa).map(pessoa -> {

            var cargo = cargoRepository.findById(pessoaRequestDTO.getIdCargo())
                    .orElseThrow(() -> new EntidadeNaoEncontrada("Cargo não encontrado"));

            String nome = pessoaRequestDTO.getNome().isEmpty() ? pessoa.getNome() : pessoaRequestDTO.getNome();
            String cidade = pessoaRequestDTO.getCidade().isEmpty() ? pessoa.getCidade() : pessoaRequestDTO.getCidade();
            String email = pessoaRequestDTO.getEmail().isEmpty() ? pessoa.getEmail() : pessoaRequestDTO.getEmail();
            String cep = pessoaRequestDTO.getCep().isEmpty() ? pessoa.getCep() : pessoaRequestDTO.getCep();
            String endereco = pessoaRequestDTO.getEndereco().isEmpty() ? pessoa.getEndereco() : pessoaRequestDTO.getEndereco();
            String pais = pessoaRequestDTO.getPais().isEmpty() ? pessoa.getPais() : pessoaRequestDTO.getPais();
            String nomeUsuario = pessoaRequestDTO.getNomeUsuario().isEmpty() ? pessoa.getNomeUsuario() : pessoaRequestDTO.getNomeUsuario();
            String telefone = pessoaRequestDTO.getTelefone().isEmpty() ? pessoa.getTelefone() : pessoaRequestDTO.getTelefone();
            LocalDate dataNascimento = pessoaRequestDTO.getDataNascimento() == null ? pessoa.getDataNascimento() : pessoaRequestDTO.getDataNascimento();
            Cargo cargoAtualizacao = pessoaRequestDTO.getIdCargo() == null ? pessoa.getCargo() : cargo;

            pessoa.setNome(nome);
            pessoa.setCidade(cidade);
            pessoa.setEmail(email);
            pessoa.setCep(cep);
            pessoa.setEndereco(endereco);
            pessoa.setPais(pais);
            pessoa.setNomeUsuario(nomeUsuario);
            pessoa.setTelefone(telefone);
            pessoa.setDataNascimento(dataNascimento);
            pessoa.setCargo(cargoAtualizacao);
            pessoa.setSalario(cargoAtualizacao.getSalario());
            var pessoaSalarioAtualizacao = pessoaSalarioRepository.findById(pessoa.getId())
                    .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
            pessoaSalarioAtualizacao.setSalario(cargoAtualizacao.getSalario());
            pessoaSalarioAtualizacao.setNome(nome);
            pessoaRepository.save(pessoa);
            pessoaSalarioRepository.save(pessoaSalarioAtualizacao);
            return pessoa;
        }).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    private void verificarDataCliente(Pessoa pessoa){
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataMinima = dataAtual.minusYears(IDADE_MINIMA);
        LocalDate dataNascimento = pessoa.getDataNascimento();
        if(dataNascimento.isAfter(dataMinima)){
            throw new BadRequestException("A pessoa dever ter pelo menos 18 anos");
        }
    }

    private static Pessoa fromRequest(PessoaRequestDTO pessoaRequestDTO) {
        var pessoa = new Pessoa();
        pessoa.setNome(pessoaRequestDTO.getNome());
        pessoa.setCidade(pessoaRequestDTO.getCidade());
        pessoa.setEmail(pessoaRequestDTO.getEmail());
        pessoa.setCep(pessoaRequestDTO.getCep());
        pessoa.setEndereco(pessoaRequestDTO.getEndereco());
        pessoa.setPais(pessoaRequestDTO.getPais());
        pessoa.setNomeUsuario(pessoaRequestDTO.getNomeUsuario());
        pessoa.setTelefone(pessoaRequestDTO.getTelefone());
        pessoa.setDataNascimento(pessoaRequestDTO.getDataNascimento());
        return pessoa;
    }
}