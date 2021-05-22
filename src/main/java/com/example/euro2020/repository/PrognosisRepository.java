package com.example.euro2020.repository;

import com.example.euro2020.entity.Prognosis;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.entity.Tour;
import com.example.euro2020.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PrognosisRepository extends CrudRepository<Prognosis, Long> {

	@Query(value = "SELECT p FROM Prognosis p WHERE p.usr IN (SELECT u FROM Users u where u.display = true )")
	List<Prognosis> findAll();
	List<Prognosis> findAllByUsrAndMatchTour(Users user, Tour tour);
	List<Prognosis> findByUsr (Users user);
	List<Prognosis> findByUsrAndMatch_Tour (Users user, Tour tour);
	List<Prognosis> findByMatchId (Long id);
	List<Prognosis> findByUsrAndMatch_TourAndMatch_TimestampLessThanEqual (Users usr, Tour match_tour, Timestamp match_timestamp);
	List<Prognosis> findByUsrAndMatch_TourAndMatch_TeamHomeOrMatch_TeamAway(Users user, Tour tour, Teams teamHome, Teams teamAway);
}
