package com.wuhan_data.pojo;

public class AnalysisTheme {
	Integer listId; // 一级栏目id
	String listName; // 一级栏目名称
	Integer listWeight; // 一级栏目权重
	Integer themeId; // 二级栏目id
	String themeName; // 二级栏目名称
	Integer isShow; // 是否展示 0-正常 1-不展示 9-权限限制
	Boolean isFavorite; // 二级栏目是否被收藏

	public Integer getListId() {
		return listId;
	}

	public String getListName() {
		return listName;
	}

	public Integer getThemeId() {
		return themeId;
	}
	
	public String getThemeName() {
		return themeName;
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
