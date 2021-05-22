package com.example.euro2020.controller;

import com.example.euro2020.entity.Standings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class GroupsController extends MainControllers {

	@GetMapping("/groups")
	public ModelAndView getMain (ModelAndView model, HttpServletRequest request) {
		try {
			List<Map<String, List<Standings>>> standings = standingsService.findMapAll();
			model.addObject("standings", standings);
			model.addObject("count", standings.size() / 4);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return super.getMain(model, request);
	}
}
