package com.example.euro2020.objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidator implements ConstraintValidator<MyValidator, String> {

	@Override
	public void initialize (MyValidator constraintAnnotation) {}

	@Override
	public boolean isValid (String contactField, ConstraintValidatorContext constraintValidatorContext) {

		Pattern p = Pattern.compile("\\s");
		Matcher m = p.matcher(contactField);
		System.out.println(m.matches());
		return m.find();
	}
}
