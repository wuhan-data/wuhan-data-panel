package com.wuhan_data.app.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wuhan_data.app.mapper.AnalysisMapper;
import com.wuhan_data.app.service.PlateAnalysisService;
import com.wuhan_data.app.showType.BarStackLineType;
import com.wuhan_data.app.showType.BarStoreType;
import com.wuhan_data.app.showType.BarType;
import com.wuhan_data.app.showType.DoubleXaxisLineType;
import com.wuhan_data.app.showType.LineAndBarType;
import com.wuhan_data.app.showType.LineType;
import com.wuhan_data.app.showType.PieType;
import com.wuhan_data.app.showType.PointType;
import com.wuhan_data.app.showType.RadarType;
import com.wuhan_data.app.showType.TableType;
import com.wuhan_data.app.showType.pojo.BarEntity;
import com.wuhan_data.app.showType.pojo.BarStackLineEntity;
import com.wuhan_data.app.showType.pojo.BarStoreEntity;
import com.wuhan_data.app.showType.pojo.DoubleXaxisLineEntity;
import com.wuhan_data.app.showType.pojo.LineAndBarEntity;
import com.wuhan_data.app.showType.pojo.LineEntity;
import com.wuhan_data.app.showType.pojo.PieEntity;
import com.wuhan_data.app.showType.pojo.PointEntity;
import com.wuhan_data.app.showType.pojo.RadarEntity;
import com.wuhan_data.app.showType.pojo.TableEntity;
import com.wuhan_data.pojo.AnalysisIndi;
import com.wuhan_data.pojo.AnalysisIndiValue;
import com.wuhan_data.pojo.AnalysisPlate;

public class PlateAnalysisServiceImpl implements PlateAnalysisService {
	@Autowired
	AnalysisMapper analysisMapper;

	@Override
	public List<String> getPublicFreq(List<AnalysisPlate> cpList) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getPublicTimeSpan(List<String> Freq, List<AnalysisPlate> cpList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map> getTimeCondition(List<String> Freq, List<String> timeSpan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getTotalList(List<AnalysisPlate> cpList, String startTime, String endTime,List<String> dataXais,String freqName) {
		List<Object> TotalList=new ArrayList<Object>();
		for(int i=0;i<cpList.size();i++) {
			String id = String.valueOf(cpList.get(i).getPlateId());//板块id
			String title = cpList.get(i).getPlateName();//板块名
			List<AnalysisIndi> indiList=analysisMapper.getIndiByPid(cpList.get(i).getPlateId());
			switch(cpList.get(i).getShowType()) {
			case "折线图":
			{
				System.out.println("进入折线图");
				List<List<String>> dataValue=new ArrayList<List<String>>();
				List<String> legend=new ArrayList<String>();
				LineType lineType = new LineType();
				for(int j=0;j<indiList.size();j++) {
					Map<String,Object> queryMap=new HashMap<String,Object>();
					queryMap.put("freqName",freqName);
					queryMap.put("startTime",startTime);
					queryMap.put("endTime",endTime);
					queryMap.put("indiCode",indiList.get(j).getIndiCode());
					List<AnalysisIndiValue> indiInfoList=analysisMapper.getIndiValue(queryMap);
					List<String> dataIndiValue = Arrays.asList(new String[dataXais.size()]);
					for(int m=0;m<indiInfoList.size();m++) {
						String dataXTemp=indiInfoList.get(m).getTime();
						if(dataXais.contains(dataXTemp)) {
							int index=dataXais.indexOf(dataXTemp);
							dataIndiValue.set(index,indiInfoList.get(m).getIndiValue());
						}
					}
					dataValue.add(dataIndiValue);
					legend.add(indiList.get(j).getIndiName());
				}
				TableType tableType=new TableType();
				LineEntity lineEntity=lineType.getOption(id,title,dataXais,legend,dataValue);
				List<List<String>> dataXaisTable=new ArrayList<List<String>>();
				for(int q=0;q<indiList.size();q++) {
					dataXaisTable.add(dataXais);
				}
				TableEntity tableEntity=tableType.getTable(id, title, dataXaisTable, legend, dataValue);
				TotalList.add(lineEntity);
				TotalList.add(tableEntity);
			}break;
			case "柱状图":
			{
				System.out.println("进入柱状图");
				List<List<String>> dataValue=new ArrayList<List<String>>();
				List<String> legend=new ArrayList<String>();
				BarType barType=new BarType();
				for(int j=0;j<indiList.size();j++) {
					Map<String,Object> queryMap=new HashMap<String,Object>();
					queryMap.put("freqName",freqName);
					queryMap.put("startTime",startTime);
					queryMap.put("endTime",endTime);
					queryMap.put("indiCode",indiList.get(j).getIndiCode());
					List<AnalysisIndiValue> indiInfoList=analysisMapper.getIndiValue(queryMap);
					List<String> dataIndiValue = Arrays.asList(new String[dataXais.size()]);
					for(int m=0;m<indiInfoList.size();m++) {
						String dataXTemp=indiInfoList.get(m).getTime();
						if(dataXais.contains(dataXTemp)) {
							int index=dataXais.indexOf(dataXTemp);
							dataIndiValue.set(index,indiInfoList.get(m).getIndiValue());
						}
					}
					dataValue.add(dataIndiValue);
					legend.add(indiList.get(j).getIndiName());
				}
				TableType tableType=new TableType();
				BarEntity barEntity=barType.getOption(id,title,dataXais,legend,dataValue);
				List<List<String>> dataXaisTable=new ArrayList<List<String>>();
				for(int q=0;q<indiList.size();q++) {
					dataXaisTable.add(dataXais);
				}
				TableEntity tableEntity=tableType.getTable(id, title, dataXaisTable, legend, dataValue);
				TotalList.add(barEntity);
				TotalList.add(tableEntity);
	
			}break;
			case "饼状图":
			{
				System.out.println("进入饼状图");
				List<String> dataV=new ArrayList<String>();
				List<String> legend=new ArrayList<String>();
				PieType pieType=new PieType();
				//饼状图只有一个指标，不用for循环
				Map<String,Object> queryMap=new HashMap<String,Object>();
				queryMap.put("freqName",freqName);
				queryMap.put("startTime",endTime);
				queryMap.put("endTime",endTime);
				queryMap.put("indiCode",indiList.get(0).getIndiCode());
				List<AnalysisIndiValue> indiInfoList=analysisMapper.getIndiValue(queryMap);
				List<String> dataIndiValue = Arrays.asList(new String[dataXais.size()]);
				for(int m=0;m<indiInfoList.size();m++) {
					String dataXTemp=indiInfoList.get(m).getTime();
					if(dataXais.contains(dataXTemp)) {
						int index=dataXais.indexOf(dataXTemp);
						dataIndiValue.set(index,indiInfoList.get(m).getIndiValue());
						//此处legend取得的值可能需要更改
						legend.set(index,indiInfoList.get(m).getFreqName());
					}
				}
				dataV=dataIndiValue;
				PieEntity pieEntity=pieType.getOption(id, title, dataV, legend);
				TotalList.add(pieEntity);
	
			}break;
			
			case "散点图":
			{
				System.out.println("进入散点图");
				List<List<String>> dataValue=new ArrayList<List<String>>();
				List<String> legend=new ArrayList<String>();
				PointType pointType=new PointType();
				for(int j=0;j<indiList.size();j++) {
					//由于不是单纯的散点图（有折线在其中）需要加一个判断
					if(indiList.get(j).getShowType()==null) {
						
					}
					Map<String,Object> queryMap=new HashMap<String,Object>();
					queryMap.put("freqName",freqName);
					queryMap.put("startTime",startTime);
					queryMap.put("endTime",endTime);
					queryMap.put("indiCode",indiList.get(j).getIndiCode());
					List<AnalysisIndiValue> indiInfoList=analysisMapper.getIndiValue(queryMap);
					List<String> dataIndiValue = Arrays.asList(new String[dataXais.size()]);
					for(int m=0;m<indiInfoList.size();m++) {
						String dataXTemp=indiInfoList.get(m).getTime();
						if(dataXais.contains(dataXTemp)) {
							int index=dataXais.indexOf(dataXTemp);
							dataIndiValue.set(index,indiInfoList.get(m).getIndiValue());
						}
					}
					dataValue.add(dataIndiValue);
					legend.add(indiList.get(j).getIndiName());
					
				}
				
				PointEntity pointEntity=pointType.getOption(id, title, legend, dataValue);
				TotalList.add(pointEntity);				
			}break;
			case "折柱混搭图":
			{
				System.out.println("进入折柱混搭图");
				List<List<String>> dataValue=new ArrayList<List<String>>();
				List<String> legend=new ArrayList<String>();
				List<String> showType=new ArrayList<String>();
				LineAndBarType lineAndBarType=new LineAndBarType();
				for(int j=0;j<indiList.size();j++) {
					String sType=indiList.get(j).getShowType();
					showType.add(sType);
					Map<String,Object> queryMap=new HashMap<String,Object>();
					queryMap.put("freqName",freqName);
					queryMap.put("startTime",startTime);
					queryMap.put("endTime",endTime);
					queryMap.put("indiCode",indiList.get(j).getIndiCode());
					List<AnalysisIndiValue> indiInfoList=analysisMapper.getIndiValue(queryMap);
					List<String> dataIndiValue = Arrays.asList(new String[dataXais.size()]);
					for(int m=0;m<indiInfoList.size();m++) {
						String dataXTemp=indiInfoList.get(m).getTime();
						if(dataXais.contains(dataXTemp)) {
							int index=dataXais.indexOf(dataXTemp);
							dataIndiValue.set(index,indiInfoList.get(m).getIndiValue());
						}
					}
					dataValue.add(dataIndiValue);
					legend.add(indiList.get(j).getIndiName());
				}
				TableType tableType=new TableType();
				LineAndBarEntity lineAndBarEntity=lineAndBarType.getOption(id,title,dataXais,legend,dataValue,showType);
				List<List<String>> dataXaisTable=new ArrayList<List<String>>();
				for(int q=0;q<indiList.size();q++) {
					dataXaisTable.add(dataXais);
				}
				TableEntity tableEntity=tableType.getTable(id, title, dataXaisTable, legend, dataValue);
				TotalList.add(lineAndBarEntity);
				TotalList.add(tableEntity);
				
				
				
			}break;
			case "雷达图":
			{
				System.out.println("进入雷达图");
				//记录图例，此处为时间
//				List legendData=new ArrayList();
				//分指标记录值
				List<List<String>> dataValue=new ArrayList<List<String>>();
				//记录指标名
				List<String> dataName=new ArrayList<String>();
				//记录以时间跨度的值
				List<List<String>> dataByTime=new ArrayList<List<String>>();
				RadarType radarType=new RadarType();
				for(int j=0;j<indiList.size();j++) {
					Map<String,Object> queryMap=new HashMap<String,Object>();
					queryMap.put("freqName",freqName);
					queryMap.put("startTime",startTime);
					queryMap.put("endTime",endTime);
					queryMap.put("indiCode",indiList.get(j).getIndiCode());
					List<AnalysisIndiValue> indiInfoList=analysisMapper.getIndiValue(queryMap);
					List<String> dataIndiValue = Arrays.asList(new String[dataXais.size()]);
					for(int m=0;m<indiInfoList.size();m++) {
						String dataXTemp=indiInfoList.get(m).getTime();
						if(dataXais.contains(dataXTemp)) {
							int index=dataXais.indexOf(dataXTemp);
							dataIndiValue.set(index,indiInfoList.get(m).getIndiValue());
						}
					}
					dataValue.add(dataIndiValue);
					dataName.add(indiList.get(j).getIndiName());
					
					}
				for(int k=0;k<dataXais.size();k++) {
					List<String> dataOfTime=new ArrayList<String>();
					for(int n=0;n<dataValue.size();n++) {
						List<String> dataTem=dataValue.get(n);
						dataOfTime.add(dataTem.get(k));
						
						
					}
					dataByTime.add(dataOfTime);
					
				}
				RadarEntity radarEntity=radarType.getOption(id, title, dataXais, dataName, dataValue, dataByTime);
				TotalList.add(radarEntity);
					
					
					
					
				}break;
			case "柱状堆积图":
			{
				System.out.println("进入柱状堆叠图");
				List<List<String>> dataValue=new ArrayList<List<String>>();
				List<String> legend=new ArrayList<String>();
				BarStoreType barStoreType=new BarStoreType();
				for(int j=0;j<indiList.size();j++) {
					Map<String,Object> queryMap=new HashMap<String,Object>();
					queryMap.put("freqName",freqName);
					queryMap.put("startTime",startTime);
					queryMap.put("endTime",endTime);
					queryMap.put("indiCode",indiList.get(j).getIndiCode());
					List<AnalysisIndiValue> indiInfoList=analysisMapper.getIndiValue(queryMap);
					List<String> dataIndiValue = Arrays.asList(new String[dataXais.size()]);
					for(int m=0;m<indiInfoList.size();m++) {
						String dataXTemp=indiInfoList.get(m).getTime();
						if(dataXais.contains(dataXTemp)) {
							int index=dataXais.indexOf(dataXTemp);
							dataIndiValue.set(index,indiInfoList.get(m).getIndiValue());
						}
					}
					dataValue.add(dataIndiValue);
					//此处legend的值可能需要更改
					legend.add(indiList.get(j).getIndiName());
					
					
					}
				BarStoreEntity barStoreEntity=barStoreType.getOption(id, title, dataXais, legend, dataValue);
				TotalList.add(barStoreEntity);
				
			}break;
			case "双X轴折线图":
			{
				System.out.println("进入双X轴折线图");
				List<List<String>> dataValue=new ArrayList<List<String>>();
				List<String> legend=new ArrayList<String>();
				List<List<String>> dataDoubleXais=new ArrayList<List<String>>();
				DoubleXaxisLineType doubleXaxisLineType=new DoubleXaxisLineType();
				for(int j=0;j<indiList.size();j++) {
					Map<String,Object> queryMap=new HashMap<String,Object>();
					queryMap.put("indiCode",indiList.get(j).getIndiCode());
					//此处可能需要更改方法，而且传进来的开始时间和结束时间似乎也需要两套
					List<AnalysisIndiValue> indiInfoList=analysisMapper.getIndiValue(queryMap);
					List<String> dataIndiValue = Arrays.asList(new String[dataXais.size()]);
					List<String> dataIndiXais=Arrays.asList(new String[dataXais.size()]);
					for(int m=0;m<indiInfoList.size();m++) {
						String dataXTemp=indiInfoList.get(m).getTime();
						if(dataXais.contains(dataXTemp)) {
							int index=dataXais.indexOf(dataXTemp);
							dataIndiValue.set(index,indiInfoList.get(m).getIndiValue());
							dataIndiXais.set(index, indiInfoList.get(m).getTime());
							
						}
						
					}
					dataValue.add(dataIndiValue);
					dataDoubleXais.add(dataIndiXais);
					legend.add(indiList.get(j).getIndiName());
				}
				DoubleXaxisLineEntity doubleXaxisLineEntity=doubleXaxisLineType.getOption(id, title, dataDoubleXais, legend, dataValue);
				TotalList.add(doubleXaxisLineEntity);
			}break;
			case "柱状堆积折线图":
			{
				System.out.println("进入柱状堆叠折线图");
				List<List<String>> dataValue=new ArrayList<List<String>>();
				List<String> legend=new ArrayList<String>();
				List<String> showType=new ArrayList<String>();
				BarStackLineType barStackLineType=new BarStackLineType();
				for(int j=0;j<indiList.size();j++) {
					Map<String,Object> queryMap=new HashMap<String,Object>();
					queryMap.put("freqName",freqName);
					queryMap.put("startTime",startTime);
					queryMap.put("endTime",endTime);
					queryMap.put("indiCode",indiList.get(j).getIndiCode());
					List<AnalysisIndiValue> indiInfoList=analysisMapper.getIndiValue(queryMap);
					List<String> dataIndiValue = Arrays.asList(new String[dataXais.size()]);
					for(int m=0;m<indiInfoList.size();m++) {
						String dataXTemp=indiInfoList.get(m).getTime();
						if(dataXais.contains(dataXTemp)) {
							int index=dataXais.indexOf(dataXTemp);
							dataIndiValue.set(index,indiInfoList.get(m).getIndiValue());
						}
					}
					dataValue.add(dataIndiValue);
					legend.add(indiList.get(j).getIndiName());
					showType.add(indiList.get(j).getShowType());
				}
				BarStackLineEntity barStackLineEntity=barStackLineType.getOption(id, title, dataXais, legend, dataValue, showType);
				TotalList.add(barStackLineEntity);				
				
			}break;
				
			}
			
				
			
		}
		// TODO Auto-generated method stub
		return TotalList;
	}

	@Override
	public List<Map> getRelatedData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getBaseInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> returnParam() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AnalysisPlate> getAnalysisPlate(int themeId) {
		// TODO Auto-generated method stub
		return analysisMapper.getAnalysisPlate(themeId);
	}

}
