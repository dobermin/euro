package com.example.euro2020.rest.controller;

import com.example.euro2020.entity.Users;
import com.example.euro2020.rest.dto.Registered;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestRegisteredController extends RestMainController {

	@GetMapping(value = "/registered")
	public ResponseEntity<?> registered () {
		List<Users> users = usersService.findAll();

		List<String> usersList = new ArrayList<>();
		Registered registereds = new Registered();
		users.forEach(
			u -> usersList.add(String.format("%s %s", u.getFirstName(), u.getLastName()))
		);
		registereds.setUsers(usersList);

		return ResponseEntity.ok(registereds);
	}
}
