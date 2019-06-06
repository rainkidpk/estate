package com.laptrinhjavaweb.service;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.paging.Pageble;

public interface IBuildingService {
	BuildingDTO save(BuildingDTO newBuilding);
	BuildingDTO update(BuildingDTO buildingDTO,Long id);
	BuildingDTO delete(BuildingDTO buildingDTO,Long id);
	BuildingDTO findById(Long id);
	List<BuildingDTO> findAll(Map<String,Object> properties, Pageble pageRequest);
}
