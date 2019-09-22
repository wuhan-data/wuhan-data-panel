package com.wuhan_data.pojo;

public class AnalysisSearch {
	Integer themeId; // 二级栏目id
	String themeName; // 二级栏目名称
	Integer themeWeight; // 一级栏目权重
	Integer labelId; // 所属分类标签id
	String labelName; // 所属分类标签名称
	Integer typeId; // 一级栏目id
	String typeName; // 一级栏目名称
	Integer isShow; // 是否展示 0-正常 1-不展示 9-权限限制

	public Integer getThemeId() {
		return themeId;
	}

	public String getThemeName() {
		return themeName;
	}

	public Integer getThemeWeight() {
		return themeWeight;
	}

	public Integer getLabelId() {
		return labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public Integer getIsShow() {
		return isShow;
	}
}
