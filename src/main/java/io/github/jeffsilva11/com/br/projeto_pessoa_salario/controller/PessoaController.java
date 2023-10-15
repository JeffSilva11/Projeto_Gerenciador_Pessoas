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

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.PessoaDTO;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Pessoa;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.services.PessoaService;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

	@Autowired 
	private PessoaService pessoaService;
	
//	@GetMapping
//	public ResponseEntity<List<Cargo>> findAll() {
//		List<Cargo> list = service.findAll();
//		return ResponseEntity.ok().body(list);
//	}
	
	@GetMapping
    public List<PessoaDTO> listarPessoas() {
        return pessoaService.listarPessoasDTO();
    }

	@GetMapping(value = "/{id}")
	public ResponseEntity<Pessoa> findById(@PathVariable Long id) {
		Pessoa obj = pessoaService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> insert(@RequestBody Pessoa obj) {
		obj = pessoaService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		pessoaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Pessoa> update(@PathVariable Long id, @RequestBody Pessoa obj) {
		obj = pessoaService.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}