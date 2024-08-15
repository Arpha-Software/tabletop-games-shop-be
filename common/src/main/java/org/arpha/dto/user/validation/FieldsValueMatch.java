package org.arpha.dto.user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.arpha.dto.user.validation.validator.FieldsValueMatchValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ TYPE })
@Constraint(validatedBy = {FieldsValueMatchValidator.class })
public @interface FieldsValueMatch {

    String message() default "Fields doesn't match";
    String field();
    String fieldMatch();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({ TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FieldsValueMatch[] value();
    }
}
