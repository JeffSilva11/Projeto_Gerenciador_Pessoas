package io.github.jeffsilva11.com.br.projeto_pessoa_salario.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception.ResourceNotFoundException;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Pessoa;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.CargoRepository;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.PessoaRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Named(value = "pessoaMB")
@ViewScoped
@Slf4j
public class PessoaMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private List<Pessoa> pessoas = new ArrayList<>();

    @Getter
    @Setter
    private Cargo cargo = new Cargo(); // Inicialize o objeto cargo aqui

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CargoRepository cargoRepository;

    public List<Pessoa> listarTodos() {
        return pessoas = pessoaRepository.findAll();
        
    }
        @PostConstruct
    public void init() {
       // pessoa = new Pessoa();
        listarCargos();
        listaCargos = cargoRepository.findAll();
        listarTodos();
    }
    //@Getter
    @Setter
    private List<Cargo> listaCargos = new ArrayList<>();
    //private List<Cargo> listaCargos;
      
    // Getter para listaCargos
    public List<Cargo> getListaCargos() {
        return listaCargos;
    }
   
    public List<Cargo> listarCargos() {
    listaCargos = cargoRepository.findAll();
    	return listaCargos;
    }
    
    public List<Cargo> listarCargosPessoa() {
        return entityManager.createQuery("SELECT c FROM Cargo c order by nome_cargo", Cargo.class).getResultList();
   }
   
    public void calcularSalario() {
        restTemplate.put("http://localhost:8081/pessoas/calcular", null);
        listarTodos();
        log.info("Executado com sucesso");
    }

    public void recalcularSalario() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put("http://localhost:8081/pessoas/recalcular", null);
        log.info("Executado com sucesso");
    }

    public Integer getTamanhoDaLista() {
        return pessoas.size();
    }
    
    //MÉTODOS CRUD
    
    @Getter
    @Setter
    private Pessoa pessoa = new Pessoa(); // Inicialize o objeto cargo aqui
        
    @Autowired
    private PessoaRepository pessoaRepository;
      
    @PersistenceContext
    private EntityManager entityManager;
     
    @Autowired
	private final JdbcTemplate jdbcTemplate;

	public PessoaMB(JdbcTemplate jdbcTemplate) {
	     this.jdbcTemplate = jdbcTemplate;
	    }
	    
    private void limpar() {
      	pessoa = new Pessoa();
    }
    
    @SuppressWarnings("unused")
	private void limparNull() {
    	pessoa = null;
    }
	
	private void limparJavaScript() { //LIMPAR COM JAVASCRIPT
		PrimeFaces.current().executeScript("limparFormulario();");
		pessoa.setNome("");
		pessoa.setCidade("");
		pessoa.setCep("");
		pessoa.setEmail("");
		pessoa.setEndereco("");
		pessoa.setPais("");
		pessoa.setNomeUsuario("");
		pessoa.setTelefone("");
		pessoa.setDataNascimento(null);
		pessoa.setCargo(null);
	}
	
	public void cadastrarPessoa() {
	    if (pessoa.getId() == null) {  
	        String sql = "SELECT nextval('pessoa_id_seq')";
	        Long proximoId = jdbcTemplate.queryForObject(sql, Long.class);
	        pessoa.setId(proximoId);
	    }
	    try {
	    	pessoaRepository.save(pessoa);
	        limparJavaScript();
	        
	        FacesMessage msg = new FacesMessage("Pessoa Cadastrado com Sucesso!");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	       	        
	    } catch (Exception e) {
	        // Tratar exceções, se houver algum erro ao salvar o usuário.
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Cadastrar Pessoa", null);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }
}
   
    public Cargo buscarCargo(Long id) {
        return cargoRepository.findById(id).orElse(null);
    }
    
    public List<Pessoa> listarPessoas() {
        //return entityManager.createQuery("SELECT c FROM Pessoa c order by nome", Pessoa.class).getResultList();
        return entityManager.createQuery("SELECT c FROM Pessoa c order by id desc", Pessoa.class).getResultList();
   }
    
    public Pessoa findById(Long pessoaId) {
        // Use o método findById do seu repositório Spring Data JPA para buscar o cargo por ID
        return pessoaRepository.findById(pessoaId)
            .orElseThrow(() -> new ResourceNotFoundException("Pessoa not found with id " + pessoaId));
    }
    
    public String visualizarPessoa(Long pessoaId) {
    	pessoa = findById(pessoaId); // Suponha que você tenha um método findById no seu serviço
        return "pessoa_visualizar.xhtml"; // Redirecionar para a página de visualização de pessoa
    }
    
    public String visualizarPessoaEditar(Long pessoaId) {
    	pessoa = findById(pessoaId); // Suponha que você tenha um método findById no seu serviço
        return "pessoa_editar.xhtml"; // Redirecionar para a página de visualização de pessoa
    }
    
    public String visualizarPessoaApagar(Long pessoaId) {
    	pessoa = findById(pessoaId); // Suponha que você tenha um método findById no seu serviço
		return "pessoa_apagar.xhtml"; // Redirecionar para a página de visualização de pessoa
   }
    
    public void cancelarPessoa () {
        if (pessoa != null) {
            limpar(); 
        
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        try {
                externalContext.redirect("pessoa_listar.xhtml");
	            } catch (IOException e) {   // Lida com exceções de redirecionamento
	            	e.printStackTrace();
	            }
	    }
 }
    
	public void editarPessoa () {
        if (pessoa != null) {
        	pessoaRepository.save(pessoa);
            limpar();            
            FacesContext facesContext = FacesContext.getCurrentInstance();
	        facesContext.getExternalContext().getFlash().setKeepMessages(true); // Mantém as mensagens entre as páginas - Abordagem chamada "FlashScope".
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pessoa Alterada com Sucesso", null));
	        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        try {
	        	externalContext.redirect("pessoa_listar.xhtml");
	        } catch (IOException e) {   // Lida com exceções de redirecionamento
	        }
        }
}
	
    //public String apagarPessoa(Long pessoaId) {
    public void apagarPessoa() {
      		//pessoa = findById(pessoaId); // Suponha que você tenha um método findById no seu serviço
	    	if (pessoa != null) {
	    		try {
		    		pessoaRepository.delete(pessoa);
		    		limpar();
		    		// Se a operação for bem-sucedida
					FacesContext facesContext = FacesContext.getCurrentInstance();
			        facesContext.getExternalContext().getFlash().setKeepMessages(true); // Mantém as mensagens entre as páginas - Abordagem chamada "FlashScope".
				    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pessoa Excluída com Sucesso", null));
				    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			        try {
			            externalContext.redirect("pessoa_listar.xhtml");
			        } catch (IOException e) {   // Lida com exceções de redirecionamento
			        }
	    		} catch (Exception e) {
	    		    // Se ocorrer qualquer outra exceção
	    		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Excluir Pessoa!", e.getMessage()));
	    	    }
	    }
    }
    
    //<<VER TAMANHO DA LISTA>>
    public int contarPessoas() {
        List<Pessoa> pessoas = listarPessoas(); // Chama o método para obter a lista
        return pessoas.size(); // Retorna o tamanho da lista
    }
    
    
    
}