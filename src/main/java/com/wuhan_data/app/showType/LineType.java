package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuhan_data.app.showType.pojo.LineEntity;
import com.wuhan_data.app.showType.pojo.LineOptionEntity;

// 折线图or趋势图
public class LineType {
	String type="line";
	
	//参数 板块id，板块名称，数组,值的数组
	//y轴的最大最小值根据数据再计算
//	public LineEntity getOption(String id,String title,List dataX,List dataV) {
	public LineEntity getOption(String id,String title,List<String> dataX,List<String> legendData,List<List<String>> dataV) {
		LineOptionEntity lineOptionEntity = new LineOptionEntity();
		Map<String,Object> gridMap = new HashMap<String,Object>();
		gridMap.put("containLabel", true);
		lineOptionEntity.setGrid(gridMap);

		Map<String,Object> toolTipMap=new HashMap<String,Object>();
		toolTipMap.put("show",true);
		toolTipMap.put("trigger","axis");
		lineOptionEntity.setTooltip(toolTipMap);
		
		Map<String,Object> maplegend=new HashMap<String,Object>();
		maplegend.put("data",legendData);
		lineOptionEntity.setLegend(maplegend);
		
//		Map xAxisMap = new HashMap();
//		xAxisMap.put("type", "category");
//		xAxisMap.put("data", dataX);
//		lineOptionEntity.setxAxis(xAxisMap);
		
		List<Map<String,Object>> xAxis = new ArrayList<Map<String,Object>>();
//		for(int i=0;i<dataV.size();i++)
//		{
			List<String> temList= new ArrayList<String>();
			temList =  dataX;
			Map<String,Object> xAxisMap = new HashMap<String,Object>();
			xAxisMap.put("type", "category");
			xAxisMap.put("name","");
			xAxisMap.put("data",temList );
			xAxis.add(xAxisMap);
//		}
		lineOptionEntity.setxAxis(xAxis);

		List<Map<String,Object>> yAxis = new ArrayList<Map<String,Object>>();
//		for(int i=0;i<dataV.size();i++) {
//			List temList1=new ArrayList();
//			temList1=dataV.get(i);
//			List temListDouble=new ArrayList();
//			System.out.println("dataV[i]"+temList1);
//			//由于不能将null强制转换，逐个取出做判断
//			
//			for(int j=0;j<temList1.size();j++) {
//				
////				System.out.println("null强制转换"+Double.parseDouble( (String) temList1.get(j)));
//				if(temList1.get(j)==null){
//					temListDouble.add(0.0);
//				}
//				else {
//					temListDouble.add(Double.parseDouble( (String) temList1.get(j)));
//					
//				}
//				
//				
//			}
//			Map yAxisMap = new HashMap();
//			yAxisMap.put("type", "value");
//			yAxisMap.put("name","");
//			System.out.print("temList:"+temList1.size());
//			double max= Collections.max(temListDouble);
//			double min= Collections.min(temListDouble);
////			double span=(Double.parseDouble(max)-Double.parseDouble(min))*1.2;
//			double space=(max-min)*0.1;
////			double maxd=Double.parseDouble(max);
////			double mind=Double.parseDouble(min);
//			int minL=(int) Math.floor(min-space);
//			int maxL=(int) Math.ceil(max+space);
//			
//			yAxisMap.put("min", minL);
//			yAxisMap.put("max", maxL);
//			yAxis.add(yAxisMap);
//		}
		Map<String,Object> yAxisMap = new HashMap<String,Object>();
		yAxisMap.put("type", "value");
		yAxisMap.put("name","");
		yAxis.add(yAxisMap);
		   lineOptionEntity.setyAxis(yAxis);
		
		   List<Map<String,Object>> seriesList=new ArrayList<Map<String,Object>>();
		   for(int i=0;i<dataV.size();i++) {
			   List<String> temList1= new ArrayList<String>();
			   temList1 =  dataV.get(i);
			   Map<String,Object> seriesListMap=new HashMap<String,Object>();
			   seriesListMap.put("name", legendData.get(i));
			   seriesListMap.put("type", type);
			   seriesListMap.put("data", temList1);
			   seriesList.add(seriesListMap);
		   }
		     
		     lineOptionEntity.setSeries(seriesList);

		LineEntity lineEntity = new LineEntity(id, title, lineOptionEntity);		
		
		return lineEntity;
	}

}
