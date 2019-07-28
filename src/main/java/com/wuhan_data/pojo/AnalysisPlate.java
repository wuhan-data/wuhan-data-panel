package com.wuhan_data.pojo;

public class AnalysisPlate {
	Integer plateId; // 版块id
	Integer themeId; // 所属栏目id
	String themeName; // 所属栏目名称
	String plateName; // 版块名称
	String showType; // 版块展示类型
	Integer showTerm; // 版块展示期数
	Integer isShow; // 是否展示 0-正常 1-不展示 9-权限限制

	public Integer getPlateId() {
		return plateId;
	}

	public Integer getThemeId() {
		return themeId;
	}

	public String getThemeName() {
		return themeName;
	}

	public String getPlateName() {
		return plateName;
	}

	public String getShowType() {
		return showType;
	}

	public Integer getShowTerm() {
		return showTerm;
	}

	public Integer getIsShow() {
		return isShow;
	}
}
