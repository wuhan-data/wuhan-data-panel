package com.wuhan_data.app.showType.pojo;

//雷达图
public class RadarEntity {
	
	String id;
	String classTitle;
	String classType="echarts";
	String classHeight="400";
	RadarOptionEntity echartOption;
	
	public RadarEntity() {
		super();
	}
	public RadarEntity(String id, String classTitle, RadarOptionEntity echartOption) {
		super();
		this.id = id;
		this.classTitle = classTitle;
		this.echartOption = echartOption;
	}
	public RadarEntity(String id, String classTitle, String classType, String classHeight, RadarOptionEntity echartOption) {
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
	public RadarOptionEntity getEchartOption() {
		return echartOption;
	}
	public void setEchartOption(RadarOptionEntity echartOption) {
		this.echartOption = echartOption;
	}
	
	

}
