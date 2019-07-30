package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuhan_data.app.showType.pojo.LineAndBarEntity;
import com.wuhan_data.app.showType.pojo.LineAndBarOptionEntity;

//折柱混搭
public class LineAndBarType {
	//参数：图例名称、数据、数据、显示类型
	public LineAndBarEntity getOption(String id,String title,List<String> dataX,List<String> legendData,List<List<String>> data,List<String> showType) {
		LineAndBarOptionEntity lineAndBarOptionEntity = new LineAndBarOptionEntity();
		Map<String,Object> gridMap = new HashMap<String,Object>();
		gridMap.put("containLabel", true);
		lineAndBarOptionEntity.setGrid(gridMap);
		
		Map<String,Object> toolTipMap = new HashMap<String,Object>();
		toolTipMap.put("show", true);
		toolTipMap.put("trigger", "axis");
		toolTipMap.put("snap", true);
		lineAndBarOptionEntity.setTooltip(toolTipMap);
		
		Map<String,Object> legendMap = new HashMap<String,Object>();
		legendMap.put("top", "top");
		legendMap.put("data", legendData);
		lineAndBarOptionEntity.setLegend(legendMap);
		
		List<Map<String,Object>> xAxis = new ArrayList<Map<String,Object>>();
//		for(int i=0;i<dataX.size();i++)
//		{
			List<String> temList= new ArrayList<String>();
			temList =  dataX;
			Map<String,Object> xAxisMap = new HashMap<String,Object>();
			xAxisMap.put("type", "category");
			xAxisMap.put("name","");
			xAxisMap.put("data",temList );
			xAxis.add(xAxisMap);
//		}
		lineAndBarOptionEntity.setxAxis(xAxis);
		
		List<Map<String,Object>> yAxis = new ArrayList<Map<String,Object>>();
//		for(int i=0;i<dataX.size();i++)
//		{
			Map<String,Object> yAxisFirstMap = new HashMap<String,Object>();
			yAxisFirstMap.put("type", "value");
			yAxisFirstMap.put("name","");
			yAxis.add(yAxisFirstMap);
			
			Map<String,Object> yAxisSecondMap = new HashMap<String,Object>();
			yAxisSecondMap.put("type", "value");
			yAxisSecondMap.put("name","");
			yAxis.add(yAxisSecondMap);
//		}
		lineAndBarOptionEntity.setyAxis(yAxis);
		
		List<Map<String,Object>> seriesList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<data.size();i++)
		{
			List<String> tempList= new ArrayList<String>();
			tempList=data.get(i);//数据
			String showT= (String) showType.get(i);//展示类型
			Map<String,Object> seriesListMap = new HashMap<String,Object>();
			if(showT.equals("bar"))
			{
				seriesListMap.put("name", legendData.get(i));
				seriesListMap.put("type", showT);
				seriesListMap.put("data", tempList);
				seriesListMap.put("yAxisIndex", 1);
			}
			else
			{
				seriesListMap.put("name", legendData.get(i));
				seriesListMap.put("type", showT);
				seriesListMap.put("data", tempList);
			}
			seriesList.add(seriesListMap);
		}
		
		lineAndBarOptionEntity.setSeries(seriesList);
		
		LineAndBarEntity lineAndBarEntity = new LineAndBarEntity(id, title, lineAndBarOptionEntity);		

		return lineAndBarEntity;
	}

}
