package com.example.euro2020.controller;

import com.example.euro2020.entity.Teams;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

@Controller
@SessionAttributes("user")
public class ChampionController extends MainControllers {

	@GetMapping(value =  "/champion")
	public ModelAndView getRating (ModelAndView model, HttpServletRequest request, Principal principal) {
		String champion = "";
		setUser(principal);
		try {
			champion = getUser().getChampion().getTeams();
		} catch (Exception ignored) {}
		setBlocked(getConfig().configService.timeOutStartCup());
		model.addObject("champion", champion);
		model.addObject("teams", teamsService.findAll());

		return super.getMain(model, request);
	}
	@PostMapping(value = "/champion", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	@Transactional
	public boolean save( @RequestBody Map<String, Object> map) {
		if (isBlocked()) return false;
		String champion = String.valueOf(map.get("champion"));
		Teams teams = teamsService.findByTeam(champion);
		try {
			getUser().setChampion(teams);
			usersService.save(getUser());
		} catch (SQLIntegrityConstraintViolationException ignored) {

		} catch (Exception e) {
			rollback();
			return false;
		}
		return true;
	}
}
