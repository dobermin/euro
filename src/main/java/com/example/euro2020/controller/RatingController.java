package com.example.euro2020.controller;

import com.example.euro2020.entity.Rating;
import com.example.euro2020.objects.DayPoints;
import com.example.euro2020.objects.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class RatingController extends MainControllers {

	@GetMapping("/rating")
	public ModelAndView Rating (ModelAndView model, HttpServletRequest request, Principal principal) {
		List<Rating> rating = ratingService.findAll();
		if (rating.size() == 0 || !getConfig().configService.timeAfterFirstMatch()) {
			setMessage(Messages.AFTER_FIRST_MATCH);
			return super.getMain(model, request);
		}
		model.addObject("score", getConfig().configService.getScore());
		model.addObject("difference", getConfig().configService.getDifference());
		model.addObject("winner", getConfig().configService.getWinner());
		model.addObject("place", getConfig().configService.getPlace());
		model.addObject("prognosis_1_4", getConfig().configService.getPrognosisQuarter());
		model.addObject("prognosis_1_2", getConfig().configService.getPrognosisSemi());
		model.addObject("champion_points", getConfig().configService.getChampionPoints());
		model.addObject("bombardier_points", getConfig().configService.getBombardierPoints());

		List<DayPoints> prognosis_points = prognosisService.getPointsDay(rating, getConfig().configService);

		model.addObject("day_points", prognosis_points);


		model.addObject("users", rating);
		model.addObject("userActive", getUser(principal));

		return super.getMain(model, request);
	}
}
