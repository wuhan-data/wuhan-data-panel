package com.wuhan_data.app.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wuhan_data.app.mapper.AnalysisMapper;
import com.wuhan_data.app.service.PlateAnalysisService;
import com.wuhan_data.app.showType.LineType;
import com.wuhan_data.pojo.AnalysisIndi;
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
	public List getTotalList(List<AnalysisPlate> cpList, String startTime, String endTime,List<String> dataXais) {
		for(int i=0;i<cpList.size();i++) {
			String id = String.valueOf(cpList.get(i).getPlateId());//板块id
			String title = cpList.get(i).getPlateName();//板块名
			List<AnalysisIndi> indiList=plateAnalysisService.getIndiByPid(cpList.get(i).getPlateId());
			switch(cpList.get(i).getShowType()) {
			case "折线图":
				System.out.println("进入折线图");
				List<String> dataValue=new ArrayList();
				List<String> legend=new ArrayList();
				LineType lineType = new LineType();
				for(int j=0;j<indiList.size();j++) {
					//此处需要添加获取指标详细数据的方法
					List<String> dataIndiValue = Arrays.asList(new String[dataXais.size()]);
					
					
					
				}
			}
		}
		// TODO Auto-generated method stub
		return null;
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
