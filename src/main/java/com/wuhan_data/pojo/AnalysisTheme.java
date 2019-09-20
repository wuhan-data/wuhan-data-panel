package com.wuhan_data.pojo;

public class AnalysisTheme {
	Integer themeId; // 一级栏目id
	Integer labelId; // 所属分类标签id
	String themeName; // 一级栏目名称
	Integer themeWeight; // 一级栏目权重
	Integer isShow; // 是否展示 0-正常 1-不展示 9-权限限制
	Boolean isFavorite; // 是否被收藏

	public Integer getThemeId() {
		return themeId;
	}
	
	public Integer getLabelId() {
		return labelId;
	}
	
	public String getThemeName() {
		return themeName;
	}
	
	public Integer getThemeWeight() {
		return themeWeight;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsFavorite(Boolean is_favorite) {
		this.isFavorite = is_favorite;
	}

	public Boolean getIsFavorite() {
		return isFavorite;
	}

}
