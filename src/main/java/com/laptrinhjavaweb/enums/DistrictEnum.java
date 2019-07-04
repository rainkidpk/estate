package com.laptrinhjavaweb.enums;

public enum DistrictEnum {
	QUAN_1("Quận 1"),
	QUAN_2("Quận 2"),
	QUAN_3("Quận 3");
	
	private String value;
	
	DistrictEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	
	
	
	
}
