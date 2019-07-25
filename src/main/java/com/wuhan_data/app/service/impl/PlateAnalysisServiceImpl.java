package com.wuhan_data.app.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wuhan_data.app.mapper.AnalysisMapper;
import com.wuhan_data.app.service.PlateAnalysisService;
import com.wuhan_data.app.showType.LineType;
import com.wuhan_data.app.showType.TableType;
import com.wuhan_data.app.showType.pojo.LineEntity;
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
		List<Object> TotalList=new ArrayList();
		for(int i=0;i<cpList.size();i++) {
			String id = String.valueOf(cpList.get(i).getPlateId());//板块id
			String title = cpList.get(i).getPlateName();//板块名
			List<AnalysisIndi> indiList=analysisMapper.getIndiByPid(cpList.get(i).getPlateId());
			switch(cpList.get(i).getShowType()) {
			case "折线图":
			{
				System.out.println("进入折线图");
				List<List> dataValue=new ArrayList();
				List<String> legend=new ArrayList();
				LineType lineType = new LineType();
				for(int j=0;j<indiList.size();j++) {
					Map<String,Object> queryMap=new HashMap();
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
				List<List> dataXaisTable=new ArrayList();
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
				
			}
				
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
