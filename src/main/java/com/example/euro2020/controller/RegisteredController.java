package com.example.euro2020.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisteredController extends MainControllers {

	@GetMapping("/registered")
	public ModelAndView getRating (ModelAndView model, HttpServletRequest request) {
		model.addObject("users", usersService.findAll());

		return super.getMain(model, request);
	}
}
