package com.wuhan_data.app.showType.pojo;

import java.util.List;
import java.util.Map;

public class RadarOptionEntity {
	Map<String,Object> grid;
	Map<String,Object> legend;
	Map<String,Object> radar;
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
	public Map<String,Object> getRadar() {
		return radar;
	}
	public void setRadar(Map<String,Object> radar) {
		this.radar = radar;
	}
	public List<Map<String,Object>> getSeries() {
		return series;
	}
	public void setSeries(List<Map<String, Object>> listSeries) {
		this.series = listSeries;
	}
	
	

}
