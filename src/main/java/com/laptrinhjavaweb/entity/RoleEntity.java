package com.laptrinhjavaweb.entity;

import com.laptrinhjavaweb.annotation.Entity;

@Entity
public class RoleEntity extends BaseEntity {
	
	private String name;
	private String code;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
