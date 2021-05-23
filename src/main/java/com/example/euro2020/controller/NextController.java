package com.example.euro2020.controller;

import com.example.euro2020.entity.Next;
import com.example.euro2020.entity.PlacingTeam;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.entity.Tour;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class NextController extends MainControllers {

	@GetMapping("/next")
	public ModelAndView getRating (Principal principal, ModelAndView model, HttpServletRequest request) {

		ModelAndView m = new ModelAndView("redirect:/placing?next=true");
		setUser(principal);
		List<Tour> tours = tourService.findAll();
		List<PlacingTeam> placings;
		Tour tourQuarter;
		Tour tourSemi;
		List<String> quarter;
		List<String> semi;
		try {
			tourQuarter = tours.get(4);
			tourSemi = tours.get(5);
			placings = placingService.findByUser(getUser());
			if (placings.size() < teamsService.findAll().size()) {
				return m;
			}

			List<Next> teamsQuarter = nextService.findByTourAndUser(tourQuarter, getUser());
			quarter = teamsService.getTeamsFromNext(teamsQuarter);

			List<Next> teamsSemi = nextService.findByTourAndUser(tourSemi, getUser());
			semi = teamsService.getTeamsFromNext(teamsSemi);
		} catch (Exception e) {
			return m;
		}

		model.addObject("teamsQuarter", quarter);
		model.addObject("teamsSemi", semi);
		model.addObject("placings", placings);

		return super.getMain(model, request);
	}

	@PostMapping(value = "/next_save", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	@Transactional
	public boolean save (@RequestBody Map<String, List<String>> map, Principal principal) {
		List<Teams> tour_4 = teamsService.getTeamsFromString( map.get("tour_4"));
		List<Teams> tour_2 = teamsService.getTeamsFromString( map.get("tour_2"));
		try {
			nextService.deleteByUser(getUser(principal));
			List<Tour> tours = tourService.findAll();
			nextService.save(tour_4, tours.get(4), getUser());
			nextService.save(tour_2, tours.get(5), getUser());
		} catch (Exception e) {
			rollback();
			return false;
		}

		return true;
	}
}
