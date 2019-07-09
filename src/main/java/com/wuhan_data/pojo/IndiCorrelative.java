package com.wuhan_data.pojo;

public class IndiCorrelative {
	
	int id;
	int cid;//指标上一级栏目id
	int indi_id;//指标id
	String indi_name;//指标名称
	int is_show;//是否展示
	public int getIs_show() {
		return is_show;
	}
	public void setIs_show(int is_show) {
		this.is_show = is_show;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getIndi_id() {
		return indi_id;
	}
	public void setIndi_id(int indi_id) {
		this.indi_id = indi_id;
	}
	public String getIndi_name() {
		return indi_name;
	}
	public void setIndi_name(String indi_name) {
		this.indi_name = indi_name;
	}

	
}
