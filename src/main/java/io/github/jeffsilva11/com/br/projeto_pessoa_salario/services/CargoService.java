package io.github.jeffsilva11.com.br.projeto_pessoa_salario.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.dto.CargoDTO;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception.DatabaseException;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception.HttpRequestMethodNotSupportedException;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception.ResourceNotFoundException;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.CargoRepository;

@Service
public class CargoService {

	@Autowired
	private CargoRepository repository;

	public List<Cargo> findAll() {
		return repository.findAll();
	}

	public List<CargoDTO> listarCargosDTO() {
	    List<Cargo> cargos = repository.findAll();
	    return cargos.stream()
	            .map(cargo -> new CargoDTO(cargo.getId(), cargo.getNomeCargo(), cargo.getSalario()))
	            .sorted(Comparator.comparing(CargoDTO::getId)) // Ordenar pelo ID
	            .collect(Collectors.toList());
	}
	
	public Cargo findById(Long id) {
		Optional<Cargo> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
		
	public Cargo insert(Cargo obj) {
		try {	
			return repository.save(obj);
		} catch (HttpRequestMethodNotSupportedException e) {
			throw new HttpRequestMethodNotSupportedException(e.getMessage());
		}
	}
	
	public void delete(Long id) {
		try {
				repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
				throw new ResourceNotFoundException(id); 
		} catch (DataIntegrityViolationException e) {
				throw new DatabaseException(e.getMessage());
		}
	}
	
	public Cargo update(Long id, Cargo obj) {
		try {
			Cargo entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (RuntimeException e) {
			throw new ResourceNotFoundException(id); //NAO FUNCIONOU NO POSTMAN
			//e.printStackTrace();
			//throw new EntityNotFoundException(); //NAO FUNCIONOU NO POSTMAN
		}
	}

	private void updateData(Cargo entity, Cargo obj) {
		entity.setNomeCargo(obj.getNomeCargo());
		entity.setSalario(obj.getSalario());
	}
}