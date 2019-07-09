package com.wuhan_data.app.showType.pojo;
//折柱混搭
public class LineAndBarEntity {
	String id;
	String classTitle;
	String classType="echarts";
	String classHeight="300";
	LineAndBarOptionEntity echartOption;
	
	public LineAndBarEntity() {
		super();
	}
	public LineAndBarEntity(String id, String classTitle, LineAndBarOptionEntity echartOption) {
		super();
		this.id = id;
		this.classTitle = classTitle;
		this.echartOption = echartOption;
	}
	public LineAndBarEntity(String id, String classTitle, String classType, String classHeight, LineAndBarOptionEntity echartOption) {
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
	public LineAndBarOptionEntity getEchartOption() {
		return echartOption;
	}
	public void setEchartOption(LineAndBarOptionEntity echartOption) {
		this.echartOption = echartOption;
	}
	
	

}
