package com.wuhan_data.app.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.wuhan_data.app.showType.BarStackLineType;
import com.wuhan_data.app.showType.BarStoreType;
import com.wuhan_data.app.showType.BarType;
import com.wuhan_data.app.showType.LineAndBarType;
import com.wuhan_data.app.showType.LineType;
import com.wuhan_data.app.showType.PieType;
import com.wuhan_data.app.showType.RadarType;
import com.wuhan_data.app.showType.TableType;
import com.wuhan_data.app.showType.pojo.BarEntity;
import com.wuhan_data.app.showType.pojo.BarStackLineEntity;
import com.wuhan_data.app.showType.pojo.BarStoreEntity;
import com.wuhan_data.app.showType.pojo.LineAndBarEntity;
import com.wuhan_data.app.showType.pojo.LineEntity;
import com.wuhan_data.app.showType.pojo.PieEntity;
import com.wuhan_data.app.showType.pojo.RadarEntity;
import com.wuhan_data.app.showType.pojo.TableEntity;
import com.wuhan_data.pojo.AnalysisIndi;
import com.wuhan_data.pojo.AnalysisIndiValue;
import com.wuhan_data.pojo.AnalysisPlate;
import com.wuhan_data.pojo.AnalysisTheme;

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

	/**
	 * 获取初始化版块数据
	 */
	public Map<String, Object> getAnalysisPlate(int themeId) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 获取版块信息
		List<AnalysisPlate> analysisPlate = analysisMapper.getAnalysisPlate(themeId);
		if (analysisPlate.size() == 0) {
			return result;
		}

		// 获取时间可取区间数据
		ArrayList<Map<String, Object>> timeCondition = this.getTimeCondition(analysisPlate);
		String errorTimeCondition = "[{current=[0, 0], startArray=[], freqName=月度, endArray=[]}, {startArray=[], freqName=季度, endArray=[]}, {startArray=[], freqName=年度, endArray=[]}]";
		if (timeCondition.toString().equals(errorTimeCondition)) {
			return result;
		}

		// 构建查询条件
		Map<String, Object> freqObject = timeCondition.get(0);
		ArrayList<Integer> current = (ArrayList<Integer>) freqObject.get("current");
		List<String> startTimeList = (List<String>) freqObject.get("startArray");
		List<String> endTimeList = (List<String>) freqObject.get("endArray");
		String freqName = (String) freqObject.get("freqName");
		List<String> xAxis = startTimeList.subList(current.get(0), current.get(1));
		System.out.println(xAxis.toString());
		String startTime = startTimeList.get(current.get(0)).toString();
		String startTimeRadar = endTimeList.get(startTimeList.size() - 4).toString();
		String endTime = endTimeList.get(current.get(1)).toString();
		String endTimeRadar = endTimeList.get(endTimeList.size() - 1).toString();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("freqName", freqName);
		queryMap.put("startTime", startTime);
		queryMap.put("startTimeRadar", startTimeRadar);
		queryMap.put("endTime", endTime);
		queryMap.put("endTimeRadar", endTimeRadar);

		// 查询指标数据并绘制图形
		List<Object> classInfo = this.getClassInfo(analysisPlate, queryMap, xAxis);

		result.put("timeCondition", timeCondition);
		result.put("classInfo", classInfo);

		return result;
	}

	/**
	 * 根据版块信息获取可取的时间区间
	 * 
	 * @param analysisPlate
	 * @return
	 */
	public ArrayList<Map<String, Object>> getTimeCondition(List<AnalysisPlate> analysisPlate) {
		ArrayList<Map<String, Object>> timeCondition = new ArrayList<Map<String, Object>>();
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

		// 获取时间选择器区间，取指标的并集
		for (int i = 0; i < timeFreq.size(); i++) {
			String freqName = timeFreq.get(i);
			Map<String, Object> timeConditionMap = new HashMap<String, Object>();
			Set<String> timeSpanFinal = new HashSet<String>();
			for (int j = 0; j < analysisPlate.size(); j++) {
				Integer pid = analysisPlate.get(j).getPlateId();
				Integer showTerm = analysisPlate.get(j).getShowTerm();
				List<AnalysisIndi> indiList = analysisMapper.getIndiByPid(pid);
				for (int k = 0; k < indiList.size(); k++) {
					Map<String, Object> queryMap = new HashMap<String, Object>();
					String indiCode = indiList.get(k).getIndiCode();
					queryMap.put("indiCode", indiCode);
					queryMap.put("freqName", freqName);
					queryMap.put("showTerm", 999); // showTerm
					List<String> timeList = analysisMapper.getTimeByFreqname(queryMap);
					Set<String> timeSpanSet = new HashSet<String>(timeList);
					timeSpanFinal.addAll(timeSpanSet);
				}
			}
			List<String> timeList = new ArrayList<String>(timeSpanFinal);
			Collections.sort(timeList);
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
			// 添加默认选择的时间区间，只有最小粒度需要
			if (i == 0) {
				ArrayList<Integer> subIndex = new ArrayList<Integer>();
				Integer currentLen = 8;
				System.out.println(currentLen);
				if (timeList.size() > currentLen) {
					subIndex.add(timeList.size() - currentLen - 1);
					subIndex.add(timeList.size() - 1);
				} else if (timeList.size() < 1) {
					subIndex.add(0);
					subIndex.add(0);
				} else {
					subIndex.add(0);
					subIndex.add(timeList.size() - 1);
				}
				System.out.println(subIndex.toString());
				timeConditionMap.put("current", subIndex);
			}
			timeCondition.add(timeConditionMap);
		}
		return timeCondition;
	}

	@Override
	public List<Object> getClassInfo(List<AnalysisPlate> analysisPlate, Map<String, Object> queryMap,
			List<String> xAxis) {
		List<Object> TotalList = new ArrayList<Object>();
		for (int i = 0; i < analysisPlate.size(); i++) {
			String id = String.valueOf(analysisPlate.get(i).getPlateId());// 板块id
			String title = analysisPlate.get(i).getPlateName();// 板块名
			List<AnalysisIndi> indiList = analysisMapper.getIndiByPid(analysisPlate.get(i).getPlateId());
			switch (analysisPlate.get(i).getShowType()) {
			case "折线图": {
				System.out.println("进入折线图");
				List<List<String>> dataValue = new ArrayList<List<String>>();
				List<String> legend = new ArrayList<String>();
				LineType lineType = new LineType();
				for (int j = 0; j < indiList.size(); j++) {
					queryMap.put("indiCode", indiList.get(j).getIndiCode());
					List<AnalysisIndiValue> indiInfoList = analysisMapper.getIndiValue(queryMap);
					System.out.println(indiInfoList.size());
					List<String> dataIndiValue = Arrays.asList(new String[xAxis.size()]);
					for (int m = 0; m < indiInfoList.size(); m++) {
						String dataXTemp = indiInfoList.get(m).getTime();
						if (xAxis.contains(dataXTemp)) {
							int index = xAxis.indexOf(dataXTemp);
							dataIndiValue.set(index, indiInfoList.get(m).getIndiValue());
						}
					}
					dataValue.add(dataIndiValue);
					legend.add(indiList.get(j).getIndiName());
				}
				TableType tableType = new TableType();
				LineEntity lineEntity = lineType.getOption(id, title, xAxis, legend, dataValue);
				List<List<String>> dataXaisTable = new ArrayList<List<String>>();
				for (int q = 0; q < indiList.size(); q++) {
					dataXaisTable.add(xAxis);
				}
//				System.out.println(dataXaisTable.toString());
//				TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legend, dataValue);
//				TotalList.add(lineEntity);
//				TotalList.add(tableEntity);
			}
				break;
			case "柱状图": {
				System.out.println("进入柱状图");
				List<List<String>> dataValue = new ArrayList<List<String>>();
				List<String> legend = new ArrayList<String>();
				BarType barType = new BarType();
				for (int j = 0; j < indiList.size(); j++) {
					queryMap.put("indiCode", indiList.get(j).getIndiCode());
					List<AnalysisIndiValue> indiInfoList = analysisMapper.getIndiValue(queryMap);
					List<String> dataIndiValue = Arrays.asList(new String[xAxis.size()]);
					for (int m = 0; m < indiInfoList.size(); m++) {
						String dataXTemp = indiInfoList.get(m).getTime();
						if (xAxis.contains(dataXTemp)) {
							int index = xAxis.indexOf(dataXTemp);
							dataIndiValue.set(index, indiInfoList.get(m).getIndiValue());
						}
					}
					dataValue.add(dataIndiValue);
					legend.add(indiList.get(j).getIndiName());
				}
				TableType tableType = new TableType();
				BarEntity barEntity = barType.getOption(id, title, xAxis, legend, dataValue);
				List<List<String>> dataXaisTable = new ArrayList<List<String>>();
				for (int q = 0; q < indiList.size(); q++) {
					dataXaisTable.add(xAxis);
				}
				TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legend, dataValue);
				TotalList.add(barEntity);
				TotalList.add(tableEntity);
			}
				break;
			case "雷达图": {
				System.out.println("进入雷达图");
				// 记录图例，此处为时间
				// 分指标记录值
				List<List<String>> dataValue = new ArrayList<List<String>>();
				// 记录指标名
				List<String> dataName = new ArrayList<String>();
				// 记录以时间跨度的值
				List<List<String>> dataByTime = new ArrayList<List<String>>();
				RadarType radarType = new RadarType();
				// 雷达图支取最近的三期数据进行展示
				List<String> xAxisRadar = xAxis.subList(xAxis.size() - 3, xAxis.size());
//				xAxis = xAxis.subList(xAxis.size() - 3, xAxis.size());
				for (int j = 0; j < indiList.size(); j++) {
					// 时间不与时间选择器进行关联
					Map<String, Object> queryMap1 = new HashMap<String, Object>();
					queryMap1.put("freqName", queryMap.get("freqName"));
					queryMap1.put("startTime", queryMap.get("startTimeRadar"));
					queryMap1.put("endTime", queryMap.get("endTimeRadar"));
					queryMap1.put("indiCode", indiList.get(j).getIndiCode());
					List<AnalysisIndiValue> indiInfoList = analysisMapper.getIndiValue(queryMap1);
					List<String> dataIndiValue = Arrays.asList(new String[xAxisRadar.size()]);
					for (int m = 0; m < indiInfoList.size(); m++) {
						String dataXTemp = indiInfoList.get(m).getTime();
						if (xAxisRadar.contains(dataXTemp)) {
							int index = xAxisRadar.indexOf(dataXTemp);
							String indiValue = indiInfoList.get(m).getIndiValue();
							dataIndiValue.set(index, indiValue);
						}
					}
					dataValue.add(dataIndiValue);
					dataName.add(indiList.get(j).getIndiName());
				}
				for (int k = 0; k < xAxisRadar.size(); k++) {
					List<String> dataOfTime = new ArrayList<String>();
					for (int n = 0; n < dataValue.size(); n++) {
						List<String> dataTem = dataValue.get(n);
						dataOfTime.add(dataTem.get(k));
					}
					dataByTime.add(dataOfTime);
				}
				RadarEntity radarEntity = radarType.getOption(id, title, xAxisRadar, dataName, dataValue, dataByTime);
				TotalList.add(radarEntity);
			}
				break;
			case "饼图": {
				System.out.println("进入饼状图");
				List<String> dataV = new ArrayList<String>();
				List<String> legend = new ArrayList<String>();
				PieType pieType = new PieType();
				for (int j = 0; j < indiList.size(); j++) {
					String indiName = indiList.get(j).getIndiName().toString();
					// 饼状图只有一个指标，不用for循环
					Map<String, Object> queryMapPie = new HashMap<String, Object>();
					queryMapPie.put("freqName", queryMap.get("freqName"));
					queryMapPie.put("startTime", queryMap.get("endTime"));
					queryMapPie.put("endTime", queryMap.get("endTime"));
					queryMapPie.put("indiCode", indiList.get(j).getIndiCode());
					List<AnalysisIndiValue> indiInfoList = analysisMapper.getIndiValue(queryMapPie);
					System.out.println(indiInfoList.toString());
					String indiValue = "0";
					if (indiInfoList.get(0) != null) {
						indiValue = indiInfoList.get(0).getIndiValue();
					}
					legend.add(j, indiName);
					dataV.add(j, indiValue);
				}
				PieEntity pieEntity = pieType.getOption(id, title, dataV, legend);
				TotalList.add(pieEntity);
			}
				break;
			case "折柱混搭图": {
				System.out.println("进入折柱混搭图");
				List<List<String>> dataValue = new ArrayList<List<String>>();
				List<String> legend = new ArrayList<String>();
				List<String> showType = new ArrayList<String>();
				LineAndBarType lineAndBarType = new LineAndBarType();
				for (int j = 0; j < indiList.size(); j++) {
					String sType = indiList.get(j).getShowType();
					showType.add(sType);
					queryMap.put("indiCode", indiList.get(j).getIndiCode());
					List<AnalysisIndiValue> indiInfoList = analysisMapper.getIndiValue(queryMap);
					List<String> dataIndiValue = Arrays.asList(new String[xAxis.size()]);
					for (int m = 0; m < indiInfoList.size(); m++) {
						String dataXTemp = indiInfoList.get(m).getTime();
						if (xAxis.contains(dataXTemp)) {
							int index = xAxis.indexOf(dataXTemp);
							dataIndiValue.set(index, indiInfoList.get(m).getIndiValue());
						}
					}
					dataValue.add(dataIndiValue);
					legend.add(indiList.get(j).getIndiName());
				}
				TableType tableType = new TableType();
				LineAndBarEntity lineAndBarEntity = lineAndBarType.getOption(id, title, xAxis, legend, dataValue,
						showType);
				List<List<String>> dataXaisTable = new ArrayList<List<String>>();
				for (int q = 0; q < indiList.size(); q++) {
					dataXaisTable.add(xAxis);
				}
				TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legend, dataValue);
				TotalList.add(lineAndBarEntity);
				TotalList.add(tableEntity);
			}
				break;
			case "柱状堆积图": {
				System.out.println("进入柱状堆叠图");
				List<List<String>> dataValue = new ArrayList<List<String>>();
				List<String> legend = new ArrayList<String>();
				BarStoreType barStoreType = new BarStoreType();
				for (int j = 0; j < indiList.size(); j++) {
					queryMap.put("indiCode", indiList.get(j).getIndiCode());
					List<AnalysisIndiValue> indiInfoList = analysisMapper.getIndiValue(queryMap);
					List<String> dataIndiValue = Arrays.asList(new String[xAxis.size()]);
					for (int m = 0; m < indiInfoList.size(); m++) {
						String dataXTemp = indiInfoList.get(m).getTime();
						if (xAxis.contains(dataXTemp)) {
							int index = xAxis.indexOf(dataXTemp);
							dataIndiValue.set(index, indiInfoList.get(m).getIndiValue());
						}
					}
					dataValue.add(dataIndiValue);
					// 此处legend的值可能需要更改
					legend.add(indiList.get(j).getIndiName());
				}
				BarStoreEntity barStoreEntity = barStoreType.getOption(id, title, xAxis, legend, dataValue);
				TotalList.add(barStoreEntity);
				TableType tableType = new TableType();
				List<List<String>> dataXaisTable = new ArrayList<List<String>>();
				for (int q = 0; q < indiList.size(); q++) {
					dataXaisTable.add(xAxis);
				}
				TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legend, dataValue);
				TotalList.add(tableEntity);
			}
				break;
			case "柱状堆积折线图": {
				System.out.println("进入柱状堆叠折线图");
				List<List<String>> dataValue = new ArrayList<List<String>>();
				List<String> legend = new ArrayList<String>();
				List<String> showType = new ArrayList<String>();
				BarStackLineType barStackLineType = new BarStackLineType();
				for (int j = 0; j < indiList.size(); j++) {
					queryMap.put("indiCode", indiList.get(j).getIndiCode());
					List<AnalysisIndiValue> indiInfoList = analysisMapper.getIndiValue(queryMap);
					List<String> dataIndiValue = Arrays.asList(new String[xAxis.size()]);
					for (int m = 0; m < indiInfoList.size(); m++) {
						String dataXTemp = indiInfoList.get(m).getTime();
						if (xAxis.contains(dataXTemp)) {
							int index = xAxis.indexOf(dataXTemp);
							dataIndiValue.set(index, indiInfoList.get(m).getIndiValue());
						}
					}
					dataValue.add(dataIndiValue);
					legend.add(indiList.get(j).getIndiName());
					showType.add(indiList.get(j).getShowType());
				}
				BarStackLineEntity barStackLineEntity = barStackLineType.getOption(id, title, xAxis, legend, dataValue,
						showType);
				TotalList.add(barStackLineEntity);

				TableType tableType = new TableType();
				List<List<String>> dataXaisTable = new ArrayList<List<String>>();
				for (int q = 0; q < indiList.size(); q++) {
					dataXaisTable.add(xAxis);
				}
				TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legend, dataValue);
				TotalList.add(tableEntity);
			}
				break;
			default:
				break;
			}
		}
		// TODO Auto-generated method stub
		return TotalList;
	}

}