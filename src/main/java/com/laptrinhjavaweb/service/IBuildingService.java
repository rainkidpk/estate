package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.BuildingDTO;

public interface IBuildingService {
	BuildingDTO save(BuildingDTO newBuilding);
	BuildingDTO update(BuildingDTO buildingDTO,Long id);
	BuildingDTO delete(BuildingDTO buildingDTO,Long id);
	BuildingDTO findById(Long id);
}
