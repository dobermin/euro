package com.example.euro2020.service;

import com.example.euro2020.entity.Next;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.entity.Tour;
import com.example.euro2020.entity.Users;
import com.example.euro2020.repository.NextRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class NextService implements INextService {

	private final NextRepository repository;

	public NextService (NextRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Next> findAll() {
		List<Next> list = new ArrayList<>((List<Next>) repository.findAll());
		list.sort(Comparator.comparing(o -> o.getTeams().getTeams()));
		return list;
	}

	@Override
	public Next findById (Long id) {
		return null;
	}

	@Override
	public List<Next> findByTourAndUser (Tour tour, Users user) {
		return new ArrayList<>(repository.findByTourAndUsr(tour, user));
	}

	@Override
	public Next findByTeamAndUser (Teams team, Users user) {
		return new ArrayList<>(repository.findByTeamsAndUsr(team, user)).get(0);
	}

	@Override
	public void save (Next next) throws Exception {
		repository.save(next);
	}

	@Override
	public void saveAll (List<Next> entities) throws Exception {
		for (Next entity : entities) {
			save(entity);
		}
	}

	@Override
	public void update (Next entity) {

	}

	@Override
	public void deleteByTeamAndUser (Teams team, Users user) {
		repository.deleteByTeamsAndUsr(team, user);
	}

	@Override
	public void deleteByUser (Users user) {
		repository.deleteAllByUsr(user);
	}

	public void save (List<Teams> teams, Tour tour, Users user) throws Exception {
		if (teams != null)
		for (Teams t : teams) {
			Next next = new Next();
			next.setTour(tour);
			next.setTeams(t);
			next.setUsr(user);
			save(next);
		}
	}

	@Override
	public void deleteByTeamsAndUser (List<Teams> teams, Users user) throws Exception {
		teams.forEach(team -> deleteByTeamAndUser(team, user));
	}

}
