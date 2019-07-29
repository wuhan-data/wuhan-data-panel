package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuhan_data.app.showType.pojo.BarStackLineEntity;
import com.wuhan_data.app.showType.pojo.BarStackLineOptionEntity;

public class BarStackLineType {
	// 参数：图例名称列表、x轴数据、数据、展示类型
	public BarStackLineEntity getOption(String id, String title, List<String> dataX, List<String> legendData,
			List<List<String>> data, List<String> showType) {
		BarStackLineOptionEntity barStackLineOptionEntity = new BarStackLineOptionEntity();
		Map<String,Object> mapGrid = new HashMap<String,Object>();
		mapGrid.put("height", "320");
		mapGrid.put("bottom", "50");
		mapGrid.put("containLabel", true);
		barStackLineOptionEntity.setGrid(mapGrid);

		Map<String,Object> mapTooltip = new HashMap<String,Object>();
		Map<String,Object> mapTooltipType = new HashMap<String,Object>();
		mapTooltip.put("show", true);
		mapTooltip.put("trigger", "axis");
		mapTooltip.put("snap", true);
		mapTooltipType.put("type", "cross");
		mapTooltip.put("axisPointer", mapTooltipType);
		barStackLineOptionEntity.setTooltip(mapTooltip);

		Map<String,Object> mapLegend = new HashMap<String,Object>();
		mapLegend.put("bottom", "420");
		mapLegend.put("orient", "vertical");
		mapLegend.put("data", legendData);
		barStackLineOptionEntity.setLegend(mapLegend);

		List<Map<String,Object>> xAxis = new ArrayList<Map<String,Object>>();
//		for(int i=0;i<dataX.size();i++)
//		{
		List<String> temList = new ArrayList<String>();
		temList = dataX;
		Map<String,Object> mapxAis = new HashMap<String,Object>();
		mapxAis.put("type", "category");
		mapxAis.put("name", "x轴");
		mapxAis.put("data", temList);
		xAxis.add(mapxAis);
//		}
		barStackLineOptionEntity.setxAxis(xAxis);

		List<Map<String,Object>> yAxis = new ArrayList<Map<String,Object>>();
//		for(int i=0;i<dataX.size();i++)
//		{
		Map<String,Object> mapFirst_yAxis = new HashMap<String,Object>();
		mapFirst_yAxis.put("type", "value");
		mapFirst_yAxis.put("name", "y0轴");
		yAxis.add(mapFirst_yAxis);

		Map<String,Object> mapsecond_yAxis = new HashMap<String,Object>();
		mapsecond_yAxis.put("type", "value");
		mapsecond_yAxis.put("name", "y1轴");
		yAxis.add(mapsecond_yAxis);
//		}
		barStackLineOptionEntity.setyAxis(yAxis);

		List<Map<String,Object>> seriesList = new ArrayList<Map<String,Object>>();
		
		for (int i = 0; i < data.size(); i++) {
			List<String> tempList = new ArrayList<String>();
			tempList = data.get(i);// 数据
			String showT = (String) showType.get(i);// 展示类型
			Map<String,Object> mapSeriesList = new HashMap<String,Object>();
			if (showT.equals("line")) {
				mapSeriesList.put("name", legendData.get(i));
				mapSeriesList.put("type", showT);
				mapSeriesList.put("data", tempList);
				mapSeriesList.put("yAxisIndex", 1);
			} else {
				mapSeriesList.put("name", legendData.get(i));
				mapSeriesList.put("type", showT);
				mapSeriesList.put("stack", "广告");
				mapSeriesList.put("data", tempList);
			}
			seriesList.add(mapSeriesList);
		}

		barStackLineOptionEntity.setSeries(seriesList);

		BarStackLineEntity barStackLineEntity = new BarStackLineEntity(id, title, barStackLineOptionEntity);

		return barStackLineEntity;
	}

}
