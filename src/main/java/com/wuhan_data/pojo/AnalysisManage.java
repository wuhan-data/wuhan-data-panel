package com.wuhan_data.pojo;

public class AnalysisManage {
	Integer id;//栏目id
	String type_name;//父栏目名称
	String theme_name;//父栏目名称
	String indi_name;//指标名称
	Integer is_show;//是否展示 0-正常 1-不展示
	Integer weight;//权重
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getIndi_name() {
		return indi_name;
	}
	public void setIndi_name(String indi_name) {
		this.indi_name = indi_name;
	}
	public Integer getIs_show() {
		return is_show;
	}
	public void setIs_show(Integer is_show) {
		this.is_show = is_show;
	}
	
	public String getTheme_name() {
		return theme_name;
	}
	public void setTheme_name(String theme_name) {
		this.theme_name = theme_name;
	}
	
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "AnalysisManage [id=" + id + ", type_name=" + type_name + ", indi_name=" + indi_name + ", is_show="
				+ is_show + "]";
	}
	

}
