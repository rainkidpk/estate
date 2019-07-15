package com.laptrinhjavaweb.repository;

import com.laptrinhjavaweb.entity.RentAreaEntity;

public interface IRentAreaRepository extends GenericJDBC<RentAreaEntity> {
	
	void deleteByBuilding(long id);
}
