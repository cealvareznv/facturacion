package com.proj.facturacion.validator;

import com.proj.facturacion.constraint.ProductStockConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductStockValidator implements ConstraintValidator<ProductStockConstraint,Integer> {
    @Override
    public void initialize(ProductStockConstraint stock){}

    @Override
    public boolean isValid(Integer stock, ConstraintValidatorContext context){
        return ((stock != null) && (stock > 0));
    }
}
