package com.example.euro2020.repository;

import com.example.euro2020.entity.Next;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.entity.Tour;
import com.example.euro2020.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NextRepository extends CrudRepository<Next, Long> {

	List<Next> findByTourAndUser (Tour tour, Users user);

	List<Next> findByTeamAndUser (Teams team, Users user);

	void deleteByTeamAndUser (Teams team, Users user);

	void deleteByUser (Users user);

	void deleteAllByUser (Users user);
}
