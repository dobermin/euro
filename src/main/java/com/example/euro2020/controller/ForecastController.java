package com.example.euro2020.controller;

import com.example.euro2020.entity.Matches;
import com.example.euro2020.entity.Prognosis;
import com.example.euro2020.entity.Tour;
import com.example.euro2020.entity.Users;
import com.example.euro2020.objects.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class ForecastController extends MainControllers {

	@GetMapping("/forecast")
	public ModelAndView forecast (ModelAndView model, HttpServletRequest request, Principal principal) {
		setUser(principal);

		List<Matches> matches = matchesService.findAll();
		List<Tour> tours = tourService.findActualForecast(matches, getConfig().configService.timeOutStartMatch());
		Tour tourSelect;
		try {
			tourSelect = tours.get(tours.size() - 1);
		} catch (Exception e) {
			setMessage(Messages.BEFORE_FIRST_MATCH);
			return super.getMain(model, request);
		}
		List<Users> users = usersService.findWithoutUser(getUser());
		Long id = matchesService.getIdActualMatch(getConfig().configService.getTimeNow());
		List<Prognosis> prognoses = prognosisService.getPrognoses(tourSelect, null, id, getConfig().configService);

		model.addObject("tours", tours);
		model.addObject("tourSelect", tourSelect);
		model.addObject("users", users);
		model.addObject("userActive", getUser());
		model.addObject("prognoses", prognoses);

		return super.getMain(model, request);
	}

	@PostMapping(value = "/forecast_tour", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public ModelAndView change(ModelAndView model, @RequestBody Map<String, String> map, Principal principal) {
		String tour = String.valueOf(map.get("tour"));
		Tour tourSelect = tourService.findByTour(tour);
		String user = String.valueOf(map.get("user"));
		Users userSelect = usersService.findByFullName(user);
		setUser(principal);

		List<Matches> matches = matchesService.findAll();
		try {
			if (tourSelect.getId() < tourService.findByTime(matches, getConfig().configService.getTimeNow()).getId() && userSelect == null)
				userSelect = usersService.findWithoutUser(getUser()).get(0);
		} catch (Exception ignored) {}

		Long id = matchesService.getIdActualMatch(getConfig().configService.getTimeNow());
		List<Prognosis> prognoses = prognosisService.getPrognoses(tourSelect, userSelect, id, getConfig().configService);

		List<Users> users = usersService.findWithoutUser(getUser());
		List<Tour> tours = tourService.findActualForecast(matches, getConfig().configService.timeOutStartMatch());

		model.addObject("prognoses", prognoses);
		model.addObject("userActive", getUser());
		model.addObject("users", users);
		model.addObject("userSelect", userSelect);
		model.addObject("tourSelect", tourSelect);
		model.addObject("tours", tours);

		model.setViewName("forecast");
		return model;
	}
}
