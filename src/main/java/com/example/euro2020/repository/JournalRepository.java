package com.example.euro2020.repository;

import com.example.euro2020.entity.Journal;
import com.example.euro2020.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalRepository extends CrudRepository<Journal, Long> {

	@Query("SELECT h FROM Journal h WHERE h.id IN (SELECT MAX(h.id) FROM h GROUP BY h.usr HAVING COUNT(h) > 1)")
	List<Journal> findAllByDate ();

	@Query("SELECT h FROM Journal h WHERE h.id = (SELECT MAX (h.id) FROM h WHERE h.usr = :user)")
	List<Journal> findByUser (Users user);
}
