package com.wuhan_data.app.showType.pojo;

//双X轴折线图
public class DoubleXaxisLineEntity {
	
	String id;
	String classTitle;
	String classType="echarts";
	String classHeight="500";
	DoubleXaxisLineOptionEntity echartOption;
	
	public DoubleXaxisLineEntity() {
		super();
	}
	public DoubleXaxisLineEntity(String id, String classTitle, DoubleXaxisLineOptionEntity echartOption) {
		super();
		this.id = id;
		this.classTitle = classTitle;
		this.echartOption = echartOption;
	}
	public DoubleXaxisLineEntity(String id, String classTitle, String classType, String classHeight, DoubleXaxisLineOptionEntity echartOption) {
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
	public DoubleXaxisLineOptionEntity getEchartOption() {
		return echartOption;
	}
	public void setEchartOption(DoubleXaxisLineOptionEntity echartOption) {
		this.echartOption = echartOption;
	}
	
	

}
