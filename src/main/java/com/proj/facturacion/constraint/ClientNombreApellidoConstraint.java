package com.proj.facturacion.constraint;

import com.proj.facturacion.validator.ClientNombreApellidoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ClientNombreApellidoValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClientNombreApellidoConstraint {
    String message() default "Dato no valido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
