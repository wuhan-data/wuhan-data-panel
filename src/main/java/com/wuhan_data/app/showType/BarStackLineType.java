package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuhan_data.app.showType.pojo.BarStackLineEntity;
import com.wuhan_data.app.showType.pojo.BarStackLineOptionEntity;

public class BarStackLineType {
	// 参数：图例名称列表、数据、数据、展示类型
	public BarStackLineEntity getOption(String id, String title, List<String> dataX, List<String> legendData,
			List<List<String>> dataV, List<String> showColor, List<String> showType) {

		// 预处理数据，合并1月及2月的数据
		int ignoreX = -1;
		for (int i = 0; i < dataX.size(); i++) {
			if (dataX.get(i).length() != 7) {
				continue;
			}
			String monthString = dataX.get(i).toString().substring(5, 7);
			if (monthString.equals("01")) {
				for (int j = 0; j < dataV.size(); j++) {
					if (dataV.get(j).get(i) == null || dataV.get(j).get(i).toString().equals("")) {
						// 存在一月无数据情况，需要进行合并
						ignoreX = i;
					}
				}
			}
		}
		// 删除1月的空数据
		if (ignoreX != -1) {
			// 处理x轴数据
			List<String> dataX1 = new ArrayList<String>(dataX);
			dataX1.remove(ignoreX);
			String dataXString = dataX1.get(ignoreX).substring(0, 5) + "1-2";
			dataX1.set(ignoreX, dataXString);
			dataX = dataX1;
			// 处理数据值
			List<List<String>> dataV1 = new ArrayList<List<String>>();
			for (int i = 0; i < dataV.size(); i++) {
				List<String> tempList = new ArrayList<String>(dataV.get(i));
				tempList.remove(ignoreX);
				dataV1.add(tempList);
			}
			dataV = dataV1;
		}

		BarStackLineOptionEntity barStackLineOptionEntity = new BarStackLineOptionEntity();

		// 构建grid
		Map<String, Object> gridMap = new HashMap<String, Object>();
		gridMap.put("containLabel", true);
		gridMap.put("bottom", "60");
		gridMap.put("bottom", "250");
		barStackLineOptionEntity.setGrid(gridMap);

		// 构建toolTip
		Map<String, Object> toolTipMap = new HashMap<String, Object>();
		toolTipMap.put("show", true);
		toolTipMap.put("trigger", "axis");
		List<String> toolTipPosition = new ArrayList<String>();
		toolTipPosition.add("10%");
		toolTipPosition.add("50%");
		toolTipMap.put("position", toolTipPosition);
		toolTipMap.put("snap", true);
		Map<String, Object> axisPointerMap = new HashMap<String, Object>();
		axisPointerMap.put("type", "line");
		axisPointerMap.put("axis", "x");
		Map<String, Object> axisPointerLabelMap = new HashMap<String, Object>();
		axisPointerLabelMap.put("show", true);
		axisPointerMap.put("label", axisPointerLabelMap);
		toolTipMap.put("axisPointer", axisPointerMap);
		barStackLineOptionEntity.setTooltip(toolTipMap);

		// 构建legend
		Map<String, Object> legendMap = new HashMap<String, Object>();
		legendMap.put("orient", "vertical");
		legendMap.put("bottom", "330");
		legendMap.put("data", legendData);
		// 计算legend高度
		int legendHeight = (legendData.size() > 5 ? 5 : legendData.size()) * 35;
		legendMap.put("height", String.valueOf(legendHeight));
		if (legendData.size() > 5) {
			legendMap.put("type", "scroll");
		}
		// 控制初始展示图例个数,默认展示2个
		int showNum = 4;
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
		barStackLineOptionEntity.setLegend(legendMap);

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
		barStackLineOptionEntity.setColor(colorMap);

		// 构建x轴
		List<Map<String, Object>> xAxis = new ArrayList<Map<String, Object>>();
		Map<String, Object> xAxisMap = new HashMap<String, Object>();
		xAxisMap.put("type", "category");
		xAxisMap.put("name", ""); // 名称过长，不展示名称
		xAxisMap.put("data", dataX);
		Map<String, Object> xAxisLabelMap = new HashMap<String, Object>();
		xAxisLabelMap.put("rotate", "45");
		xAxisMap.put("axisLabel", xAxisLabelMap);
		xAxis.add(xAxisMap);
		barStackLineOptionEntity.setxAxis(xAxis);

		// 构建y轴
		List<Map<String, Object>> yAxis = new ArrayList<Map<String, Object>>();
		Map<String, Object> yAxisFirstMap = new HashMap<String, Object>();
		yAxisFirstMap.put("type", "value");
		yAxisFirstMap.put("name", "");
		yAxis.add(yAxisFirstMap);
		Map<String, Object> yAxisSecondMap = new HashMap<String, Object>();
		yAxisSecondMap.put("type", "value");
		yAxisSecondMap.put("name", "");
		yAxis.add(yAxisSecondMap);
		barStackLineOptionEntity.setyAxis(yAxis);

		// 构建series
		List<Map<String, Object>> seriesList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < dataV.size(); i++) {
			List<String> tempList = new ArrayList<String>();
			tempList = dataV.get(i);
			// 配置展示类型
			String showTypeString = showType.get(i).toString();
			Map<String, Object> seriesListMap = new HashMap<String, Object>();
			if (showTypeString.equals("bar")) {
				seriesListMap.put("name", legendData.get(i));
				seriesListMap.put("type", showTypeString);
				seriesListMap.put("data", tempList);
				seriesListMap.put("yAxisIndex", "1");
			} else {
				seriesListMap.put("name", legendData.get(i));
				seriesListMap.put("type", showTypeString);
				seriesListMap.put("data", tempList);
				seriesListMap.put("yAxisIndex", "0");
			}
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
		barStackLineOptionEntity.setSeries(seriesList);

		// 设置图例对象
		BarStackLineEntity barStackLineEntity = new BarStackLineEntity(id, title, barStackLineOptionEntity);
		int classHeight = 330 + (legendData.size() > 5 ? 5 : legendData.size()) * 35 + 10;
		barStackLineEntity.setClassHeight(String.valueOf(classHeight));
		return barStackLineEntity;
	}

}
