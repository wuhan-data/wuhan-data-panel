package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.AnalysisSecondMapper;
import com.wuhan_data.pojo.AnalysisTheme;
import com.wuhan_data.service.AnalysisSecondService;

@Service
public class AnalysisSecondServiceImpl implements AnalysisSecondService {
	
	@Autowired
	AnalysisSecondMapper analysisSecondMapper;

	@Override
	public int add(AnalysisTheme analysisTheme) {
		// TODO Auto-generated method stub
		return analysisSecondMapper.add(analysisTheme);
	}

	@Override
	public void delete(int id) {
		analysisSecondMapper.delete(id);
		
	}

	@Override
	public int update(AnalysisTheme analysisTheme) {
		// TODO Auto-generated method stub
		return analysisSecondMapper.update(analysisTheme);
	}

	@Override
	public List<AnalysisTheme> getlist(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return analysisSecondMapper.getlist(map);
	}


	@Override
	public int updateShow(AnalysisTheme analysisTheme) {
		// TODO Auto-generated method stub
		return analysisSecondMapper.updateShow(analysisTheme);
	}


	@Override
	public int updateWeight(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return analysisSecondMapper.updateWeight(map);
	}

	@Override
	public int getThemeId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return analysisSecondMapper.getThemeId(map);
	}

	@Override
	public int getMaxWeight(int label_id) {
		// TODO Auto-generated method stub
		return analysisSecondMapper.getMaxWeight(label_id);
	}

}
