package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuhan_data.app.showType.pojo.DoubleXaxisLineEntity;
import com.wuhan_data.app.showType.pojo.DoubleXaxisLineOptionEntity;

public class DoubleXaxisLineType {
	String type="line";
	//参数：id、指标或版块名字、图例名称列表、双x轴数据、指标数据
	public DoubleXaxisLineEntity getOption(String id,String title,List<List<String>> dataX,List<String> legendData,List<List<String>> data) {
		DoubleXaxisLineOptionEntity doubleXaxisLineOptionEntity = new DoubleXaxisLineOptionEntity();
		Map<String,Object> gridMap = new HashMap<String,Object>();
		gridMap.put("containLabel", true);
		doubleXaxisLineOptionEntity.setGrid(gridMap);
		
		Map<String,Object> toolTipMap = new HashMap<String,Object>();
		toolTipMap.put("show", true);
		toolTipMap.put("trigger", "axis");
		doubleXaxisLineOptionEntity.setTooltip(toolTipMap);
		
		Map<String,Object> legendMap = new HashMap<String,Object>();
		legendMap.put("top", "top");
		legendMap.put("data", legendData);
		doubleXaxisLineOptionEntity.setLegend(legendMap);
		
		List<Map<String,Object>> xAxis = new ArrayList<Map<String,Object>>();
		for(int i=0;i<dataX.size();i++)
		{
			List<String> temList= new ArrayList<String>();
			temList =  dataX.get(i);
			Map<String,Object> xAxisMap = new HashMap<String,Object>();
			xAxisMap.put("type", "category");
			xAxisMap.put("name","");
			xAxisMap.put("data",temList );
			xAxis.add(xAxisMap);
			i++;
			List<String> temList1= new ArrayList<String>();
			temList1 =  dataX.get(i);
			Map<String,Object> xAxisSecondMap = new HashMap<String,Object>();
			xAxisSecondMap.put("type", "category");
			xAxisSecondMap.put("name","");
			xAxisSecondMap.put("data",temList1);
			xAxis.add(xAxisSecondMap);
		}
		doubleXaxisLineOptionEntity.setxAxis(xAxis);
		
		List<Map<String,Object>> yAxis = new ArrayList<Map<String,Object>>();
		Map<String,Object> yAxisMap = new HashMap<String,Object>();
		yAxisMap.put("type", "value");
		yAxisMap.put("name","");
		yAxis.add(yAxisMap);
		doubleXaxisLineOptionEntity.setyAxis(yAxis);
		
		List<Map<String,Object>> seriesList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<data.size();i++)
		{
			List<String> tempList= new ArrayList<String>();
			tempList=data.get(i);//数据
			String name= (String) legendData.get(i);
			Map<String,Object> seriesListMap = new HashMap<String,Object>();
			seriesListMap.put("name", name);
			seriesListMap.put("type", type);
			seriesListMap.put("data", tempList);
			seriesListMap.put("smooth", true);
			seriesList.add(seriesListMap);
		}
		doubleXaxisLineOptionEntity.setSeries(seriesList);
		
		DoubleXaxisLineEntity doubleXaxisLineEntity = new DoubleXaxisLineEntity(id, title, doubleXaxisLineOptionEntity);		

		return doubleXaxisLineEntity;
	}

}
