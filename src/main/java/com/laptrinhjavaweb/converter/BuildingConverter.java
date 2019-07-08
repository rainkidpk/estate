package com.laptrinhjavaweb.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.paging.PageRequest;
import com.laptrinhjavaweb.repository.IRentAreaRepository;
import com.laptrinhjavaweb.repository.impl.RentAreaRepository;

public class BuildingConverter {

	//@Inject
	private IRentAreaRepository rentAreaRepository = new RentAreaRepository();

	public BuildingDTO converterToDto(BuildingEntity buildingEntity) {
		ModelMapper modelMapper = new ModelMapper();
		BuildingDTO result = modelMapper.map(buildingEntity, BuildingDTO.class);

		Map<String, Object> condition = new HashMap<>();
		condition.put("buildingid", buildingEntity.getId());

		/*
		 * List<RentAreaEntity> rentAreaEntities =
		 * rentAreaRepository.findAll(condition,new PageRequest(null, null, null));
		 * List<String> rentAreas = new ArrayList<>(); for (RentAreaEntity item :
		 * rentAreaEntities) { rentAreas.add(item.getValue()); } if(rentAreas.size() >
		 * 0) { result.setRentArea(StringUtils.join(rentAreas, ",")); }
		 */
		List<String> rentAreas = rentAreaRepository.findAll(condition, new PageRequest(null, null, null)).stream()
				.map(RentAreaEntity::getValue).collect(Collectors.toList());
		if (rentAreas.size() > 0) {
			result.setRentArea(StringUtils.join(rentAreas, ","));
		}
		
		if (StringUtils.isNotBlank(buildingEntity.getType())) {
			result.setBuildingTypes(buildingEntity.getType().split(","));
		}

		return result;
	}

	public BuildingEntity converterToEntity(BuildingDTO buildingDTO) {
		ModelMapper modelMapper = new ModelMapper();
		BuildingEntity result = modelMapper.map(buildingDTO, BuildingEntity.class);
		if (StringUtils.isNotBlank(buildingDTO.getNumberOfBasement())) {
			result.setNumberOfBasement(Integer.parseInt(buildingDTO.getNumberOfBasement()));
		}
		if (StringUtils.isNotBlank(buildingDTO.getBuildingArea())) {
			result.setBuildingArea(Integer.parseInt(buildingDTO.getBuildingArea()));
		}

		return result;
	}
}
