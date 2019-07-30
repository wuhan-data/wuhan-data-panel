package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wuhan_data.app.showType.pojo.BarEntity;
import com.wuhan_data.app.showType.pojo.BarOptionEntity;

//普通柱状图
public class BarType {
	String type="bar";
	//参数：指标或版块id、指标或版块名称、
		public BarEntity getOption(String id,String title,List<String> dataX,List<String> legendData,List<List<String>> data) {
			BarOptionEntity barOptionEntity = new BarOptionEntity();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("containLabel", true);
			barOptionEntity.setGrid(map);
			
			Map<String,Object> toolTipMap = new HashMap<String,Object>();
			toolTipMap.put("show", true);
			toolTipMap.put("trigger", "axis");
			toolTipMap.put("snap", true);
			barOptionEntity.setTooltip(toolTipMap);
			
			Map<String,Object> legendMap = new HashMap<String,Object>();
			legendMap.put("top", "top");
			legendMap.put("data", legendData);
			barOptionEntity.setLegend(legendMap);
			
			List<Map<String,Object>> xAxis = new ArrayList<Map<String,Object>>();
//			List<List> dataList=new ArrayList();
			
			Map<String,Object> xAxisMap = new HashMap<String,Object>();
//			for(int i=0;i<dataX.size();i++)
//			{
//				List temList= new ArrayList();
//				temList =  dataX.get(i);
//				dataList.add(temList);
//				Map xAxisMap = new HashMap();
//				xAxisMap.put("type", "category");
//				xAxisMap.put("name","");
//				xAxisMap.put("data",temList );
//				xAxis.add(xAxisMap);
//			}
			xAxisMap.put("type", "category");
			xAxisMap.put("name","");
			xAxisMap.put("scale", "true");
			xAxisMap.put("data",dataX);
			xAxis.add(xAxisMap);
			
			barOptionEntity.setxAxis(xAxis);
			
			List<Map<String,Object>> yAxis = new ArrayList<Map<String,Object>>();
//			for(int i=0;i<dataX.size();i++)
//			{
				Map<String,Object> yAxisMap = new HashMap<String,Object>();
				yAxisMap.put("type", "value");
				yAxisMap.put("name","");
				yAxis.add(yAxisMap);
//			}
			barOptionEntity.setyAxis(yAxis);
			

			List<Map<String,Object>> seriesList=new ArrayList<Map<String,Object>>();
			for(int i=0;i<data.size();i++)
			{
				List<String> tempList= new ArrayList<String>();
				tempList=data.get(i);//数据
				Map<String,Object> seriesListMap = new HashMap<String,Object>();
				seriesListMap.put("name", legendData.get(i));
				seriesListMap.put("type", type);
				seriesListMap.put("data", tempList);
				seriesList.add(seriesListMap);
			}
			barOptionEntity.setSeries(seriesList);
			
			BarEntity barEntity = new BarEntity(id, title, barOptionEntity);		

			return barEntity;
		}

}
