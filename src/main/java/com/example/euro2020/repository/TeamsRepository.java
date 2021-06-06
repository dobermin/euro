package com.example.euro2020.repository;

import com.example.euro2020.entity.Teams;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamsRepository extends CrudRepository<Teams, Long> {

	List<Teams> findAllByOrderByTeamsAsc ();

	@Query("SELECT t FROM Teams t WHERE t.teams = :team")
	List<Teams> findAllByTeams (String team);

	List<Teams> findTopByOrderByTeamsAsc ();
}
