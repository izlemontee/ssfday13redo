package sg.nus.vttp.day13redo.model;

import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContactValidator.class)
public @interface ContactValidation {

    String message() default "Invalid age";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
    
}
