package io.github.jeffsilva11.com.br.projeto_pessoa_salario.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.PessoaDTO;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception.DatabaseException;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception.HttpRequestMethodNotSupportedException;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception.ResourceNotFoundException;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Pessoa;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.CargoRepository;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}

	public List<PessoaDTO> listarPessoasDTO() {
	    List<Pessoa> pessoas = pessoaRepository.findAll();
	    return pessoas.stream()
	            .map(pessoa -> new PessoaDTO(pessoa.getId(), pessoa.getNome(), pessoa.getCargo().getNomeCargo()))
	            .sorted(Comparator.comparing(PessoaDTO::getId)) // Ordenar pelo ID
	            .collect(Collectors.toList());
	}
	
	public Pessoa findById(Long id) {
		Optional<Pessoa> obj = pessoaRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
		
	public Pessoa insert(Pessoa obj) {
		try {	
			return pessoaRepository.save(obj);
		} catch (HttpRequestMethodNotSupportedException e) {
			throw new HttpRequestMethodNotSupportedException(e.getMessage());
		}
	}
	
	public void delete(Long id) {
		try {
				pessoaRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
				throw new ResourceNotFoundException(id); 
		} catch (DataIntegrityViolationException e) {
				throw new DatabaseException(e.getMessage());
		}
	}
	
	public Pessoa update(Long id, Pessoa obj) {
		try {
			Pessoa entity = pessoaRepository.getReferenceById(id);
			updateData(entity, obj);
			return pessoaRepository.save(entity);
		} catch (RuntimeException e) {
			throw new ResourceNotFoundException(id); //NAO FUNCIONOU NO POSTMAN
			//e.printStackTrace();
			//throw new EntityNotFoundException(); //NAO FUNCIONOU NO POSTMAN
		}
	}

	@Autowired
	private CargoRepository cargoRepository;
	
	private void updateData(Pessoa entity, Pessoa obj) {
		entity.setNome(obj.getNome());
		entity.setCidade(obj.getCidade());
		entity.setCep(obj.getCep());
		entity.setEmail(obj.getEmail());
		entity.setEndereco(obj.getEndereco());
		entity.setPais(obj.getPais());
		entity.setNomeUsuario(obj.getNomeUsuario());
		entity.setTelefone(obj.getTelefone());
		entity.setDataNascimento(obj.getDataNascimento());
		//entity.setCargo(obj.getCargo());
		//entity.setCargo(obj.getCargo().getId());
	    // Configurar o cargo com base no ID
	    if (obj.getCargo() != null && obj.getCargo().getId() != null) {
	        // Você deve carregar o objeto Cargo com base no ID a partir do repositório
	        Cargo cargo = cargoRepository.findById(obj.getCargo().getId()).orElse(null);
	        if (cargo != null) {
	            entity.setCargo(cargo);
	        } else {
	            // Trate o caso em que o cargo não foi encontrado, se necessário
	        }
	    } else {
	        entity.setCargo(null); // Ou defina como nulo se não houver um ID de cargo
	    }
	}
}