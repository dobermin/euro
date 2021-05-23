package com.example.euro2020.service;

import com.example.euro2020.entity.PlacingTeam;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.entity.Users;

import java.util.List;
import java.util.Map;

public interface IPlacingTeamService extends IService<PlacingTeam> {

	List<Teams> saveByTeamAndPosition (List<Teams> country, List<String> positions, Users user) throws Exception;

	PlacingTeam findByTeamAndUser (Teams teams, Users user);

	void deleteByTeamAndUser (Teams team, Users user);

	Map<String, PlacingTeam> findByUserTeam (Users user);

	List<PlacingTeam> findByUser (Users user);

	List<PlacingTeam> findByUserAndPosition (Users user, int position);

	Integer getPoints (Users user, ConfigService configService);

	Map<Users, Integer> getPoints (List<Users> users, ConfigService configService);
}
