package ru.yandex.practicum.filmorate.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validation.validators.AfterDateValidator;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AfterDateValidator.class)
public @interface AfterDate {
    String message() default "Дата должна быть после {date}";

    String date() default "";
    boolean fromNow() default false;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}