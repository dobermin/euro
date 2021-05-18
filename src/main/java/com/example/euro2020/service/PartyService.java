package com.example.euro2020.service;

import com.example.euro2020.entity.Party;
import com.example.euro2020.repository.PartyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PartyService implements IPartyService {

	private final PartyRepository repository;

	public PartyService (PartyRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Party> findAll() {
		List<Party> list = new ArrayList<>((List<Party>) repository.findAll());
		list.sort(Comparator.comparing(Party::getId));
		list.forEach(
			x -> x.getTeams().sort(Comparator.comparing(o -> o.getStanding().getPosition()))
		);
		return list;
	}

	@Override
	public Party findById (Long id) {
		return new ArrayList<>((List<Party>) repository.findAllById(Collections.singleton(id))).get(0);
	}

	@Override
	public void save (Party Party) {
		repository.save(Party);
	}

	@Override
	public void saveAll (List<Party> entities) {

	}

	@Override
	public void update (Party entity) {

	}


}
