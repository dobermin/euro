package com.example.euro2020.service;

import com.example.euro2020.config.ConfigProperties;
import com.example.euro2020.entity.*;
import com.example.euro2020.objects.DateTime;
import com.example.euro2020.objects.DayPoints;
import com.example.euro2020.repository.PrognosisRepository;
import com.example.euro2020.security.model.enums.Status;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PrognosisService implements IPrognosisService {

	private final PrognosisRepository repository;
	private Integer points = 0;

	public PrognosisService (PrognosisRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Prognosis> findAll () {
		return new ArrayList<>((List<Prognosis>) repository.findAll());
	}

	@Override
	public Prognosis findById (Long id) {
		return new ArrayList<>((List<Prognosis>) repository.findAllById(Collections.singleton(id))).get(0);
	}

	@Override
	public void save (Prognosis entity) {
		repository.save(entity);
	}

	@Override
	public void saveAll (List<Prognosis> entities) {
		entities.forEach(this::save);
	}

	@Override
	public void update (Prognosis entity) {

	}

	@Override
	public List<Prognosis> findByUser (Users user) {
		return new ArrayList<>(repository.findByUsr(user));
	}

	@Override
	public Prognosis findByPrognosis (Users user, Tour tour, Teams team) {
		return new ArrayList<>(repository.findByUsrAndMatch_TourAndMatch_TeamHomeOrMatch_TeamAway(user, tour,
			team, team)).get(0);
	}

	public List<Prognosis> findByUserAndTour (Users user, Tour tour) {
		try {
			List<Prognosis> list = new ArrayList<>(repository.findByUsr(user));
			return list.stream()
				.filter(t -> t.getMatch().getTour().equals(tour))
				.sorted(Comparator.comparing(Prognosis::getId))
				.collect(Collectors.toList());
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public List<DayPoints> getPointsDay (List<Rating> rating, ConfigService configService) {
		Calendar calendar = new GregorianCalendar(Locale.getDefault());
		calendar.setTime(new Date(configService.getTimeNow()));
		int date = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		String start = String.format("16:00:00_%s.0%s.%s", date, month, year);
		String finish = String.format("15:59:00_%s.0%s.%s", date + 1, month, year);
		long st = new DateTime(start).getTimestamp().getTime();
		long fi = new DateTime(finish).getTimestamp().getTime();
		List<DayPoints> list = new ArrayList<>();
		rating.forEach(
			r -> {
				List<Prognosis> prognoses =
					r.getUsr().getPrognosis().stream()
						.filter(s -> s.getMatch().getTimestamp().getTime() > st)
						.filter(s -> s.getMatch().getTimestamp().getTime() < fi)
						.collect(Collectors.toList());
				DayPoints dayPoints = new DayPoints();
				points = 0;
				try {
					prognoses
						.forEach(
							d -> {
								if (d.getPoints() != null)
									points += d.getPoints();
							}
						);
				} catch (Exception ignored) {
				}
				dayPoints.setPoints(points);
				dayPoints.setMax(
					configService.getTimeNow() < configService.getCupGroupsEnd() ?
						prognoses.size() * configService.getScore() :
						prognoses.size() * (configService.getScorePO() + configService.getNextRoundPO())
				);
				list.add(dayPoints);
			}
		);
		return list;
	}

	@Override
	public List<Prognosis> findByTour (Users user, Tour tour) {
		List<Prognosis> list = new ArrayList<>(repository.findAllByUsrAndMatchTour(user, tour));
		list.sort(Comparator.comparing(Prognosis::getId));
		return list;
	}

	@Override
	public List<Prognosis> getPrognoses (Tour tour, Users user, Long idMatch, ConfigService configService) {
		if (user == null) {
			return new ArrayList<>(repository.findByMatchId(idMatch)).stream()
				.filter(u -> u.getUsr().getStatus() == Status.ACTIVE)
				.filter(u -> u.getUsr().isDisplay())
				.collect(Collectors.toList());
		}

		return findByUser(user).stream()
			.filter(t -> t.getMatch().getTour().equals(tour))
			.filter(timestamp -> timestamp.getMatch().getTimestamp().getTime() <= configService.timeOutStartMatch())
			.sorted(Comparator.comparing(s -> s.getMatch().getId()))
			.collect(Collectors.toList());
	}

	@Override
	public void save (Users user, Tour tourSelect, List<Matches> matches, List<String> prognoses,
	                  List<Teams> countries, List<Teams> next, Long time) {
		List<Prognosis> list = findByUserAndTour(user, tourSelect);
		for (int i = 0; i < countries.size(); i += 2) {
			int finalI = i;
			Matches match =
				matches.stream().filter(s -> s.getTeamHome().equals(countries.get(finalI))).findFirst().orElse(null);

			assert match != null;
			if (match.getTimestamp().getTime() <= time) continue;

			Prognosis prognosis = list.stream()
				.filter(s -> s.getMatch().equals(match))
				.findFirst().orElse(new Prognosis());
			prognosis.setHome(null);
			prognosis.setAway(null);
			try {
				prognosis.setHome(Integer.valueOf(prognoses.get(i)));
				prognosis.setAway(Integer.valueOf(prognoses.get(i + 1)));
				prognosis.setNext(next.get(i / 2));
			} catch (Exception ignored) {
				prognosis.setNext(null);
			}
			prognosis.setMatch(match);
			prognosis.setUsr(user);

			repository.save(prognosis);
		}
	}

	public List<Prognosis> setPrognosis (List<Prognosis> prognoses, ConfigProperties configProperties) {
		for (Prognosis m : prognoses) {
			int home = configProperties.generateNumber();
			int away = configProperties.generateNumber();
			m.setHome(home);
			m.setAway(away);
			if (m.getMatch().getTour().getTour().toLowerCase().contains("финал")) {
				if (home == away) {
					int i = configProperties.generateNumber(1) == 0 ? 1 : 2;
					if (i == 1) m.setNext(m.getMatch().getTeamHome());
					else m.setNext(m.getMatch().getTeamAway());
				}
			}
		}
		return prognoses;
	}

	public List<Prognosis> setPoints (List<Prognosis> prognoses, ConfigService configService) {
		for (Prognosis prognosis : prognoses) {
			Matches matches = prognosis.getMatch();
			int points = 0;
			String tour = prognosis.getMatch().getTour().getTour();
			boolean bool = tour.toLowerCase().contains("финал");

			try {
				int p_home = prognosis.getHome();
				int p_away = prognosis.getAway();
				Teams p_next = prognosis.getNext();
				int m_home = Integer.parseInt(matches.getMainHome());
				int m_away = Integer.parseInt(matches.getMainAway());
				Teams m_next = matches.getNext();

				//угадан счет
				if (
					p_home == m_home &&
						p_away == m_away
				)
					points = bool ? configService.getScorePO() : configService.getScore();

					//угадана разница
				else if (
					p_home - p_away == m_home - m_away
				)
					points = bool ? configService.getDifferencePO() :
						configService.getDifference();

					//угадан победитель
				else if (
					(p_home > p_away && m_home > m_away) ||
						(p_home < p_away && m_home < m_away)
				)
					points = bool ? configService.getWinnerPO() : configService.getWinner();

				if (bool) {

					if (
						(
							p_home > p_away &&
								(
									m_home > m_away || m_next == matches.getTeamHome()
								)
						) ||
							(
								p_home < p_away &&
									(
										m_home < m_away || m_next == matches.getTeamAway()
									)
							) ||
							(
								p_next != null &&
									p_next == m_next
							) ||
							(
								p_next == matches.getTeamHome() && m_home > m_away
							) ||
							(
								p_next == matches.getTeamAway() && m_home < m_away
							)
					)
						if (!tour.equals("Финал"))
							points += configService.getNextRoundPO();
				}

				prognosis.setPoints(points);

			} catch (Exception ignored) {
			}
		}

		return prognoses;
	}

}
