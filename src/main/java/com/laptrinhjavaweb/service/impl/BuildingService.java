package com.laptrinhjavaweb.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.repository.IBuildingRepository;
import com.laptrinhjavaweb.repository.IRentAreaRepository;
import com.laptrinhjavaweb.repository.impl.BuildingRepository;
import com.laptrinhjavaweb.repository.impl.RentAreaRepository;
import com.laptrinhjavaweb.service.IBuildingService;

public class BuildingService implements IBuildingService {
	
	//@Inject
	private IBuildingRepository buildingRepository = new BuildingRepository();
	
	//@Inject
	private BuildingConverter buildingConverter = new BuildingConverter();
	
	//@Inject
	private IRentAreaRepository rentAreaRepository = new RentAreaRepository();

	 /*public BuildingService() {
		if(buildingRepository == null) {
			buildingRepository = new BuildingRepository();
		}
		if(buildingConverter == null) {
			buildingConverter = new BuildingConverter();
		}
	}*/


	@Override
	public BuildingDTO save(BuildingDTO buildingDTO) {
		BuildingEntity buildingEntity = buildingConverter.converterToEntity(buildingDTO);
		buildingEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		buildingEntity.setCreatedBy("");
		buildingEntity.setType(StringUtils.join(buildingDTO.getBuildingTypes(), ","));
		Long id = buildingRepository.insert(buildingEntity);
		
		//save rentarea
		for (String item : buildingDTO.getRentArea().split(",")) {
			RentAreaEntity rentArea = new RentAreaEntity();
			rentArea.setValue(item);
			rentArea.setBuildingId(id);
			rentAreaRepository.insert(rentArea);
		}
		
		
		return buildingConverter.converterToDto(buildingRepository.findById(id));
	}

	@Override
	public BuildingDTO update(BuildingDTO buildingDTO, long id) {
		BuildingEntity oldBuilding = buildingRepository.findById(id);
		BuildingEntity newBuilding = buildingConverter.converterToEntity(buildingDTO);
		newBuilding.setCreatedBy(oldBuilding.getCreatedBy());
		newBuilding.setCreatedDate(oldBuilding.getCreatedDate());	
		updateRentArea(buildingDTO.getRentArea(),id);
		newBuilding.setType(StringUtils.join(buildingDTO.getBuildingTypes(), ","));
		buildingRepository.update(newBuilding);
		return null;
	}

	private void updateRentArea(String rentArea, long buildingId) {
		//delete rent area by buildingid
		rentAreaRepository.deleteByBuilding(buildingId);
		//insert rent area
		for (String item : rentArea.split(",")) {
			RentAreaEntity rentAreaEntity = new RentAreaEntity();
			rentAreaEntity.setBuildingId(buildingId);
			rentAreaEntity.setValue(item);
			rentAreaRepository.insert(rentAreaEntity);
		}
	}

	@Override
	public void delete(Long[] ids) {
		for (long item : ids) {
			rentAreaRepository.deleteByBuilding(item);
			buildingRepository.delete(item);
		}
	}

	@Override
	public BuildingDTO findById(Long id) {
		BuildingConverter buildingConverter = new BuildingConverter();
		BuildingEntity buildingEntity = buildingRepository.findById(id);
		BuildingDTO buildingDTO = buildingConverter.converterToDto(buildingEntity);
		return buildingDTO;

	}

	@Override
	public List<BuildingDTO> findAll(BuildingSearchBuilder buildingSearchBuilder, Pageble pageRequest) {		
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(buildingSearchBuilder, pageRequest);
		List<BuildingDTO> result = buildingEntities.stream().map(item -> buildingConverter.converterToDto(item))
				.collect(Collectors.toList());
		return result;
	}

	@Override
	public BuildingDTO findById(long id) {		
		return buildingConverter.converterToDto(buildingRepository.findById(id));
	}

	@Override
	public int getTotalItem(BuildingSearchBuilder builder) {	
		return buildingRepository.countByProperty(builder);
	}

	

}
