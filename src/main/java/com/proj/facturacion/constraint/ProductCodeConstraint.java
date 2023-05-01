package com.proj.facturacion.constraint;

import com.proj.facturacion.validator.ProductCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProductCodeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductCodeConstraint {
    String message() default "Debe de ingresar un código valido para el producto.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
