package com.example.euro2020.controller.admin;

import com.example.euro2020.entity.Users;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UsersController extends AdminController {

	@GetMapping(value = "/users")
	public ModelAndView users (ModelAndView model, HttpServletRequest request) {
		model.addObject("users", usersService.findAllActive());

		return super.getMain(model, request);
	}

	@PostMapping(value = "/users", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	@Transactional
	public boolean save (@RequestBody List<Users> users) {
		try {
			usersService.updateAll(users);
		} catch (Exception e) {
			rollback();
			return false;
		}
		return true;
	}
}
