package com.wuhan_data.app.service.impl;

import java.util.Date;
import java.text.SimpleDateFormat;
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
import com.wuhan_data.app.mapper.CollectMapperApp;
import com.wuhan_data.app.service.AnalysisService;
import com.wuhan_data.app.showType.BarStackLineType;
import com.wuhan_data.app.showType.BarStoreType;
import com.wuhan_data.app.showType.BarType;
import com.wuhan_data.app.showType.LineAndBarType;
import com.wuhan_data.app.showType.LineType;
import com.wuhan_data.app.showType.PieType;
import com.wuhan_data.app.showType.PointType;
import com.wuhan_data.app.showType.RadarType;
import com.wuhan_data.app.showType.TableType;
import com.wuhan_data.app.showType.pojo.BarEntity;
import com.wuhan_data.app.showType.pojo.BarStackLineEntity;
import com.wuhan_data.app.showType.pojo.BarStoreEntity;
import com.wuhan_data.app.showType.pojo.LineAndBarEntity;
import com.wuhan_data.app.showType.pojo.LineEntity;
import com.wuhan_data.app.showType.pojo.PieEntity;
import com.wuhan_data.app.showType.pojo.PointEntity;
import com.wuhan_data.app.showType.pojo.RadarEntity;
import com.wuhan_data.app.showType.pojo.TableEntity;
import com.wuhan_data.pojo.AnalysisIndi;
import com.wuhan_data.pojo.AnalysisIndiTime;
import com.wuhan_data.pojo.AnalysisIndiValue;
import com.wuhan_data.pojo.AnalysisPlate;
import com.wuhan_data.pojo.AnalysisTheme;
import com.wuhan_data.pojo.Collect;

@Service
public class AnalysisServiceImpl implements AnalysisService {

	@Autowired
	AnalysisMapper analysisMapper;

	@Autowired
	CollectMapperApp collectMapperApp;

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
			// 从用户收藏表获取指标收藏信息
			Collect collect = new Collect();
			collect.setType("经济分析");
			collect.setIndex_id(indexId);
			collect.setUid(userId);
			List<Integer> collectInfo = collectMapperApp.getTypeCollect(collect);
			if (collectInfo.size() != 0) {
				subListMap.put("isFavorite", true);
			} else {
				subListMap.put("isFavorite", false);
			}
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
	public Map<String, Object> initAnalysisPlate(int themeId, int userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println("开始初始化数据:" + df.format(new Date()));// new Date()为获取当前系统时间
		// 获取版块信息
		List<AnalysisPlate> analysisPlate = analysisMapper.getAnalysisPlate(themeId);
		if (analysisPlate.size() == 0) {
			return result;
		}
		// 根据themeId查询analysis_theme表中的信息
		List<AnalysisTheme> baseInfoList = analysisMapper.getThemeBaseInfo(themeId);
		String indexName = baseInfoList.get(0).getThemeName();
		String source = baseInfoList.get(0).getListName();
		Map<String, Object> baseInfo = new HashMap<String, Object>();
		baseInfo.put("indexId", themeId);
		baseInfo.put("indexName", indexName);
		baseInfo.put("source", source);
		// 根据userId/type/indexId查询收藏信息
		Collect collect = new Collect();
		collect.setType("经济分析");
		collect.setIndex_id(String.valueOf(themeId));
		collect.setUid(userId);
		List<Integer> collectInfo = collectMapperApp.getTypeCollect(collect);
		if (collectInfo.size() != 0) {
			baseInfo.put("isFavorite", true);
		} else {
			baseInfo.put("isFavorite", false);
		}
		System.out.println("版块数据获取成功:" + df.format(new Date()));

		// 获取时间可取区间数据
		ArrayList<Map<String, Object>> timeCondition = this.getTimeCondition(analysisPlate);
		String errorTimeCondition = "[{current=[0, 0], startArray=[], freqName=月度, endArray=[]}, {startArray=[], freqName=季度, endArray=[]}, {startArray=[], freqName=年度, endArray=[]}]";
		if (timeCondition.toString().equals(errorTimeCondition)) {
			return result;
		}
		System.out.println("时间区间数据获取成功:" + df.format(new Date()));

		// 构建查询条件
		Map<String, Object> freqObject = timeCondition.get(0);
		ArrayList<Integer> current = (ArrayList<Integer>) freqObject.get("current");
		List<String> startTimeList = (List<String>) freqObject.get("startArray");
		List<String> endTimeList = (List<String>) freqObject.get("endArray");
		String freqName = (String) freqObject.get("freqName");
		List<String> xAxis = startTimeList.subList(current.get(0), current.get(1));
		String startTime = startTimeList.get(current.get(0)).toString();
		String startTimeRadar = endTimeList.get(startTimeList.size() - 3).toString();
		String startTimePoint = endTimeList.get(0).toString();
		String endTime = endTimeList.get(current.get(1)).toString();
		String endTimeRadar = endTimeList.get(endTimeList.size() - 1).toString();
		String endTimePoint = endTimeList.get(endTimeList.size() - 1).toString();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("freqName", freqName);
		queryMap.put("startTime", startTime);
		queryMap.put("startTimeRadar", startTimeRadar);
		queryMap.put("startTimePoint", startTimePoint);
		queryMap.put("endTime", endTime);
		queryMap.put("endTimeRadar", endTimeRadar);
		queryMap.put("endTimePoint", endTimePoint);

		// 查询指标数据并绘制图形
		List<Object> classInfo = this.getClassInfo(analysisPlate, queryMap, xAxis, startTimeList);
		System.out.println("指标数据查询绘制成功:" + df.format(new Date()));
		result.put("baseInfo", baseInfo);
		result.put("timeCondition", timeCondition);
		result.put("classInfo", classInfo);

		return result;
	}

	public Map<String, Object> initAnalysisPlateByTime(int themeId, String startTime, String endTime, String freqName) {
		Map<String, Object> result = new HashMap<String, Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println("开始初始化数据:" + df.format(new Date()));// new Date()为获取当前系统时间
		// 获取版块信息
		List<AnalysisPlate> analysisPlate = analysisMapper.getAnalysisPlate(themeId);
		if (analysisPlate.size() == 0) {
			return result;
		}
		System.out.println("版块数据获取成功:" + df.format(new Date()));
		// 构建查询条件
		List<String> startTimeList = this.fillTimeList(freqName, startTime, endTime);
		List<String> endTimeList = this.fillTimeList(freqName, startTime, endTime);
		System.out.println("时间区间数据获取成功:" + df.format(new Date()));
		// 根据起始时间结束时间设置x轴
		Integer startFlag = 0;
		Integer endFlag = 0;
		for (int i = 0; i < startTimeList.size(); i++) {
			if (startTimeList.get(i).equals(startTime)) {
				startFlag = i;
			}
			if (startTimeList.get(i).equals(endTime)) {
				endFlag = i;
			}
		}
		List<String> xAxis = startTimeList.subList(startFlag, endFlag);

		String startTimeRadar = endTimeList.get(startTimeList.size() - 4).toString();
		String startTimePoint = endTimeList.get(0).toString();
		String endTimeRadar = endTimeList.get(endTimeList.size() - 1).toString();
		String endTimePoint = endTimeList.get(endTimeList.size() - 1).toString();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("freqName", freqName);
		queryMap.put("startTime", startTime);
		queryMap.put("startTimeRadar", startTimeRadar);
		queryMap.put("startTimePoint", startTimePoint);
		queryMap.put("endTime", endTime);
		queryMap.put("endTimeRadar", endTimeRadar);
		queryMap.put("endTimePoint", endTimePoint);

		// 查询指标数据并绘制图形
		List<Object> classInfo = this.getClassInfo(analysisPlate, queryMap, xAxis, startTimeList);
		System.out.println("指标数据查询绘制成功:" + df.format(new Date()));
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
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

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
				// 查询指标所有可取的时间区间
				List<String> freqNameList = analysisMapper.getFreqnameByIndicode(indiCode);
				// 所以这里对可能取不到的频度进行忽略
				for (int k = 0; k < freqNameList.size(); k++) {
					if (freqNameList.get(k).equals("")) {
						continue;
					} else {
						timeFreq.retainAll(freqNameList);
					}
				}
			}
		}
		System.out.println("时间频度数据获取成功:" + df.format(new Date()));

		// 获取时间选择器区间，取指标的并集
		for (int i = 0; i < timeFreq.size(); i++) {
			String freqName = timeFreq.get(i);
			Map<String, Object> timeConditionMap = new HashMap<String, Object>();
			Set<String> timeSpanFinal = new HashSet<String>();
			for (int j = 0; j < analysisPlate.size(); j++) {
				Integer pid = analysisPlate.get(j).getPlateId();
				List<AnalysisIndi> indiList = analysisMapper.getIndiByPid(pid);
				for (int k = 0; k < indiList.size(); k++) {
					Map<String, Object> queryMap = new HashMap<String, Object>();
					String indiCode = indiList.get(k).getIndiCode();
					queryMap.put("indiCode", indiCode);
					queryMap.put("freqName", freqName);
					// TOOD 从app_analysis_indi_time视图中直接取出start_time/end_time并组成数组
					// List<String> timeList = analysisMapper.getTimeByFreqname(queryMap);
					List<AnalysisIndiTime> timeList1 = analysisMapper.getTimeByFreq(queryMap);
					String startTime = timeList1.get(0).getStartTime();
					String endTime = timeList1.get(0).getEndTime();
					List<String> timeList2 = this.fillTimeList(freqName, startTime, endTime);
					Set<String> timeSpanSet = new HashSet<String>(timeList2);
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
				timeConditionMap.put("current", subIndex);
			}
			timeCondition.add(timeConditionMap);
			System.out.println(freqName + "时间频度区间成功:" + df.format(new Date()));
		}
		return timeCondition;
	}

	@Override
	public List<Object> getClassInfo(List<AnalysisPlate> analysisPlate, Map<String, Object> queryMap,
			List<String> xAxis, List<String> timeList) {
		List<Object> TotalList = new ArrayList<Object>();
		for (int i = 0; i < analysisPlate.size(); i++) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			System.out.println("正在处理第" + i + "个版块数据:" + df.format(new Date()));
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
				TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legend, dataValue);
				TotalList.add(lineEntity);
				TotalList.add(tableEntity);
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
			case "散点图": {
				System.out.println("进入散点图");
				List<List<String>> dataValue = new ArrayList<List<String>>();
				List<String> legend = new ArrayList<String>();
				PointType pointType = new PointType();
				for (int j = 0; j < indiList.size(); j++) {
					Map<String, Object> queryMap1 = new HashMap<String, Object>();
					queryMap1.put("freqName", queryMap.get("freqName"));
					queryMap1.put("startTime", queryMap.get("startTimePoint"));
					queryMap1.put("endTime", queryMap.get("endTimePoint"));
					queryMap1.put("indiCode", indiList.get(j).getIndiCode());
					List<AnalysisIndiValue> indiInfoList = analysisMapper.getIndiValue(queryMap1);
					List<String> dataIndiValue = Arrays.asList(new String[timeList.size()]);
					for (int m = 0; m < indiInfoList.size(); m++) {
						String dataXTemp = indiInfoList.get(m).getTime();
						if (timeList.contains(dataXTemp)) {
							int index = timeList.indexOf(dataXTemp);
							dataIndiValue.set(index, indiInfoList.get(m).getIndiValue());
						}
					}
					dataValue.add(dataIndiValue);
					legend.add(indiList.get(j).getIndiName());
				}
				PointEntity pointEntity = pointType.getOption(id, title, legend, dataValue);
				TotalList.add(pointEntity);
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

	public List<AnalysisPlate> getAnalysisPlate(int themeId) {
		return analysisMapper.getAnalysisPlate(themeId);
	}

	public List<AnalysisIndi> getAnalysisIndi(int plateId) {
		return analysisMapper.getIndiByPid(plateId);
	}

	public List<String> fillTimeList(String freqName, String startTime, String endTime) {
		List<String> timeList = new ArrayList<String>();
		switch (freqName) {
		case "月度":
			// 2016/01
			int startYear1 = Integer.parseInt(startTime.substring(0, 4));
			int startMonth1 = Integer.parseInt(startTime.substring(5, 7));
			int endYear1 = Integer.parseInt(endTime.substring(0, 4));
			int endMonth1 = Integer.parseInt(endTime.substring(5, 7));
			for (int i = startYear1; i <= endYear1; i++) {
				if (i == startYear1) {
					for (int j = startMonth1; j <= 12; j++) {
						String timeItem = String.valueOf(i) + '/' + String.format("%02d", Integer.valueOf(j));
						timeList.add(timeItem);
					}
				}
				if (i != startYear1 && i != endYear1) {
					for (int j = 1; j <= 12; j++) {
						String timeItem = String.valueOf(i) + '/' + String.format("%02d", Integer.valueOf(j));
						timeList.add(timeItem);
					}
				}
				if (i == endYear1) {
					for (int j = 1; j <= endMonth1; j++) {
						String timeItem = String.valueOf(i) + '/' + String.format("%02d", Integer.valueOf(j));
						timeList.add(timeItem);
					}
				}
			}
			break;
		case "季度":
			// 2016/Q1
			int startYear2 = Integer.parseInt(startTime.substring(0, 4));
			int startQuarter2 = Integer.parseInt(startTime.substring(6, 7));
			int endYear2 = Integer.parseInt(endTime.substring(0, 4));
			int endQuarter2 = Integer.parseInt(endTime.substring(6, 7));
			for (int i = startYear2; i <= endYear2; i++) {
				if (i == startYear2) {
					for (int j = startQuarter2; j <= 4; j++) {
						String timeItem = String.valueOf(i) + "/Q" + String.valueOf(j);
						timeList.add(timeItem);
					}
				}
				if (i != startYear2 && i != endYear2) {
					for (int j = 1; j <= 4; j++) {
						String timeItem = String.valueOf(i) + "/Q" + String.valueOf(j);
						timeList.add(timeItem);
					}
				}
				if (i == endYear2) {
					for (int j = 1; j <= endQuarter2; j++) {
						String timeItem = String.valueOf(i) + "/Q" + String.valueOf(j);
						timeList.add(timeItem);
					}
				}
			}
			break;
		case "年度":
			// 2016
			int startYear3 = Integer.parseInt(startTime.substring(0, 4));
			int endYear3 = Integer.parseInt(endTime.substring(0, 4));
			for (int i = startYear3; i <= endYear3; i++) {
				String timeItem = String.valueOf(i);
				timeList.add(timeItem);
			}
			break;
		default:
			break;
		}
		return timeList;
	}

}