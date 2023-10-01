package io.github.jeffsilva11.com.br.projeto_pessoa_salario.converter;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.stereotype.Component;

import io.github.jeffsilva11.com.br.projeto_pessoa_salario.beans.CargoMB;
import io.github.jeffsilva11.com.br.projeto_pessoa_salario.model.Cargo;

@Component
//@FacesConverter(value = "entityConverter")
@FacesConverter(forClass = Cargo.class)
public class EntityConverter implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
    	return ((Cargo) value ).getId().toString();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	ValueExpression vex =
    			context.getApplication().getExpressionFactory()
                        .createValueExpression(context.getELContext(),
                                "#{cargoMB}", CargoMB.class);
    	CargoMB cargoMB = (CargoMB)vex.getValue(context.getELContext());
        return cargoMB.getCargo(Long.valueOf(value));
    }}  




