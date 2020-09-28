package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wuhan_data.app.showType.pojo.LineAndBarEntity;
import com.wuhan_data.app.showType.pojo.LineAndBarOptionEntity;

//折柱混搭
public class LineAndBarType {
	// 参数：图例名称、数据、数据、显示类型
	public LineAndBarEntity getOption(String id, String title, List<String> dataX, List<String> legendData,
			List<List<String>> dataV, List<String> showColor, List<String> showType, List<String> unitName) {
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

		LineAndBarOptionEntity lineAndBarOptionEntity = new LineAndBarOptionEntity();

		// 设置背景颜色
		String backgroundColor = "#fff";
		lineAndBarOptionEntity.setBackgroundColor(backgroundColor);

		// 构建grid
		Map<String, Object> gridMap = new HashMap<String, Object>();
		gridMap.put("containLabel", true);
		gridMap.put("left", "10%");
		gridMap.put("right", "10%");
		gridMap.put("bottom", "10");
		gridMap.put("height", "250");
		lineAndBarOptionEntity.setGrid(gridMap);

		// 构建toolTip
		Map<String, Object> toolTipMap = new HashMap<String, Object>();
		toolTipMap.put("confine", true);
		toolTipMap.put("show", true);
		toolTipMap.put("trigger", "axis");
//		List<String> toolTipPosition = new ArrayList<String>();
//		toolTipPosition.add("25%");
//		toolTipPosition.add("65%");
//		toolTipMap.put("position", toolTipPosition);
		toolTipMap.put("snap", true);
		Map<String, Object> axisPointerMap = new HashMap<String, Object>();
		axisPointerMap.put("type", "line");
		axisPointerMap.put("axis", "x");
		Map<String, Object> axisPointerLabelMap = new HashMap<String, Object>();
		axisPointerLabelMap.put("show", false);
		axisPointerMap.put("label", axisPointerLabelMap);
		toolTipMap.put("axisPointer", axisPointerMap);
		lineAndBarOptionEntity.setTooltip(toolTipMap);

		// 构建legend
		Map<String, Object> legendMap = new HashMap<String, Object>();
		legendMap.put("formatter", "{name}");
		legendMap.put("orient", "vertical");
		legendMap.put("left", "center");
//		legendMap.put("top", "10");
		legendMap.put("bottom", "300");
		legendMap.put("data", legendData);
		// 计算legend高度
		int legendHeight = 150;
		if (legendData.size() > 5) {
			legendHeight = 125;
			legendMap.put("type", "scroll");
		} else if (legendData.size() <= 3) {
			legendHeight = legendData.size() * 20;
		} else {
			legendHeight = legendData.size() * 25;
		}
		legendMap.put("height", String.valueOf(legendHeight));
		// 控制初始展示图例个数,默认展示4个
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
		lineAndBarOptionEntity.setLegend(legendMap);

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
		lineAndBarOptionEntity.setColor(colorMap);

		// 构建x轴
		List<Map<String, Object>> xAxis = new ArrayList<Map<String, Object>>();
		List<String> temList = new ArrayList<String>();
		temList = dataX;
		Map<String, Object> xAxisMap = new HashMap<String, Object>();
		xAxisMap.put("type", "category");
		xAxisMap.put("name", "");
		xAxisMap.put("data", temList);
		Map<String, Object> xAxisLabelMap = new HashMap<String, Object>();
//		xAxisLabelMap.put("interval", "0");
		xAxisLabelMap.put("rotate", "75");
		xAxisMap.put("axisLabel", xAxisLabelMap);
		xAxis.add(xAxisMap);
		lineAndBarOptionEntity.setxAxis(xAxis);

		// 构建y轴
		List<Map<String, Object>> yAxis = new ArrayList<Map<String, Object>>();
		Map<String, Object> yAxisFirstMap = new HashMap<String, Object>();
		yAxisFirstMap.put("type", "value");
		yAxisFirstMap.put("name", "");
		Map<String, Boolean> yAxisFirstSplitLineMap = new HashMap<String, Boolean>();
		yAxisFirstSplitLineMap.put("show", true);
		yAxisFirstMap.put("splitLine", yAxisFirstSplitLineMap);
		Map<String, Object> yAxisSecondMap = new HashMap<String, Object>();
		yAxisSecondMap.put("type", "value");
		yAxisSecondMap.put("name", "");
		Map<String, Boolean> yAxisSecondSplitLineMap = new HashMap<String, Boolean>();
		yAxisSecondSplitLineMap.put("show", false);
		yAxisSecondMap.put("splitLine", yAxisSecondSplitLineMap);
		// 配置经济分析特殊图表的y轴样式
		List<String> boundaryGap = new ArrayList<String>();
		switch (id) {
		case "9":
			yAxisFirstMap.put("scale", "true");
			boundaryGap.add("0.1");
			boundaryGap.add("0.1");
			yAxisFirstMap.put("boundaryGap", boundaryGap);
			break;
		case "31":
			yAxisFirstMap.put("scale", "true");
			boundaryGap.add("0.3");
			boundaryGap.add("0.2");
			yAxisFirstMap.put("boundaryGap", boundaryGap);
			break;
		case "98":
		case "99":
			yAxisFirstMap.put("scale", "true");
			break;
		case "192":
			yAxisFirstMap.put("scale", "true");
			boundaryGap.add("0.00001");
			boundaryGap.add("0");
			yAxisFirstMap.put("boundaryGap", boundaryGap);
			break;
		default:
			break;
		}
		
		// 构建series
		List<Map<String, Object>> seriesList = new ArrayList<Map<String, Object>>();
		int z = 3;
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
				seriesListMap.put("yAxisIndex", "0");
				// 配置经济分析单位
				yAxisFirstMap.put("name", unitName.get(i));
			} else {
				seriesListMap.put("smooth", "0.6");
				seriesListMap.put("name", legendData.get(i));
				seriesListMap.put("type", showTypeString);
				seriesListMap.put("data", tempList);
				seriesListMap.put("yAxisIndex", "1");
				// 配置经济分析单位
				yAxisSecondMap.put("name", unitName.get(i));
				seriesListMap.put("z", z + i);
				seriesListMap.put("connectNulls", true); // 折线图连接空数据
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
		lineAndBarOptionEntity.setSeries(seriesList);
		// 进出口总额及增速（综合）中个别图例的y轴单位是【千美元】需要换算
		System.out.println("id"+id);
		if (id.equals("213") || id.equals("215") || id.equals("217")) {
			System.out.println("iddddd"+id);
			yAxisFirstMap.put("name", "千美元");
		}
		Map<String, Object> yAxisLabelMap = new HashMap<String, Object>();
		yAxisLabelMap.put("formatter", "{value}");
		yAxisFirstMap.put("axisLabel", yAxisLabelMap);
		yAxis.add(yAxisFirstMap);
		yAxis.add(yAxisSecondMap);
		lineAndBarOptionEntity.setyAxis(yAxis);

		// 设置图例对象
		LineAndBarEntity lineAndBarEntity = new LineAndBarEntity(id, title, lineAndBarOptionEntity);
		int classHeight = 350 + legendHeight + (legendData.size() > 5 ? 50 : 20);
		lineAndBarEntity.setClassHeight(String.valueOf(classHeight));
		return lineAndBarEntity;
	}
}
