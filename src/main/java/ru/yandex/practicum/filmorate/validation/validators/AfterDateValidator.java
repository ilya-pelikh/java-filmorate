package ru.yandex.practicum.filmorate.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.validation.annotations.AfterDate;
import java.time.LocalDate;

public class AfterDateValidator implements ConstraintValidator<AfterDate, LocalDate> {
    private LocalDate minDate;
    private boolean fromNow;

    @Override
    public void initialize(AfterDate constraintAnnotation) {
        String dateString = constraintAnnotation.date();
        boolean fromNow = constraintAnnotation.fromNow();

        if (fromNow) {
            this.fromNow = true;
            return;
        }


        try {
            this.minDate = LocalDate.parse(dateString);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Некорректный формат даты в аннотации @AfterDate: " + dateString +
                            ". Используйте формат yyyy-MM-dd"
            );
        }
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;
        }

        if (fromNow) {
            return LocalDate.now().isAfter(date);
        }

        return date.isAfter(minDate);
    }
}