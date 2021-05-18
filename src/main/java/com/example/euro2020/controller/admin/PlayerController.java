package com.example.euro2020.controller.admin;

import com.example.euro2020.entity.Teams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PlayerController extends AdminController {

	@RequestMapping(value = "/get_player")
	public ModelAndView getRating () {

		List<Teams> teams = teamsService.findAll();
		playersService.getPlayersAndSave(teams, getConfig());

		return new ModelAndView("redirect:/");
	}

}
