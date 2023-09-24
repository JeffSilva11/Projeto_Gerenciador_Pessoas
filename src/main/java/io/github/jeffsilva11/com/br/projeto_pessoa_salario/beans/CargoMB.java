package io.github.jeffsilva11.com.br.projeto_pessoa_salario.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

//import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.client.RestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception.ResourceNotFoundException;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.CargoRepository;
import lombok.Getter;
import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;

@Named(value = "cargoMB")
@ViewScoped
//@Slf4j
public class CargoMB implements Serializable {

    private static final long serialVersionUID = 1L;
        
    @Getter
    @Setter
	private Cargo cargo = new Cargo(); // Inicialize o objeto cargo aqui
     
    @Autowired
    private CargoRepository cargoRepository; 

    //MÉTODOS CRUD
    
    @PersistenceContext
    private EntityManager entityManager;
    
	@Autowired
    private JdbcTemplate jdbcTemplate;
		    
    public void cadastrarCargo () {
	    	if (cargo.getId() == null) {  
	    	String sql = "SELECT nextval('cargo_id_seq')";  // Consulta SQL para obter o próximo valor da sequência
	        Long proximoId = jdbcTemplate.queryForObject(sql, Long.class); // Executar a consulta e obter o próximo valor da sequência
	        cargo.setId(proximoId); // Definir o ID gerado no objeto Cargo
	    	}
		       //cargo.setNomeCargo(cargo.getNomeCargo());
		       // cargo.setSalario(cargo.getSalario());
		        cargoRepository.save(cargo); 
		        limpar();
		        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		        try {
		        	  externalContext.redirect("cargo_cadastrar.xhtml");
		        } catch (IOException e) {   // Lida com exceções de redirecionamento
		        }
		        //return; // Ou retorne uma string de navegação válida se necessário
		    }
		    //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Operação realizada com sucesso.")); 
	        	//} catch (Exception e) {
       // 		// Em caso de erro, adicione uma mensagem de erro ao contexto JSF
        //	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Ocorreu um erro ao cadastrar o cargo. Detalhes: " + e.getMessage()));
       // 		limpar();
		//}	
    //}

    public void editarCargo () {
            if (cargo != null) {
                cargoRepository.save(cargo);
                limpar();            
		        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		        try {
		        	externalContext.redirect("cargo_listar.xhtml");
		        } catch (IOException e) {   // Lida com exceções de redirecionamento
		        }
            }
    }
        	
    public void cancelarCargo () {
            if (cargo != null) {
                limpar(); 
            
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		        try {
	                externalContext.redirect("cargo_listar.xhtml");
		            } catch (IOException e) {   // Lida com exceções de redirecionamento
		            	e.printStackTrace();
		            }
		    }
     }
     
    private void limpar() {
      	cargo = new Cargo();
    }
       
    public List<Cargo> listarCargos() {
         return entityManager.createQuery("SELECT c FROM Cargo c order by id", Cargo.class).getResultList();
    }
        
    public List<Cargo> listarCargosPessoa() {
        return entityManager.createQuery("SELECT c FROM Cargo c order by nome_cargo", Cargo.class).getResultList();
   }
    
    public Cargo findById(Long cargoId) {
        // Use o método findById do seu repositório Spring Data JPA para buscar o cargo por ID
        return cargoRepository.findById(cargoId)
            .orElseThrow(() -> new ResourceNotFoundException("Cargo not found with id " + cargoId));
    }
       
    public String visualizarCargo(Long cargoId) {
        cargo = findById(cargoId); // Suponha que você tenha um método findById no seu serviço
        return "cargo_visualizar.xhtml"; // Redirecionar para a página de visualização de cargo
    }
    
    public String visualizarCargoEditar(Long cargoId) {
        cargo = findById(cargoId); // Suponha que você tenha um método findById no seu serviço
        return "cargo_editar.xhtml"; // Redirecionar para a página de visualização de cargo
    }
    
    public String apagarCargo(Long cargoId) {
    	   	cargo = findById(cargoId); // Suponha que você tenha um método findById no seu serviço
    	    cargoRepository.delete(cargo);
    		limpar();
	        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        try {
	            externalContext.redirect("cargo_listar.xhtml");
	        } catch (IOException e) {   // Lida com exceções de redirecionamento
	        }
	        return null; // Ou retorne uma string de navegação válida se necessário
	    }
    
    //<<VER TAMANHO DA LISTA>>
    public int contarCargos() {
        List<Cargo> cargos = listarCargos(); // Chama o método para obter a lista
        return cargos.size(); // Retorna o tamanho da lista
    }
    
    @Getter
    @Setter
    private int itensPorPagina = 10; // Inicialmente, definimos 10 como padrão

    public void atualizarItensPorPagina() {
        // Atualize a lista de cargos com base no número selecionado
        // Você pode ajustar a lógica aqui para buscar os cargos de acordo com a seleção
        listarCargos();
    }
    
    public Cargo buscarCargo(Long id) {
        return cargoRepository.findById(id).orElse(null);
    }
}
