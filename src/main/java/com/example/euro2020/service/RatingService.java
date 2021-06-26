package com.example.euro2020.service;

import com.example.euro2020.config.ConfigProperties;
import com.example.euro2020.entity.*;
import com.example.euro2020.repository.RatingRepository;
import org.jsoup.nodes.Document;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RatingService implements IRatingService {

	private final RatingRepository repository;
	private final IPlacingTeamService placingService;
	private final ITourService tourService;
	private final INextService nextService;
	private final IMatchesService matchesService;
	private final IPlayersService playersService;
	private int bombardier = 0;

	public RatingService (RatingRepository repository, IPlacingTeamService placingService,
	                      ITourService tourService, INextService nextService, IMatchesService matchesService,
	                      IPlayersService playersService) {
		this.repository = repository;
		this.placingService = placingService;
		this.tourService = tourService;
		this.nextService = nextService;
		this.matchesService = matchesService;
		this.playersService = playersService;
	}

	@Override
	public List<Rating> findAll () {
		return new ArrayList<>(repository.findAll(orderByPointsAsc()));
	}

	@Override
	public Rating findById (Long id) {
		return new ArrayList<>((List<Rating>) repository.findAllById(Collections.singleton(id))).get(0);
	}

	@Override
	public Rating findByUser (Users user) {
		try {
			return new ArrayList<>(repository.findByUsr(user)).get(0);
		} catch (Exception e) {
			return new Rating();
		}
	}

	@Override
	public void save (Rating rating) {
		repository.save(rating);
	}

	@Override
	public void saveAll (List<Rating> entities) {
		entities.forEach(this::save);
	}

	@Override
	public void update (Rating entity) {

	}

	@Override
	public Sort orderByPointsAsc () {
		return Sort.by(Sort.Direction.DESC, "points").
			and(Sort.by(Sort.Direction.DESC, "allScore")).
			and(Sort.by(Sort.Direction.DESC, "champion")).
			and(Sort.by(Sort.Direction.DESC, "prognosisPlayoff")).
			and(Sort.by(Sort.Direction.DESC, "scorePlayoff")).
			and(Sort.by(Sort.Direction.DESC, "difference")).
			and(Sort.by(Sort.Direction.DESC, "winner")).
			and(Sort.by(Sort.Direction.DESC, "teamPlacingTeam"));
	}

	public List<Player> getBombardier (Document document) {

		String str = document.select("#table31").first().select("tr").get(3).select("td").get(1).text();

		String reg = "\\s\\([А-Яа-я\\s\\d]*\\)";
		str = str.replaceAll(", ", "");

		return Stream.of(str.split(reg).clone())
			.map(
				s -> s.replaceAll(reg, "")
			)
			.filter(f -> !f.equals("."))
			.map(
				m -> Arrays.stream(m.toLowerCase().split(" "))
					.map(x -> x.substring(0, 1).toUpperCase(Locale.ROOT) + x.substring(1))
					.collect(Collectors.joining(" "))
			)
			.map(playersService::findByPlayer)
			.collect(Collectors.toList());
	}

	@Override
	public List<Player> getBombardiers (ConfigProperties configProperties) {
		List<Player> players = new ArrayList<>();
		if (
			configProperties.configService.getTimeNow() > configProperties.configService.getCupEnd()
		) {
			try {
				Document doc = configProperties.getDocFromBombardier();
				players = getBombardier(doc);
			} catch (Exception ignored) {
			}
		}

		return players;
	}

	@Override
	public void getRatingAndSave (List<Users> users, List<Player> players, ConfigService configService) {
		List<Tour> tours = tourService.findAll();
		Matches lastMatch = matchesService.findLastMatch();

		for (Users user : users) {

			Rating rating = findByUser(user);

			int points = 0;
			int score = 0;
			int scorePO = 0;
			int scoreAll = 0;
			int difference = 0;
			int differencePO = 0;
			int winner = 0;
			int winnerPO = 0;
			int champion = 0;
			int prognosesPO = 0;
			int teamPlacingTeam = 0;
			int prognosisQuarter = 0;
			int prognosisSemi = 0;

			List<Prognosis> prognoses = user.getPrognosis().stream()
				.filter(f -> f.getMatch().getTimestamp().getTime() < configService.getTimeNow())
				.collect(Collectors.toList());

			for (Prognosis prognosis : prognoses) {

				if (prognosis.getPoints() != null) {
					int point = prognosis.getPoints();

					boolean bool =
						prognosis.getMatch().getTour().getTour().toLowerCase().contains("тур");
					if (bool) {
						if (configService.getScore() == point) score++;
						else if (configService.getDifference() == point) difference++;
						else if (configService.getWinner() == point) winner++;
					} else {
						if (
							configService.getScorePO() == point ||
								(configService.getScorePO() + configService.getNextRoundPO()) == point
						) scorePO++;
						else if (
							configService.getDifferencePO() == point ||
								(configService.getDifferencePO() + configService.getNextRoundPO() == point)
						) differencePO++;
						else if (
							configService.getWinnerPO() == point ||
								(configService.getWinnerPO() + configService.getNextRoundPO() == point)
						) winnerPO++;
						prognosesPO += point;
					}
					points += point;
				}

				scoreAll = score + scorePO;

			}
			if (
				configService.getTimeNow() > configService.getCupGroupsEnd() &&
					configService.getTimeNow() < configService.getCupEightStart()
			) {
				List<PlacingTeam> placings = placingService.findByUser(user);
				for (PlacingTeam placing : placings) {
					int position = placing.getPosition();
					if (position == placing.getTeams().getStanding().getPosition())
						teamPlacingTeam++;
				}
			} else {
				try {
					teamPlacingTeam = rating.getTeamPlacingTeam();
				} catch (Exception ignored) {
				}
			}
			points += teamPlacingTeam * configService.getPlace();

			if (
				configService.getTimeNow() > configService.getCupEightStart() &&
					configService.getTimeNow() < configService.getCupFourStart()
			) {
				List<Next> next = nextService.findByTourAndUser(tours.get(4), user);
				List<Teams> teams = matchesService.findTeamsByTour(tours.get(4));
				for (Next n : next) {
					if (teams.contains(n.getTeams()))
						prognosisQuarter++;
				}
			} else {
				try {
					prognosisQuarter = rating.getPrognosis_1_4();
				} catch (Exception ignored) {
				}
			}
			points += prognosisQuarter * configService.getPrognosisQuarter();

			if (
				configService.getTimeNow() > configService.getCupFourEnd() &&
					configService.getTimeNow() < configService.getCupEnd()
			) {
				List<Next> next = nextService.findByTourAndUser(tours.get(5), user);
				List<Teams> teams = matchesService.findTeamsByTour(tours.get(5));
				for (Next n : next) {
					if (teams.contains(n.getTeams()))
						prognosisSemi++;
				}
			} else {
				try {
					prognosisSemi = rating.getPrognosis_1_2();
				} catch (Exception ignored) {
				}
			}
			points += prognosisSemi * configService.getPrognosisSemi();

			if (
				configService.getTimeNow() > configService.getCupEnd()
			) {
				int m_h = Integer.parseInt(lastMatch.getMainHome());
				int m_a = Integer.parseInt(lastMatch.getMainAway());
				try {

					Teams championTeam = user.getChampion();
					if (
						(
							(
								m_h > m_a ||
									lastMatch.getNext().equals(lastMatch.getTeamHome())
							) &&
								championTeam.equals(lastMatch.getTeamHome())
						) ||
							(
								(
									m_h < m_a ||
										lastMatch.getNext().equals(lastMatch.getTeamAway())
								) &&
									championTeam.equals(lastMatch.getTeamAway())
							)
					) champion = configService.getChampionPoints();
				} catch (Exception ignored) {
				}

				players.forEach(
					x -> {
						if (x.equals(user.getBombardier())) {
							bombardier = configService.getBombardierPoints();
						}
					}
				);

				points += champion + bombardier;
			}

			rating.setScore(score);
			rating.setDifference(difference);
			rating.setWinner(winner);

			rating.setScorePlayoff(scorePO);
			rating.setDifferencePlayoff(differencePO);
			rating.setWinnerPlayoff(winnerPO);
			rating.setPrognosisPlayoff(prognosesPO);
			rating.setPrognosis_1_4(prognosisQuarter);
			rating.setPrognosis_1_2(prognosisSemi);

			rating.setAllScore(scoreAll);
			rating.setTeamPlacingTeam(teamPlacingTeam);
			rating.setChampion(champion);
			rating.setBombardier(bombardier);

			rating.setUsr(user);
			rating.setPoints(points);

			save(rating);
		}

		List<Rating> ratings = findAll();
		for (int i = 0; i < ratings.size(); i++) {
			Rating rating = ratings.get(i);
			Integer now = rating.getPositionNow();
			Integer before = rating.getPositionBefore();
			int a = i + 1;
			if (now != null)
				rating.setPositionBefore(now);
			rating.setPositionNow(a);
			if (before == null)
				rating.setPositionBefore(a);
			ratings.set(i, rating);
		}
		saveAll(ratings);
	}
}
