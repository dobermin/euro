package com.example.euro2020.security.repository;

import com.example.euro2020.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersSecurityRepository extends JpaRepository<Users, Long>{
	Optional<Users> findByLogin(String login);
	Boolean existsByLogin(String login);
}
