package com.wuhan_data.app.showType.pojo;

import java.util.List;
import java.util.Map;
//散点图
public class PointOptionEntity {
	Map grid;
	Map tooltip;
	Map legend;
	Map xAxis;
	Map yAxis;
	List<Map> series;
	public Map getGrid() {
		return grid;
	}
	public void setGrid(Map grid) {
		this.grid = grid;
	}
	public Map getTooltip() {
		return tooltip;
	}
	public void setTooltip(Map tooltip) {
		this.tooltip = tooltip;
	}
	public Map getLegend() {
		return legend;
	}
	public void setLegend(Map legend) {
		this.legend = legend;
	}
	public Map getxAxis() {
		return xAxis;
	}
	public void setxAxis(Map xAxis) {
		this.xAxis = xAxis;
	}
	public Map getyAxis() {
		return yAxis;
	}
	public void setyAxis(Map yAxis) {
		this.yAxis = yAxis;
	}
	public List<Map> getSeries() {
		return series;
	}
	public void setSeries(List<Map> series) {
		this.series = series;
	}
	

}
