package com.example.euro2020.repository;

import com.example.euro2020.entity.Player;
import com.example.euro2020.entity.Teams;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayersRepository extends CrudRepository<Player, Long> {

	List<Player> findAllByOrderByTeamsAsc ();

	List<Player> findByTeamsTeamsAndDisplayTrueOrderByPlayerAsc (String team);

	List<Player> findByPlayer (String player);

	List<Player> findByTeamsAndDisplayTrueOrderByPlayerAsc (Teams team);
}
