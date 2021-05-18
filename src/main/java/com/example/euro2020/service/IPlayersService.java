package com.example.euro2020.service;

import com.example.euro2020.config.ConfigProperties;
import com.example.euro2020.entity.Player;
import com.example.euro2020.entity.Teams;

import java.util.List;

public interface IPlayersService extends IService<Player> {

	List<Player> getPlayers (Teams team, ConfigProperties configProperties);

	List<Player> findByTeams (Teams team);

	List<Player> findByTeam (String team);

	void getPlayersAndSave (List<Teams> teams, ConfigProperties configProperties);

	Player findByPlayer (String player);
}
