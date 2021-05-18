package com.example.euro2020.security.repository;

import com.example.euro2020.security.model.Role;
import com.example.euro2020.security.model.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(Roles name);
}
