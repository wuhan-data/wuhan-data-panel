package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wuhan_data.app.showType.pojo.PieEntity;
import com.wuhan_data.app.showType.pojo.PieOptionEntity;

//饼图
public class PieType {
	String type = "pie";

	// 参数：指标或版块id、指标或版块名称、
	public PieEntity getOption(String id, String title, List<String> dataV, List<String> legend) {

		PieOptionEntity pieOptionEntity = new PieOptionEntity();

		// 构建grid
		Map<String, Object> gridMap = new HashMap<String, Object>();
		gridMap.put("containLabel", true);
		pieOptionEntity.setGrid(gridMap);

		// 构建toopTip
		Map<String, Object> toolTipMap = new HashMap<String, Object>();
		toolTipMap.put("show", true);
		toolTipMap.put("trigger", "item");
		pieOptionEntity.setTooltip(toolTipMap);

		// 构建legend
		Map<String, Object> legendMap = new HashMap<String, Object>();
		legendMap.put("top", "top");
		legendMap.put("data", legend);
		pieOptionEntity.setLegend(legendMap);

		// 构建series
		Map<String, Object> seriesListMap = new HashMap<String, Object>();
		List<Map<String, Object>> listTotal = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < dataV.size(); i++) {
			Map<String, Object> mapData = new HashMap<String, Object>();
			mapData.put("value", dataV.get(i));
			mapData.put("name", legend.get(i));
			listData.add(mapData);
		}
		seriesListMap.put("type", "pie");
		seriesListMap.put("radius", "50%");
		List<String> center = new ArrayList<String>();
		center.add("50%");
		center.add("50%");
		seriesListMap.put("center", center);
		seriesListMap.put("data", listData);
		listTotal.add(seriesListMap);
		pieOptionEntity.setSeries(listTotal);
		
		PieEntity pieEntity = new PieEntity(id, title, pieOptionEntity);
		return pieEntity;
	}
}
