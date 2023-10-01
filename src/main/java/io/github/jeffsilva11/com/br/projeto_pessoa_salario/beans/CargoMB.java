package io.github.jeffsilva11.com.br.projeto_pessoa_salario.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
//import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.client.RestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception.ResourceNotFoundException;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.CargoRepository;
//import lombok.EqualsAndHashCode;
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
		    
    private void limpar() {
      	cargo = new Cargo();
    }
	
    @SuppressWarnings("unused")
	private void limparNull() {
    	cargo = null;
    }
	
	private void limparJavaScript() { //LIMPAR COM JAVASCRIPT
		PrimeFaces.current().executeScript("limparFormulario();");
		cargo.setNomeCargo("");
		cargo.setSalario(null);
	}
		
	public void cadastrarCargo() {
	    if (cargo.getId() == null) {  
	        String sql = "SELECT nextval('cargo_id_seq')";
	        Long proximoId = jdbcTemplate.queryForObject(sql, Long.class);
	        cargo.setId(proximoId);
	    }
	    try {
	    	cargoRepository.save(cargo);
	        limparJavaScript();
	        
	        FacesMessage msg = new FacesMessage("Cargo Cadastrado com Sucesso!");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	       	        
	    } catch (Exception e) {
	        // Tratar exceções, se houver algum erro ao salvar o usuário.
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Cadastrar Cargo", null);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }
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
    
    public String visualizarCargoApagar(Long cargoId) {
		cargo = findById(cargoId); // Suponha que você tenha um método findById no seu serviço
		return "cargo_apagar.xhtml"; // Redirecionar para a página de visualização de usuario
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
       
    public void editarCargo () {
        if (cargo != null) {
            cargoRepository.save(cargo);
            limpar();    
            FacesContext facesContext = FacesContext.getCurrentInstance();
	        facesContext.getExternalContext().getFlash().setKeepMessages(true); // Mantém as mensagens entre as páginas - Abordagem chamada "FlashScope".
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cargo Alterado com Sucesso", null));
	        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        try {
	        	externalContext.redirect("cargo_listar.xhtml");
	        } catch (IOException e) {   // Lida com exceções de redirecionamento
	        }
        }
}
    
  //public String apagarCargo(Long cargoId) {
    public void apagarCargo() {
        	   	//cargo = findById(cargoId); // Suponha que você tenha um método findById no seu serviço
        	if (cargo != null) {
        		try {
        			cargoRepository.delete(cargo);
        			limpar();
        			// Se a operação for bem-sucedida
        			FacesContext facesContext = FacesContext.getCurrentInstance();
        	        facesContext.getExternalContext().getFlash().setKeepMessages(true); // Mantém as mensagens entre as páginas - Abordagem chamada "FlashScope".
        		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cargo Excluído com Sucesso", null));
        		    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        		    try {
    	                externalContext.redirect("cargo_listar.xhtml");
    		    	} catch (IOException e) {   // Lida com exceções de redirecionamento
    		    	}
        		} catch (Exception e) {
        		    // Se ocorrer qualquer outra exceção
        		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir Cargo: Há uma ou mais Pessoas associadas a este cargo!", e.getMessage()));
        	    }
 	    }
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
	
	public Cargo getCargo(Long valueOf) {
        if (valueOf == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (Cargo cargo : listarCargos()){
            if (valueOf.equals(cargo.getId())){
                return cargo;
            }
        }
        return null;
    }

}