package ru.yandex.practicum.filmorate.validate;

import javax.validation.Constraint;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;


@Target( { FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateFilmValidator.class)
public @interface DateFilmValidation {
    public String message() default "date is before 1895-2-27";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    }

