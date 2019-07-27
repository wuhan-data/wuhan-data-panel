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
		RadarOptionEntity oe = new RadarOptionEntity();
		Map map = new HashMap();
		map.put("containLabel", true);
		oe.setGrid(map);

		Map map1 = new HashMap();
		map1.put("data", legendData);
		oe.setLegend(map1);

		Map map2 = new HashMap();

		Map mapi1 = new HashMap();
		Map mapi2 = new HashMap();
		List list1 = new ArrayList();
		for (int i = 0; i < nameData.size(); i++) {
			List temList = data.get(i);
			List temListDouble = new ArrayList();
			System.out.println(temList.toString());
			for (int j = 0; j < temList.size(); j++) {
				if (temList.get(j) == null) {
					temListDouble.add(0.0);
				} else {
					temListDouble.add(Double.parseDouble((String) temList.get(j)));
				}
			}
			Map mapi = new HashMap();
			double max = Collections.max(temListDouble);
			double maxd = max * 1.1;
			mapi.put("name", nameData.get(i));
			mapi.put("max", maxd);
			list1.add(mapi);
		}
		map2.put("radius", "70%");
		map2.put("shape", "circle");
		mapi1.put("color", "#000000");
		mapi1.put("fontSize", 10);
		mapi2.put("show", true);
		mapi2.put("textStyle", mapi1);
		map2.put("name", mapi2);
		map2.put("indicator", list1);
		oe.setRadar(map2);

		Map map3 = new HashMap();
		Map mapTem1 = new HashMap();
		Map mapTem2 = new HashMap();
		List listTem = new ArrayList();
		List listSeries = new ArrayList();
		mapTem1.put("show", true);
		mapTem2.put("normal", mapTem1);
		for (int i = 0; i < legendData.size(); i++) {
			Map mapTem3 = new HashMap();
			mapTem3.put("label", mapTem2);
			
			
			for (int j = 0; j < dataByTime.get(i).size(); j++) {
				if (dataByTime.get(i).get(j) == null) {
					dataByTime.get(i).set(j, "");
				}
			}
			mapTem3.put("value", dataByTime.get(i));
			mapTem3.put("name", legendData.get(i));
			listTem.add(mapTem3);
		}
		map3.put("type", "radar");
		map3.put("data", listTem);
		listSeries.add(map3);
		oe.setSeries(listSeries);

		/**
		 * 
		 * 
		 * 
		 */

		RadarEntity pe = new RadarEntity(id, title, oe);

		return pe;
	}

}
