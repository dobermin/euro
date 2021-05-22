package com.example.euro2020.service;

import com.example.euro2020.config.ConfigProperties;
import com.example.euro2020.entity.Party;
import com.example.euro2020.entity.Standings;
import com.example.euro2020.repository.StandingsRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StandingsService implements IStandingsService {

	private final StandingsRepository standingsRepository;

	public StandingsService (StandingsRepository standingsRepository) {
		this.standingsRepository = standingsRepository;
	}

	@Override
	public List<Map<String, List<Standings>>>  findMapAll() {
		List<Standings> list = findAll();
		List<Map<String, List<Standings>>> s = new ArrayList<>();
		for (int i = 0; i < list.size() / 4; i++) {
			Map<String, List<Standings>> l = new TreeMap<>();
			l.put(list.get(4 * i).getParty().getParty(), list.subList(4 * i, 4 * i + 4));
			s.add(l);
		}
		return s;
	}

	@Override
	public List<Standings> findAll () {
		return new ArrayList<>(standingsRepository.findAllByOrderByPartyIdAscPositionAsc());
	}

	@Override
	public Standings findById (Long id) {
		return new ArrayList<>((List<Standings>)standingsRepository.findAllById(Collections.singleton(id))).get(0);
	}

	@Override
	public void save (Standings standing) {
		standingsRepository.save(standing);
	}

	@Override
	public void saveAll (List<Standings> entities) {
		entities.forEach(this::save);
	}

	@Override
	public void update (Standings entity) {

	}

	@Override
	public void save (List<Standings> standings, ConfigProperties configProperties) {
		if (configProperties.configService.getTesting()) {
			for (int i = 0; i < standings.size() / 4; i++) {
				List<Integer> integers = configProperties.randomPlaces();
				for (int j = 0; j < integers.size(); j++) {
					standings.get(4 * i + j).setPosition(integers.get(j));
				}
			}
			saveAll(standings);
		}
	}

	@Override
	public Sort orderByGroupsAsc () {
		return Sort.by(Sort.Direction.ASC, "party_id").
			and(Sort.by(Sort.Direction.ASC, "position"));
	}

	public void getStandings(List<Party> groups, ConfigProperties configProperties) {
		for (Party party : groups) {
			Document doc = configProperties.getDocByGroup(party);
			getStandingsByTeams(doc, configProperties);
		}
	}

	private void getStandingsByTeams(Document doc, ConfigProperties configProperties) {
		Elements elements = doc.select("#table18").first().select("tr");
		elements.remove(0);
		List<Standings> standings = findAll();
		for (Element element : elements) {
			Elements td = element.select("td");
			String team = td.get(1).text().toLowerCase().trim();
			Standings standing = standings.stream().filter(s -> s.getTeam().getTeams().equals(configProperties.getCountry(team))).findFirst().orElse(new Standings());
			standing.setPosition(Integer.valueOf(td.get(0).text()));
			standing.setGames(Integer.valueOf(td.get(2).text()));
			standing.setWin(Integer.valueOf(td.get(3).text()));
			standing.setDraw(Integer.valueOf(td.get(4).text()));
			standing.setLoss(Integer.valueOf(td.get(5).text()));
			standing.setGoalsDiff(standing.getGoalsScored() - standing.getGoalsMissed());
			standing.setGoalsScored(Integer.valueOf(td.get(6).text().split("-")[0]));
			standing.setGoalsMissed(Integer.valueOf(td.get(6).text().split("-")[1]));
			standing.setPoints(Integer.valueOf(td.get(7).text()));
			save(standing);
		}

	}

}
