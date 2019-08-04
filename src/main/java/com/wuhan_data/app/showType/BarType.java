package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wuhan_data.app.showType.pojo.BarEntity;
import com.wuhan_data.app.showType.pojo.BarOptionEntity;

//普通柱状图
public class BarType {
	String type = "bar";

	// 参数：指标或版块id、指标或版块名称、
	public BarEntity getOption(String id, String title, List<String> dataX, List<String> legendData,
			List<List<String>> dataV, List<String> showColor, List<String> showType) {

		BarOptionEntity barOptionEntity = new BarOptionEntity();

		// 构建grid
		Map<String, Object> gridMap = new HashMap<String, Object>();
		gridMap.put("containLabel", true);
		gridMap.put("bottom", "60");
		gridMap.put("height", "250");
		barOptionEntity.setGrid(gridMap);

		// 构建toolTip
		Map<String, Object> toolTipMap = new HashMap<String, Object>();
		toolTipMap.put("show", true);
		toolTipMap.put("trigger", "axis");
		toolTipMap.put("position", "['10%', '50%']");
		toolTipMap.put("snap", true);
		Map<String, Object> axisPointerMap = new HashMap<String, Object>();
		axisPointerMap.put("type", "line");
		axisPointerMap.put("axis", "x");
		Map<String, Object> axisPointerLabelMap = new HashMap<String, Object>();
		axisPointerLabelMap.put("show", true);
		axisPointerMap.put("label", axisPointerLabelMap);
		toolTipMap.put("axisPointer", axisPointerMap);
		barOptionEntity.setTooltip(toolTipMap);

		// 构建legend
		Map<String, Object> legendMap = new HashMap<String, Object>();
		legendMap.put("orient", "vertical");
		legendMap.put("bottom", "350");
		legendMap.put("data", legendData);
		// 控制初始展示图例个数,默认展示2个
		int showNum = 2;
		if (legendData.size() >= showNum) {
			Map<String, Boolean> legendSelectedMap = new HashMap<String, Boolean>();
			for (int i = 0; i < legendData.size(); i++) {
				if (i < showNum) {
					legendSelectedMap.put(legendData.get(i).toString(), true);
				} else {
					legendSelectedMap.put(legendData.get(i).toString(), false);
				}
			}
			legendMap.put("selected", legendSelectedMap);
		}
		barOptionEntity.setLegend(legendMap);

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
		barOptionEntity.setColor(colorMap);

		// 构建x轴
		List<Map<String, Object>> xAxis = new ArrayList<Map<String, Object>>();
		Map<String, Object> xAxisMap = new HashMap<String, Object>();
		xAxisMap.put("type", "category");
		xAxisMap.put("name", "");
		xAxisMap.put("data", dataX);
		xAxis.add(xAxisMap);
		barOptionEntity.setxAxis(xAxis);

		// 构建y轴
		List<Map<String, Object>> yAxis = new ArrayList<Map<String, Object>>();
		Map<String, Object> yAxisMap = new HashMap<String, Object>();
		yAxisMap.put("type", "value");
		yAxisMap.put("name", "");
		yAxis.add(yAxisMap);
		barOptionEntity.setyAxis(yAxis);

		// 构建series
		List<Map<String, Object>> seriesList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < dataV.size(); i++) {
			List<String> tempList = new ArrayList<String>();
			tempList = dataV.get(i);// 数据
			Map<String, Object> seriesListMap = new HashMap<String, Object>();
			seriesListMap.put("name", legendData.get(i));
			seriesListMap.put("type", type);
			seriesListMap.put("data", tempList);
			// 配置特定的颜色参数
			Map<String, Object> seriesItemStyleMap = new HashMap<String, Object>();
			if (i < showColor.size()) {
				if (showColor.get(i) != null && showColor.get(i) != "") {
					System.out.println(i + "has showColor");
					seriesItemStyleMap.put("color", showColor.get(i).toString());
				}
			}
			seriesListMap.put("itemStyle", seriesItemStyleMap);
			seriesList.add(seriesListMap);
		}
		barOptionEntity.setSeries(seriesList);

		BarEntity barEntity = new BarEntity(id, title, barOptionEntity);
		return barEntity;
	}

}
