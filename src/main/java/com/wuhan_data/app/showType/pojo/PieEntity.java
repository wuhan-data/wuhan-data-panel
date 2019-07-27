package com.wuhan_data.app.showType.pojo;

public class PieEntity {
	
	String id;
	String classTitle;
	String classType="echarts";
	String classHeight="300";
	PieOptionEntity echartOption;
	
	
	public PieEntity() {
		super();
	}
	public PieEntity(String id, String classTitle, PieOptionEntity echartOption) {
		super();
		this.id = id;
		this.classTitle = classTitle;
		this.echartOption = echartOption;
	}
	public PieEntity(String id, String classTitle, String classType, String classHeight, PieOptionEntity echartOption) {
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
	public PieOptionEntity getEchartOption() {
		return echartOption;
	}
	public void setEchartOption(PieOptionEntity echartOption) {
		this.echartOption = echartOption;
	}
	
	

}
