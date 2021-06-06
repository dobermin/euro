package com.example.euro2020.rest.controller;

import com.example.euro2020.entity.Next;
import com.example.euro2020.entity.PlacingTeam;
import com.example.euro2020.entity.Tour;
import com.example.euro2020.rest.dto.NextDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("@securityService.hasRole('ADMIN')")
public class RestNextController extends RestMainController {

	@GetMapping(value = "/next")
	public ResponseEntity<?> next (Principal principal) {
		setUser(principal);
		List<Tour> tours = tourService.findAll();
		List<PlacingTeam> placings;
		Tour tourQuarter;
		Tour tourSemi;
		List<String> quarter;
		List<String> semi;
		NextDto nextDto = new NextDto();
		try {
			tourQuarter = tours.get(4);
			tourSemi = tours.get(5);
			placings = placingService.findByUser(getUser());
			if (placings.size() < teamsService.findAll().size()) {
				nextDto.setReferer(true);
			}
			nextDto.setPlacings(
				placings.stream()
					.map(m -> m.getTeams().getTeams())
					.sorted(Comparator.comparing(String::intern))
					.collect(Collectors.toList())
			);

			List<Next> teamsQuarter = nextService.findByTourAndUser(tourQuarter, getUser());
			quarter = teamsService.getTeamsFromNext(teamsQuarter);
			nextDto.setQuarter(quarter);

			List<Next> teamsSemi = nextService.findByTourAndUser(tourSemi, getUser());
			semi = teamsService.getTeamsFromNext(teamsSemi);
			nextDto.setSemi(semi);
		} catch (Exception e) {
			nextDto.setReferer(true);
		}
		setBlocked(getConfig().configService.timeOutStartCup());
		nextDto.setBlocked(isBlocked());

		return ResponseEntity.ok(nextDto);
	}
}
