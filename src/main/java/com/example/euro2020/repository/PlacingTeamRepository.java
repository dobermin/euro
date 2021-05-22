package com.example.euro2020.repository;

import com.example.euro2020.entity.PlacingTeam;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.entity.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlacingTeamRepository extends CrudRepository<PlacingTeam, Long> {

	List<PlacingTeam> findByUsr (Users user);
	List<PlacingTeam> findByTeamsAndUsr(Teams team, Users user);

	void deleteByTeamsAndUsr (Teams team, Users user);

	@Modifying
	@Query("UPDATE PlacingTeam p set p.position = :position WHERE p.id = :id")
	void update (Integer position, Long id);
}