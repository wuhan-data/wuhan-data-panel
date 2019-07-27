package com.wuhan_data.app.showType.pojo;
//柱状图实体类
public class BarEntity {
	
	String id;
	String classTitle;
	String classType="echarts";
	String classHeight="400";
	BarOptionEntity echartOption;
	
	public BarEntity() {
		super();
	}
	public BarEntity(String id, String classTitle, BarOptionEntity echartOption) {
		super();
		this.id = id;
		this.classTitle = classTitle;
		this.echartOption = echartOption;
	}
	public BarEntity(String id, String classTitle, String classType, String classHeight, BarOptionEntity echartOption) {
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
	public BarOptionEntity getEchartOption() {
		return echartOption;
	}
	public void setEchartOption(BarOptionEntity echartOption) {
		this.echartOption = echartOption;
	}
	
	
	
}
