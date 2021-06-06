package com.example.euro2020.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class Champion {

	private String champion;
	private List<String> teams;
	private boolean blocked;
}
