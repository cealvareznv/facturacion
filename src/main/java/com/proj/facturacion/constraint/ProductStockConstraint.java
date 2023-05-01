package com.proj.facturacion.constraint;

import com.proj.facturacion.validator.ProductStockValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProductStockValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductStockConstraint {
    String message() default "El producto debe tener una cantidad en stock.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
