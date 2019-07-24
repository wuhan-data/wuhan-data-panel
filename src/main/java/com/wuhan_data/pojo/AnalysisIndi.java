package com.wuhan_data.pojo;

public class AnalysisIndi {
	Integer indiId; // 版块下指标id
	Integer plateId; // 所属版块id
	String indiName; // 指标展示名称
	String showType; // 指标展示类型，一般用于复合图重病
	String indiCode; // 查同方指标表的指标CODE
	String timePoint; // 查同方指标表的时点
	String source; // 查同方指标表的数据来源
	String areaName; // 查同方指标表的地区名称
	Integer indiWeigth;// 指标展示权重
	Integer isShow; // 是否展示 0-正常 1-不展示

	public Integer getIndiId() {
		return indiId;
	}

	public Integer getPlateId() {
		return plateId;
	}

	public String getIndiName() {
		return indiName;
	}

	public String getShowType() {
		return showType;
	}

	public String getIndiCode() {
		return indiCode;
	}

	public String getTimePoint() {
		return timePoint;
	}

	public String getSource() {
		return source;
	}
	
	public String getAreaName() {
		return areaName;
	}
	
	public Integer getIndiWeigth() {
		return indiWeigth;
	}

	public Integer getIsShow() {
		return isShow;
	}
}
