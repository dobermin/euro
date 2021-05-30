package com.example.euro2020.controller.admin;

import com.example.euro2020.entity.Journal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class JournalController extends AdminController {

	@RequestMapping(value = "/journal")
	public ModelAndView journal (ModelAndView model, HttpServletRequest request) {

		List<Journal> journals = journalService.findAll();

		model.addObject("journals", journals);

		return super.getMain(model, request);
	}
}
