package io.github.jeffsilva11.com.br.projeto_pessoa_salario.beans;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.exception.EntidadeNaoEncontrada;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Pessoa;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.PessoaSalario;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.CargoRepository;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.PessoaRepository;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.PessoaSalarioRepository;
import lombok.Getter;
import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;

@Named(value = "pessoaSalarioMB")
@ViewScoped
//@Slf4j
public class PessoaSalarioMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private List<Pessoa> pessoas = new ArrayList<>();

    @Getter
    @Setter
    private Pessoa pessoa = new Pessoa(); // Inicialize o objeto pessoa aqui
    
    @Getter
    @Setter
    private Cargo cargo = new Cargo(); // Inicialize o objeto cargo aqui
    
    @Getter
    @Setter
    private PessoaSalario pessoaSalario = new PessoaSalario(); // Inicialize o objeto pessoaSalario aqui

    @Autowired
    private CargoRepository cargoRepository;
    
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaSalarioRepository pessoaSalarioRepository;
    
    public List<Pessoa> listarTodos() {
        return pessoas = pessoaRepository.findAll();
    }       
    
    private void limpar() {
    	//pessoaSalario = new PessoaSalario();
    	pessoa = new Pessoa();
    	//cargo = new Cargo();
    	//pessoaMB.pessoa.cargo = new Cargo();
    }
    
    public void cancelarPessoa () {
        if (pessoa != null) {
            limpar(); 
        }
    }
     
    @PersistenceContext
    private EntityManager entityManager;
    
    public void calcularSalario() {
        String jpql = "SELECT p.id, p.nome, c.salario " +
                      "FROM Pessoa p " +
                      "LEFT JOIN p.cargo c " +
                      "WHERE NOT EXISTS (SELECT 1 FROM PessoaSalario ps WHERE ps.id = p.id)";
        Query query = entityManager.createQuery(jpql);
        @SuppressWarnings("unchecked")
		List<Object[]> resultList = (List<Object[]>) query.getResultList();
		if (resultList.isEmpty()) {
	        // Se a lista estiver vazia, exiba a mensagem
	        System.out.println("Não há salários a calcular");
	        FacesContext facesContext = FacesContext.getCurrentInstance();
	        facesContext.getExternalContext().getFlash().setKeepMessages(true); // Mantém as mensagens entre as páginas - Abordagem chamada "FlashScope".
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não há Salários para Calcular", null));
		    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		    try {
		    	externalContext.redirect("pessoa_salario.xhtml");
	    	} catch (IOException e) {   // Lida com exceções de redirecionamento
	    	}
	        //return; // Saia do método ou faça qualquer outra ação necessária
	    }
	    // Processar os resultados
        for (Object[] result : resultList) {
            Long id = (Long) result[0];
            String nome = (String) result[1];
            BigDecimal salario = (BigDecimal) result[2];
            // Crie e configure o objeto PessoaSalario
            PessoaSalario pessoaSalario = new PessoaSalario();
            pessoaSalario.setId(id);
            pessoaSalario.setNome(nome);
            pessoaSalario.setSalario(salario);
            try {
            // Salve o objeto PessoaSalario no repositório
            pessoaSalarioRepository.save(pessoaSalario);
            // Se a operação for bem-sucedida
			FacesContext facesContext = FacesContext.getCurrentInstance();
	        facesContext.getExternalContext().getFlash().setKeepMessages(true); // Mantém as mensagens entre as páginas - Abordagem chamada "FlashScope".
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salários Calculados com Sucesso", null));
		    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		    try {
		    	externalContext.redirect("pessoa_salario.xhtml");
	    	} catch (IOException e) {   // Lida com exceções de redirecionamento
	    	}
		} catch (Exception e) {
		    // Se ocorrer qualquer outra exceção
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Calcular Salários!", e.getMessage()));
	    } finally {
	    }
    }
}
    
    public void calcularSalario2() {
        List<Pessoa> listaPessoa = pessoaRepository.findAll();
        for (Pessoa pessoa : listaPessoa) {
            Long idCargo = pessoa.getCargo().getId();
            if (pessoaSalarioRepository.existsById(pessoa.getId())) {
                continue;
            }
            var cargo = cargoRepository.findById(idCargo)
                    .orElseThrow(() -> new EntidadeNaoEncontrada("Cargo não encontrado"));
            var pessoaSalario = new PessoaSalario();
            pessoaSalario.setId(pessoa.getId());
            pessoaSalario.setNome(pessoa.getNome());
            pessoaSalario.setSalario(cargo.getSalario());
            //pessoaSalarioRepository.save(pessoaSalario);
            //pessoa.setSalario(cargo.getSalario()); //COMENTAR
            //pessoaRepository.save(pessoa); //COMENTAR
            try {
            	pessoaSalarioRepository.save(pessoaSalario);
            	// Se a operação for bem-sucedida
    			FacesContext facesContext = FacesContext.getCurrentInstance();
    	        facesContext.getExternalContext().getFlash().setKeepMessages(true); // Mantém as mensagens entre as páginas - Abordagem chamada "FlashScope".
    		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salários Calculados com Sucesso", null));
    		    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    		    try {
    		    	externalContext.redirect("pessoa_salario.xhtml");
		    	} catch (IOException e) {   // Lida com exceções de redirecionamento
		    	}
    		} catch (Exception e) {
    		    // Se ocorrer qualquer outra exceção
    		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Calcular Salários!", e.getMessage()));
    	    } finally {
    	        // Código que será executado independentemente de exceções
    	    }
        }
    }
  
    public BigDecimal buscarSalario(Long id) {
        Optional<PessoaSalario> optionalPessoaSalario = pessoaSalarioRepository.findById(id);
        if (optionalPessoaSalario.isPresent()) {
            return optionalPessoaSalario.get().getSalario();
        } else {
            return null; // Ou você pode escolher um valor padrão, se preferir
        }
    }
}