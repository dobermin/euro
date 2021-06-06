package com.example.euro2020.rest.controller;

import com.example.euro2020.entity.Player;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.rest.dto.Bombardier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RestBombardierController extends RestMainController {

	@GetMapping(value = "/bombardier")
	public ResponseEntity<?> bombardier (Principal principal) {
		Bombardier bombardier = new Bombardier();
		List<String> teams = teamsService.findAll().stream()
			.map(Teams::getTeams)
			.collect(Collectors.toList());

		bombardier.setTeams(teams);
		setBlocked(getConfig().configService.timeOutStartCup());
		bombardier.setBlocked(
			isBlocked()
		);
		List<Player> players = null;
		try {
			Player player = getUser(principal).getBombardier();
			Teams country = player.getTeams();
			players = playersService.findByTeams(country);

			bombardier.setBombardier(
				player.getPlayer()
			);
			bombardier.setTeam(
				player.getTeams().getTeams()
			);
		} catch (Exception e) {
			try {
				players = playersService.findByTeam(teams.get(0));
			} catch (Exception ignored) {

			}
		}
		assert players != null;
		bombardier.setPlayers(
			players.stream()
				.map(Player::getPlayer)
				.collect(Collectors.toList())
		);

		return ResponseEntity.ok(bombardier);
	}

	@PostMapping(value = "/bombardier/player", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public ResponseEntity<?> player (@RequestBody Teams team, Principal principal) {
		Bombardier bombardier = new Bombardier();
		List<Player> players = teamsService.findByTeam(team.getTeams()).getPlayers();
		bombardier.setPlayers(
			players.stream()
				.map(Player::getPlayer)
				.collect(Collectors.toList())
		);
		Player player = getUser(principal).getBombardier();
		bombardier.setBombardier(
			player.getPlayer()
		);
		bombardier.setTeam(
			player.getTeams().getTeams()
		);
		bombardier.setBlocked(isBlocked());
		return ResponseEntity.ok(bombardier);
	}
}
