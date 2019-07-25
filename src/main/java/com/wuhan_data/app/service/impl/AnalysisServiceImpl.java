package com.wuhan_data.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.AnalysisMapper;
import com.wuhan_data.app.service.AnalysisService;
import com.wuhan_data.pojo.AnalysisIndi;
import com.wuhan_data.pojo.AnalysisPlate;
import com.wuhan_data.pojo.AnalysisTheme;
import com.wuhan_data.pojo.ColPlateIndi;

@Service
public class AnalysisServiceImpl implements AnalysisService {

	@Autowired
	AnalysisMapper analysisMapper;

	@Override
	public ArrayList<Object> getAnalysisList(int userId) {
		// 处理经济分析栏目列表
		ArrayList<Object> result = new ArrayList<Object>();
		List<AnalysisTheme> typeList = analysisMapper.getAnalysisList();
		for (int i = 0; i < typeList.size(); i++) {
			Map<String, Object> subListMap = new HashMap<String, Object>();
			subListMap.put("listId", typeList.get(i).getListId());
			subListMap.put("listName", typeList.get(i).getListName());
			ArrayList<Object> subList = this.getAnalysisSubList(typeList.get(i).getListId(), userId);
			subListMap.put("subList", subList);
			result.add(subListMap);
		}
		return result;
	}

	@Override
	public ArrayList<Object> getAnalysisSubList(int typeId, int userId) {
		ArrayList<Object> result = new ArrayList<Object>();
		// 从数据库获取二级栏目数据
		List<AnalysisTheme> subListTemp = analysisMapper.getAnalysisSubList(typeId);
		// 根据roleList 选择性的给栏目数据
		ArrayList<String> roleList = new ArrayList<String>();
		if (userId != 0) {
			// TODO 根据用户userId获取对应的role_list
//			roleList.add("analysis_zonghe");
		}
		// 根据权限列表筛选二级栏目数据
		List<AnalysisTheme> subList = this.getAnalysisRoleList(subListTemp, roleList);
		// 获取收藏信息，构建二级栏目数据集
		for (int i = 0; i < subList.size(); i++) {
			Map<String, Object> subListMap = new HashMap<String, Object>();
			String indexId = subList.get(i).getThemeId().toString();
			String indexName = subList.get(i).getThemeName().toString();
			subListMap.put("indexId", indexId);
			subListMap.put("indexName", indexName);
			// TODO 从用户收藏表获取指标收藏信息

			subListMap.put("isFavorite", false);
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
	public List<AnalysisTheme> getAnalysisRoleList(List<AnalysisTheme> subList, ArrayList<String> roleList) {
		List<AnalysisTheme> result = new ArrayList<AnalysisTheme>();
		if (roleList.isEmpty()) {
			return subList;
		}
		for (int i = 0; i < roleList.size(); i++) {
			String roleName = roleList.get(i);
			switch (roleName) {
			case "analysis_zonghe":
				for (int j = 0; j < subList.size(); j++) {
					String isShow = subList.get(j).getIsShow().toString();
					String listName = subList.get(j).getListName().toString();
					if (listName.equals("综合")) {
						System.out.println("listName=zonghe");
						if (isShow.equals("0") || isShow.equals("9")) {
							result.add(subList.get(j));
						}
					} else {
						if (isShow.equals("0")) {
							result.add(subList.get(j));
						}
					}
				}
				break;

			// TODO 对更多权限的判断和处理

			default:
				for (int j = 0; j < subList.size(); j++) {
					String isShow = subList.get(j).getIsShow().toString();
					if (isShow.equals("0")) {
						result.add(subList.get(j));
					}
				}
				break;
			}
		}
		return result;
	}

	public ArrayList<Object> getAnalysisPlate(int themeId) {
		ArrayList<Object> result = new ArrayList<Object>();
		List<AnalysisPlate> analysisPlate = analysisMapper.getAnalysisPlate(themeId);
		// 记录整个栏目的频度信息
		List<String> timeFreq = new ArrayList<String>();
		// 此处顺序不能调换，关系到后面取最小粒度数据
		timeFreq.add("MM");
		timeFreq.add("SS");
		timeFreq.add("YY");
		// 获得所有指标的可取的时间区间及频度信息
		for (int i = 0; i < analysisPlate.size(); i++) {
			// 查询每个板块下的指标数据
			List<AnalysisIndi> indiList = analysisMapper.getIndiByPid(analysisPlate.get(i).getPlateId());

			result.add(indiList);
		}
		result.add(analysisPlate);
		result.add(timeFreq);
		return result;
	}
}