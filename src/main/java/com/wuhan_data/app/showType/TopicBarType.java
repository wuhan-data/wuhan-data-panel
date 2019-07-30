package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wuhan_data.app.showType.pojo.BarEntity;
import com.wuhan_data.app.showType.pojo.BarOptionEntity;

//普通柱状图
public class TopicBarType {
	String type="bar";
	//参数：指标或版块id、指标或版块名称、
		public BarEntity getOption(String id,String title,List<String> dataX,List<String> legendData,List<List<Float>> data) {
			BarOptionEntity oe = new BarOptionEntity();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("containLabel", true);
			oe.setGrid(map);
			
			Map<String,Object> map1 = new HashMap<String,Object>();
			map1.put("show", true);
			map1.put("trigger", "axis");
			map1.put("snap", true);
			oe.setTooltip(map1);
			
			Map<String,Object> map2 = new HashMap<String,Object>();
			map2.put("top", "top");
			map2.put("data", legendData);
			oe.setLegend(map2);
			
			List<Map<String,Object>> xAxis = new ArrayList<Map<String,Object>>();

				Map<String,Object> map3 = new HashMap<String,Object>();
				map3.put("type", "category");
				map3.put("name","");
				map3.put("data",dataX );
				xAxis.add(map3);
			oe.setxAxis(xAxis);
			
			List<Map<String,Object>> yAxis = new ArrayList<Map<String,Object>>();
				Map<String,Object> map4 = new HashMap<String,Object>();
				map4.put("type", "value");
				map4.put("name","");
				yAxis.add(map4);
			oe.setyAxis(yAxis);
			

			List<Map<String,Object>> seriesList=new ArrayList<Map<String,Object>>();
			for(int i=0;i<data.size();i++)
			{
				Map<String,Object> map6 = new HashMap<String,Object>();
				map6.put("name", legendData.get(i));
				map6.put("type", type);
				map6.put("data", data.get(i));
				seriesList.add(map6);
			}
			oe.setSeries(seriesList);
			
			BarEntity pe = new BarEntity(id, title, oe);		

			return pe;
		}

}
