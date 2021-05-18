package com.example.euro2020.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GroupsControllerTest {

	@Autowired
	private GroupsController groupsController;

	@Test
	public void test() {
		int count = groupsController.standingsService.findMapAll().size();

		assert(count == 6);
	}
}