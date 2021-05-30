package com.example.euro2020.controller;

import com.example.euro2020.entity.Player;
import com.example.euro2020.entity.Teams;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class BombardierController extends MainControllers {

	@RequestMapping(value = "/bombardier")
	public ModelAndView getRating (ModelAndView model, HttpServletRequest request, Principal principal) {
		List<Teams> teams = teamsService.findAll();
		List<Player> players = new ArrayList<>();

		try {
			setUser(principal);
			setBlocked(getConfig().configService.timeOutStartCup());
			Player bombardier = getUser().getBombardier();
			Teams country = bombardier.getTeams();
			players = playersService.findByTeams(country);
			model.addObject("country", country.getTeams());
			model.addObject("bombardier", bombardier.getPlayer());
		} catch (Exception e) {
			try {
				players = playersService.findByTeams(teams.get(0));
			} catch (Exception ignored) {

			}
		}
		model.addObject("select", "champion-body");
		model.addObject("option", "option");
		model.addObject("counties", teams);
		model.addObject("players", players);

		return super.getMain(model, request);
	}

	@PostMapping(value = "/bombardier_player", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public ModelAndView player (ModelAndView model, @RequestBody Map<String, Object> map, Principal principal) {
		String country = String.valueOf(map.get("team"));
		List<Player> players = playersService.findByTeam(country);
		model.addObject("players", players);
		model.addObject("bombardier", getUser(principal).getBombardier().getPlayer());
		model.addObject("controller", "bombardier");
		model.addObject("isBlocked", isBlocked());
		model.addObject("btn_title", getBtnTitle());
		model.setViewName("bombardier-body");
		return model;
	}

	@PostMapping(value = "/bombardier_save", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	@Transactional
	public boolean save (@RequestBody Map<String, String> map, Principal principal) {
		if (isBlocked()) return false;
		setUser(principal);
		String player = map.get("bombardier");
		Player bombardier = playersService.findByPlayer(player);
		try {
			getUser().setBombardier(bombardier);
			usersService.save(getUser());
		} catch (Exception e) {
			rollback();
			return false;
		}
		return true;
	}
}
