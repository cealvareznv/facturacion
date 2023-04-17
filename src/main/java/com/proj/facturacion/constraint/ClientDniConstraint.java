package com.proj.facturacion.constraint;

import com.proj.facturacion.validator.ClientDniValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ClientDniValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClientDniConstraint {
    String message() default "Debe de ingresar un numero de documento valido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
