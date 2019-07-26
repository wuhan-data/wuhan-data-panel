package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuhan_data.app.showType.pojo.BarEntity;
import com.wuhan_data.app.showType.pojo.BarOptionEntity;
import com.wuhan_data.app.showType.pojo.DoubleXaxisLineEntity;
import com.wuhan_data.app.showType.pojo.DoubleXaxisLineOptionEntity;

public class DoubleXaxisLineType {
	String type="line";
	//参数：id、指标或版块名字、图例名称列表、双x轴数据、指标数据
	public DoubleXaxisLineEntity getOption(String id,String title,List<List<String>> dataX,List<String> legendData,List<List<String>> data) {
		DoubleXaxisLineOptionEntity oe = new DoubleXaxisLineOptionEntity();
		Map map = new HashMap();
		map.put("containLabel", true);
		oe.setGrid(map);
		
		Map map1 = new HashMap();
		map1.put("show", true);
		map1.put("trigger", "axis");
		oe.setTooltip(map1);
		
		Map map2 = new HashMap();
		map2.put("top", "top");
		map2.put("data", legendData);
		oe.setLegend(map2);
		
		List<Map> xAxis = new ArrayList();
		for(int i=0;i<dataX.size();i++)
		{
			List temList= new ArrayList();
			temList =  dataX.get(i);
			Map map3 = new HashMap();
			map3.put("type", "category");
			map3.put("name","x0轴");
			map3.put("data",temList );
			xAxis.add(map3);
			i++;
			List temList1= new ArrayList();
			temList1 =  dataX.get(i);
			Map map4 = new HashMap();
			map4.put("type", "category");
			map4.put("name","x1轴");
			map4.put("data",temList1);
			xAxis.add(map4);
		}
		oe.setxAxis(xAxis);
		
		List<Map> yAxis = new ArrayList();
		Map map5 = new HashMap();
		map5.put("type", "value");
		map5.put("name","y轴");
		yAxis.add(map5);
		oe.setyAxis(yAxis);
		
		List<Map> seriesList=new ArrayList();
		for(int i=0;i<data.size();i++)
		{
			List tempList= new ArrayList();
			tempList=data.get(i);//数据
			String name= (String) legendData.get(i);
			Map map6 = new HashMap();
			map6.put("name", name);
			map6.put("type", type);
			map6.put("data", tempList);
			map6.put("smooth", true);
			seriesList.add(map6);
		}
		oe.setSeries(seriesList);
		
		DoubleXaxisLineEntity pe = new DoubleXaxisLineEntity(id, title, oe);		

		return pe;
	}

}
