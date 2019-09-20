package com.wuhan_data.pojo;

public class AnalysisLabel {
	Integer labelId; // 分类标签id
	Integer typeId; // 一级栏目id
	String labelName; // 分类标签名称
	Integer labelWeight; // 分类标签权重
	Integer isShow; // 是否展示 0-正常 1-不展示 9-权限限制

	public Integer getLabelId() {
		return labelId;
	}
	
	public Integer getTypeId() {
		return typeId;
	}

	public String getLabelName() {
		return labelName;
	}
	
	public Integer getLabelWeight() {
		return labelWeight;
	}
	
	public Integer getIsShow() {
		return isShow;
	}
}
