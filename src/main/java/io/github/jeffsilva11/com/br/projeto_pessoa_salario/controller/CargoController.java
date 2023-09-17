package io.github.jeffsilva11.com.br.projeto_pessoa_salario.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.CargoRepository;

@RestController
@RequestMapping("/cargo")
public class CargoController {

	/*
	@Autowired 
	private CargoService cargoService;
    */
	private CargoRepository repository;

	CargoController(CargoRepository cargoRepository) {
	       this.repository = cargoRepository;
	   }
	   // métodos do CRUD aqui
	
	@PostMapping ("/create")
	public Cargo create(@RequestBody Cargo cargo){
	   return repository.save(cargo);
	}
/*
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
    */
}