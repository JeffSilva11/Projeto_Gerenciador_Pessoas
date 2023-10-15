package io.github.jeffsilva11.com.br.projeto_pessoa_salario.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.CargoDTO;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.services.CargoService;

@RestController
@RequestMapping("/api/cargos")
public class CargoController {


	@Autowired 
	private CargoService service;
	
//	@GetMapping
//	public ResponseEntity<List<Cargo>> findAll() {
//		List<Cargo> list = service.findAll();
//		return ResponseEntity.ok().body(list);
//	}
	
	@GetMapping
    public List<CargoDTO> listarCargos() {
        return service.listarCargosDTO();
    }

	@GetMapping(value = "/{id}")
	public ResponseEntity<Cargo> findById(@PathVariable Long id) {
		Cargo obj = service.findById(id);
		return ResponseEntity.ok().body(obj);

	}
	
	@PostMapping
	public ResponseEntity<Cargo> insert(@RequestBody Cargo obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Cargo> update(@PathVariable Long id, @RequestBody Cargo obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}