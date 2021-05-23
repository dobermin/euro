package com.example.euro2020.controller.admin;

import com.example.euro2020.entity.Config;
import com.example.euro2020.entity.Teams;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class SettingsController extends AdminController {

	@GetMapping(value =  "/settings")
	public ModelAndView settings (ModelAndView model, HttpServletRequest request) {
		model.addObject("config", getConfig().configService.findAll());

		return super.getMain(model, request);
	}
	@PostMapping(value = "/settings", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	@Transactional
	public boolean save( @RequestBody Config settings) {
		try {
			getConfig().configService.update(settings);
		} catch (Exception e) {
			rollback();
			return false;
		}
		return true;
	}
}
