package com.example.euro2020.rest.controller;

import com.example.euro2020.entity.Matches;
import com.example.euro2020.entity.Prognosis;
import com.example.euro2020.entity.Tour;
import com.example.euro2020.entity.Users;
import com.example.euro2020.objects.Messages;
import com.example.euro2020.rest.dto.Forecast;
import com.example.euro2020.rest.dto.ForecastPrognosis;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class RestForecastController extends RestMainController {

	@GetMapping(value = "/forecast")
	public ResponseEntity<?> forecast (Principal principal) {
		setUser(principal);
		List<Matches> matches = matchesService.findAll();
		List<Tour> tours = tourService.findActualForecast(matches,
			getConfig().configService.timeOutStartMatch());
		Tour tourSelect;
		Forecast forecast = new Forecast();
		try {
			tourSelect = tours.get(tours.size() - 1);
		} catch (Exception e) {
			forecast.setMessage(Messages.BEFORE_FIRST_MATCH);
			return ResponseEntity.ok(forecast);
		}
		List<Users> users = usersService.findWithoutUser(getUser());
		Long id = matchesService.getIdActualMatch(getConfig().configService.getTimeNow());
		List<Prognosis> prognoses = prognosisService.getPrognoses(tourSelect, null, id,
			getConfig().configService);
		List<String> color = getConfig().getColorClass(prognoses);

		forecast.setTours(
			tours.stream()
				.map(Tour::getTour)
				.collect(Collectors.toList())
		);
		forecast.setTourSelect(tourSelect.getTour());
		forecast.setUsers(
			users.stream()
				.map(user -> String.format("%s %s", user.getFirstName(), user.getLastName()))
				.collect(Collectors.toList())
		);
		Users u = getUser();
		forecast.setUserActive(String.format("%s %s", u.getFirstName(), u.getLastName()));
		try {
			forecast.setChampion(u.getChampion().getTeams());
		} catch (Exception ignored) {
		}
		try {
			forecast.setBombardier(String.format("%s(%s)", u.getBombardier().getPlayer(),
				u.getBombardier().getTeams().getTeams()));
		} catch (Exception ignored) {
		}
		List<ForecastPrognosis> list = new ArrayList<>();
		prognoses.forEach(
			prognosis -> {
				ForecastPrognosis forecastPrognosis = new ForecastPrognosis();
				try {
					forecastPrognosis.setPrognosisNext(prognosis.getNext().getTeams());
				} catch (Exception ignored) {
				}
				try {
					Matches match = prognosis.getMatch();
					forecastPrognosis.setScore(String.format("%s:%s(%s:%s)%s",
						match.getScoreHome(), match.getScoreAway(), match.getMainHome(),
						match.getMainAway(), match.getOvertime()));
					forecastPrognosis.setNext(match.getNext().getTeams());
				} catch (Exception ignored) {
				}
				forecastPrognosis.setHome(String.valueOf(prognosis.getHome()));
				forecastPrognosis.setAway(String.valueOf(prognosis.getAway()));
				forecastPrognosis.setPoints(String.valueOf(prognosis.getPoints()));
				Users usr = prognosis.getUsr();
				forecastPrognosis.setUser(String.format("%s %s", usr.getFirstName(),
					usr.getLastName()));
				list.add(forecastPrognosis);
			}
		);
		forecast.setPrognoses(list);
		forecast.setColor(color);

		return ResponseEntity.ok(forecast);
	}

	@PostMapping(value = "/forecast/tour", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public ResponseEntity<?> change (@RequestBody Map<String, String> map, Principal principal) {
		String tour = String.valueOf(map.get("tour"));
		Tour tourSelect = tourService.findByTour(tour);
		String user = String.valueOf(map.get("user"));
		Users userSelect = usersService.findByFullName(user);
		setUser(principal);

		List<Matches> matches = matchesService.findAll();
		try {
			if (tourSelect.getId() < tourService.findByTime(matches,
				getConfig().configService.getTimeNow()).getId() && userSelect == null)
				userSelect = usersService.findWithoutUser(getUser()).get(0);
		} catch (Exception ignored) {
		}

		Long id = matchesService.getIdActualMatch(getConfig().configService.getTimeNow());
		List<Prognosis> prognoses = prognosisService.getPrognoses(tourSelect, userSelect, id,
			getConfig().configService);
		List<String> color = getConfig().getColorClass(prognoses);

		List<Users> users = usersService.findWithoutUser(getUser());
		List<Tour> tours = tourService.findActualForecast(matches,
			getConfig().configService.timeOutStartMatch());

		Forecast forecast = new Forecast();
		forecast.setTours(
			tours.stream()
				.map(Tour::getTour)
				.collect(Collectors.toList())
		);
		forecast.setTourSelect(tourSelect.getTour());
		forecast.setUsers(
			users.stream()
				.map(u -> String.format("%s %s", u.getFirstName(), u.getLastName()))
				.collect(Collectors.toList())
		);
		Users u = userSelect;
		forecast.setUserActive(String.format("%s %s", u.getFirstName(), u.getLastName()));
		try {
			forecast.setChampion(u.getChampion().getTeams());
		} catch (Exception ignored) {
		}
		try {
			forecast.setBombardier(String.format("%s(%s)", u.getBombardier().getPlayer(),
				u.getBombardier().getTeams().getTeams()));
		} catch (Exception ignored) {
		}
		List<ForecastPrognosis> list = new ArrayList<>();
		prognoses.forEach(
			prognosis -> {
				ForecastPrognosis forecastPrognosis = new ForecastPrognosis();
				try {
					forecastPrognosis.setPrognosisNext(prognosis.getNext().getTeams());
				} catch (Exception ignored) {
				}
				try {
					Matches match = prognosis.getMatch();
					forecastPrognosis.setScore(String.format("%s:%s(%s:%s)%s",
						match.getScoreHome(), match.getScoreAway(), match.getMainHome(),
						match.getMainAway(), match.getOvertime()));
					forecastPrognosis.setNext(match.getNext().getTeams());
				} catch (Exception ignored) {
				}
				forecastPrognosis.setHome(String.valueOf(prognosis.getHome()));
				forecastPrognosis.setAway(String.valueOf(prognosis.getAway()));
				forecastPrognosis.setPoints(String.valueOf(prognosis.getPoints()));
				Users usr = prognosis.getUsr();
				forecastPrognosis.setUser(String.format("%s %s", usr.getFirstName(),
					usr.getLastName()));
				list.add(forecastPrognosis);
			}
		);
		forecast.setPrognoses(list);
		forecast.setColor(color);

//		model.addObject("prognoses", prognoses);
//		model.addObject("userActive", getUser());
//		model.addObject("users", users);
//		model.addObject("userSelect", userSelect);
//		model.addObject("tourSelect", tourSelect);
//		model.addObject("tours", tours);
//		model.addObject("color", color);
//
//		model.setViewName("forecast");
		return ResponseEntity.ok(forecast);
	}
}
