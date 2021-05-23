package com.example.euro2020.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController extends MainControllers implements ErrorController {

	@RequestMapping("/error")
	public ModelAndView render404 (ModelAndView model, HttpServletRequest request) {
		setMessage("Страница не найдена");
		return super.getMain(model, request);
	}

	@Override
	public String getErrorPath () {
		return null;
	}
}
