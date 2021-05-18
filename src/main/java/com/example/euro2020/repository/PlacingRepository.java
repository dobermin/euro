package com.example.euro2020.repository;

import com.example.euro2020.entity.Placing;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.entity.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlacingRepository extends CrudRepository<Placing, Long> {

	List<Placing> findByUser (Users user);
	List<Placing> findByTeamAndUser(Teams team, Users user);

	void deleteByTeamAndUser (Teams team, Users user);

	void deleteAllByUser (Users users);

	@Modifying
	@Query("UPDATE Placing p set p.position = :position WHERE p.id = :id")
	void update (Integer position, Long id);
}