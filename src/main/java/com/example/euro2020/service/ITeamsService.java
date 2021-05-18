package com.example.euro2020.service;

import com.example.euro2020.config.ConfigProperties;
import com.example.euro2020.entity.Next;
import com.example.euro2020.entity.Teams;

import java.util.List;

public interface ITeamsService extends IService<Teams> {

	Teams findByTeam (String team);

	List<Teams> findByTeam (List<String> team);

	List<String> getTeamsFromNext (List<Next> next);

	void save (ConfigProperties config, IPartyService partyService, IStandingsService standingsService) throws Exception;

	Teams findFirst ();

	List<Teams> getTeamsFromString (List<String> next);
}
