package com.example.euro2020.controller;

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
import java.util.List;
import java.util.Map;

@Controller
public class PlacingTeamController extends MainControllers {

	@RequestMapping(value = "/placing")
	public ModelAndView getRating (ModelAndView model, HttpServletRequest request, Principal principal) {
		setUser(principal);
		setBlocked(getConfig().configService.timeOutStartCup());
		System.out.println(getUser());

		model.addObject("teams", partyService.findAll());
		model.addObject("placing", placingService.findByUserTeam(getUser()));
		model.addObject("point", getConfig().configService.getPlace());
		model.addObject("unClock", getConfig().configService.cupGroupsEnd());
		return super.getMain(model, request);
	}

	@PostMapping(value = "/placing_save", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	@Transactional
	public boolean save (@RequestBody Map<String, List<String>> map, Principal principal) throws Exception {
		if (isBlocked()) return false;
		List<String> t = map.get("team");
		List<String> position = map.get("position");

		List<Teams> country = teamsService.findByTeam(t);
		setUser(principal);

		try {
			List<Teams> remove = placingService.saveByTeamAndPosition(country, position, getUser());
			nextService.deleteByTeamsAndUser(remove, getUser());
		} catch (Exception e) {
			rollback();
			return false;
		}
		return true;
	}
}
