package com.example.euro2020.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TeamsController extends AdminController {

	@GetMapping("/get_teams")
	public ModelAndView teams () {

		try {
			teamsService.save(getConfig(), partyService, standingsService);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/");
	}
}
