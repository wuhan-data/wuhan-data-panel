package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wuhan_data.app.showType.pojo.BarEntity;
import com.wuhan_data.app.showType.pojo.BarOptionEntity;

//普通柱状图
public class BarType {
	String type="bar";
	//参数：指标或版块id、指标或版块名称、
		public BarEntity getOption(String id,String title,List<List> dataX,List legendData,List<List> data) {
			BarOptionEntity oe = new BarOptionEntity();
			System.out.println("legendList"+legendData);
			System.out.println("data"+data);
			System.out.println("dataX"+dataX);
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
			map2.put("data",legendData);
			
			
			oe.setLegend(map2);
			
			List<Map> xAxis = new ArrayList();
			for(int i=0;i<dataX.size();i++)
			{
				List temList= new ArrayList();
				temList =  dataX.get(i);
				Map map3 = new HashMap();
				map3.put("type", "category");
				map3.put("name","x轴");
				map3.put("data",temList );
				
				xAxis.add(map3);
			}
			System.out.println("xAxis"+xAxis);
			oe.setxAxis(xAxis);
			
//			List<Map> yAxis = new ArrayList();
//			for(int i=0;i<dataX.size();i++)
//			{
//				Map map4 = new HashMap();
//				map4.put("type", "value");
//				map4.put("name","y轴");
//				yAxis.add(map4);
//			}
//			oe.setyAxis(yAxis);
			List<Map> yAxis = new ArrayList();
			   for(int i=0;i<data.size();i++) {
			    List temList=new ArrayList();
			    temList=data.get(i);
			    Map map21 = new HashMap();
			    map21.put("type", "value");
			    map21.put("name","y轴");
			    String max=(String) Collections.max(temList);
			    String min=(String) Collections.min(temList);
//			    double span=(Double.parseDouble(max)-Double.parseDouble(min))*1.2;
			    double space=(Double.parseDouble(max)-Double.parseDouble(min))*0.1;
			    double maxd=Double.parseDouble(max);
			    double mind=Double.parseDouble(min);
			    double minL=(mind)-space;
			    double maxL=(maxd)+space; 
			    minL= (double) Math.round(minL * 100) / 100;
			    maxL= (double) Math.round(maxL * 100) / 100;
			    map21.put("min", minL);
			    map21.put("max", maxL);
			    yAxis.add(map21);
			   }
			      oe.setyAxis(yAxis);
			

			List<Map> seriesList=new ArrayList();
			for(int i=0;i<data.size();i++)
			{
				List tempList= new ArrayList();
				tempList=data.get(i);//数据
				Map map6 = new HashMap();
				map6.put("name", legendData.get(0));
				map6.put("type", type);
				map6.put("data", tempList);
				
				seriesList.add(map6);
			}
			
			oe.setSeries(seriesList);
			
			BarEntity pe = new BarEntity(id, title, oe);		
			
			return pe;
		}

}
