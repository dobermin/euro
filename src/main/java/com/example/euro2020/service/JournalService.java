package com.example.euro2020.service;

import com.example.euro2020.entity.Journal;
import com.example.euro2020.entity.Users;
import com.example.euro2020.repository.JournalRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JournalService implements IJournalService {

	private final JournalRepository journalRepository;

	public JournalService (JournalRepository journalRepository) {
		this.journalRepository = journalRepository;
	}

	@Override
	public List<Journal> findAll () {
		return new ArrayList<>(journalRepository.findAllByDate());
	}

	@Override
	public Journal findById (Long id) {
		return journalRepository.findById(id).orElse(new Journal());
	}

	@Override
	public void save (Journal entity) throws Exception {
		journalRepository.save(entity);
	}

	@Override
	public void saveAll (List<Journal> entities) throws Exception {
		for (Journal entity : entities) {
			save(entity);
		}
	}

	@Override
	public void update (Journal entity) {

	}

	@Override
	public Journal findByUser (Users user) {
		return new ArrayList<>(journalRepository.findByUser(user)).get(0);
	}
}
