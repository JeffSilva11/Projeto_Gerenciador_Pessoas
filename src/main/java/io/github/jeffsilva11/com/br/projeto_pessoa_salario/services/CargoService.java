package io.github.jeffsilva11.com.br.projeto_pessoa_salario.services;

import java.util.List;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.request.CargoRequestDTO;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;

public interface CargoService {

    void cadastrarCargo(CargoRequestDTO cargoRequestDTO);

    void editarCargo(Long id, CargoRequestDTO cargoRequestDTO);

    List<Cargo> listarCargo();
}