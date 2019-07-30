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
	public PointEntity getOption(String id,String title,List<String> nameData,List<List<String>> data) {
		PointOptionEntity pointOptionEntity = new PointOptionEntity();
		Map<String,Object> gridMap = new HashMap<String,Object>();
		gridMap.put("containLabel", true);
		pointOptionEntity.setGrid(gridMap);
		
		Map<String,Object> toolTipMap = new HashMap<String,Object>();
		toolTipMap.put("show", true);
		toolTipMap.put("trigger", "item");
		toolTipMap.put("snap", true);
		toolTipMap.put("formatter","({c0})");
		pointOptionEntity.setTooltip(toolTipMap);
		
		Map<String,Object> legendMap = new HashMap<String,Object>();
		legendMap.put("top", "top");
		pointOptionEntity.setLegend(legendMap);
		
		Map<String,Object> xAxisMap = new HashMap<String,Object>();
		xAxisMap.put("name","");
		pointOptionEntity.setxAxis(xAxisMap);
		
		Map<String,Object> yAxisMap = new HashMap<String,Object>();
		yAxisMap.put("name","");
		pointOptionEntity.setyAxis(yAxisMap);
		
		List<Map<String,Object>> seriesList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<data.size();i++)
		{
			List<String> tempList= new ArrayList<String>();
			List<String> tempListSecond=new ArrayList<String>();
//			List<String> tempListDataX=new ArrayList<String>();
			tempList=data.get(i);//数据
			i++;
			tempListSecond=data.get(i);
//			tempListDataX=dataX.get(i);
			List<List<String>> dataTotal=new ArrayList<List<String>>();
			
			Map<String,Object> seriesListMap = new HashMap<String,Object>();
			seriesListMap.put("symbolSize", 10);
			seriesListMap.put("name", nameData.get(i));
			seriesListMap.put("type", type);
			for(int j=0;j<tempList.size();j++) {
				List<String> data_each=new ArrayList<String>();
//				data_each.add(tempListDataX.get(j));
				data_each.add(tempList.get(j));
				data_each.add(tempListSecond.get(j));
				dataTotal.add(data_each);
			}
			seriesListMap.put("data", dataTotal);
			
			seriesList.add(seriesListMap);
		}
		pointOptionEntity.setSeries(seriesList);
		
		PointEntity pointEntity = new PointEntity(id, title, pointOptionEntity);		

		return pointEntity;
	}

}
