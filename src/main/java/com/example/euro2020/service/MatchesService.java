package com.example.euro2020.service;

import com.example.euro2020.config.ConfigProperties;
import com.example.euro2020.entity.Matches;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.entity.Tour;
import com.example.euro2020.objects.DateTime;
import com.example.euro2020.objects.Jsoup;
import com.example.euro2020.objects.MyMatcher;
import com.example.euro2020.repository.MatchesRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchesService implements IMatchesService {

	@Autowired
	private MatchesRepository repository;
	@Autowired
	private DateTime dateTime;

	private Matches matches;

	private final List<Matches> matchesList = new ArrayList<>();
	private final List<Tour> tourList = new ArrayList<>();

	@Override
	public List<Matches> findAll() {
		return new ArrayList<>((List<Matches>) repository.findAll());
	}

	@Override
	public void save (Matches matches) {
		try {
			repository.save(matches);
		} catch (Exception e) {}
	}

	@Override
	public void saveAll (List<Matches> matches) {
		matches.forEach(this::save);
	}

	@Override
	public void update (Matches entity) {

	}
	@Override
	public Matches findMatchesByTourAndTeamHome (Tour tour, Teams team) {
		Matches matches = new ArrayList<>(repository.findMatchesByTourAndTeamHome(tour, team)).get(0);
		return Objects.requireNonNullElse(matches, new Matches());
	}

	@Override
	public Matches findLastMatch () {
		return new ArrayList<>(repository.findTopByOrderByIdDesc()).get(0);
	}

	@Override
	public Matches findActualMatch (Long time) {
		List<Matches> list = findActualMatches(time);

		return list.get(50);
	}

	@Override
	public Long getIdActualMatch (Long time) {
		List<Matches> matches = findAll();
		try {
			return findAll().stream()
				.filter(t -> t.getTimestamp().getTime() <= time)
				.reduce((first, second) -> second)
				.get().getId();
		} catch (Exception e) {
			return matches.get(matches.size() - 1).getId();
		}

	}

	@Override
	public List<Matches> findByTour (Tour tour) {
		try {
			return new ArrayList<>(repository.findMatchesByTour(tour)).stream().sorted(Comparator.comparing(Matches::getId)).collect(Collectors.toList());
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Matches> findActualMatches (Long time) {
		List<Matches> list = findAll();
		List<Matches> filter = list.stream()
			.filter(
				m -> m.getTimestamp().getTime() > time
			)
			.collect(Collectors.toList());
		if (filter.isEmpty()) return list;
		return filter;
	}

	public List<Teams> findTeamsByTour (Tour tour) {
		List<Teams> teams = new ArrayList<>();
		findByTour(tour).forEach(
			x -> Collections.addAll(teams, x.getTeamHome(), x.getTeamAway())
		);
		return teams;
	}

	@Override
	public Matches findById (Long id) {
		return repository.findById(id).orElse(new Matches());
	}

	public Elements getInfo(ConfigProperties configProperties) {
		Elements center = configProperties.getDocFromCalendar().select("center ");
		center.select("#table30").remove();
		Elements tr = center.select("tr");
		tr.last().remove();

		return tr;
	}

	public void setMatches(Elements elements, ITourService tourService, ITeamsService teamsService, ConfigProperties configProperties) {
		Tour tour = null;
		String regex = "([а-яА-Я]+\\s+[А-Яа-я]+)|([а-яА-Я]+)";
		for (Element element : elements) {
			boolean bool = element.select("td[bgcolor~=(#0C8A08|#2D902B|#66AE33)]").isEmpty();
			if (!bool && MyMatcher.find(element.text(), "[А-Я]{2,}").isEmpty()) {
				tour = new Tour();

				tour.setTour(element.text().replaceAll("лы", "л"));
				tourList.add(tour);

//				if (configProperties.getConfigService().getTesting())
					try {
						tourService.save(tour);
					} catch (Exception e) {
						tour = tourService.findByTour(tour.getTour());
					}
			}
			bool = element.select("td[bgcolor$=#E9E9E9]").isEmpty();
			element:
			if (!bool) {
				matches = new Matches();
				Elements td = element.select("td");
				Element match = td.get(2);
				if (!MyMatcher.find(match.text(), "^\\d || ь\\s\\d").isEmpty()) break element;
				String date = td.get(0).text();
				String time = td.get(1).text();
				String group = td.get(3).select("a").text();

				try {
					List<String> teams = MyMatcher.find(match.text(), regex);
					Teams teamHome =
						teamsService.findByTeam(configProperties.getCountry(teams.get(0)));
					Teams teamAway =
						teamsService.findByTeam(configProperties.getCountry(teams.get(1)));
					matches = findMatchesByTourAndTeamHome(tour, teamHome);
					matches.setTeamHome(teamHome);
					matches.setTeamAway(teamAway);
					matches.setTour(tour);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				matches.setTimestamp(dateTime.getTimestampMatch(date, time));

				String reg = "(\\d+)|(\\(.*)";
				List<String> score = MyMatcher.find(match.text(), reg);
				matches.setScoreHome("");
				matches.setScoreAway("");
				matches.setNext(null);

				if (configProperties.configService.getTesting()) {
					matches.setScoreHome(String.valueOf(configProperties.generateNumber()));
					matches.setScoreAway(String.valueOf(configProperties.generateNumber()));
				} else
				if (!score.isEmpty()) {
					matches.setScoreHome(score.get(0));
					matches.setScoreAway(score.get(1));
					matches.setMainHome(score.get(0));
					matches.setMainAway(score.get(1));
				}
				matches.setOvertime("");

				if (tour.getTour().toLowerCase().contains("финал"))
					getWinner(score, element, configProperties);

				matchesList.add(matches);
//				if (configProperties.getConfigService().getTesting())
					repository.save(matches);
			}

		}
	}

	private void getWinner (List<String> score, Element element, ConfigProperties configProperties) {
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

			if ( p.get(0) > p.get(1)) matches.setNext(matches.getTeamHome());
			else matches.setNext(matches.getTeamAway());
			matches.setOvertime(configProperties.getPENALTY());
		} catch (Exception e) {}

		String url = element.select("a").attr("href");
		Document document = new Jsoup(configProperties.configService.getHost() + url).getDocument();
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
			matches.setMainHome(String.valueOf( Math.round(count / 2.0)));
			matches.setMainAway(matches.getMainHome());
			matches.setOvertime(configProperties.getOVERTIME());

			if (Integer.parseInt(matches.getScoreHome()) > Integer.parseInt(matches.getScoreAway()))
				matches.setNext(matches.getTeamHome());
			else if (Integer.parseInt(matches.getScoreHome()) < Integer.parseInt(matches.getScoreAway()))
				matches.setNext(matches.getTeamAway());
		}
	}

	public List<Matches> getMatches () {
		return matchesList;
	}

	public List<Tour> getTours () {
		return tourList.stream().distinct().collect(Collectors.toList());
	}

	public List<Matches> setPrognosis(List<Matches> matches, ConfigProperties configProperties) {
		for (Matches m : matches) {
			int home = configProperties.generateNumber();
			int away = configProperties.generateNumber();
			m.setScoreHome(String.valueOf(home));
			m.setScoreAway(String.valueOf(away));
			m.setMainHome(String.valueOf(home));
			m.setMainAway(String.valueOf(away));
			if (m.getTour().getTour().toLowerCase().contains("финал")) {
				while (home == away) away = configProperties.generateNumber();
				if (Math.abs(home - away) == 1) {
					String min = String.valueOf(Math.min(home, away));
					m.setMainHome(min);
					m.setMainAway(min);
					String str = configProperties.generateNumber(1) == 0 ? configProperties.getOVERTIME() : configProperties.getPENALTY();
					m.setOvertime(str);
				}
				if (home > away) m.setNext(m.getTeamHome());
				if (home < away) m.setNext(m.getTeamAway());
			}
		}
		return matches;
	}
}
