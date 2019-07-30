package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuhan_data.app.showType.pojo.LineEntity;
import com.wuhan_data.app.showType.pojo.LineOptionEntity;

public class TopicLineType {
	
String type="line";
	
	//参数 板块id，板块名称，数组,值的数组
	//y轴的最大最小值根据数据再计算
//	public LineEntity getOption(String id,String title,List dataX,List dataV) {
	public LineEntity getOption(String id,String title,List<String> dataX,List<String> legendData,List<List<Float>> dataV,float min,float max) {
		LineOptionEntity oe = new LineOptionEntity();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("containLabel", true);
		oe.setGrid(map);

		Map<String,Object> mapTool=new HashMap<String,Object>();
		mapTool.put("show",true);
		mapTool.put("trigger","axis");
		oe.setTooltip(mapTool);	
		
		Map<String,Object> maplegend=new HashMap<String,Object>();
		maplegend.put("data",legendData);
		oe.setLegend(maplegend);
		
		Map<String,Object> map1 = new HashMap<String,Object>();
		List<Map<String,Object>> xAxis = new ArrayList<Map<String,Object>>();
		map1.put("type", "category");
		map1.put("name", "");
		map1.put("name","");
		map1.put("data", dataX);
		xAxis.add(map1);
		oe.setxAxis(xAxis);
		
		List<Map<String,Object>> yAxis = new ArrayList<Map<String,Object>>();

			Map<String,Object> map2 = new HashMap<String,Object>();
			map2.put("type", "value");	
			map2.put("min", min);
			map2.put("max", max);
			map2.put("name","");			
			yAxis.add(map2);
		   oe.setyAxis(yAxis);

		   
		   
		   
		   
		   
//		   List<Map> seriesList=new ArrayList();
//			   Map map3=new HashMap();
//			   map3.put("name", legendData.get(0));
//			   map3.put("type", type);
//			   map3.put("data", dataV);
//			   seriesList.add(map3);		     
//		     oe.setSeries(seriesList);
//		     
//		     
//		     
//		     
				List<Map<String,Object>> seriesList=new ArrayList<Map<String,Object>>();
				for(int i=0;i<dataV.size();i++)
				{
						Map<String,Object> map6 = new HashMap<String,Object>();
						map6.put("name", legendData.get(i));
						map6.put("type", "line");
						map6.put("data", dataV.get(i));
					seriesList.add(map6);
				}			
				oe.setSeries(seriesList);

		LineEntity le = new LineEntity(id, title, oe);		

		return le;
	}


}
