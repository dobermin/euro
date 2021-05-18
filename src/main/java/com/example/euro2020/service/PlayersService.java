package com.example.euro2020.service;

import com.example.euro2020.config.ConfigProperties;
import com.example.euro2020.entity.Player;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.repository.PlayersRepository;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class PlayersService implements IPlayersService {

	private final PlayersRepository playersRepository;

	public PlayersService (PlayersRepository repository) {
		this.playersRepository = repository;
	}

	@Override
	public List<Player> findAll() {
		return new ArrayList<>(playersRepository.findAllByOrderByTeamsAsc());
	}

	@Override
	public Player findById (Long id) {
		return new ArrayList<>((List<Player>) playersRepository.findAllById(Collections.singleton(id))).get(0);
	}

	@Override
	public List<Player> getPlayers (Teams team, ConfigProperties configProperties) {
		String link = team.getImg();
		if (link.equals("svovakia")) link = "slovakia";
		Element element = configProperties.getDocFromTeams(link).select("table table").last();
		int countTr = element.select("tr").size();
		String position = null;
		Elements td;
		List<Player> players = new ArrayList<>();
		for (int i = 0; i < countTr; i++) {
			Element tr = element.select("tr").get(i);
			td = tr.select("td");
			if (td.get(1).attr("bgcolor").equals("#808080")) {
				position = td.get(1).text().substring(0, 1);
			} else {
				Player player = new Player();
				player.setPosition(position);
				player.setPlayer(td.get(1).text());
				player.setTeam(td.get(5).text());
				player.setTeams(team);
				switch (Objects.requireNonNull(position)) {
					case "П":
					case "Н":
						player.setDisplay(true);
				}
				players.add(player);
			}
		}
		return players;
	}

	@Override
	public List<Player> findByTeams (Teams team) {
		return new ArrayList<>(playersRepository.findByTeamsAndDisplayTrueOrderByPlayerAsc(team));
	}

	@Override
	public List<Player> findByTeam (String team) {
		return new ArrayList<>(playersRepository.findByTeamsTeamsAndDisplayTrueOrderByPlayerAsc(team));
	}

	@Override
	public void getPlayersAndSave (List<Teams> teams, ConfigProperties configProperties) {
		teams.forEach(
			t -> saveAll(getPlayers(t, configProperties))
		);
	}

	@Override
	public Player findByPlayer (String player) {
		return new ArrayList<>(playersRepository.findByPlayer(player)).get(0);
	}

	@Override
	public void save (Player player) {
		playersRepository.save(player);
	}

	@Override
	public void saveAll (List<Player> entities) {
		entities.forEach(this::save);
	}

	@Override
	public void update (Player entity) {

	}

}
