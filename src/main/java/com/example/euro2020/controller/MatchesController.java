package com.example.euro2020.controller;

import com.example.euro2020.entity.Matches;
import com.example.euro2020.objects.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MatchesController extends MainControllers {

	private Matches matches;

	@GetMapping("/get_matches")
	public ModelAndView getRating (User user, HttpSession session, ModelAndView model, HttpServletRequest request) {
/*
		Elements center = getDocFromCalendar().select("center ");
		center.select("#table30").remove();
		Elements tr = center.select("tr");
		tr.last().remove();

		Tour tour = null;
		for (Element element : tr) {
			boolean bool = element.select("td[bgcolor~=(#0C8A08|#2D902B|#66AE33)]").isEmpty();
			if (!bool) {
				tour = new Tour();
//				switch (element.text()) {
//					case "1/8 финала": tour.setTour("1/8"); break;
//					case "Четвертьфиналы": tour.setTour("1/4"); break;
//					case "Полуфиналы": tour.setTour("1/2"); break;
//					case "Финал": tour.setTour("Финал"); break;
//					default: tour.setTour(element.text());
//				}

				tour.setTour(element.text().replaceAll("лы", "л"));
				tourService.save(tour);
			}
			bool = element.select("td[bgcolor$=#E9E9E9]").isEmpty();
			element:
			if (!bool) {
				Elements td = element.select("td");
				Element match = td.get(2);
				if (!MyMatcher.find(match.text(), "^\\d*").isEmpty()) break element;
				String date = td.get(0).text();
				String time = td.get(1).text();
				String group = td.get(3).select("a").text();
//				setMatch(tour, date, time, match, group);
			}

		}
*/
		return new ModelAndView("redirect:/");
	}
/*
	private void setMatch (Tour tour, String date, String time, Element match, String group) {
		matches = new Matches();

		matches.setTour(tour);
//		party.setParty(partyService.findByParty(group).getId().toString());
//		matches.setParty(partyService.findByParty(group));
		matches.setTimestamp(getTimestamp(date, time));
		getMatch(tour, match);
//
////		party.getMatchesList().add(matches);
////		partyService.save(party);
		matchesService.save(matches);

//		log.info(matches.getTeam_home() + " - " + matches.getTeam_away());
//		log.info(matches.getScore_home() + " - " + matches.getScore_away());
//		log.info(matches.getTour());
////		log.info(matches.getParty().getParty());
//		log.info(matches.getTimestamp().toString());

//		System.out.println(matchesService.findAll().get(0).getParty().getParty());
	}

	private void getMatch (Tour tour, Element element) {
		String match = element.text();
		String regex = "([а-яА-Я]+\\s+[А-Яа-я]+)|([а-яА-Я]+)";
		List<String> teams = MyMatcher.find(match, regex);
		matches.setTeamHome(teamsService.findByTeam(getCountry(teams.get(0))));
		matches.setTeamAway(teamsService.findByTeam(getCountry(teams.get(1))));

		regex = "(\\d+)|(\\(.*)";
		List<String> score = MyMatcher.find(match, regex);
		try {
			matches.setScoreHome(score.get(0));
			matches.setScoreAway(score.get(1));
		} catch (Exception e) {}
		if (Integer.parseInt(score.get(0)) > Integer.parseInt(score.get(1))) matches.setNext(matches.getTeam_home());
		else matches.setNext(matches.getTeam_away());

		if (tour.getTour().toLowerCase().contains("финал"))
			getWinner(score, element);
	}

	private void getWinner (List<String> score, Element element) {
		String m = "";
		List<Integer> p = new ArrayList<Integer>() {
			{
				add(null);
				add(null);
			}
		};
		try {
			m = score.get(2);
			List<String> penalty = MyMatcher.find(m, "(\\d+)");
			p.set(0, Integer.valueOf(penalty.get(0)));
			p.set(1, Integer.valueOf(penalty.get(1)));

//			if ( p.get(0) > p.get(1)) matches.setNext(matches.getTeam_home());
//			else matches.setNext(matches.getTeam_away());
			matches.setOvertime(getConfig().getPENALTY());
		} catch (Exception e) {}

		String url = element.select("a").attr("href");
		Document document = new Jsoup(configService.getHost() + url).getDocument();
		Element table = document.select("table").last();
		Element tr = table.select("tr").get(2);
		List<Integer> goals = MyMatcher.find(tr.text(), ",\\s\\d+", true);
		Integer max = MyMatcher.Max(goals);
		if ( max <= 90 ) {
			matches.setMainHome(score.get(0));
			matches.setMainAway(score.get(1));
		} else {
			Integer minute = goals.get(0);
			int count = 0;
			while (minute <= 90) {
				count++;
				minute = goals.get(count);
			}
			matches.setMainHome(String.valueOf( Math.round(count / 2)));
			matches.setMainAway(matches.getMainHome());
			if (matches.getOvertime() == null) {
				matches.setOvertime(getConfig().getOVERTIME());
			}
		}
	}
*/

}
