package com.example.euro2020.controller;

import com.example.euro2020.entity.Matches;
import com.example.euro2020.entity.Prognosis;
import com.example.euro2020.entity.Users;
import com.example.euro2020.objects.Browser;
import com.example.euro2020.objects.DateTime;
import com.example.euro2020.objects.User;
import com.example.euro2020.security.model.enums.Roles;
import com.example.euro2020.security.model.enums.Status;
import com.example.euro2020.security.repository.UsersSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.InetAddress;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("user")
public class Controllers extends MainControllers {

	@Autowired
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UsersSecurityRepository usersSecurityRepository;

	public Controllers (PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/")
	public ModelAndView getMain () {
		return new ModelAndView("forward:/groups");
	}

	@GetMapping(value = {"/authorization", "/authorization/"})
	public ModelAndView getAuthorization (User user,
	                                      @RequestParam(value = "error", defaultValue = "false") boolean loginError, ModelAndView model, HttpServletRequest request, Principal principal) {
		if (principal != null) return new ModelAndView("redirect:/");
		setBtn_title("Авторизоваться");
		if (loginError) {
			model.addObject("error", true);
		}
		return super.getMain(model, request);
	}

	@PostMapping(value = {"/authorization", "/authorization/"})
	public ModelAndView auth (User user, HttpSession session) {

		if (user.getLogin() != null) {

			Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
					user.getLogin(),
					user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			session.setAttribute("user", user);
		}

		return new ModelAndView("redirect:/");
	}

	@GetMapping(value = {"/registration", "/registration/"})
	public ModelAndView getRegistration (User user, ModelAndView model, HttpServletRequest request,
	                                     Principal principal) {
		if (!getConfig().configService.canRegistration()) {
			return new ModelAndView("redirect:/authorization");
		}
		if (principal != null) return new ModelAndView("redirect:/");
		setBtn_title("Зарегистрироваться");
		return super.getMain(model, request);
	}

	@PostMapping(value = {"/registration", "/registration/"})
	@Transactional
	public ModelAndView getRegistrationCheck (@Valid User user, BindingResult bindingResult, ModelAndView model,
	                                          HttpServletRequest request, HttpSession session) throws Exception {

		if (!getConfig().configService.canRegistration())
			return new ModelAndView("redirect:/authorization");
		if (bindingResult.hasErrors()) {
			return super.getMain(model, request);
		}
		if (usersSecurityRepository.existsByLogin(user.getLogin())) {
			return super.getMain(model, request);
		}
		Users users = new Users();
		users.setFirstName(user.getName());
		users.setLastName(user.getSurname());
		users.setLogin(user.getLogin());
		users.setPassword(passwordEncoder.encode(user.getPassword()));

		Timestamp timestamp = new DateTime().getTimestamp();
		users.setDateAuthorizationLast(timestamp);

		users.setStatus(Status.ACTIVE);
		users.setIp(InetAddress.getLocalHost().getHostAddress());

		Browser browser = new Browser(request);
		users.setBrowser(browser.getBrowser());
		users.setOs(browser.getOs());
		users.setDisplay(true);

		users.setRoles(Roles.USER);

		setUser(users);
		try {
			usersService.save(users);
		} catch (Exception e) {
			System.out.println(e.getStackTrace()[0].getFileName());
			rollback();
			return new ModelAndView("redirect:/registration");
		}

		Users u = usersService.findByLogin(users.getLogin());
		List<Matches> matches = matchesService.findAll();
		List<Prognosis> prognoses = new ArrayList<>();
		matches.forEach(
			match -> {
				Prognosis prognosis = new Prognosis();
				prognosis.setMatch(match);
				prognosis.setUsr(u);
				prognoses.add(prognosis);
			}
		);

		try {
			prognosisService.saveAll(prognoses);
		} catch (Exception e) {
			rollback();
			return new ModelAndView("redirect:/registration");
		}

		if (user.getLogin() != null) {

			Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
					user.getLogin(),
					user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			session.setAttribute("user", user);
			setMessage("Регистрация прошла успешно");
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("redirect:/authorization");
	}

	@PostMapping(value = "/logout")
	public ModelAndView getRegistrationCheck () {
		return new ModelAndView("redirect:/");
	}
}
