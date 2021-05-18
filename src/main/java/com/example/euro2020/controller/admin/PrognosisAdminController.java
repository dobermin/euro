package com.example.euro2020.controller.admin;

import com.example.euro2020.entity.Prognosis;
import com.example.euro2020.objects.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class PrognosisAdminController extends AdminController {

	@RequestMapping(value =  "/set_prognosis")
	public ModelAndView matches (Principal principal) {
		setUser(principal);
		System.out.println(new DateTime(getConfig().getConfigService().getTimeNow()).getDate());

		if (getConfig().configService.getTesting()) {
			List<Prognosis> list = prognosisService.findAll();
			list = prognosisService.setPrognosis(list, getConfig());

			try {
				prognosisService.saveAll(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new ModelAndView("redirect:/prognosis");
	}

	@RequestMapping(value =  "/set_prognosis_points")
	public ModelAndView points () {
		System.out.println(new DateTime(getConfig().getConfigService().getTimeNow()).getDate());

		List<Prognosis> list = prognosisService.findAll();
		list = prognosisService.setPoints(list, getConfig().configService);

		try {
			prognosisService.saveAll(list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/prognosis");
	}
}
