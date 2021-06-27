package com.example.euro2020.controller;

import com.example.euro2020.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
public class PrognosisController extends MainControllers {

	@RequestMapping(value = "/prognosis")
	public ModelAndView getRating (ModelAndView model, HttpServletRequest request, Principal principal) {
		List<Matches> matches = matchesService.findAll();
		Tour tourSelect = tourService.findByTime(matches, getConfig().configService.getTimeNow());

		if (tourSelect == null)
			return new ModelAndView("redirect:/");
		setModel(model, matches, tourSelect, principal);

		return super.getMain(model, request);
	}

	@PostMapping(value = "/prognosis_tour", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public ModelAndView tour (ModelAndView model, @RequestBody Map<String, String> map, Principal principal) {
		Tour tourSelect = tourService.findByTour(map.get("tour"));
		setModel(model, null, tourSelect, principal);

		model.addObject("timeBlocked", getTimeBlocked());
		model.addObject("isBlocked", isBlocked());
		model.addObject("controller", "prognosis");
		model.addObject("btn_title", getBtnTitle());
		model.setViewName("prognosis");
		return model;
	}

	@PostMapping(value = "/prognosis_save", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	@Transactional
	public boolean save (@RequestBody Map<String, String[]> map, Principal principal) {
		if (isBlocked()) return false;
		String tour = String.valueOf(map.get("tour")[0]);
		List<String> prognoses = Arrays.asList(map.get("prognosis"));
		List<String> countries = Arrays.asList(map.get("country"));
		List<String> next = Arrays.asList(map.get("next"));

		Tour tourSelect = tourService.findByTour(tour);
		List<Teams> nextTeams = null;
		try {
			nextTeams = teamsService.getTeamsFromString(next);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		List<Teams> countriesTeams = teamsService.getTeamsFromString(countries);
		List<Matches> matches = matchesService.findByTour(tourSelect);

		try {
			prognosisService.save(getUser(principal), tourSelect, matches, prognoses, countriesTeams,
				nextTeams, getConfig().configService.timeOutStartMatch());
		} catch (Exception e) {
			rollback();
			return false;
		}
		return true;
	}

	private void setModel (ModelAndView model, List<Matches> matches, Tour tourSelect, Principal principal) {
		List<Prognosis> prognoses = prognosisService.findByUserAndTour(getUser(principal), tourSelect);
		if (matches == null)
			matches = matchesService.findAll();
		List<Tour> tours = tourService.findActual(matches);
		matches = matchesService.findByTour(tourSelect);

		if (matches.size() != prognoses.size()) {
			AtomicBoolean bool = new AtomicBoolean(false);
			matches.forEach(
				m -> {
					bool.set(false);
					int i = 0;
					prognoses.forEach(
						p -> {
							if (m.getId().equals(p.getMatch().getId())) {
								bool.set(true);
								return;
							}
						}
					);
					if (!bool.get()) {
						Prognosis p = new Prognosis();
						p.setUsr(getUser());
						p.setMatch(m);
						prognoses.add(i, p);
					}
				}
			);
		}

		List<String> color = getConfig().getColorClass(prognoses);
		setBlocked(getConfig().configService.timeOutEndCup());

		List<Tour> toursAll = tourService.findAll();
		List<PlacingTeam> placings;
		Tour tourQuarter;
		Tour tourSemi;
		List<String> quarter;
		List<String> semi;
		placings = placingService.findByUser(getUser());
		if (placings.size() == teamsService.findAll().size()) {
			try {
				tourQuarter = toursAll.get(4);
				tourSemi = toursAll.get(5);

				List<Next> teamsQuarter = nextService.findByTourAndUser(tourQuarter, getUser());
				quarter = teamsService.getTeamsFromNext(teamsQuarter);

				List<Next> teamsSemi = nextService.findByTourAndUser(tourSemi, getUser());
				semi = teamsService.getTeamsFromNext(teamsSemi);
				placings = placingService.findByUserAndPosition(getUser(), 3);

				model.addObject("teamsQuarter", quarter);
				model.addObject("teamsSemi", semi);
				model.addObject("placings", placings);
			} catch (Exception e) {
			}
		}

		model.addObject("tours", tours);
		model.addObject("tourSelect", tourSelect);
		model.addObject("matches", matches);
		model.addObject("prognoses", prognoses);
		model.addObject("color", color);
		setTimeBlocked(getConfig().configService.timeOutStartMatch());
	}
}
