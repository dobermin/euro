package com.example.euro2020.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class Forecast {

	private List<String> tours;
	private String tourSelect;
	private String userActive;
	private String message;
	private String champion;
	private String bombardier;
	private List<String> users;
	private List<ForecastPrognosis> prognoses;
	private List<String> color;
}
