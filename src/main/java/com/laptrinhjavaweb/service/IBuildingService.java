package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.paging.Pageble;

public interface IBuildingService {
	BuildingDTO save(BuildingDTO newBuilding);
	BuildingDTO update(BuildingDTO buildingDTO,Long id);
	BuildingDTO delete(BuildingDTO buildingDTO,Long id);
	BuildingDTO findById(Long id);
	List<BuildingDTO> findAll(BuildingSearchBuilder buildingSearchBuilder, Pageble pageRequest);
}
