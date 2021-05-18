package com.example.euro2020.repository;

import com.example.euro2020.entity.Standings;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandingsRepository extends CrudRepository<Standings, Long> {

	List<Standings> findAll (Sort orderByGroupsAsc);

	Standings findByTeamTeams (String team);
}
