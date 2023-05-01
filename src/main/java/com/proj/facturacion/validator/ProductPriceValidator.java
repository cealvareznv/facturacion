package com.proj.facturacion.validator;

import com.proj.facturacion.constraint.ProductPriceConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductPriceValidator implements ConstraintValidator<ProductPriceConstraint,Double> {
    @Override
    public void initialize(ProductPriceConstraint price){}

    @Override
    public boolean isValid(Double price, ConstraintValidatorContext context){
        return ((price != null) && (price > 0));
    }
}
