package com.laptrinhjavaweb.jdbc;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.laptrinhjavaweb.enums.BuildingTypeEnum;

public class OperationMain {
	public static void main(String[] args) {
		
		Map<String, String> buildingTypes = new HashMap<>();
		Stream.of(BuildingTypeEnum.values()).forEach(item -> {
			buildingTypes.put(item.name(), item.getValue());
		});
		System.out.println(buildingTypes);
	}
}
