package com.example.euro2020.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Component
@Data
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class User implements Serializable {

	@NotNull
	@Size(min = 2, message = "Имя должно быть минимум 2 знака")
	private String name;

	@Size(min = 3, message = "Фамилия должна быть минимум 3 знака")
	private String surname;

	@Size(min = 6, message = "Логин должен быть минимум 6 знаков")
	@JsonProperty("login")
//	@MyValidator(message = "Пробелы")
	private String login;

	@JsonProperty("password")
	@Size(min = 6, message = "Пароль должен быть минимум 6 знаков")
	private String password;

	private boolean LogIn = false;

	public User () {
	}

	@JsonCreator
	public User (@Size(min = 6, message = "Логин должен быть минимум 6 знаков") String login, @Size(min = 6,
		message = "Пароль должен быть минимум 6 знаков") String password) {
		this.login = login;
		this.password = password;
	}

	public User (@Size(min = 2, message = "Имя должно быть минимум 2 знака") String name) {
//		new User(name, "", "", "");
		this.name = name;
		this.surname = "surname";
		this.login = "login123";
		this.password = "password";
	}

	public User (@Size(min = 2, message = "Имя должно быть минимум 2 знака") String name, @Size(min = 3, message =
		"Фамилия должна быть минимум 3 знака") String surname, @Size(min = 6, message = "Логин должен быть " +
		"минимум 6 знаков") String login,
	             @Size(min = 6, message = "Пароль должен быть минимум 6 знаков") String password) {
		this.name = name;
		this.surname = surname;
		this.login = login;
		this.password = password;
	}
}
