package com.example.euro2020.security.service;

import com.example.euro2020.security.model.enums.Roles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SecurityService {

	public boolean hasRole(Roles roles) {
		Collection<? extends GrantedAuthority> grantedAuthorities =
			((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities();
		for (GrantedAuthority g : grantedAuthorities)
			if (g.getAuthority().equals(roles.name()))
				return true;
		return false;
	}
}
