package com.example.euro2020.service;

import com.example.euro2020.config.ConfigProperties;
import com.example.euro2020.entity.*;
import com.example.euro2020.objects.DayPoints;

import java.util.List;

public interface IPrognosisService extends IService<Prognosis> {

	List<Prognosis> findByUser (Users user);

	Prognosis findByPrognosis (Users user, Tour tour, Teams team);

	List<List<Prognosis>> getPrognosesBefore (Tour tour, Users user, Long idMatch, Long now,
	                                          ConfigService configService);

	List<List<Prognosis>> getPrognosesBefore (Tour tour, Users user, Long idMatch, String now,
	                                          ConfigService configService);

	List<Prognosis> findByTour (Users user, Tour tour);

	List<Prognosis> getPrognoses (Tour tour, Users user, Long idMatch, ConfigService configService);

	void save (Users user, Tour tourSelect, List<Matches> matches, List<String> prognoses,
	           List<Teams> countries, List<Teams> next, Long time) throws Exception;

	List<Prognosis> setPrognosis (List<Prognosis> list, ConfigProperties config);

	List<Prognosis> setPoints (List<Prognosis> list, ConfigService configService);

	List<Prognosis> findByUserAndTour (Users user, Tour tourSelect);

	List<DayPoints> getPointsDay (List<Rating> rating, ConfigService configService);
}
