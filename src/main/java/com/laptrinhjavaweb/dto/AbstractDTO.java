package com.laptrinhjavaweb.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AbstractDTO<T> {
	
	private Long id;
	private String createdBy;
	private String modifiedBy;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private int maxPageItem = 10;
	private int page = 1;
	private String action;
	private List<T> listResults = new ArrayList<T>();
	private Long[] ids;
	
	
	
	public int getMaxPageItem() {
		return maxPageItem;
	}
	public void setMaxPageItem(int maxPageItem) {
		this.maxPageItem = maxPageItem;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public List<T> getListResults() {
		return listResults;
	}
	public void setListResults(List<T> listResults) {
		this.listResults = listResults;
	}
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	
	
	
}
