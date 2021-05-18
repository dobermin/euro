package com.example.euro2020.service;

import com.example.euro2020.config.ConfigProperties;
import com.example.euro2020.entity.Party;
import com.example.euro2020.entity.Standings;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public interface IStandingsService extends IService<Standings> {

	List<Map<String, List<Standings>>> findMapAll ();

	void save (List<Standings> standings, ConfigProperties configProperties);

	void getStandings(List<Party> groups, ConfigProperties configProperties);

	Sort orderByGroupsAsc ();
}
