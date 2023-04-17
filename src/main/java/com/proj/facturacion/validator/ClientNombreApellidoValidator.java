package com.proj.facturacion.validator;

import com.proj.facturacion.constraint.ClientNombreApellidoConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClientNombreApellidoValidator implements ConstraintValidator<ClientNombreApellidoConstraint, String> {
    @Override
    public void initialize(ClientNombreApellidoConstraint clientNombreApellidoConstraint){}
    @Override
    public boolean isValid(String clientNombreApellido, ConstraintValidatorContext context){
        return (clientNombreApellido != null && clientNombreApellido.matches("^[A-Za-z]+\\s?[A-Za-z]+$")
                && (clientNombreApellido.length() > 0) && (clientNombreApellido.length() < 76));
    }
}
