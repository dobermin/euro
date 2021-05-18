package com.example.euro2020.service;

import com.example.euro2020.entity.Users;
import com.example.euro2020.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.euro2020.security.model.enums.Status.ACTIVE;

@Service
public class UsersService implements IUsersService {

	private final UsersRepository usersRepository;

	public UsersService (UsersRepository repository) {
		this.usersRepository = repository;
	}

	@Override
	public List<Users> findByStatus () {
		return new ArrayList<>(usersRepository.findByStatus(ACTIVE));
	}

	@Override
	public List<Users> findAll () {
		return usersRepository.findAll().stream()
			.filter(Users::isDisplay)
			.sorted(Comparator.comparing(Users::getLastName))
			.collect(Collectors.toList());
	}

	@Override
	public Users findById (Long id) {
		return new ArrayList<>(usersRepository.findAllById(Collections.singleton(id))).get(0);
	}

	@Override
	public List<Users> findWithoutUser (Users user) {
		return findAll().stream()
			.filter(u -> !u.equals(user))
			.collect(Collectors.toList());
	}

	@Override
	public Users findByLogin (String login) {
		try {
			return new ArrayList<>(usersRepository.findByLogin(login)).get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Users findByFullName (String fullName) {
		List<Users> list = findAll();
		try {
			return list.stream()
				.filter(name -> (name.getFirstName() + " " + name.getLastName()).equals(fullName))
				.collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void save (Users users) throws Exception {
		usersRepository.save(users);
//		dof();
	}

	private void dof () throws Exception {
		throw new Exception("Ошибка");
	}
	@Override
	public void saveAll (List<Users> entities) throws Exception {
		for (Users entity : entities) {
			save(entity);
		}
	}

	@Override
	public void update (Users entity) {

	}


}
