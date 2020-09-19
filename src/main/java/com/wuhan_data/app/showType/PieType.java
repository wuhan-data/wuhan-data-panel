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
	public PieEntity getOption(String id, String title, List<String> dataV, List<String> legendData,
			List<String> showColor) {

		PieOptionEntity pieOptionEntity = new PieOptionEntity();

		// 设置背景颜色
		String backgroundColor = "#fff";
		pieOptionEntity.setBackgroundColor(backgroundColor);

		// 构建grid
		Map<String, Object> gridMap = new HashMap<String, Object>();
		gridMap.put("containLabel", true);
		gridMap.put("bottom", "50");
		gridMap.put("height", "250");
		pieOptionEntity.setGrid(gridMap);

		// 构建toopTip
		Map<String, Object> toolTipMap = new HashMap<String, Object>();
		toolTipMap.put("confine", true);
		toolTipMap.put("show", true);
		toolTipMap.put("trigger", "item");
//		toolTipMap.put("backgroundColor", "transparent");
		Map<String, Object> textStyleMap = new HashMap<String, Object>();
		textStyleMap.put("color", "#fff");
		toolTipMap.put("textStyle", textStyleMap);
		pieOptionEntity.setTooltip(toolTipMap);

		// 构建legend
//		Map<String, Object> legendMap = new HashMap<String, Object>();
//		legendMap.put("orient", "vertical");
//		legendMap.put("left", "20%");
//		legendMap.put("bottom", "320");
//		legendMap.put("data", legendData);
//		// 计算legend高度
//		int legendHeight = 150;
//		if (legendData.size() > 5) {
//			legendHeight = legendData.size() * 22;
//			legendMap.put("type", "scroll");
//		} else {
//			legendHeight = legendData.size() * 35;
//		}
//		legendMap.put("height", String.valueOf(legendHeight));
//		pieOptionEntity.setLegend(legendMap);

		// 构建调色盘
		List<String> colorMap = new ArrayList<String>();
		// 一期调色盘
		colorMap.add("#f0855b");
		colorMap.add("#96cdf6");
		colorMap.add("#8ac583");
		colorMap.add("#6d95e6");
		colorMap.add("#738c36");
		colorMap.add("#ee74b2");
		// 默认调色盘
		colorMap.add("#c23531");
		colorMap.add("#2f4554");
		colorMap.add("#61a0a8");
		colorMap.add("#d48265");
		colorMap.add("#91c7ae");
		colorMap.add("#749f83");
		colorMap.add("#ca8622");
		colorMap.add("#bda29a");
		colorMap.add("#6e7074");
		colorMap.add("#546570");
		colorMap.add("#c4ccd3");
		pieOptionEntity.setColor(colorMap);

		// 构建series
		Map<String, Object> seriesList = new HashMap<String, Object>();
		List<Map<String, Object>> listTotal = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < dataV.size(); i++) {
			Map<String, Object> seriesListMap = new HashMap<String, Object>();
			seriesListMap.put("value", dataV.get(i));
			seriesListMap.put("name", legendData.get(i));
			// 配置label到饼上
			Map<String, Object> seriesLabelMap = new HashMap<String, Object>();
			seriesLabelMap.put("show", true);
			seriesLabelMap.put("position", "inside");
			seriesLabelMap.put("formatter", "{d}%");
			seriesListMap.put("label", seriesLabelMap);
			// 配置特定的颜色参数
			Map<String, Object> seriesItemStyleMap = new HashMap<String, Object>();
			if (i < showColor.size()) {
				if (showColor.get(i) != null && showColor.get(i) != "") {
					System.out.println(i + "has showColor");
					seriesItemStyleMap.put("color", showColor.get(i).toString());
				}
			}
			seriesListMap.put("itemStyle", seriesItemStyleMap);
			listData.add(seriesListMap);
		}
		seriesList.put("type", "pie");
		seriesList.put("radius", "50%");
		List<String> center = new ArrayList<String>();
		center.add("50%");
		center.add("50%");
		seriesList.put("center", center);
		seriesList.put("data", listData);
		listTotal.add(seriesList);
		pieOptionEntity.setSeries(listTotal);

		PieEntity pieEntity = new PieEntity(id, title, pieOptionEntity);
		return pieEntity;
	}
}
