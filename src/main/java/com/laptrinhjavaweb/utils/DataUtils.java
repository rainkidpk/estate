package com.laptrinhjavaweb.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.laptrinhjavaweb.enums.BuildingTypeEnum;
import com.laptrinhjavaweb.enums.DistrictEnum;

public class DataUtils {
	
	public static Map<String, String> getBuildingType() {
		Map<String, String> result = new HashMap<String, String>();
		Stream.of(BuildingTypeEnum.values()).forEach(item -> {
			result.put(item.name(), item.getValue());
		});
		return result;
	}
	
	public static Map<String, String> getDistrict() {
		Map<String, String> result = new HashMap<String, String>();
		Stream.of(DistrictEnum.values()).forEach(item -> {
			result.put(item.name(), item.getValue());
		});
		return result;
	}
}
