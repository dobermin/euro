package com.example.euro2020.rest.controller;

import com.example.euro2020.entity.Standings;
import com.example.euro2020.rest.dto.Groups;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RestGroupsController extends RestMainController {

	@GetMapping(value = "/groups")
	public ResponseEntity<?> groups () {
		List<Map<String, List<Standings>>> standings = standingsService.findMapAll();
		List<Map<String, List<Groups>>> groups = new ArrayList<>();
		standings.forEach(
			map -> {
				Map<String, List<Groups>> ml = new HashMap<>();
				map.forEach((key, value) -> {
					List<Groups> list = new ArrayList<>();
					value.forEach(
						v -> {
							Groups g = new Groups();
							g.setPosition(String.valueOf(v.getPosition()));
							g.setTeam(v.getTeam().getTeams());
							g.setGames(String.valueOf(v.getGames()));
							g.setWin(String.valueOf(v.getWin()));
							g.setDraw(String.valueOf(v.getDraw()));
							g.setLoss(String.valueOf(v.getLoss()));
							g.setGoalsScored(String.valueOf(v.getGoalsScored()));
							g.setGoalsMissed(String.valueOf(v.getGoalsMissed()));
							g.setPoints(String.valueOf(v.getPoints()));

							list.add(g);
						}
					);
					ml.put(key, list);
				});
				groups.add(ml);
			}
		);

		return ResponseEntity.ok(groups);
	}
}
