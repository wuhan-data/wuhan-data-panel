package com.wuhan_data.app.showType.pojo;

import java.util.List;
import java.util.Map;

public class BarStoreOptionEntity {
	Map grid;
	Map tooltip;
	Map legend;
	List<Map> xAxis;
	List<Map> yAxis;
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
	public List<Map> getxAxis() {
		return xAxis;
	}
	public void setxAxis(List<Map> xAxis) {
		this.xAxis = xAxis;
	}
	public List<Map> getyAxis() {
		return yAxis;
	}
	public void setyAxis(List<Map> yAxis) {
		this.yAxis = yAxis;
	}
	public List<Map> getSeries() {
		return series;
	}
	public void setSeries(List<Map> series) {
		this.series = series;
	}
	

}
