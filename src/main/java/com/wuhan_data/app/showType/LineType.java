package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuhan_data.app.showType.pojo.LineEntity;
import com.wuhan_data.app.showType.pojo.LineOptionEntity;

// 折线图or趋势图
public class LineType {
	String type="line";
	
	//参数 板块id，板块名称，x轴数组,值的数组
	//y轴的最大最小值根据数据再计算
//	public LineEntity getOption(String id,String title,List dataX,List dataV) {
	public LineEntity getOption(String id,String title,List<List> dataX,List legendData,List<List> dataV) {
		LineOptionEntity oe = new LineOptionEntity();
		Map map = new HashMap();
		map.put("containLabel", true);
		oe.setGrid(map);

		Map mapTool=new HashMap();
		mapTool.put("show",true);
		mapTool.put("trigger","axis");
		oe.setTooltip(mapTool);
		
		Map maplegend=new HashMap();
		maplegend.put("data",legendData);
		oe.setLegend(maplegend);
		
//		Map map1 = new HashMap();
//		map1.put("type", "category");
//		map1.put("data", dataX);
//		oe.setxAxis(map1);
		
		List<Map> xAxis = new ArrayList();
		for(int i=0;i<dataX.size();i++)
		{
			List temList= new ArrayList();
			temList =  dataX.get(i);
			Map map1 = new HashMap();
			map1.put("type", "category");
			map1.put("name","x轴");
			map1.put("data",temList );
			xAxis.add(map1);
		}
		oe.setxAxis(xAxis);

		List<Map> yAxis = new ArrayList();
		for(int i=0;i<dataV.size();i++) {
			List temList=new ArrayList();
			temList=dataV.get(i);
			Map map2 = new HashMap();
			map2.put("type", "value");
			map2.put("name","y轴");
			String max=(String) Collections.max(temList);
			String min=(String) Collections.min(temList);
//			double span=(Double.parseDouble(max)-Double.parseDouble(min))*1.2;
			double space=(Double.parseDouble(max)-Double.parseDouble(min))*0.1;
			double maxd=Double.parseDouble(max);
			double mind=Double.parseDouble(min);
			int minL=(int)(mind)-(int)space;
			int maxL=(int)(maxd)+(int)space;
			
			map2.put("min", minL);
			map2.put("max", maxL);
			yAxis.add(map2);
		}
		   oe.setyAxis(yAxis);
		
		   List<Map> seriesList=new ArrayList();
		   for(int i=0;i<dataV.size();i++) {
			   List temList= new ArrayList();
			   temList =  dataV.get(i);
			   Map map3=new HashMap();
			   map3.put("name", legendData.get(i));
			   map3.put("type", type);
			   map3.put("data", temList);
			   seriesList.add(map3);
		   }
		     
		     oe.setSeries(seriesList);

		LineEntity le = new LineEntity(id, title, oe);		
		
		return le;
	}

}
