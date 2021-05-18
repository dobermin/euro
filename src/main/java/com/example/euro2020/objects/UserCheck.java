package com.example.euro2020.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserCheck implements Serializable {

	@Size(min = 6, message = "Логин должен быть минимум 6 знаков")
	@JsonProperty("login")
//	@MyValidator(message = "Пробелы")
	private String login;

	@JsonProperty("password")
	@Size(min = 6, message = "Пароль должен быть минимум 6 знаков")
	private String password;

	public UserCheck () {
	}

	@JsonCreator
	public UserCheck (@Size(min = 6, message = "Логин должен быть минимум 6 знаков") String login, @Size(min = 6,
		message = "Пароль должен быть минимум 6 знаков") String password) {
		this.login = login;
		this.password = password;
	}

	public String getLogin () {
		return login;
	}

	public void setLogin (String login) {
		this.login = login;
	}

	public String getPassword () {
		return password;
	}

	public void setPassword (String password) {
		this.password = password;
	}
}
