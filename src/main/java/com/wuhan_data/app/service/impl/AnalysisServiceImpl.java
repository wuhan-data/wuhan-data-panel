package com.wuhan_data.app.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.AnalysisMapper;
import com.wuhan_data.app.service.AnalysisService;
import com.wuhan_data.pojo.AnalysisIndi;
import com.wuhan_data.pojo.AnalysisIndiValue;
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
		timeFreq.add("月度");
		timeFreq.add("季度");
		timeFreq.add("年度");

		// 获得所有指标的可取的时间区间及频度信息
		for (int i = 0; i < analysisPlate.size(); i++) {
			// 查询每个板块下的指标数据
			Integer pid = analysisPlate.get(i).getPlateId();
			List<AnalysisIndi> indiList = analysisMapper.getIndiByPid(pid);
			for (int j = 0; j < indiList.size(); j++) {
				String indiCode = indiList.get(j).getIndiCode();
				List<String> freqNameList = analysisMapper.getFreqnameByIndicode(indiCode);
				// TOOD 因为测试数据不全，所以这里对可能取不到的频度进行忽略
				for (int k = 0; k < freqNameList.size(); k++) {
					if (freqNameList.get(k).equals("")) {
						continue;
					} else {
						timeFreq.retainAll(freqNameList);
					}
				}
			}
		}

		ArrayList<Object> timeCondition = new ArrayList<Object>();
		// 获取时间选择器区间，取指标的并集
		for (int i = 0; i < timeFreq.size(); i++) {
			String freqName = timeFreq.get(i);
			Map<String, Object> timeConditionMap = new HashMap<String, Object>();
			Set<String> timeSpanFinal = new HashSet<String>();
			for (int j = 0; j < analysisPlate.size(); j++) {
				Integer pid = analysisPlate.get(j).getPlateId();
				Integer showTerm = analysisPlate.get(j).getShowTerm();
				System.out.println(showTerm);
				List<AnalysisIndi> indiList = analysisMapper.getIndiByPid(pid);
				for (int k = 0; k < indiList.size(); k++) {
					Map<String, Object> queryMap = new HashMap<String, Object>();
					String indiCode = indiList.get(k).getIndiCode();
					queryMap.put("indiCode", indiCode);
					queryMap.put("freqName", freqName);
					queryMap.put("showTerm", 999); // showTerm
					System.out.println(queryMap.toString());
					List<String> timeList = analysisMapper.getTimeByFreqname(queryMap);

					Set<String> timeSpanSet = new HashSet<String>(timeList);
					timeSpanFinal.addAll(timeSpanSet);
//					System.out.println(timeSpanFinal.toString());
				}
			}
			List<String> timeList = new ArrayList<String>(timeSpanFinal);
//			Collections.sort(timeList);
			Collections.reverse(timeList);
			switch (freqName) {
			case "月度":
				timeConditionMap.put("freqName", "月度");
				break;
			case "季度":
				timeConditionMap.put("freqName", "季度");
				break;
			case "年度":
				timeConditionMap.put("freqName", "年度");
				break;
			default:
				timeConditionMap.put("freqName", "暂无");
			}
			timeConditionMap.put("startArray", timeList);
			timeConditionMap.put("endArray", timeList);
			timeCondition.add(timeConditionMap);
		}
		Map<String, Object> queryMap = new HashMap<String, Object>();
		String indiCode = "PMI001;400:706501;363:706401;62:1";
		String freqName = "月度";
		queryMap.put("indiCode", indiCode);
		queryMap.put("freqName", freqName);
		queryMap.put("startTime", "2019/01");
		queryMap.put("endTime", "2019/06");
		List<AnalysisIndiValue> test = analysisMapper.getIndiValue(queryMap);
		result.add(test);
//		result.add(timeCondition);
		return result;
	}

}