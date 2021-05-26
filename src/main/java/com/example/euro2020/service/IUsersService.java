package com.example.euro2020.service;

import com.example.euro2020.entity.Users;

import java.util.List;

public interface IUsersService extends IService<Users> {

	List<Users> findByStatus ();

	List<Users> findAllActive ();

	List<Users> findWithoutUser (Users user);

	Users findByLogin (String login);

	Users findByFullName (String fullName);

	void updateAll (List<Users> users);
}
