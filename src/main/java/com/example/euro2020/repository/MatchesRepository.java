package com.example.euro2020.repository;

import com.example.euro2020.entity.Matches;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.entity.Tour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchesRepository extends CrudRepository<Matches, Long> {

	List<Matches> findMatchesByTour (Tour tour);

	List<Matches> findMatchesByTourAndTeamHome (Tour tour, Teams team);

	List<Matches> findTopByOrderByIdDesc ();
}
