package io.github.jeffsilva11.com.br.projeto_pessoa_salario.converter;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Pessoa;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.CargoRepository;
import lombok.Getter;
import lombok.Setter;

@Component
//@FacesConverter(value = "entityConverter")
@FacesConverter(forClass = Cargo.class)
public class EntityConverter2 implements Converter {

    @Getter
    @Setter
	private Cargo cargo = new Cargo(); // Inicialize o objeto cargo aqui
    
    @Getter
    @Setter
    private Pessoa pessoa = new Pessoa(); // Inicialize o objeto cargo aqui
    
    //@Autowired
    private CargoRepository cargoRepository; 
    
    
    @PersistenceContext
    //private EntityManager entityManager;
    private EntityManager em;
    
//	@Autowired
//    private JdbcTemplate jdbcTemplate;
//	
//    @Inject
//    private CargoRepository cargoRepository;
    
    @PostConstruct
    public void init() {
         // Injete uma instância do CargoRepository aqui.
  
    }
	

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        Long id = Long.parseLong(value);
        return em.find(Cargo.class, id);
    } 
    
    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Long) {
            Long cargoId = (Long) value;
            return String.valueOf(cargoId);
        }
        return "";
    }
	
}  

