package com.example.euro2020.service;

import com.example.euro2020.entity.Rating;
import com.example.euro2020.objects.DayPoints;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class PrognosisServiceTest {

	@Autowired
	RatingService ratingService;
	@Autowired
	PrognosisService prognosisService;
	@Autowired
	ConfigService configService;
	List<Rating> ratings;

	@BeforeEach
	void setUp () {
		ratings = ratingService.findAll();
	}

	@AfterEach
	void tearDown () {
	}

	@Test
	void getPointsDay () {
		List<DayPoints> list = prognosisService.getPointsDay(ratings, configService);
		Assertions.assertEquals(4, list.get(0).getPoints());
		Assertions.assertEquals(12, list.get(0).getMax());

		Assertions.assertEquals(4, list.get(1).getPoints());
		Assertions.assertEquals(12, list.get(1).getMax());

		Assertions.assertEquals(1, list.get(2).getPoints());
		Assertions.assertEquals(12, list.get(2).getMax());
	}
}