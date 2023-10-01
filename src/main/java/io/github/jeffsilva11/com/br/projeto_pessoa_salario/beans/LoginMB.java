package io.github.jeffsilva11.com.br.projeto_pessoa_salario.beans;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Usuario;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.UsuarioRepository;

@Named(value = "loginMB")
@ManagedBean
public class LoginMB {
  private Usuario usuario = new Usuario();

  @Autowired
  private UsuarioRepository usuarioRepository; 

  //MÉTODOS CRUD
  
  @PersistenceContext
  private EntityManager entityManager;
  
	@SuppressWarnings("unused")
	@Autowired
  private JdbcTemplate jdbcTemplate;
  
  
  public String doEfetuarLogin() {
	    try {
	        // Consulta o login e a senha no banco de dados
	        String login = usuario.getLogin();
	        String senha = usuario.getSenha();
	        
	        // Verifica se o login e a senha estão corretos
	        Usuario usuario = usuarioRepository.findByLoginAndSenha(login, senha);
	        if (usuario != null) {
	            // Login e senha corretos
	            //return "principal.xhtml";
	            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    		    try {
	                externalContext.redirect("principal.xhtml");
    		    	} catch (IOException e) {   // Lida com exceções de redirecionamento
    		    	}
	        }
	    } catch (Exception e) {
	        // Erro ao consultar o banco de dados
	        return null;
	    }

	    // Login e senha incorretos
	    /* Cria uma mensagem. */
	      FacesMessage msg = new FacesMessage("Usuário ou senha inválido!");
	      /* Obtém a instancia atual do FacesContext e adiciona a mensagem de erro nele. */
	      FacesContext.getCurrentInstance().addMessage("erro", msg);
	    return null;
	}
    
  
  public String doEfetuarLogin2() {
    if("jeff".equals(usuario.getLogin())
            && "123".equals(usuario.getSenha())) {
      return "principal.xhtml";
      //return  "http://localhost:8081/teste.xhtml";
    } else {
      /* Cria uma mensagem. */
      FacesMessage msg = new FacesMessage("Usuário ou senha inválido!");
      /* Obtém a instancia atual do FacesContext e adiciona a mensagem de erro nele. */
      FacesContext.getCurrentInstance().addMessage("erro", msg);
      return null;
    }
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }
}