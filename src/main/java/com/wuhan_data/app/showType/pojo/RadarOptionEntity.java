package com.wuhan_data.app.showType.pojo;

import java.util.List;
import java.util.Map;

public class RadarOptionEntity {
	Map grid;
	Map legend;
	Map radar;
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
	public Map getRadar() {
		return radar;
	}
	public void setRadar(Map radar) {
		this.radar = radar;
	}
	public List<Map> getSeries() {
		return series;
	}
	public void setSeries(List<Map> series) {
		this.series = series;
	}
	
	

}
