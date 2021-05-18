package com.example.euro2020.controller.admin;

import com.example.euro2020.entity.Party;
import com.example.euro2020.entity.Standings;
import com.example.euro2020.objects.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StandingsController extends AdminController {

	@RequestMapping(value = "/set_team_placing")
	public ModelAndView placing () {
		System.out.println(new DateTime(getConfig().getConfigService().getTimeNow()).getDate());

		List<Standings> list = standingsService.findAll();
		standingsService.save(list, getConfig());

		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/standings")
	public ModelAndView standings () {
		System.out.println(new DateTime(getConfig().getConfigService().getTimeNow()).getDate());

		List<Party> party = partyService.findAll();
		standingsService.getStandings(party, getConfig());

		return new ModelAndView("redirect:/");
	}
}
