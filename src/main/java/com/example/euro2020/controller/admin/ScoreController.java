package com.example.euro2020.controller.admin;

import com.example.euro2020.entity.Matches;
import com.example.euro2020.objects.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class ScoreController extends AdminController {

	@RequestMapping(value =  "/score")
	public ModelAndView setScore (ModelAndView model, HttpServletRequest request, Principal principal) {
		setUser(principal);
		System.out.println(new DateTime(getConfig().configService.getTimeNow()).getDate());

		List<Matches> matches = scoreService.getScore(getConfig().configService);

		model.addObject("matches", matches);

		return super.getMain(model, request);
	}

	@PostMapping(value = "/score_save", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public void save( @RequestBody Map<String, List<Matches>> map) throws IOException {
		List<Matches> score = map.get("score");
		scoreService.saveAll(score);
	}

//	@RequestMapping(value =  "/football")
//	public String foot (ModelAndView model, HttpServletRequest request, Principal principal) {
//
//		return "football";
//	}
}
