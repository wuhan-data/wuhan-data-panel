package com.wuhan_data.app.showType.pojo;

import java.util.List;
import java.util.Map;

public class LineEntity {
	String id;
	String classTitle;
	String classType="echarts";
	String classHeight="400";
	LineOptionEntity echartOption;
	
	
	public LineEntity() {
		super();
	}
	public LineEntity(String id, String classTitle, LineOptionEntity echartOption) {
		super();
		this.id = id;
		this.classTitle = classTitle;
		this.echartOption = echartOption;
	}
	public LineEntity(String id, String classTitle, String classType, String classHeight, LineOptionEntity echartOption) {
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
	public LineOptionEntity getEchartOption() {
		return echartOption;
	}
	public void setEchartOption(LineOptionEntity echartOption) {
		this.echartOption = echartOption;
	}
	
	
}
