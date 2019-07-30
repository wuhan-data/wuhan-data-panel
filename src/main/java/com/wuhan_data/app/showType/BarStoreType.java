package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuhan_data.app.showType.pojo.BarStoreEntity;
import com.wuhan_data.app.showType.pojo.BarStoreOptionEntity;

//堆积柱状图
public class BarStoreType {
	String type = "bar";

	// 参数：图例名称列表、数据、数据
	public BarStoreEntity getOption(String id, String title, List<String> dataX, List<String> legendData,
			List<List<String>> data) {
		BarStoreOptionEntity barStoreOptionEntity = new BarStoreOptionEntity();
		Map<String,Object> gridMap = new HashMap<String,Object>();
		gridMap.put("height", "320");
		gridMap.put("bottom", "50");
		gridMap.put("containLabel", true);
		barStoreOptionEntity.setGrid(gridMap);

		Map<String,Object> toolTipMap = new HashMap<String,Object>();
		Map<String,Object> toolTipTypeMap = new HashMap<String,Object>();
		toolTipMap.put("show", true);
		toolTipMap.put("trigger", "axis");
		toolTipMap.put("snap", true);
		toolTipTypeMap.put("type", "cross");
		toolTipMap.put("axisPointer", toolTipTypeMap);
		barStoreOptionEntity.setTooltip(toolTipMap);

		Map<String,Object> legendDataMap = new HashMap<String,Object>();
		legendDataMap.put("bottom", "420");
		legendDataMap.put("orient", "vertical");
		legendDataMap.put("data", legendData);
		barStoreOptionEntity.setLegend(legendDataMap);

		List<Map<String,Object>> xAxis = new ArrayList<Map<String,Object>>();
//		for(int i=0;i<dataX.size();i++)
//		{
		List<String> temList = new ArrayList<String>();
		temList = dataX;
		Map<String,Object> xAxisMap = new HashMap<String,Object>();
		xAxisMap.put("type", "category");
		xAxisMap.put("name", "");
		xAxisMap.put("data", temList);
		xAxis.add(xAxisMap);
//		}
		barStoreOptionEntity.setxAxis(xAxis);

		List<Map<String,Object>> yAxis = new ArrayList<Map<String,Object>>();
//		for(int i=0;i<dataX.size();i++)
//		{
		Map<String,Object> yAxisMap = new HashMap<String,Object>();
		yAxisMap.put("type", "value");
		yAxisMap.put("name", "");
		yAxis.add(yAxisMap);
//		}
		barStoreOptionEntity.setyAxis(yAxis);

		List<Map<String,Object>> seriesList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < data.size(); i++) {
			List<String> tempList = new ArrayList<String>();
			tempList = data.get(i);// 数据
			Map<String,Object> seriesListMap = new HashMap<String,Object>();
			seriesListMap.put("name", legendData.get(i));
			seriesListMap.put("type", type);
			seriesListMap.put("stack", "三次产业");
			seriesListMap.put("data", tempList);
			seriesList.add(seriesListMap);
		}

		barStoreOptionEntity.setSeries(seriesList);

		BarStoreEntity barStoreEntity = new BarStoreEntity(id, title, barStoreOptionEntity);

		return barStoreEntity;
	}

}
