package io.github.jeffsilva11.com.br.projeto_pessoa_salario.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Usuario;

@Named(value = "loginMB")
@ManagedBean
public class LoginMB {
  private Usuario usuario = new Usuario();

  public String doEfetuarLogin() {
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