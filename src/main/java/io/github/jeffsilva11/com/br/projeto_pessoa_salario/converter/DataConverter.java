package io.github.jeffsilva11.com.br.projeto_pessoa_salario.converter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class DataConverter implements Converter {

    @Override
public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
    if (value == null || value.isEmpty()) {
        return null;
    }

    LocalDate data = LocalDate.parse(value);
	Instant dataInstant = data.atStartOfDay(ZoneId.of("UTC")).toInstant();
	Date dataConvertida = Date.from(dataInstant.atZone(ZoneId.systemDefault()).toInstant());
	return dataConvertida;
}

@Override
public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    if (value == null) {
        return null;
    }

    Date data = (Date) value;
    return new SimpleDateFormat("dd/MM/yyyy").format(data);
}
}
