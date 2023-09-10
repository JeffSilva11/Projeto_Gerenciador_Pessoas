package io.github.jeffsilva11.com.br.projeto_pessoa_salario.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.request.CargoRequestDTO;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception.EntidadeNaoEncontrada;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Pessoa;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.CargoRepository;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.PessoaRepository;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.services.CargoService;

@Service
public class CargoServiceImpl implements CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public void cadastrarCargo(CargoRequestDTO cargoRequestDTO) {
        var cargo = new Cargo();
        cargo.setNomeCargo(cargoRequestDTO.getNomeCargo());
        cargo.setSalario(cargoRequestDTO.getSalario());
        cargoRepository.save(cargo);
    }

    public List<Cargo> listarCargo() {
        return cargoRepository.findAll();
    }

    public void editarCargo(Long id, CargoRequestDTO cargoRequestDTO) {
        cargoRepository.findById(id).map(cargo -> {
            cargo.setNomeCargo(cargoRequestDTO.getNomeCargo());
            cargo.setSalario(cargoRequestDTO.getSalario());
            List<Pessoa> pessoasCargo = cargo.getPessoas();
            for (Pessoa pessoa : pessoasCargo) {
                pessoa.setSalario(cargoRequestDTO.getSalario());
                pessoaRepository.save(pessoa);
            }

            pessoaRepository.saveAll(pessoasCargo);
            return cargoRepository.save(cargo);
        }).orElseThrow(() -> new EntidadeNaoEncontrada("Cargo não encontrado!"));
    }
}