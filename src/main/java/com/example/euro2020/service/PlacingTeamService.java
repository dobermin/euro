package com.example.euro2020.service;

import com.example.euro2020.entity.PlacingTeam;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.entity.Users;
import com.example.euro2020.repository.PlacingTeamRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlacingTeamService implements IPlacingTeamService {

	private final PlacingTeamRepository placingRepository;

	public PlacingTeamService (PlacingTeamRepository repository) {
		this.placingRepository = repository;
	}

	@Override
	public List<PlacingTeam> findAll() {
		List<PlacingTeam> list = new ArrayList<>( (List<PlacingTeam>) placingRepository.findAll());
		list.sort(Comparator.comparing(o -> o.getTeams().getTeams()));
		return list;
	}

	@Override
	public PlacingTeam findById (Long id) {
		return new ArrayList<>((List<PlacingTeam>) placingRepository.findAllById(Collections.singleton(id))).get(0);
	}

	@Override
	public void save (PlacingTeam placing) throws Exception {
		placingRepository.save(placing);
	}

	@Override
	public List<Teams> saveByTeamAndPosition(List<Teams> country, List<String> positions, Users user) throws Exception {
		List<Teams> list = new ArrayList<>();
		for (int i = 0; i < country.size(); i++) {
			Teams teams = country.get(i);
			String position = positions.get(i);
			if (!position.isEmpty()) {
				PlacingTeam placing = findByTeamAndUser(teams, user);
				placing.setPosition(Integer.parseInt(position));
				placing.setUsr(user);
				placing.setTeams(teams);
				save(placing);
			} else {
				deleteByTeamAndUser(teams, user);
				list.add(teams);
			}
		}
		return list;
	}

	@Override
	public void saveAll (List<PlacingTeam> entities) throws Exception {
		for (PlacingTeam entity : entities) {
			save(entity);
		}
	}

	@Override
	public void update (PlacingTeam entity) {
		placingRepository.update(entity.getPosition(), entity.getId());
	}

	@Override
	public PlacingTeam findByTeamAndUser (Teams team, Users user) {
		try {
			return new ArrayList<>(placingRepository.findByTeamsAndUsr(team, user)).get(0);
		} catch (Exception e) {
			return new PlacingTeam();
		}
	}

	@Override
	public List<PlacingTeam> findByUser (Users user) {
		try {
			List<PlacingTeam> list =  new ArrayList<>(placingRepository.findByUsr(user));
			list.sort(Comparator.comparing(PlacingTeam::getPosition));
			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public List<PlacingTeam> findByUserAndPosition (Users user, int position) {
		try {
			List<PlacingTeam> list =  new ArrayList<>(placingRepository.findAllByUsrAndPositionLessThanEqualOrderByTeamsAsc(user, position));
			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public void deleteByTeamAndUser (Teams team, Users user) {
		placingRepository.deleteByTeamsAndUsr(team, user);
	}

	@Override
	public Map<String, PlacingTeam> findByUserTeam (Users user) {
		return findByUser(user).stream()
			.collect(Collectors.toMap(s -> s.getTeams().getTeams(), p -> p));
	}

	@Override
	public Integer getPoints (Users user, ConfigService configService) {
		Integer teamPlacingTeam = 0;
		if (
			configService.getTimeNow() > configService.getCupGroupsEnd() &&
				configService.getTimeNow() < configService.getCupEightStart()
		) {
			List<PlacingTeam> placings = findByUser(user);
			for (PlacingTeam placing : placings) {
				int position = placing.getPosition();
				if (position == placing.getTeams().getStanding().getPosition())
					teamPlacingTeam++;
			}
		}
		return teamPlacingTeam;
	}

	@Override
	public Map<Users, Integer> getPoints (List<Users> users, ConfigService configService) {
		Map<Users, Integer> map = new HashMap<>();

		users.forEach(
			u -> map.put(u, getPoints(u, configService))
		);

		return map;
	}
}
