package com.example.euro2020.service;

import com.example.euro2020.entity.Matches;
import com.example.euro2020.entity.Tour;

import java.util.List;

public interface ITourService extends IService<Tour> {

	List<Tour> findActual (List<Matches> matches);

	List<Tour> findActualForecast (List<Matches> matches, Long time);

	Tour findByTour (String tour);

	Tour findByTime (List<Matches> matches, Long time);
}
