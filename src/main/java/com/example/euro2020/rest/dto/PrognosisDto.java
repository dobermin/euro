package com.example.euro2020.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class PrognosisDto {

	private List<MatchesDto> matches;
	private String tour;
	private List<String> tours;
	private List<String> color;
	private boolean blocked;
}
