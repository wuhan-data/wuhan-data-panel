package com.wuhan_data.app.showType.pojo;

import java.util.List;
import java.util.Map;

public class PieOptionEntity {
	Map grid;
	Map legend;
	Map tooltip;
//	Map series;
	List<Map> series;
	public Map getGrid() {
		return grid;
	}
	public void setGrid(Map grid) {
		this.grid = grid;
	}
	public Map getLegend() {
		return legend;
	}
	public void setLegend(Map legend) {
		this.legend = legend;
	}
//	public Map getSeries() {
//		return series;
//	}
	public List<Map> getSeries() {
		return series;
	}
//	public void setSeries(Map series) {
//		this.series = series;
//	}
	public void setSeries(List<Map> listTotal) {
		this.series = listTotal;
	}
	public Map getTooltip() {
		return tooltip;
	}
	public void setTooltip(Map tooltip) {
		this.tooltip=tooltip;
	}
	

}
