package com.laptrinhjavaweb.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.repository.IBuildingRepository;
import com.laptrinhjavaweb.repository.impl.BuildingRepository;
import com.laptrinhjavaweb.service.IBuildingService;

public class BuildingService implements IBuildingService {
	
	private IBuildingRepository iBuildingRepository;
	
	 public BuildingService() {
		iBuildingRepository = new BuildingRepository();
	}

	@Override
	public BuildingDTO save(BuildingDTO buildingDTO) {
		BuildingConverter buildingConverter = new BuildingConverter();
		BuildingEntity buildingEntity = buildingConverter.converterToEntity(buildingDTO);
		buildingEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		Long id = iBuildingRepository.insert(buildingEntity);
		return null;
	}

	@Override
	public BuildingDTO update(BuildingDTO buildingDTO,Long id) {
		BuildingConverter buildingConverter = new BuildingConverter();
		BuildingEntity buildingEntity = buildingConverter.converterToEntity(buildingDTO);
		buildingEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		iBuildingRepository.update(buildingEntity);
		return null;
	}

	@Override
	public BuildingDTO delete(BuildingDTO buildingDTO,Long id) {
		BuildingConverter buildingConverter = new BuildingConverter();
		BuildingEntity buildingEntity = buildingConverter.converterToEntity(buildingDTO);
		iBuildingRepository.delete(id);
		return null;
	}

	@Override
	public BuildingDTO findById(Long id) {
		BuildingConverter buildingConverter = new BuildingConverter();
		BuildingEntity buildingEntity = iBuildingRepository.findById(id);
		BuildingDTO buildingDTO = buildingConverter.converterToDto(buildingEntity);
		return buildingDTO;
		
	}

	@Override
	public List<BuildingDTO> findAll(Map<String, Object> properties, Pageble pageRequest) {
		BuildingConverter buildingConverter = new BuildingConverter();
		BuildingDTO buildingDTO = new BuildingDTO();
		List<BuildingDTO> buildingDTOs = new ArrayList<BuildingDTO>();
		List<BuildingEntity> buildingEntities = iBuildingRepository.findAll(properties, pageRequest);
		for(BuildingEntity item : buildingEntities) {
			
			buildingDTO = buildingConverter.converterToDto(item);
			buildingDTOs.add(buildingDTO);
		}
		return buildingDTOs;
	}
	
}
