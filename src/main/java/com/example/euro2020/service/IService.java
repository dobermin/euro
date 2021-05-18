package com.example.euro2020.service;

import com.example.euro2020.entity.MyEntity;

import java.util.List;

public interface IService<T extends MyEntity> {

	List<T> findAll();
	T findById(Long id);

	void save(T entity) throws Exception;
	void saveAll(List<T> entities) throws Exception;

	void update(T entity);
}
