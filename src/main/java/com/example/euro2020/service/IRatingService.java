package com.example.euro2020.service;

import com.example.euro2020.config.ConfigProperties;
import com.example.euro2020.entity.Player;
import com.example.euro2020.entity.Rating;
import com.example.euro2020.entity.Users;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IRatingService extends IService<Rating> {

	Rating findByUser (Users user);

	Sort orderByPointsAsc();

	List<Player> getBombardiers(ConfigProperties configProperties);

	void getRatingAndSave (List<Users> users, List<Player> players, ConfigService configService);
}
