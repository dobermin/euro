package com.example.euro2020.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class RatingDto {

	private boolean blocked;
	private List<Rating> rating;
	private String message;

	@Data
	public static class Rating {
		private String score;
		private String difference;
		private String winner;
		private String champion;
		private String bombardier;
		private String prognosisPlayoff;
		private String scorePlayoff;
		private String differencePlayoff;
		private String winnerPlayoff;
		private String teamPlacingTeam;
		private String prognosis_1_4;
		private String prognosis_1_2;
		private String points;
		private String user;
		private boolean active;
	}
}
