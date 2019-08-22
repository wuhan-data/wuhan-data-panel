package com.wuhan_data.pojo;

import java.util.Date;

public class IndexManage {
	Integer id;
	String indi_code;//指示代码
	String indi_name;//指示名称
//	String freq_code;//频度代码
//	String start_time;//报告期开始时间
//	String end_time;//报告结束时间
//	String indi_value;//指标值
//	Integer time_point;//时点
	Integer status;//指示状态 0正常 1不展示
	String sjly_name2;
	
	public String getSjly_name2() {
		return sjly_name2;
	}
	public void setSjly_name2(String sjly_name2) {
		this.sjly_name2 = sjly_name2;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	String show_type;//展示类型
	String source;//来源
	public Integer getId() {
		return id;
	}
//	public String getFreq_code() {
//		return freq_code;
//	}
//	public void setFreq_code(String freq_code) {
//		this.freq_code = freq_code;
//	}
//	public String getStart_time() {
//		return start_time;
//	}
//	public void setStart_time(String start_time) {
//		this.start_time = start_time;
//	}
//	public String getEnd_time() {
//		return end_time;
//	}
//	public void setEnd_time(String end_time) {
//		this.end_time = end_time;
//	}
//	public String getIndi_value() {
//		return indi_value;
//	}
//	public void setIndi_value(String indi_value) {
//		this.indi_value = indi_value;
//	}
//	public Integer getTime_point() {
//		return time_point;
//	}
//	public void setTime_point(Integer time_point) {
//		this.time_point = time_point;
//	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getShow_type() {
		return show_type;
	}
	public void setShow_type(String show_type) {
		this.show_type = show_type;
	}
	
	
	
	
}
