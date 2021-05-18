package com.example.euro2020.service;

import com.example.euro2020.entity.Placing;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.entity.Users;
import com.example.euro2020.repository.PlacingRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlacingService implements IPlacingService {

	private final PlacingRepository placingRepository;

	public PlacingService (PlacingRepository repository) {
		this.placingRepository = repository;
	}

	@Override
	public List<Placing> findAll() {
		List<Placing> list = new ArrayList<>( (List<Placing>) placingRepository.findAll());
		list.sort(Comparator.comparing(o -> o.getTeam().getTeams()));
		return list;
	}

	@Override
	public Placing findById (Long id) {
		return new ArrayList<>((List<Placing>) placingRepository.findAllById(Collections.singleton(id))).get(0);
	}

	@Override
	public void save (Placing placing) throws Exception {
		placingRepository.save(placing);
	}

	@Override
	public List<Teams> saveByTeamAndPosition(List<Teams> country, List<String> positions, Users user) throws Exception {
		List<Teams> list = new ArrayList<>();
		for (int i = 0; i < country.size(); i++) {
			Teams teams = country.get(i);
			String position = positions.get(i);
			if (!position.isEmpty()) {
				Placing placing = findByTeamAndUser(teams, user);
				placing.setPosition(Integer.parseInt(position));
				placing.setUser(user);
				placing.setTeam(teams);
				save(placing);
			} else {
				deleteByTeamAndUser(teams, user);
				list.add(teams);
			}
		}
		return list;
	}

	@Override
	public void saveAll (List<Placing> entities) throws Exception {
		for (Placing entity : entities) {
			save(entity);
		}
	}

	@Override
	public void update (Placing entity) {
		placingRepository.update(entity.getPosition(), entity.getId());
	}

	@Override
	public Placing findByTeamAndUser (Teams team, Users user) {
		try {
			return new ArrayList<>(placingRepository.findByTeamAndUser(team, user)).get(0);
		} catch (Exception e) {
			return new Placing();
		}
	}

	@Override
	public List<Placing> findByUser (Users user) {
		try {
			List<Placing> list =  new ArrayList<>(placingRepository.findByUser(user));
			list.sort(Comparator.comparing(Placing::getPosition));
			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public void deleteByTeamAndUser (Teams team, Users user) {
		placingRepository.deleteByTeamAndUser(team, user);
	}

	@Override
	public Map<String, Placing> findByUserTeam (Users user) {
		return findByUser(user).stream()
			.collect(Collectors.toMap(s -> s.getTeam().getTeams(), p -> p));
	}

	@Override
	public Integer getPoints (Users user, ConfigService configService) {
		Integer teamPlacing = 0;
		if (
			configService.getTimeNow() > configService.getCupGroupsEnd() &&
				configService.getTimeNow() < configService.getCupEightStart()
		) {
			List<Placing> placings = findByUser(user);
			for (Placing placing : placings) {
				int position = placing.getPosition();
				if (position == placing.getTeam().getStanding().getPosition())
					teamPlacing++;
			}
		}
		return teamPlacing;
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
