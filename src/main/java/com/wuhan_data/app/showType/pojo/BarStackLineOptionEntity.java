package com.wuhan_data.app.showType.pojo;

import java.util.List;
import java.util.Map;

public class BarStackLineOptionEntity {

	Map<String, Object> grid;
	Map<String, Object> tooltip;
	Map<String, Object> legend;
	List<String> color;
	List<Map<String, Object>> xAxis;
	List<Map<String, Object>> yAxis;
	List<Map<String, Object>> series;

	public Map<String, Object> getGrid() {
		return grid;
	}

	public void setGrid(Map<String, Object> grid) {
		this.grid = grid;
	}

	public Map<String, Object> getTooltip() {
		return tooltip;
	}

	public void setTooltip(Map<String, Object> tooltip) {
		this.tooltip = tooltip;
	}

	public Map<String, Object> getLegend() {
		return legend;
	}

	public void setLegend(Map<String, Object> legend) {
		this.legend = legend;
	}

	public List<String> getColor() {
		return color;
	}

	public void setColor(List<String> color) {
		this.color = color;
	}

	public List<Map<String, Object>> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<Map<String, Object>> xAxis2) {
		this.xAxis = xAxis2;
	}

	public List<Map<String, Object>> getyAxis() {
		return yAxis;
	}

	public void setyAxis(List<Map<String, Object>> yAxis) {
		this.yAxis = yAxis;
	}

	public List<Map<String, Object>> getSeries() {
		return series;
	}

	public void setSeries(List<Map<String, Object>> series) {
		this.series = series;
	}

}
