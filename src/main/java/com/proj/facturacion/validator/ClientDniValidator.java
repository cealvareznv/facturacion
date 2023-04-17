package com.proj.facturacion.validator;

import com.proj.facturacion.constraint.ClientDniConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClientDniValidator implements ConstraintValidator<ClientDniConstraint, String> {
    @Override
    public void initialize(ClientDniConstraint dni){}

    @Override
    public boolean isValid(String dniNumber, ConstraintValidatorContext context){
        return (dniNumber != null && dniNumber.matches("^([A-Z]){0,2}(\\d{1,3}\\.?\\d{3}\\.?\\d{3})([A-Z]){0,2}$")
                && (dniNumber.length() > 7) && (dniNumber.length() < 12));
    }
}
