package com.comparedata.service;

import java.util.List;
import java.util.Map;

public class ComparisonResponse {
    private int totalItems;
    private int totalDifferent;
    private String table1;
    private String table2;
    private List<Map<String, Object>> different;
    private List<Map<String, Object>> user2;
    private List<Map<String, Object>> user;
	public ComparisonResponse(int totalItems, int totalDifferent, String table1, String table2,
			List<Map<String, Object>> different, List<Map<String, Object>> user2, List<Map<String, Object>> user) {
		super();
		this.totalItems = totalItems;
		this.totalDifferent = totalDifferent;
		this.table1 = table1;
		this.table2 = table2;
		this.different = different;
		this.user2 = user2;
		this.user = user;
	}
	public int getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	public int getTotalDifferent() {
		return totalDifferent;
	}
	public void setTotalDifferent(int totalDifferent) {
		this.totalDifferent = totalDifferent;
	}
	public String getTable1() {
		return table1;
	}
	public void setTable1(String table1) {
		this.table1 = table1;
	}
	public String getTable2() {
		return table2;
	}
	public void setTable2(String table2) {
		this.table2 = table2;
	}
	public List<Map<String, Object>> getDifferent() {
		return different;
	}
	public void setDifferent(List<Map<String, Object>> different) {
		this.different = different;
	}
	public List<Map<String, Object>> getUser2() {
		return user2;
	}
	public void setUser2(List<Map<String, Object>> user2) {
		this.user2 = user2;
	}
	public List<Map<String, Object>> getUser() {
		return user;
	}
	public void setUser(List<Map<String, Object>> user) {
		this.user = user;
	}

    
}
