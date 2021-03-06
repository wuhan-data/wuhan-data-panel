package com.wuhan_data.app.showType.pojo;

//散点图
public class PointEntity {
	String id;
	String classTitle;
	String classType="echarts";
	String classHeight="300";
	PointOptionEntity echartOption;
	
	public PointEntity() {
		super();
	}
	public PointEntity(String id, String classTitle, PointOptionEntity echartOption) {
		super();
		this.id = id;
		this.classTitle = classTitle;
		this.echartOption = echartOption;
	}
	public PointEntity(String id, String classTitle, String classType, String classHeight, PointOptionEntity echartOption) {
		super();
		this.id = id;
		this.classTitle = classTitle;
		this.classType = classType;
		this.classHeight = classHeight;
		this.echartOption = echartOption;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassTitle() {
		return classTitle;
	}
	public void setClassTitle(String classTitle) {
		this.classTitle = classTitle;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getClassHeight() {
		return classHeight;
	}
	public void setClassHeight(String classHeight) {
		this.classHeight = classHeight;
	}
	public PointOptionEntity getEchartOption() {
		return echartOption;
	}
	public void setEchartOption(PointOptionEntity echartOption) {
		this.echartOption = echartOption;
	}
	
	

}
