package com.example.euro2020.service;

import com.example.euro2020.config.ConfigProperties;
import com.example.euro2020.entity.Matches;
import com.example.euro2020.entity.Teams;
import com.example.euro2020.entity.Tour;
import org.jsoup.select.Elements;

import java.util.List;

public interface IMatchesService extends IService<Matches> {

	Object findMatchesByTourAndTeamHome (Tour tour, Teams team);

	Matches findLastMatch ();

	Matches findActualMatch (Long time);

	Long getIdActualMatch (Long time);

	List<Matches> findByTour (Tour tour);

	List<Matches> findActualMatches (Long time);

	Elements getInfo (ConfigProperties config);

	void setMatches (Elements elements, ITourService tourService, ITeamsService teamsService, ConfigProperties config);

	List<Matches> setPrognosis (List<Matches> list, ConfigProperties config);

	List<Teams> findTeamsByTour (Tour tour);
}
