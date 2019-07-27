package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuhan_data.app.showType.pojo.LineAndBarEntity;
import com.wuhan_data.app.showType.pojo.LineAndBarOptionEntity;

//折柱混搭
public class TopicLineAndBarType {
	//参数：图例名称、x轴数据、数据、显示类型
	public LineAndBarEntity getOption(String id,String title,List<String> dataX,List<String> legendData,List<List<Float>> data,List showType) {
		
		LineAndBarOptionEntity oe = new LineAndBarOptionEntity();
		Map map11 = new HashMap();
		map11.put("type", "cross");
		
		Map map1 = new HashMap();
		map1.put("show", true);
		map1.put("trigger", "axis");
		map1.put("snap", true);
		map1.put("axisPointer", map11);
		oe.setTooltip(map1);
		
		
		if(legendData.size()>=4) {
			
			Map map = new HashMap();
			map.put("containLabel", true);
			map.put("height", "330");
			map.put("bottom", "50");
			oe.setGrid(map);
			
			Map map2 = new HashMap();
			map2.put("orient", "vertical");
			if(legendData.size()==4) {
				map2.put("bottom", "420");
			}else {
				map2.put("bottom", "430");
			}
			
			map2.put("data", legendData);
			oe.setLegend(map2);
		}else {
			Map map = new HashMap();
			map.put("containLabel", true);
			oe.setGrid(map);
			
			Map map2 = new HashMap();
			map2.put("top", "top");
			map2.put("data", legendData);
			oe.setLegend(map2);
		}
		
		
		List<Map> xAxis = new ArrayList();

			Map map3 = new HashMap();
			map3.put("type", "category");
			map3.put("name","x轴");
			map3.put("data",dataX );
			xAxis.add(map3);

		oe.setxAxis(xAxis);
		
		List<Map> yAxis = new ArrayList();

			Map map4 = new HashMap();
			map4.put("type", "value");
			map4.put("name","y0轴");
			yAxis.add(map4);
			
			Map map5 = new HashMap();
			map5.put("type", "value");
			map5.put("name","y1轴");
			yAxis.add(map5);

		oe.setyAxis(yAxis);
		
		List<Map> seriesList=new ArrayList();
		for(int i=0;i<data.size();i++)
		{
			List tempList= new ArrayList();
			tempList=data.get(i);//数据
			String showT= (String) showType.get(i);//展示类型
			Map map6 = new HashMap();
			if(showT.equals("bar"))
			{
				map6.put("name", legendData.get(i));
				map6.put("type", showT);
				map6.put("data", tempList);
				map6.put("yAxisIndex", 1);
			}
			else
			{
				map6.put("name", legendData.get(i));
				map6.put("type", showT);
				map6.put("data", tempList);
			}
			seriesList.add(map6);
		}
		
		oe.setSeries(seriesList);
		
		LineAndBarEntity pe = new LineAndBarEntity(id, title, oe);
		if(legendData.size()>4) {
			pe.setClassHeight("630");
		}
		if(legendData.size()==4){
			pe.setClassHeight("560");
		}
		return pe;
	}

}
