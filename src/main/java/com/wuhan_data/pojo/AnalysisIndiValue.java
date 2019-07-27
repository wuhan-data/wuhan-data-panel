package com.wuhan_data.pojo;

public class AnalysisIndiValue {
	String indiCode; // 指标id//INDEXDATAID
	String freqName; // 指标值时间频度//INDICATOR_GRP21_ITEM_NAME
	String time; // 指标值时间//DATA_TIME_NAME
	String timePoint;// 指标时点//INDICATOR_GRP22_ITEM_NAME
	String indiValue; // 指标值//VALUE
	String unitName; // 指标值单位名称//UNIT_NAME

	public String getIndiCode() {
		return indiCode;
	}

	public String getFreqName() {
		return freqName;
	}

	public String getTimePoint() {
		return timePoint;
	}

	public String getTime() {
		return time;
	}

	public String getIndiValue() {
		return indiValue;
	}

	public String getUnitName() {
		return unitName;
	}

}
