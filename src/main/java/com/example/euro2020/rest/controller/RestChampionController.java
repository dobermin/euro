package com.example.euro2020.rest.controller;

import com.example.euro2020.entity.Teams;
import com.example.euro2020.rest.dto.Champion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
public class RestChampionController extends RestMainController {

	@GetMapping(value = "/champion")
	public ResponseEntity<?> champion (Principal principal) {
		Champion champion = new Champion();
		try {
			champion.setChampion(
				getUser(principal).getChampion().getTeams()
			);
		} catch (Exception ignored) {
		}
		champion.setTeams(
			teamsService.findAll().stream()
				.map(Teams::getTeams)
				.collect(Collectors.toList())
		);
		champion.setBlocked(
			getConfig().configService.timeOutStartCup()
		);

		return ResponseEntity.ok(champion);
	}
}
