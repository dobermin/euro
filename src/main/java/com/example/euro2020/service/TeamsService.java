package com.example.euro2020.service;

import com.example.euro2020.config.ConfigProperties;
import com.example.euro2020.entity.Next;
import com.example.euro2020.entity.Party;
import com.example.euro2020.entity.Standings;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.repository.TeamsRepository;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TeamsService implements ITeamsService {

	private final TeamsRepository repository;

	private List<String> groups;
	private List<String> teams;
	private List<String> imgLink;

	public TeamsService (TeamsRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Teams> findAll () {
		return new ArrayList<>(repository.findAllByOrderByTeamsAsc());
	}

	@Override
	public Teams findById (Long id) {
		return new ArrayList<>((List<Teams>) repository.findAllById(Collections.singleton(id))).get(0);
	}

	@Override
	public Teams findByTeam (String team) {
		return new ArrayList<>(repository.findAllByTeams(team)).get(0);
	}

	@Override
	public List<Teams> findByTeam (List<String> team) {
		return team.stream()
			.filter(x -> !x.isEmpty())
			.map(this::findByTeam)
			.collect(Collectors.toList());
	}

	@Override
	public void save (Teams teams) {
		repository.save(teams);
	}

	@Override
	public void saveAll (List<Teams> entities) {
		entities.forEach(this::save);
	}

	@Override
	public void update (Teams entity) {

	}

	@Override
	public List<String> getTeamsFromNext (List<Next> next) {
		return next.stream()
			.map(s -> s.getTeam().getTeams())
			.reduce((o1, o2) -> o1)
			.stream()
			.collect(Collectors.toList());
	}

	public void getInfo (ConfigProperties configProperties) {
		Elements td = configProperties.getDocFromCalendar().select("#table30 td");
		Elements a = td.select("a[style]");
		Elements img = td.select("img");

		groups = configProperties.getListFromElements(a);
		teams = configProperties.getListFromElements(img);
		imgLink = configProperties.getListFromElements(img, "src");
	}

	@Override
	public void save (ConfigProperties configProperties, IPartyService partyService,
	                  IStandingsService standingsService) throws Exception {
		getInfo(configProperties);
		int j = 0;
		for (String string : groups) {
			Party group = new Party();
			group.setParty(string);
			partyService.save(group);
			int g = 0;
			for (int i = j; i < 12 + j; i += 3) {
				Teams team = new Teams();
				team.setParty(group);
				team.setTeams(configProperties.getCountry(teams.get(i)));
				team.setImg(imgLink.get(i));
				save(team);
				Standings standings = new Standings();
				standings.setTeam(team);
				standings.setPosition(++g);
				standings.setParty(group);
				standings.setDraw(0);
				standings.setGames(0);
				standings.setGoalsDiff(0);
				standings.setGoalsMissed(0);
				standings.setGoalsScored(0);
				standings.setLoss(0);
				standings.setPoints(0);
				standings.setWin(0);
				standingsService.save(standings);
			}
			if (j == 2) j += 9;
			j++;
		}
	}

	@Override
	public Teams findFirst () {
		return new ArrayList<>(repository.findTopByOrderByTeamsAsc()).get(0);
	}

	@Override
	public List<Teams> getTeamsFromString (List<String> next) {
		List<Teams> list = new ArrayList<>(findAll());
		List<Teams> teams = new ArrayList<>();
		if (next.size() != 0) {
			List<String> finalNext = next.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
			for (int i = 0; i < list.size(); i++) {
				int finalI = i;
				teams.add(
					list.stream()
						.filter(x -> x.getTeams().equals(finalNext.get(finalI)))
						.findFirst().orElse(null)
				);
			}
		}
		return teams;
	}
}
