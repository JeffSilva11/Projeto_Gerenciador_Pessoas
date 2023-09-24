package io.github.jeffsilva11.com.br.projeto_pessoa_salario.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Pessoa;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.CargoRepository;
import lombok.Getter;
import lombok.Setter;

@Component
@FacesConverter(value = "entityConverter")
//@FacesConverter(forClass = Cargo.class)
public class EntityConverter implements Converter {

    @Getter
    @Setter
    private Cargo cargo = new Cargo(); // Inicialize o objeto cargo aqui
	//private Optional<Cargo> cargo; // Inicialize o objeto cargo aqui
    //private Long cargo; // Inicialize o objeto cargo aqui
    
    @Getter
    @Setter
    private Pessoa pessoa = new Pessoa(); // Inicialize o objeto cargo aqui
    
    @Autowired
    private CargoRepository cargoRepository; 
        
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
   	  
    
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
         if (value != null && !value.isEmpty()) {
            try {
            	Cargo cargo = new Cargo();            //PERSISTE SER GRAVAR O ID
                cargo.setId(Long.valueOf(value));     //PERSISTE SER GRAVAR O ID
                //cargo.setId(Long.parseLong(value));
                //cargo = Long.valueOf(value);          //PERSISTE SER GRAVAR O ID
                //return cargo;
            } catch (NumberFormatException e) {
                // Trate exceções de conversão, caso ocorram
            }
        }

        return null;
    }
        
       
    
    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Long) {
            Long cargoId = (Long) value;
            return String.valueOf(cargoId);
        }
        return "";
    }
    

	
//    @Override
//    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//        if (value != null && !value.isEmpty()) {
//            Long cargoId = Long.valueOf(value);
//            //CargoMB cargomb = new CargoMB(); 
//            //return cargomb.findById(cargoId);
//            return cargoRepository.findById(cargoId);
//        }
//        return null;
//    }	
//    	
//    @Override
//    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        if (value instanceof Cargo) {
//            Cargo cargo = (Cargo) value;
//            return cargo.getId().toString();
//        }
//        return null;
//    }
	
}  


