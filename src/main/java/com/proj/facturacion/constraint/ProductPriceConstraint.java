package com.proj.facturacion.constraint;

import com.proj.facturacion.validator.ProductPriceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProductPriceValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductPriceConstraint {
    String message() default "El producto debe de tener un precio.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
