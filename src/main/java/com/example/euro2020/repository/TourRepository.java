package com.example.euro2020.repository;

import com.example.euro2020.entity.Tour;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends CrudRepository<Tour, Long> {

	List<Tour> findByTour (String tour);

	@Query("SELECT DISTINCT tour FROM Matches ")
	List<Tour> findActual ();

//	@Query("SELECT DISTINCT tour FROM Matches WHERE Matches.timestamp <= :time")
//	List<Tour> findActualForecast (Timestamp time);
//	@Query("SELECT DISTINCT tour FROM Matches WHERE Matches.timestamp > :time")
//	List<Tour> findByTime (Timestamp time);
}
