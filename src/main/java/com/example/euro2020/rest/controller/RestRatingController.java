package com.example.euro2020.rest.controller;

import com.example.euro2020.entity.Rating;
import com.example.euro2020.objects.Messages;
import com.example.euro2020.rest.dto.RatingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RestRatingController extends RestMainController {

	@GetMapping(value = "/rating")
	public ResponseEntity<?> rating (Principal principal) {
		List<Rating> rating = ratingService.findAll();
		List<RatingDto.Rating> list = new ArrayList<>();
		RatingDto ratingDto = new RatingDto();
		if (rating.size() == 0 || !getConfig().configService.timeAfterFirstMatch()) {
			ratingDto.setBlocked(true);
			ratingDto.setMessage(Messages.AFTER_FIRST_MATCH);
		}
		try {
			rating.forEach(
				r -> {
					RatingDto.Rating dto = new RatingDto.Rating();
					dto.setUser(String.format("%s %s", r.getUsr().getFirstName(),
						r.getUsr().getLastName()));
					dto.setScore(String.valueOf(r.getScore()));
					dto.setDifference(String.valueOf(r.getDifference()));
					dto.setWinner(String.valueOf(r.getWinner()));
					dto.setTeamPlacingTeam(String.valueOf(r.getTeamPlacingTeam()));
					dto.setPrognosis_1_4(String.valueOf(r.getPrognosis_1_4()));
					dto.setPrognosis_1_2(String.valueOf(r.getPrognosis_1_2()));
					dto.setPrognosisPlayoff(String.valueOf(r.getPrognosisPlayoff()));
					dto.setChampion(String.valueOf(r.getChampion()));
					dto.setBombardier(String.valueOf(r.getBombardier()));
					dto.setPoints(String.valueOf(r.getPoints()));
					dto.setActive(principal.getName().equals(r.getUsr().getLogin()));

					list.add(dto);
				}
			);
			ratingDto.setRating(list);
		} catch (Exception ignored) {
		}

		return ResponseEntity.ok(ratingDto);
	}
}
