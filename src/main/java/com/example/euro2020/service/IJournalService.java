package com.example.euro2020.service;

import com.example.euro2020.entity.Journal;
import com.example.euro2020.entity.Users;

public interface IJournalService extends IService<Journal> {
	Journal findByUser (Users user);
}