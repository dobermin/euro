package com.example.euro2020.repository;

import com.example.euro2020.entity.Prognosis;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.entity.Tour;
import com.example.euro2020.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrognosisRepository extends CrudRepository<Prognosis, Long> {

	List<Prognosis> findByUsr (Users user);

	@Query(value = "SELECT p FROM Prognosis p WHERE p.usr = :user AND p.match.tour = :tour ORDER BY p.id ASC ")
	List<Prognosis> findAllByUsrAndMatchTour (Users user, Tour tour);

	List<Prognosis> findByMatchId (Long id);

	List<Prognosis> findByUsrAndMatch_TourAndMatch_TeamHomeOrMatch_TeamAway (Users user, Tour tour, Teams teamHome
		, Teams teamAway);
}
