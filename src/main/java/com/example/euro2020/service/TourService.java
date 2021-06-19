package com.example.euro2020.service;

import com.example.euro2020.entity.Matches;
import com.example.euro2020.entity.Tour;
import com.example.euro2020.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TourService implements ITourService {

	@Autowired
	private TourRepository repository;

	@Override
	public List<Tour> findAll() {
		return new ArrayList<>((List<Tour>) repository.findAll()).stream().sorted(Comparator.comparing(Tour::getId)).collect(Collectors.toList());
	}

	@Override
	public Tour findById (Long id) {
		return new ArrayList<>((List<Tour>) repository.findAllById(Collections.singleton(id))).get(0);
	}

	@Override
	public void save (Tour tour) throws Exception {
		repository.save(tour);
	}

	@Override
	public List<Tour> findActual (List<Matches> matches) {
		return matches.stream()
			.filter(s -> s.getTeamHome() != null)
			.filter(s -> s.getTeamAway() != null)
			.map(Matches::getTour)
			.distinct()
			.sorted(Comparator.comparing(Tour::getId))
			.collect(Collectors.toList());
	}

	@Override
	public List<Tour> findActualForecast (List<Matches> matches, Long time) {
		try {
			Set<Tour> set = new HashSet<>();
			matches.stream()
				.filter(f -> f.getTimestamp().getTime() <= time)
				.map(l -> set.add(l.getTour())).collect(Collectors.toList());

			List<Tour> list = new ArrayList<>(set);
			list.sort(Comparator.comparing(Tour::getId));
			return list;
		} catch (Exception e) {
			return findAll();
		}
	}

	@Override
	public Tour findByTour (String tour) {
		return new ArrayList<>(repository.findByTour(tour)).get(0);
	}

	@Override
	public Tour findByTime (List<Matches> matches, Long time) {
		try {
			return Objects.requireNonNull(matches.stream()
				.filter(t -> t.getTimestamp().getTime() > time)
				.findFirst().orElse(null)).getTour();
		} catch (Exception e) {
			try {
				return matches.get(matches.size() - 1).getTour();
			} catch (Exception exception) {
				return new Tour();
			}
		}
	}

	@Override
	public void saveAll (List<Tour> entities) throws Exception {
		for (Tour entity : entities) {
			save(entity);
		}
	}

	@Override
	public void update (Tour entity) {

	}

}
