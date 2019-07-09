package com.wuhan_data.tools;

import java.util.List;

import com.wuhan_data.pojo.Menu;

public class MenuList {
	private String level_one;
	private List<Menu> level_twoInOneList;
	public String getLevel_one() {
		return level_one;
	}
	public void setLevel_one(String level_one) {
		this.level_one = level_one;
	}
	public List<Menu> getLevel_twoInOneList() {
		return level_twoInOneList;
	}
	public void setLevel_twoInOneList(List<Menu> level_twoInOneList) {
		this.level_twoInOneList = level_twoInOneList;
	}
	@Override
	public String toString() {
		return "MenuList [level_one=" + level_one + ", level_twoInOneList=" + level_twoInOneList + "]";
	}
	
	

}
