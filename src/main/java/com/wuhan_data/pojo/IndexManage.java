package com.wuhan_data.pojo;

import java.util.Date;

public class IndexManage {
	Integer id;
	String indi_code;//指示代码
	String indi_name;//指示名称
	Integer status;//指示状态 0正常 1不展示
	String sjly_name2;
	String source;//指标来源
	String lj;//路径
	String is_show;//是否允许展示
	String area_code;//指标的区域代码
	String area_name;//指标的区域名称
	public String getLj() {
		return lj;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public void setLj(String lj) {
		this.lj = lj;
	}
	public String getIs_show() {
		return is_show;
	}
	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}
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

	public Integer getId() {
		return id;
	}
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
