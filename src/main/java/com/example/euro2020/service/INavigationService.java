package com.example.euro2020.service;

import com.example.euro2020.entity.Navigation;

import java.util.HashMap;
import java.util.List;

public interface INavigationService {

	HashMap<String, List<Navigation>> findAllUser ();
	HashMap<String, List<Navigation>> findAllAdmin ();
}