package com.wuhan_data.pojo;

public class TPIndiValue implements Comparable<TPIndiValue> {
	private String date_code;
	private String time_point;
	private String indi_value;
	private String area_name;
	private String unitname;

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public String getDate_code() {
		return date_code;
	}

	public void setDate_code(String date_code) {
		this.date_code = date_code;
	}

	public String getTime_point() {
		return time_point;
	}

	public void setTime_point(String time_point) {
		this.time_point = time_point;
	}

	public String getIndi_value() {
		return indi_value;
	}

	public void setIndi_value(String indi_value) {
		this.indi_value = indi_value;
	}

	@Override
	public int compareTo(TPIndiValue o) {
		int nameIndex = this.date_code.compareTo(o.date_code);
		int ageIndex = 0;
		int startIndex = 0;
		return nameIndex + ageIndex + startIndex;

	}

}
