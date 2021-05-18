package com.example.euro2020.service;

import com.example.euro2020.entity.Next;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.entity.Tour;
import com.example.euro2020.entity.Users;

import java.util.List;

public interface INextService extends IService<Next> {

	List<Next> findByTourAndUser (Tour tour, Users user);

	Next findByTeamAndUser (Teams team, Users user);

	void deleteByTeamAndUser (Teams team, Users user);

	void deleteByUser (Users user);

	void save (List<Teams> teams, Tour tour, Users user) throws Exception;

	void deleteByTeamsAndUser (List<Teams> teams, Users user) throws Exception;
}
