package com.wuhan_data.pojo;

public class indi_TF {
	String indi_code;//指示代码
	String indi_name;//指示名称
	String date_code;//日期代码
	String freq_code;//频度代码	
	String indi_value;//指标值
	Integer time_point;//时点
	String unitname;//单位
	String sjly_name2;//数据来源
	//饼状图
	String group1_name;
	public String getIndi_code() {
		return indi_code;
	}
	public void setIndi_code(String indi_code) {
		this.indi_code = indi_code;
	}
	public String getIndi_name() {
		return indi_name;
	}
	public void setIndi_name(String indi_name) {
		this.indi_name = indi_name;
	}
	public String getDate_code() {
		return date_code;
	}
	public void setDate_code(String date_code) {
		this.date_code = date_code;
	}
	public String getFreq_code() {
		return freq_code;
	}
	public void setFreq_code(String freq_code) {
		this.freq_code = freq_code;
	}
	public String getIndi_value() {
		return indi_value;
	}
	public void setIndi_value(String indi_value) {
		this.indi_value = indi_value;
	}
	public Integer getTime_point() {
		return time_point;
	}
	public void setTime_point(Integer time_point) {
		this.time_point = time_point;
	}
	
	public String getSjly_name2() {
		return sjly_name2;
	}
	public void setSjly_name2(String sjly_name2) {
		this.sjly_name2 = sjly_name2;
	}
	public String getGroup1_name() {
		return group1_name;
	}
	public void setGroup1_name(String group1_name) {
		this.group1_name = group1_name;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public String getGroupName() {
		return this.group1_name;
	}
	public void setGroupName(String group1_name) {
		this.group1_name=group1_name;
	}
	

}
