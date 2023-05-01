package com.proj.facturacion.validator;

import com.proj.facturacion.constraint.ProductCodeConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductCodeValidator implements ConstraintValidator<ProductCodeConstraint,String> {
    @Override
    public void initialize(ProductCodeConstraint code){}

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context){
        return ((code != null) && (code.matches("^(((K1)|(M2)|(B3)|(T4))((\\d){4})){1,6}$"))
                && (code.length() > 0) && (code.length() < 7));
    }
}
