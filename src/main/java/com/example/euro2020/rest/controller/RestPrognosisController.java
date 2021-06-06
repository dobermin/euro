package com.example.euro2020.rest.controller;

import com.example.euro2020.entity.Matches;
import com.example.euro2020.entity.Prognosis;
import com.example.euro2020.entity.Tour;
import com.example.euro2020.rest.dto.MatchesDto;
import com.example.euro2020.rest.dto.PrognosisDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestPrognosisController extends RestMainController {

	@GetMapping(value = "/prognosis")
	public ResponseEntity<?> prognosis (Principal principal) {
		List<Matches> matches = matchesService.findAll();
		List<Tour> tours = tourService.findActual(matches);
		Tour tourSelect = tourService.findByTime(matches, getConfig().configService.getTimeNow());
		List<MatchesDto> matchesDto = new ArrayList<>();
		List<Prognosis> prognoses = prognosisService.findByUserAndTour(getUser(principal), tourSelect);

		PrognosisDto prognosis = new PrognosisDto();
		if (prognoses.isEmpty()) {
			matches = matchesService.findByTour(tourSelect);
			matches.forEach(
				match -> {
					MatchesDto m = new MatchesDto();
					Long l = match.getTimestamp().getTime();
					m.setDate(new SimpleDateFormat("dd.MM").format(l));
					m.setDay(new SimpleDateFormat("EEEE").format(l));
					m.setTime(new SimpleDateFormat("HH:mm").format(l));
					m.setMatch(match.getTeamHome().getTeams() + "-" + match.getTeamAway().getTeams());
					if (getConfig().configService.getTimeNow() > l && !match.getScoreHome().isEmpty()) {
						try {
							m.setScore(String.format("%s:%s(%s:%s)%s",
								match.getScoreHome(), match.getScoreAway(),
								match.getMainHome(), match.getMainAway(),
								match.getOvertime()));
							m.setPrognosisNext(match.getNext().getTeams());
						} catch (Exception ignored) {
						}
					}

					matchesDto.add(m);
				}
			);
		} else
			prognoses.forEach(
				pro -> {
					MatchesDto m = new MatchesDto();
					Matches match = pro.getMatch();
					Long l = match.getTimestamp().getTime();
					m.setDate(new SimpleDateFormat("dd.MM").format(l));
					m.setDay(new SimpleDateFormat("EEEE").format(l));
					m.setTime(new SimpleDateFormat("HH:mm").format(l));
					m.setMatch(match.getTeamHome().getTeams() + "-" + match.getTeamAway().getTeams());
					if (getConfig().configService.getTimeNow() > l && !match.getScoreHome().isEmpty()) {
						m.setScore(String.format("%s:%s(%s:%s)%s",
							match.getScoreHome(), match.getScoreAway(),
							match.getMainHome(),
							match.getMainAway(), match.getOvertime()));
						m.setPoint(String.valueOf(pro.getPoints()));
						m.setBlocked(true);
						try {
							m.setPrognosisNext(pro.getNext().getTeams());
							m.setNext(match.getNext().getTeams());
						} catch (Exception ignored) {
						}
						List<String> color = getConfig().getColorClass(prognoses);
						prognosis.setColor(color);
					}

					try {
						m.setPrognosisHome(String.valueOf(pro.getHome()));
						m.setPrognosisAway(String.valueOf(pro.getAway()));
					} catch (Exception ignored) {
					}

					matchesDto.add(m);
				}
			);
		prognosis.setMatches(matchesDto);
		prognosis.setTour(tourSelect.getTour());
		setBlocked(getConfig().configService.timeOutEndCup());

		prognosis.setTours(
			tours.stream()
				.map(Tour::getTour)
				.collect(Collectors.toList())
		);
		prognosis.setBlocked(isBlocked());

		return ResponseEntity.ok(prognosis);
	}

	@PostMapping(value = "/prognosis/tour", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public ResponseEntity<?> tour (@RequestBody Tour tour, Principal principal) {
		Tour tourSelect = tourService.findByTour(tour.getTour());
		List<MatchesDto> matchesDto = new ArrayList<>();
		List<Prognosis> prognoses = prognosisService.findByUserAndTour(getUser(principal), tourSelect);

		PrognosisDto prognosis = new PrognosisDto();
		if (prognoses.isEmpty()) {
			List<Matches> matches = matchesService.findAll();
			matches = matchesService.findByTour(tourSelect);
			matches.forEach(
				match -> {
					MatchesDto m = new MatchesDto();
					Long l = match.getTimestamp().getTime();
					m.setDate(new SimpleDateFormat("dd.MM").format(l));
					m.setDay(new SimpleDateFormat("EEEE").format(l));
					m.setTime(new SimpleDateFormat("HH:mm").format(l));
					m.setMatch(match.getTeamHome().getTeams() + "-" + match.getTeamAway().getTeams());
					if (getConfig().configService.getTimeNow() > l && !match.getScoreHome().isEmpty()) {
						try {
							m.setScore(String.format("%s:%s(%s:%s)%s",
								match.getScoreHome(), match.getScoreAway(),
								match.getMainHome(), match.getMainAway(),
								match.getOvertime()));
							m.setPrognosisNext(match.getNext().getTeams());
						} catch (Exception ignored) {
						}
					}

					matchesDto.add(m);
				}
			);
		} else
			prognoses.forEach(
				pro -> {
					MatchesDto m = new MatchesDto();
					Matches match = pro.getMatch();
					Long l = match.getTimestamp().getTime();
					m.setDate(new SimpleDateFormat("dd.MM").format(l));
					m.setDay(new SimpleDateFormat("EEEE").format(l));
					m.setTime(new SimpleDateFormat("HH:mm").format(l));
					m.setMatch(match.getTeamHome().getTeams() + "-" + match.getTeamAway().getTeams());
					if (getConfig().configService.getTimeNow() > l && !match.getScoreHome().isEmpty()) {
						m.setScore(match.getScoreHome() + ":" + match.getScoreAway());
						m.setPoint(String.valueOf(pro.getPoints()));
						try {
							m.setPrognosisNext(pro.getNext().getTeams());
							m.setNext(match.getNext().getTeams());
						} catch (Exception e) {
						}
						List<String> color = getConfig().getColorClass(prognoses);
						prognosis.setColor(color);
					}

					try {
						m.setPrognosisHome(String.valueOf(pro.getHome()));
						m.setPrognosisAway(String.valueOf(pro.getAway()));
					} catch (Exception ignored) {
					}

					matchesDto.add(m);
				}
			);
		prognosis.setMatches(matchesDto);
		prognosis.setTour(tourSelect.getTour());
		setBlocked(getConfig().configService.timeOutEndCup());

		prognosis.setBlocked(isBlocked());

		return ResponseEntity.ok(prognosis);
	}
}
