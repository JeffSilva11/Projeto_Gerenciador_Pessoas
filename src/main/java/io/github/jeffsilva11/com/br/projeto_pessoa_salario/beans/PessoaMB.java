package io.github.jeffsilva11.com.br.projeto_pessoa_salario.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
   
   // @Getter
   // @Setter
   // private PessoaRequestDTO pessoaRequestDTO;

   // public PessoaMB() {
   //     pessoaRequestDTO = new PessoaRequestDTO();
   // }

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
	    
	public void cadastrarPessoa() {
			if (pessoa.getId() == null) {
		        String sql = "SELECT nextval('pessoa_id_seq')"; // Consulta SQL para obter o próximo valor da sequência
		        Long proximoId = jdbcTemplate.queryForObject(sql, Long.class); // Executar a consulta e obter o próximo valor da sequência
		        pessoa.setId(proximoId); // Definir o ID gerado no objeto Pessoa
		    }
			pessoaRepository.save(pessoa);
		    limpar();
		    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        try {
	        	  externalContext.redirect("pessoa_cadastrar.xhtml");
	        } catch (IOException e) {   // Lida com exceções de redirecionamento
	        }
	        //return; // Ou retorne uma string de navegação válida se necessário
	    }
	
	public void editarPessoa () {
        if (pessoa != null) {
        	pessoaRepository.save(pessoa);
            limpar();            
	        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        try {
	        	externalContext.redirect("pessoa_listar.xhtml");
	        } catch (IOException e) {   // Lida com exceções de redirecionamento
	        }
        }
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
    private void limpar() {
      	pessoa = new Pessoa();
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
        return "pessoa_visualizar.xhtml"; // Redirecionar para a página de visualização de cargo
    }
    
    public String visualizarPessoaEditar(Long pessoaId) {
    	pessoa = findById(pessoaId); // Suponha que você tenha um método findById no seu serviço
        return "pessoa_editar.xhtml"; // Redirecionar para a página de visualização de cargo
    }
    
    public String apagarPessoa(Long pessoaId) {
    		pessoa = findById(pessoaId); // Suponha que você tenha um método findById no seu serviço
    		pessoaRepository.delete(pessoa);
    		limpar();
	        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        try {
	            externalContext.redirect("pessoa_listar.xhtml");
	        } catch (IOException e) {   // Lida com exceções de redirecionamento
	        }
	        return null; // Ou retorne uma string de navegação válida se necessário
	    }
    
    //<<VER TAMANHO DA LISTA>>
    public int contarPessoas() {
        List<Pessoa> pessoas = listarPessoas(); // Chama o método para obter a lista
        return pessoas.size(); // Retorna o tamanho da lista
    }
    
    
    
}