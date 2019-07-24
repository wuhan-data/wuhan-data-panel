package com.wuhan_data.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.AnalysisMapper;
import com.wuhan_data.app.service.AnalysisService;
import com.wuhan_data.pojo.AnalysisPlate;
import com.wuhan_data.pojo.AnalysisTheme;

@Service
public class AnalysisServiceImpl implements AnalysisService {

	@Autowired
	AnalysisMapper analysisMapper;

	@Override
	public ArrayList<Object> getAnalysisList() {
		// 处理经济分析栏目列表
		ArrayList<Object> result = new ArrayList<Object>();
		List<AnalysisTheme> typeList = analysisMapper.getAnalysisList();
		for (int i = 0; i < typeList.size(); i++) {
			Map<String, Object> subListMap = new HashMap<String, Object>();
			subListMap.put("listId", typeList.get(i).getListId());
			subListMap.put("listName", typeList.get(i).getListName());
			ArrayList<Object> twoList = this.getAnalysisSubList(typeList.get(i).getListId());
			subListMap.put("subList", twoList);
			result.add(subListMap);
		}
		return result;
	}

	@Override
	public ArrayList<Object> getAnalysisSubList(int typeId) {
		// 处理经济分析栏目列表
		ArrayList<Object> result = new ArrayList<Object>();
		List<AnalysisTheme> subList = analysisMapper.getAnalysisSubList(typeId);
		for (int i = 0; i < subList.size(); i++) {
			Map<String, Object> subListMap = new HashMap<String, Object>();
			String indexId = subList.get(i).getThemeId().toString();
			String indexName = subList.get(i).getThemeName().toString();
			subListMap.put("indexId", indexId);
			subListMap.put("indexName", indexName);
			// TODO 指标数据描述数据获取待优化
//			ArrayList<Object> descMap = new ArrayList<Object>();
//			Map<String, Object> desc1 = analysisMapper.getAnalysisDesc1(indexName);
//			Map<String, Object> desc2 = analysisMapper.getAnalysisDesc2(indexName);
//			Map<String, Object> desc3 = analysisMapper.getAnalysisDesc3(indexName);
//			if (desc1 != null) {
//				descMap.add(desc1);
//			}
//			if (desc2 != null) {
//				descMap.add(desc2);
//			}
//			if (desc3 != null) {
//				descMap.add(desc3);
//			}
//			subListMap.put("desc", descMap);
			result.add(subListMap);
		}
		return result;
	}

	/**
	 * 根据用户权限筛选栏目数据
	 * 
	 * @return
	 */
	@Override
	public List<Object> getAnalysisRoleList(List<Object> analysisList, ArrayList<String> roleList) {
		List<Object> result = new ArrayList<Object>();
		if (roleList.isEmpty()) {
			return analysisList;
		}
		for (int i = 0; i < roleList.size(); i++) {
			String roleName = roleList.get(i);
			switch (roleName) {
			case "analysis_zonghe":
				for (int j = 0; j < analysisList.size(); j++) {
//					if (analysisList.get(j).get("subList") == "") {
//						
//					}
				}
				break;

			default:
				break;
			}
		}
		return result;
	}

	public List<AnalysisPlate> getAnalysisPlate(int themeId) {
		return analysisMapper.getAnalysisPlate(themeId);
	}
}