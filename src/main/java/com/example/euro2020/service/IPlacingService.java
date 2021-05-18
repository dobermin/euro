package com.example.euro2020.service;

import com.example.euro2020.entity.Placing;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.entity.Users;

import java.util.List;
import java.util.Map;

public interface IPlacingService extends IService<Placing> {

	List<Teams> saveByTeamAndPosition (List<Teams> country, List<String> positions, Users user) throws Exception;

	Placing findByTeamAndUser (Teams teams, Users user);

	void deleteByTeamAndUser (Teams team, Users user);

	Map<String, Placing> findByUserTeam (Users user);

	List<Placing> findByUser (Users user);

	Integer getPoints (Users user, ConfigService configService);

	Map<Users, Integer> getPoints (List<Users> users, ConfigService configService);
}
