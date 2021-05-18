package com.example.euro2020.service;

import com.example.euro2020.entity.Navigation;
import com.example.euro2020.repository.NavigationRepository;
import com.example.euro2020.security.model.enums.Roles;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NavigationService implements INavigationService {

	private final NavigationRepository repository;

	public NavigationService (NavigationRepository repository) {
		this.repository = repository;
	}

	@Override
	public HashMap<String, List<Navigation>> findAllUser () {
		List<Navigation> list =
			new ArrayList<>(repository.findAllByUserOrderBySectionAscPositionAscTitleAsc(Roles.USER));

		return MySort(list);
	}

	@Override
	public HashMap<String, List<Navigation>> findAllAdmin () {
		List<Navigation> list = new ArrayList<>(repository.findAllByAdmin());

		return MySort(list);
	}

	private HashMap<String, List<Navigation>> MySort (List<Navigation> list) {

		HashMap<String, List<Navigation>> l = new HashMap<>();
		list.stream()
			.map(Navigation::getSection)
			.distinct()
			.collect(Collectors.toList())
			.forEach(
				x -> {
					List<Navigation> s = list.stream().
						filter(f -> f.getSection().equals(x))
						.collect(Collectors.toList());
					l.put(x, s);
				}
			);
		return l;
	}
}
