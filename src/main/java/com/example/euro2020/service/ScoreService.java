package com.example.euro2020.service;

import com.example.euro2020.entity.Matches;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService extends MatchesService {

	public List<Matches> findActual (ConfigService configService) {
		return findAll().stream()
			.filter(m -> m.getTimestamp().getTime() < configService.getTimeNow())
			.filter(m -> m.getScoreHome().isEmpty())
			.collect(Collectors.toList());
	}

	public List<Matches> getScore (ConfigService configService) {
		List<Matches> matchesList = new ArrayList<>();
		findActual(configService).forEach(
			x -> matchesList.add(findById(x.getId()))
		);
		return matchesList;
	}

	public void saveAll (List<Matches> matches) {
		for (Matches match : matches) {
			Matches m = findById(match.getId());
			try {
				m.setMainHome(match.getMainHome());
				m.setMainAway(match.getMainAway());
				m.setScoreHome(match.getScoreHome());
				m.setScoreAway(match.getScoreAway());
				m.setNext(match.getNext());
				m.setOvertime(match.getOvertime());
				save(m);
			} catch (Exception ignored) {
			}
		}
	}
}
