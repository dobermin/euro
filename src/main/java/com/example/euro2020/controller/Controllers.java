package com.example.euro2020.controller;

import com.example.euro2020.entity.Matches;
import com.example.euro2020.entity.Prognosis;
import com.example.euro2020.entity.Users;
import com.example.euro2020.objects.Browser;
import com.example.euro2020.objects.DateTime;
import com.example.euro2020.objects.User;
import com.example.euro2020.security.config.jwt.JwtUtils;
import com.example.euro2020.security.model.Role;
import com.example.euro2020.security.model.enums.Roles;
import com.example.euro2020.security.model.enums.Status;
import com.example.euro2020.security.repository.RoleRepository;
import com.example.euro2020.security.repository.UsersSecurityRepository;
import com.example.euro2020.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@SessionAttributes("user")
public class Controllers extends MainControllers {

	private final PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UsersSecurityRepository usersSecurityRepository;
	@Autowired
	private RoleRepository roleRepository;

	public Controllers (PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/")
	public ModelAndView getMain () {
		return new ModelAndView("forward:/groups");
	}

	@GetMapping(value = "/authorization")
	public ModelAndView getAuthorization (User user, @RequestParam(value = "error", defaultValue = "false") boolean loginError, ModelAndView model, HttpServletRequest request) {
		setBtn_title("Авторизоваться");
		if (loginError) {
			model.addObject("error", true);
		} else {
//			String jwt = jwtUtils.generateJwtToken(authentication);
//
//			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//			List<String> roles = userDetails.getAuthorities().stream()
//				.map(item -> item.getAuthority())
//				.collect(Collectors.toList());

//			return ResponseEntity.ok(
//				new JwtResponse(
//					jwt,
//					userDetails.getId(),
//					userDetails.getUsername(),
//					roles
//				));
		}
		return super.getMain(model, request);
	}
	@PostMapping(value = "/authorization")
	public ModelAndView auth(User user, HttpSession session) {

		System.out.println(user);
		if (user.getLogin() != null) {

			Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
					user.getLogin(),
					user.getPassword()));
//			System.out.println(authentication.getPrincipal());
//
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			session.setAttribute("user", user);
		}

		return new ModelAndView("redirect:/champion");
	}

	@GetMapping(value = "/registration")
	public ModelAndView getRegistration (User user, ModelAndView model, HttpServletRequest request) {

		setBtn_title("Зарегистрироваться");
		return super.getMain(model, request);
	}

	@PostMapping(value = "/registration")
	@Transactional
	public ModelAndView getRegistrationCheck (@Valid User user, BindingResult bindingResult, ModelAndView model,
	                                          HttpServletRequest request) throws Exception {

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

		Roles roles;
		try {
			roles = roleRepository.findByName(Roles.USER).orElseThrow().getName();
		} catch (Exception e) {
			roles = Roles.USER;
		}
		Roles finalR = roles;
		users.setRoles(new HashSet<>(){
			{
				add(new Role(finalR));
			}
		});
//		users.setRole(null);
		System.out.println(users.getRole());
		setUser(users);
			usersService.save(users);
		try {
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
				prognosis.setUser(u);
				prognoses.add(prognosis);
			}
		);

		try {
			prognosisService.saveAll(prognoses);
		} catch (Exception e) {
			rollback();
			return new ModelAndView("redirect:/registration");
//			return null;
		}
//		return null;
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ModelAndView getRegistrationCheck () {
		return new ModelAndView("redirect:/");
	}
}
