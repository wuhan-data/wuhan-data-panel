package com.wuhan_data.pojo;

public class AnalysisType {
	Integer typeId; // 一级栏目id
	String typeName; // 一级栏目名称
	Integer typeWeight; // 一级栏目权重
	String background; // icon背景颜色
	String iconUrl; // icon图片路径
	Integer isShow; // 是否展示 0-正常 1-不展示 9-权限限制

	public Integer getTypeId() {
		return typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public Integer getTypeWeight() {
		return typeWeight;
	}

	public String getBackground() {
		return background;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setTypeWeight(Integer typeWeight) {
		this.typeWeight = typeWeight;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
}
