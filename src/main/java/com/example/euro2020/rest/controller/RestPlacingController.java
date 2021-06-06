package com.example.euro2020.rest.controller;

import com.example.euro2020.entity.Party;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.rest.dto.Placing;
import com.example.euro2020.rest.dto.enums.Group;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RestPlacingController extends RestMainController {

	@GetMapping(value = "/placing")
	public ResponseEntity<?> champion (Principal principal) {
		setUser(principal);
		setBlocked(getConfig().configService.timeOutStartCup());
		List<Party> parties = partyService.findAll();
		List<Teams> teams = teamsService.findAll();

		Placing placing = new Placing();

		placing.setBlocked(isBlocked());
		List<Map<String, List<Map<String, String>>>> placings = new ArrayList<>();
		parties.forEach(
			party -> {
				List<Map<String, String>> l = new ArrayList<>();
				teams.stream()
					.filter(team -> team.getParty().equals(party))
					.forEach(
						t -> {
							Map<String, String> map = new HashMap<>();
							map.put(Group.TEAM.name(), t.getTeams());
							map.put(Group.PROGNOSIS.name(),
								String.valueOf(t.getPlacings().stream()
									.filter(p -> p.getUsr().equals(getUser()))
									.findFirst().get().getPosition())
							);
							map.put(Group.POSITION.name(),
								String.valueOf(t.getStanding().getPosition()));
							map.put(Group.POINTS.name(), "");
							if (isBlocked())
								map.put(Group.POINTS.name(),
									String.valueOf(map.get(Group.POSITION.name()).equals(map.get(Group.PROGNOSIS.name())) ? getConfig().configService.getPlace() : ""));

							l.add(map);
						}
					);
				Map<String, List<Map<String, String>>> m = new HashMap<>();
				m.put(party.getParty(), l);
				placings.add(m);
			}
		);
		placing.setPlacings(
			placings
		);
		return ResponseEntity.ok(placing);
	}
}
