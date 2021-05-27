package com.example.euro2020.security.service;

import com.example.euro2020.entity.Users;
import com.example.euro2020.security.model.enums.Status;
import com.example.euro2020.security.repository.UsersSecurityRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UsersSecurityRepository usersRepository;

	public UserDetailsServiceImpl (UsersSecurityRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = usersRepository.findByLogin(username)
			.filter(u -> u.getStatus() == Status.ACTIVE)
			.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return UserDetailsImpl.build(user);
	}

}
