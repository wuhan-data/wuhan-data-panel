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

	// 参数：图例名称列表、x轴数据、数据
	public BarStoreEntity getOption(String id, String title, List<String> dataX, List<String> legendData,
			List<List<String>> data) {
		BarStoreOptionEntity oe = new BarStoreOptionEntity();
		Map map = new HashMap();
		map.put("height", "320");
		map.put("bottom", "50");
		map.put("containLabel", true);
		oe.setGrid(map);

		Map map1 = new HashMap();
		Map map11 = new HashMap();
		map1.put("show", true);
		map1.put("trigger", "axis");
		map1.put("snap", true);
		map11.put("type", "cross");
		map1.put("axisPointer", map11);
		oe.setTooltip(map1);

		Map map2 = new HashMap();
		map2.put("bottom", "420");
		map2.put("orient", "vertical");
		map2.put("data", legendData);
		oe.setLegend(map2);

		List<Map> xAxis = new ArrayList();
//		for(int i=0;i<dataX.size();i++)
//		{
		List temList = new ArrayList();
		temList = dataX;
		Map map3 = new HashMap();
		map3.put("type", "category");
		map3.put("name", "x轴");
		map3.put("data", temList);
		xAxis.add(map3);
//		}
		oe.setxAxis(xAxis);

		List<Map> yAxis = new ArrayList();
//		for(int i=0;i<dataX.size();i++)
//		{
		Map map4 = new HashMap();
		map4.put("type", "value");
		map4.put("name", "y轴");
		yAxis.add(map4);
//		}
		oe.setyAxis(yAxis);

		List<Map> seriesList = new ArrayList();
		for (int i = 0; i < data.size(); i++) {
			List tempList = new ArrayList();
			tempList = data.get(i);// 数据
			Map map6 = new HashMap();
			map6.put("name", legendData.get(i));
			map6.put("type", type);
			map6.put("stack", "三次产业");
			map6.put("data", tempList);
			seriesList.add(map6);
		}

		oe.setSeries(seriesList);

		BarStoreEntity pe = new BarStoreEntity(id, title, oe);

		return pe;
	}

}
