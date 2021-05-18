package com.example.euro2020.repository;

import com.example.euro2020.entity.Users;
import com.example.euro2020.security.model.enums.Status;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

//	@Query("SELECT u FROM Users u WHERE LOWER(u.status) = LOWER(:#{#status?.toString()})")
//	@Query("SELECT u FROM Users u WHERE u.status = :login")
//	@Query("SELECT u FROM Users u WHERE u.login = ?")
	List<Users> findByStatus(Status status);
	@Query("SELECT u FROM Users u WHERE u.display = ?")
	List<Users> findByDisplay(boolean bool);

	@Query("SELECT u FROM Users u WHERE u.login = ?")
	 List<Users> findByLogin(String login);
}