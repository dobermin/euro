package com.example.euro2020.rest;

import com.example.euro2020.objects.User;
import com.example.euro2020.security.config.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyRestController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping(value = "/api/authorization")
	public ResponseEntity<?> auth1(@RequestBody User user) {

		System.out.println(user);
		String jwt = "null";
		if (user.getLogin() != null) {

			Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
					user.getLogin(),
					user.getPassword()));
//
			SecurityContextHolder.getContext().setAuthentication(authentication);
			jwt = jwtUtils.generateJwtToken(authentication);
		}

		return ResponseEntity.ok(jwt);
	}
}
