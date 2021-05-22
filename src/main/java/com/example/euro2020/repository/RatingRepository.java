package com.example.euro2020.repository;

import com.example.euro2020.entity.Rating;
import com.example.euro2020.entity.Users;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {

	List<Rating> findAll (Sort orderByPointsAsc);

	List<Rating> findByUsr (Users user);
}
