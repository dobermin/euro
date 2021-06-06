package com.example.euro2020.rest.dto;

import lombok.Data;

@Data
public class MatchesDto {

	private String date;
	private String day;
	private String time;
	private String match;
	private String prognosisHome;
	private String prognosisAway;
	private String prognosisNext;
	private String next;
	private String score;
	private String point;
	private boolean blocked;
}
