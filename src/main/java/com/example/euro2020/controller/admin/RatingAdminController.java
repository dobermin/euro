package com.example.euro2020.controller.admin;

import com.example.euro2020.entity.*;
import com.example.euro2020.objects.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RatingAdminController extends AdminController {

	private List<Player> bombardiers;

	@GetMapping("/get_rating")
	public ModelAndView setRating () {

		System.out.println(new DateTime(getConfig().configService.getTimeNow()).getDate());

		List<Users> users = usersService.findAll();

		bombardiers = ratingService.getBombardiers(getConfig());

		ratingService.getRatingAndSave(users, bombardiers, getConfig().configService);

		return new ModelAndView("redirect:/rating");
	}
}
