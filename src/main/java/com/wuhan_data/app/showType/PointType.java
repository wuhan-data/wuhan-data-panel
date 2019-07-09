package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuhan_data.app.showType.pojo.PointEntity;
import com.wuhan_data.app.showType.pojo.PointOptionEntity;

//散点图
public class PointType {
	String type="scatter";
	//参数：id、名称、数据名称、
	public PointEntity getOption(String id,String title,List nameData,List<List> data) {
		PointOptionEntity oe = new PointOptionEntity();
		Map map = new HashMap();
		map.put("containLabel", true);
		oe.setGrid(map);
		
		Map map1 = new HashMap();
		map1.put("show", true);
		map1.put("trigger", "axis");
		map1.put("snap", true);
		oe.setTooltip(map1);
		
		Map map2 = new HashMap();
		map2.put("top", "top");
		oe.setLegend(map2);
		
		Map map3 = new HashMap();
		map3.put("name","x轴");
		oe.setxAxis(map3);
		
		Map map4 = new HashMap();
		map4.put("name","y轴");
		oe.setyAxis(map4);
		
		List<Map> seriesList=new ArrayList();
		for(int i=0;i<data.size();i++)
		{
			List tempList= new ArrayList();
			tempList=data.get(i);//数据
			Map map5 = new HashMap();
			map5.put("symbolSize", 10);
			map5.put("name", nameData.get(i));
			map5.put("data", tempList);
			map5.put("type", type);
			seriesList.add(map5);
		}
		oe.setSeries(seriesList);
		
		PointEntity pe = new PointEntity(id, title, oe);		

		return pe;
	}

}
