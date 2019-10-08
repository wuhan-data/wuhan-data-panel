package com.wuhan_data.tools;

import java.util.List;

import com.wuhan_data.pojo.AnalysisTheme;
import com.wuhan_data.pojo.AnalysisType;;
import com.wuhan_data.pojo.AnalysisTheme;;

public class ThemeList {
	private String level_one;
	private List<AnalysisTheme> level_twoInOneList;
	public String getLevel_one() {
		return level_one;
	}
	public void setLevel_one(String level_one) {
		this.level_one = level_one;
	}
	public List<AnalysisTheme> getLevel_twoInOneList() {
		return level_twoInOneList;
	}
	public void setLevel_twoInOneList(List<AnalysisTheme> level_twoInOneList) {
		this.level_twoInOneList = level_twoInOneList;
	}
	@Override
	public String toString() {
		return "MenuList [level_one=" + level_one + ", level_twoInOneList=" + level_twoInOneList + "]";
	}
	

}
