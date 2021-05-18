package com.example.euro2020.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyErrorController implements ErrorController {

	@RequestMapping("/error")
	public ModelAndView render404 (Model model) {
		model.addAttribute("message", "Страница " + " не найдена");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404");
		return modelAndView;
	}

	@Override
	public String getErrorPath () {
		return null;
	}
}
