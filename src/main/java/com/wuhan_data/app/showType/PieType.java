package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wuhan_data.app.showType.pojo.LineEntity;
import com.wuhan_data.app.showType.pojo.LineOptionEntity;
import com.wuhan_data.app.showType.pojo.PieEntity;
import com.wuhan_data.app.showType.pojo.PieOptionEntity;

//饼图
public class PieType {
	String type="pie";
	//参数：指标或版块id、指标或版块名称、
//	public PieEntity getOption(String id,String title,List<Map> dataV) {
//		PieOptionEntity oe = new PieOptionEntity();
//		Map map = new HashMap();
//		map.put("containLabel", true);
//		oe.setGrid(map);
//
//		
//		Map map1 = new HashMap();
//		map1.put("top", "bottom");
//		List<String> legendData= new ArrayList();
//		for(int i=0;i<dataV.size();i++)
//		{
//			Set ld=dataV.get(i).keySet();
//			Iterator<String> it = ld.iterator();
//			String key="";
//			while(it.hasNext())
//			{
//				key=it.next();
//			}
//			legendData.add(key);
//		}
//		map1.put("data", legendData);
//		oe.setLegend(map1);;
//		
//		Map map3 = new HashMap();
//		map3.put("type", type);
//		map3.put("radius", "50%");
//		List<String> center= new ArrayList();
//		center.add("50%");
//		center.add("50%");
//		map3.put("center", center);
//		map3.put("data", dataV);
//		oe.setSeries(map3);
//
//		PieEntity pe = new PieEntity(id, title, oe);		
//
//		return pe;
//	}
	public PieEntity getOption(String id,String title,List dataV,List legend) {
		PieOptionEntity oe = new PieOptionEntity();
		Map map = new HashMap();
		map.put("containLabel", true);
		oe.setGrid(map);
		
		Map map1=new HashMap();
		map1.put("show",true);
		map1.put("trigger","item");
		oe.setTooltip(map1);
		
		Map map2=new HashMap();
		map2.put("top","top");
		map2.put("data",legend);
		oe.setLegend(map2);
		
		Map map3=new HashMap();
		List<Map> listTotal=new ArrayList();
		List listData=new ArrayList();
		for(int i=0;i<dataV.size();i++) {
			Map mapData=new HashMap();
			mapData.put("value",dataV.get(i));
			mapData.put("name",legend.get(i));
			listData.add(mapData);
		 
		}
		map3.put("type","pie");
		map3.put("radius","50%");
		List center=new ArrayList();
		center.add("50%");
		center.add("50%");
		map3.put("center",center);
		map3.put("data",listData);
		listTotal.add(map3);
		oe.setSeries(listTotal);
		PieEntity pe = new PieEntity(id, title, oe);
		return pe;
	}
}
