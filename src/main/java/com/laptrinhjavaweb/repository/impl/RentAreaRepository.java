package com.laptrinhjavaweb.repository.impl;

import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.repository.IRentAreaRepository;

public class RentAreaRepository extends AbstractJDBC<RentAreaEntity> implements IRentAreaRepository {

	@Override
	public void deleteByBuilding(long id) {
		String where = "WHERE buildingid = "+id+"";
		this.deleteByProperties(where);
		
	}	
	
	
}
