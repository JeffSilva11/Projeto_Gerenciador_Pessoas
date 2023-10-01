package io.github.jeffsilva11.com.br.projeto_pessoa_salario.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

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

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception.ResourceNotFoundException;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Usuario;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.UsuarioRepository;
import lombok.Getter;
import lombok.Setter;

@Named(value = "usuarioMB")
@ViewScoped
//@Slf4j
public class UsuarioMB implements Serializable {

    private static final long serialVersionUID = 1L;
        
    @Getter
    @Setter
	private Usuario usuario = new Usuario(); // Inicialize o objeto usuario aqui
     
    @Autowired
    private UsuarioRepository usuarioRepository; 

    //MÉTODOS CRUD
    
    @PersistenceContext
    private EntityManager entityManager;
    
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
    private void limpar() {
    	usuario = new Usuario();
    }
    
    @SuppressWarnings("unused")
	private void limparNull() {
    	usuario = null;
    }
	@SuppressWarnings("unused")
	private void limpar2() {
	    // Limpa os campos do formulário.
	    usuario.setLogin("");
	    usuario.setEmail("");
	    usuario.setSenha("");
	}
	
	private void limparJavaScript() { //LIMPAR COM JAVASCRIPT
		PrimeFaces.current().executeScript("limparFormulario();");
	    usuario.setLogin("");
	    usuario.setEmail("");
	    usuario.setSenha("");
	}
	
	public void cadastrarUsuario() {
	    if (usuario.getId() == null) {  
	        String sql = "SELECT nextval('usuario_id_seq')";
	        Long proximoId = jdbcTemplate.queryForObject(sql, Long.class);
	        usuario.setId(proximoId);
	    }
	    try {
	        usuarioRepository.save(usuario);
	        limparJavaScript();
	        
	        FacesMessage msg = new FacesMessage("Usuário Cadastrado com Sucesso!");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	       	        
	    } catch (Exception e) {
	        // Tratar exceções, se houver algum erro ao salvar o usuário.
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Cadastrar Usuário", null);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }
}
	     	
	public List<Usuario> listarUsuario() {
        return entityManager.createQuery("SELECT u FROM Usuario u order by id", Usuario.class).getResultList();
   }
       
	public List<Usuario> listarUsuarios() {
		return entityManager.createQuery("SELECT u FROM Usuario u order by login", Usuario.class).getResultList();
  }
   
	public Usuario findById(Long usuarioId) {
		// Use o método findById do seu repositório Spring Data JPA para buscar o usuario por ID
		return usuarioRepository.findById(usuarioId)
           .orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + usuarioId));
   }
      
	public String visualizarUsuario(Long usuarioId) {
		usuario = findById(usuarioId); // Suponha que você tenha um método findById no seu serviço
		return "usuario_visualizar.xhtml"; // Redirecionar para a página de visualização de usuario
   }
   
	public String visualizarUsuarioEditar(Long usuarioId) {
		usuario = findById(usuarioId); // Suponha que você tenha um método findById no seu serviço
		return "usuario_editar.xhtml"; // Redirecionar para a página de visualização de usuario
   }
   
	public String visualizarUsuarioApagar(Long usuarioId) {
		usuario = findById(usuarioId); // Suponha que você tenha um método findById no seu serviço
		return "usuario_apagar.xhtml"; // Redirecionar para a página de visualização de usuario
   }
	
	public void cancelarUsuario () {
        if (usuario != null) {
        	 limpar();  
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		        try {
	                externalContext.redirect("usuario_listar.xhtml");
		            } catch (IOException e) {   // Lida com exceções de redirecionamento
		            	e.printStackTrace();
		            }
		    }
  }
 
	public void editarUsuario () {
        if (usuario != null) {
        	usuarioRepository.save(usuario);
        	limpar(); 
    	 	FacesContext facesContext = FacesContext.getCurrentInstance();
 	        facesContext.getExternalContext().getFlash().setKeepMessages(true); // Mantém as mensagens entre as páginas - Abordagem chamada "FlashScope".
 		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário Alterado com Sucesso", null));
 	        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        try {
	        	externalContext.redirect("usuario_listar.xhtml");
	        } catch (IOException e) {   // Lida com exceções de redirecionamento
	        }
         }
 }
   
	//public String apagarUsuario(Long usuarioId) {
	public void apagarUsuario() {
		   	//usuario = findById(usuarioId); // Suponha que você tenha um método findById no seu serviço
			if (usuario != null) {
				try {
					usuarioRepository.delete(usuario);
			   		limpar();
			   		// Se a operação for bem-sucedida
					FacesContext facesContext = FacesContext.getCurrentInstance();
			        facesContext.getExternalContext().getFlash().setKeepMessages(true); // Mantém as mensagens entre as páginas - Abordagem chamada "FlashScope".
				    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário Excluído com Sucesso", null));
				    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			        try {
			            externalContext.redirect("usuario_listar.xhtml");
			        } catch (IOException e) {   // Lida com exceções de redirecionamento
			        }
				} catch (Exception e) {
        		    // Se ocorrer qualquer outra exceção
        		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir Usuário!", e.getMessage()));
        	    }
		    }
	}
     
	//<<VER TAMANHO DA LISTA>>
	 public int contarUsuarios() {
	        List<Usuario> usuarios = listarUsuarios(); // Chama o método para obter a lista
	        return usuarios.size(); // Retorna o tamanho da lista
    }
}