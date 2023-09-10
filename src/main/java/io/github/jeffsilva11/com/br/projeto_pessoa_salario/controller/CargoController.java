package io.github.jeffsilva11.com.br.projeto_pessoa_salario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.request.CargoRequestDTO;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.services.CargoService;

@RestController
@RequestMapping("/cargo")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarCargo(@RequestBody CargoRequestDTO cargoRequestDTO) {
        cargoService.cadastrarCargo(cargoRequestDTO);
    }

    @GetMapping("/listar")
    public List<Cargo> listarCargo() {
        return cargoService.listarCargo();
    }

    @PutMapping("editar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editarCargo(@PathVariable Long id, @RequestBody CargoRequestDTO cargoRequestDTO) {
        cargoService.editarCargo(id, cargoRequestDTO);
    }
}