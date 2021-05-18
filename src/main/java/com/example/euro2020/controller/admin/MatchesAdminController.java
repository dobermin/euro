package com.example.euro2020.controller.admin;

import com.example.euro2020.entity.Matches;
import com.example.euro2020.objects.DateTime;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MatchesAdminController extends AdminController {

	@RequestMapping(value =  "/get_matches")
	public ModelAndView get_matches () {
		System.out.println(new DateTime(getConfig().getConfigService().getTimeNow()).getDate());

		Elements elements = matchesService.getInfo(getConfig());
		matchesService.setMatches(elements, tourService, teamsService, getConfig());
//		if (getConfig().configService.getTesting()) {
//			List<Matches> list = matchesService.findAll();
//			list = matchesService.setPrognosis(list);
//
//			matchesService.save(list);
//		}

		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value =  "/set_matches")
	public ModelAndView matches () {
		System.out.println(new DateTime(getConfig().getConfigService().getTimeNow()).getDate());

		if (getConfig().configService.getTesting()) {
			List<Matches> list = matchesService.findAll();
			list = matchesService.setPrognosis(list, getConfig());

			try {
				matchesService.saveAll(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new ModelAndView("redirect:/");
	}
}
