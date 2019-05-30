package com.laptrinhjavaweb.repository;

import java.util.List;

import com.laptrinhjavaweb.dto.BuildingDTO;

public interface GenericJDBC<T> {
	
	
	List<T> query(String sql, Object... parameters);
	//void update(String sql, Object... parameters);
	//Long insert(String sql, Object... parameters);
	Long insert(Object object);
	void update(Object object);
	void delete(Object object);
	T findById(long id);
	
}
