package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuhan_data.app.showType.pojo.RadarEntity;
import com.wuhan_data.app.showType.pojo.RadarOptionEntity;

//雷达图
public class RadarType {
	// 参数：图例名称列表、指标名称列表、数据
	public RadarEntity getOption(String id, String title, List<String> legendData, List<String> nameData,
			List<List<String>> data, List<List<String>> dataByTime) {

		RadarOptionEntity radarOptionEntity = new RadarOptionEntity();

		// 构建grid
		Map<String, Object> gridMap = new HashMap<String, Object>();
		gridMap.put("containLabel", true);
		radarOptionEntity.setGrid(gridMap);

		// 构建legend
		Map<String, Object> legendMap = new HashMap<String, Object>();
		legendMap.put("data", legendData);
		// 控制初始展示图例个数
		int showNum = 1;
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
		radarOptionEntity.setLegend(legendMap);

		// 构建radar
		Map<String, Object> radarMap = new HashMap<String, Object>();
		Map<String, Object> radarTextStyleMap = new HashMap<String, Object>();
		Map<String, Object> radarNameMap = new HashMap<String, Object>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < nameData.size(); i++) {
			List<String> temList = data.get(i);
			List<Double> temListDouble = new ArrayList<Double>();
			for (int j = 0; j < temList.size(); j++) {
				if (temList.get(j) == null) {
					temListDouble.add(0.0);
				} else {
					temListDouble.add(Double.parseDouble((String) temList.get(j)));
				}
			}
			Map<String, Object> mapi = new HashMap<String, Object>();
			double max = Collections.max(temListDouble);
			double maxd = max * 1.1;
			mapi.put("name", nameData.get(i));
			mapi.put("max", maxd);
			list1.add(mapi);
		}
		radarMap.put("radius", "50%");
		radarMap.put("shape", "circle");
		radarTextStyleMap.put("color", "#000000");
		radarTextStyleMap.put("fontSize", 10);
		radarNameMap.put("show", true);
		radarNameMap.put("textStyle", radarTextStyleMap);
		radarMap.put("name", radarNameMap);
		radarMap.put("indicator", list1);
		radarOptionEntity.setRadar(radarMap);

		// 构建series
		Map<String, Object> seriesListMap = new HashMap<String, Object>();
		Map<String, Object> showMap = new HashMap<String, Object>();
		Map<String, Object> normalMap = new HashMap<String, Object>();
		List<Map<String, Object>> listTem = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listSeries = new ArrayList<Map<String, Object>>();
		showMap.put("show", true);
		normalMap.put("normal", showMap);
		for (int i = 0; i < legendData.size(); i++) {
			Map<String, Object> seriesListDataMap = new HashMap<String, Object>();
			seriesListDataMap.put("label", normalMap);
			for (int j = 0; j < dataByTime.get(i).size(); j++) {
				if (dataByTime.get(i).get(j) == null) {
					dataByTime.get(i).set(j, "");
				}
			}
			seriesListDataMap.put("value", dataByTime.get(i));
			seriesListDataMap.put("name", legendData.get(i));
			listTem.add(seriesListDataMap);
		}
		seriesListMap.put("type", "radar");
		seriesListMap.put("data", listTem);
		listSeries.add(seriesListMap);
		radarOptionEntity.setSeries(listSeries);

		RadarEntity radarEntity = new RadarEntity(id, title, radarOptionEntity);
		return radarEntity;
	}

}
