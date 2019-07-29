package com.wuhan_data.app.showType.pojo;

import java.util.List;
import java.util.Map;

public class PieOptionEntity {
	Map<String,Object> grid;
	Map<String,Object> legend;
	Map<String,Object> tooltip;
//	Map series;
	List<Map<String,Object>> series;
	public Map<String,Object> getGrid() {
		return grid;
	}
	public void setGrid(Map<String,Object> grid) {
		this.grid = grid;
	}
	public Map<String,Object> getLegend() {
		return legend;
	}
	public void setLegend(Map<String,Object> legend) {
		this.legend = legend;
	}
//	public Map getSeries() {
//		return series;
//	}
	public List<Map<String,Object>> getSeries() {
		return series;
	}
//	public void setSeries(Map series) {
//		this.series = series;
//	}
	public void setSeries(List<Map<String, Object>> listTotal) {
		this.series = listTotal;
	}
	public Map<String,Object> getTooltip() {
		return tooltip;
	}
	public void setTooltip(Map<String,Object> tooltip) {
		this.tooltip=tooltip;
	}
	

}
