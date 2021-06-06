package com.example.euro2020.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class Bombardier {

	private String bombardier;
	private String team;
	private List<String> teams;
	private List<String> players;
	private boolean blocked;
}
