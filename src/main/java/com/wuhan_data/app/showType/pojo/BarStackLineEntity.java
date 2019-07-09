package com.wuhan_data.app.showType.pojo;

//柱状堆叠折线图
public class BarStackLineEntity {
	
	String id;
	String classTitle;
	String classType;
	String classHeight;
	BarStackLineOptionEntity echartOption;
	
	public BarStackLineEntity() {
		super();
	}
	public BarStackLineEntity(String id, String classTitle, BarStackLineOptionEntity echartOption) {
		super();
		this.id = id;
		this.classTitle = classTitle;
		this.echartOption = echartOption;
	}
	public BarStackLineEntity(String id, String classTitle, String classType, String classHeight, BarStackLineOptionEntity echartOption) {
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
	public BarStackLineOptionEntity getEchartOption() {
		return echartOption;
	}
	public void setEchartOption(BarStackLineOptionEntity echartOption) {
		this.echartOption = echartOption;
	}
	
	

}
