package com.example.euro2020.controller;

import com.example.euro2020.config.ConfigProperties;
import com.example.euro2020.entity.Journal;
import com.example.euro2020.entity.Navigation;
import com.example.euro2020.entity.Users;
import com.example.euro2020.objects.Browser;
import com.example.euro2020.objects.DateTime;
import com.example.euro2020.objects.User;
import com.example.euro2020.security.model.enums.Roles;
import com.example.euro2020.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.security.Principal;
import java.util.*;

@Controller
@SessionAttributes("user")
public class MainControllers {

	protected boolean isActive = false;
	protected UserDetails userDetails;
	@Autowired
	protected ITeamsService teamsService;
	@Autowired
	protected IPlayersService playersService;
	@Autowired
	protected IUsersService usersService;
	@Autowired
	protected IMatchesService matchesService;
	@Autowired
	protected ITourService tourService;
	@Autowired
	protected IPrognosisService prognosisService;
	@Autowired
	protected IRatingService ratingService;
	@Autowired
	protected ScoreService scoreService;
	@Autowired
	protected IStandingsService standingsService;
	@Autowired
	protected IPartyService partyService;
	@Autowired
	protected IPlacingTeamService placingService;
	@Autowired
	protected INextService nextService;
	@Autowired
	protected IJournalService journalService;
	private String title;
	private String controller;
	private String btn_title = "Подтвердить";
	private String message = "";
	private String tfoot;
	private ModelAndView model;
	private HttpServletRequest request;
	private String uri;
	private Users user;
	private boolean isBlocked = false;
	private Long timeBlocked;
	@Autowired
	private ConfigProperties configProperties;
	@Autowired
	private INavigationService navigationService;

	public ModelAndView getMain (ModelAndView model, HttpServletRequest request) {
		this.model = model;
		this.request = request;

		model.addObject("url", "https://cup-app.herokuapp.com/");
//		model.addObject("url", "http://localhost:5000/");

		Browser browser = new Browser(request);
		String os = browser.getOs();
		if (os.equals("Windows"))
			if (
				!getConfig().configService.isEnable()
			) {
				model.setViewName("404");
				return model;
			}

		model.addObject("title", getTitle() + " | " + configProperties.configService.getTitle());
		model.addObject("year", configProperties.configService.getYear());
		model.addObject("header", "navigation");
		model.addObject("navigation", getNavigation());
		model.addObject("nav_active", getNav_active());
		model.addObject("center", getCenter());
		model.addObject("button", "button");
		model.addObject("controller", controller);
		model.addObject("btn_title", btn_title);
		model.addObject("message", getMessage());
		model.addObject("thead", getThead());
		model.addObject("tbody", getTbody());
		model.addObject("tfoot", getTfoot());
		model.addObject("isActive", isActive);
		model.addObject("isBlocked", isBlocked);
		model.addObject("timeBlocked", timeBlocked);

		model.addObject("date", new DateTime(getConfig().configService.getTimeNow()).getDate());

		model.setViewName("index");

		setMessage("");

		writeJournal();

		return model;
	}

	public String getTitle () {
		setTitle("");
		try {
			String uri = getRequest().getRequestURI();
			setUri(uri.substring(1));
			HashMap<String, List<Navigation>> nav = getNavigation();
			break1:
			for (Map.Entry<String, List<Navigation>> map : nav.entrySet()) {
				for (Navigation n : map.getValue()) {
					if (getUri().equals(n.getLink())) {
						String t = n.getTitle();
						if (!t.isEmpty()) setTitle(t);
						else setTitle(map.getKey());
						break break1;
					}
				}
			}
		} catch (Exception ignored) {
		}

		return title;
	}

	private void setTitle (String title) {
		this.title = title;
	}

	private String getCenter () {
		controller = getUri();
		switch (getUri()) {
			case "authorization":
			case "registration":
				return getUri();
			case "registration-check":
				return "registration";
			case "authorization-check":
				return "authorization";
			case "error":
				return "center";
			default:
				return "standing";
		}
	}

	public void setBtn_title (String btn_title) {
		this.btn_title = btn_title;
		this.message = "";
	}

	private String getThead () {
		if (getUri().equals("registration-check")) return "registration";
		return getUri() + "-head";
	}

	private String getTbody () {
		try {
			model.addObject("section", getTitle());
		} catch (Exception ignored) {
		}
		if (getUri().equals("registration-check")) return "registration";
		return getUri() + "-body";
	}

	private String getTfoot () {
		return tfoot;
	}

	public void setTfoot (String tfoot) {
		this.tfoot = tfoot;
	}

	private HashMap<String, List<Navigation>> getNavigation () {
		try {
			Authentication o = SecurityContextHolder.getContext().getAuthentication();
			userDetails =
				(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			isActive = true;
			for (GrantedAuthority authority : userDetails.getAuthorities()) {
				if (authority.getAuthority().equals(Roles.ADMIN.name())) {
					return navigationService.findAllAdmin();
				}
			}
			return navigationService.findAllUser();
		} catch (Exception ignored) {
		}
		isActive = false;
		HashMap<String, List<Navigation>> nav = configProperties.getNavigation();
		if (!configProperties.configService.canRegistration()) nav.remove("Регистрация");

		return nav;
	}

	public String getMessage () {
		return message;
	}

	public void setMessage (String message) {
		this.message = message;
	}

	public ConfigProperties getConfig () {
		return configProperties;
	}

	public String getBtnTitle () {
		return btn_title;
	}

	public HttpServletRequest getRequest () {
		return request;
	}

	public String getUri () {
		return uri.replaceAll("_", "-").replaceAll("[a-z]*?/", "");
	}

	private void setUri (String uri) {
		this.uri = uri;
	}

	public String getNav_active () {
		return "/" + getUri();
	}

	public Users getUser () {
		return user;
	}

	public void setUser (Users user) {
		this.user = user;
	}

	public void setUser (HttpSession session) {
		setUser(usersService.findByLogin(String.valueOf(session.getAttribute("user"))));
	}

	public void setUser (Principal principal) {
		this.user = usersService.findByLogin(principal.getName());
	}

	public Users getUser (Principal principal) {
		this.user = usersService.findByLogin(principal.getName());
		return getUser();
	}

	public Users getUser (User user) {
		this.user = usersService.findByLogin(user.getLogin());
		return getUser();
	}

	public boolean isBlocked () {
		return isBlocked;
	}

	public void setBlocked (boolean blocked) {
		isBlocked = blocked;
	}

	public Long getTimeBlocked () {
		return timeBlocked;
	}

	public void setTimeBlocked (Long timeBlocked) {
		this.timeBlocked = timeBlocked;
	}

	protected void rollback () {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}

	@Transactional
	protected void writeJournal () {
		Journal journal = new Journal();
		Browser browser = new Browser(request);
		String os = browser.getOs();
		journal.setBrowser(browser.getBrowser());
		journal.setDate(new GregorianCalendar(Locale.getDefault()));
		try {
			journal.setIp(InetAddress.getLocalHost().getHostAddress());
		} catch (Exception e) {
			journal.setIp("0.0.0.0");
		}
		journal.setOs(os);
		journal.setPage(controller);
		try {
			String referer = request.getHeader("referer").replaceAll("h[a-z\\-:\\/\\d]*\\/", "");
			journal.setReferer(referer);
			journal.setUsr(user);
		} catch (Exception e) {
			journal.setUsr(null);
		}

		try {
			journalService.save(journal);
		} catch (Exception e) {
			rollback();
			e.printStackTrace();
		}
	}
}
