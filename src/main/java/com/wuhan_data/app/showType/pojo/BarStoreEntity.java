package com.wuhan_data.app.showType.pojo;

public class BarStoreEntity {
	String id;
	String classTitle;
	String classType="echarts";
	String classHeight="550";
	BarStoreOptionEntity echartOption;
	
	public BarStoreEntity() {
		super();
	}
	public BarStoreEntity(String id, String classTitle, BarStoreOptionEntity echartOption) {
		super();
		this.id = id;
		this.classTitle = classTitle;
		this.echartOption = echartOption;
	}
	public BarStoreEntity(String id, String classTitle, String classType, String classHeight, BarStoreOptionEntity echartOption) {
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
	public BarStoreOptionEntity getEchartOption() {
		return echartOption;
	}
	public void setEchartOption(BarStoreOptionEntity echartOption) {
		this.echartOption = echartOption;
	}
	
	

}
