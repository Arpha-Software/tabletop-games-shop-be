package org.arpha.dto.user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@FieldsValueMatch(
        field = "password",
        fieldMatch = "confirmationPassword",
        message = "User's password and confirmation password aren't the same!"
)
@Constraint(validatedBy = {})
@Retention(RUNTIME)
@Target({ TYPE })
public @interface PasswordConfirmation {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
