package com.example.euro2020.objects;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FormValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyValidator {
	String message () default "Ошибка";

	Class<?>[] groups () default {};

	Class<? extends Payload>[] payload () default {};
}