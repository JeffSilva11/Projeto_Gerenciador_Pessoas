package io.github.jeffsilva11.com.br.projeto_pessoa_salario.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.repository.CargoRepository;

@Component
@FacesConverter(value = "entityConverter")
public class EntityConverter implements Converter {


    @Autowired
    private CargoRepository cargoRepository;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            Long cargoId = Long.valueOf(value);
            return cargoRepository.findById(cargoId).orElse(null);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Cargo) {
            Cargo cargo = (Cargo) value;
            return cargo.getId().toString();
        }
        return null;
    }
}