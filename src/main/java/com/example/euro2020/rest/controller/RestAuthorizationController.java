package com.example.euro2020.rest.controller;

import com.example.euro2020.entity.Users;
import com.example.euro2020.objects.User;
import com.example.euro2020.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthorizationController extends RestMainController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	public RestAuthorizationController (AuthenticationManager authenticationManager,
	                                    JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@PostMapping(value = "/authorization")
	public ResponseEntity<?> authorization (@RequestBody User user) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(),
			user.getPassword()));
		Users users = usersService.findByLogin(user.getLogin());
		String token = jwtTokenProvider.createToken(user.getLogin(), users.getRoles().name());
		return ResponseEntity.ok(token);
	}
}
