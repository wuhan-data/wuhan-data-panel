package com.wuhan_data.app.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import com.wuhan_data.app.service.SessionSQLServiceApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wuhan_data.app.service.IndiDetailService;
import com.wuhan_data.app.service.IndiSearchService;
import com.wuhan_data.app.showType.BarType;
import com.wuhan_data.app.showType.LineType;
import com.wuhan_data.app.showType.TableType;
import com.wuhan_data.app.showType.pojo.BarEntity;
import com.wuhan_data.app.showType.pojo.LineEntity;
import com.wuhan_data.app.showType.pojo.TableEntity;
import com.wuhan_data.pojo.Collect;
import com.wuhan_data.pojo.HistorySearch;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.TPIndiValue;
import com.wuhan_data.tools.MapValueComparator;
import com.wuhan_data.tools.StringToMap;

@Controller
@RequestMapping("")
public class IndiSearchAppController {

	@Autowired
	IndiSearchService indiSearchService;
	@Autowired
	IndiDetailService indiDetailService;
	@Autowired
	SessionSQLServiceApp sessionSQLServiceApp;

	@RequestMapping(value = "searchTrend", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String searchSource() {

		Map map = new HashMap();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 获取当前时间，设置日期格式
		String nowDate = df.format(new Date());
		List<String> trendKeywordList = indiSearchService.getTrendList(nowDate);
		List<String> trendCreateTimeList = indiSearchService.getTrendList1(nowDate);
		System.out.println("查询完毕！");

		String period = "7";
		String dateTime = trendCreateTimeList.get(trendKeywordList.size() - 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		String oneWeek = null, twoWeek1 = null;
		try {
			date = sdf.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(calendar.DATE, calendar.get(calendar.DATE) - Integer.parseInt(period));
		// 返回减去后的最终时间
		oneWeek = sdf.format(calendar.getTime());
		calendar.set(calendar.DATE, calendar.get(calendar.DATE) - 8);
		twoWeek1 = sdf.format(calendar.getTime());

		Map<String, Float> mapNum = new TreeMap<String, Float>();
		Map<String, Float> mapNum2 = new TreeMap<String, Float>();
		float oneWeekNum = 0, twoWeekNum = 0;
		for (int i = 0; i < trendKeywordList.size(); i++) {
			if (oneWeek.compareTo(trendCreateTimeList.get(i)) < 0) {
				oneWeekNum++;
				if (mapNum.containsKey(trendKeywordList.get(i))) {
					mapNum.put(trendKeywordList.get(i), mapNum.get(trendKeywordList.get(i)) + 1);
				} else {
					mapNum.put(trendKeywordList.get(i), (float) 1);
				}
			} else if (trendCreateTimeList.get(i).compareTo(oneWeek) < 0
					&& trendCreateTimeList.get(i).compareTo(twoWeek1) > 0) {
				twoWeekNum++;
				if (mapNum2.containsKey(trendKeywordList.get(i))) {
					mapNum2.put(trendKeywordList.get(i), mapNum2.get(trendKeywordList.get(i)) + 1);
				} else {
					mapNum2.put(trendKeywordList.get(i), (float) 1.0);
				}
			}
		}

		Set<String> s = mapNum.keySet();// 获取KEY集合
		for (String str : s) {
			mapNum.put(str, mapNum.get(str) / oneWeekNum);
		}

		Set<String> s1 = mapNum2.keySet();// 获取KEY集合
		for (String str : s1) {
			mapNum2.put(str, mapNum2.get(str) / twoWeekNum);
		}

		System.out.println("mapNum:" + mapNum);
		System.out.println("mapNum2:" + mapNum2);
		Map finaMap = new TreeMap();
		List<Float> judgeList = new ArrayList<Float>();
		Set<String> s2 = mapNum.keySet();
		for (String mapNumStr : s2) {
			if (mapNum2.containsKey(mapNumStr)) {
				judgeList.add(mapNum.get(mapNumStr) - mapNum2.get(mapNumStr));
			}

			else {
				judgeList.add(mapNum.get(mapNumStr));
			}
			finaMap.put(mapNumStr, mapNum.get(mapNumStr));

		}
		System.out.println("judgeList:" + judgeList);
		Map<String, Float> resultMap = sortMapByValue(mapNum);// 本周排序后的结果
		System.out.println("finaMap:" + finaMap);
		System.out.println("resultMap:" + resultMap);
		Set set = resultMap.entrySet();
		Iterator i = set.iterator();
		List<Map<String, String>> paramList = new ArrayList<Map<String, String>>();// 接口最终数据
		int index = 1;
		while (i.hasNext()) {
			Map tempMap = new TreeMap();
			Map.Entry me = (Map.Entry) i.next();
			// 获得搜索指标的来源
			Map paraMap = new HashMap();
			paraMap.put("indi_name", me.getKey());
			paraMap.put("nowDate", nowDate);
			System.out.println("me.getKey():" + me.getKey());
//			calendar.set(calendar.DATE, calendar.get(calendar.DATE) - 8);
//			paraMap.put("beforeDate", calendar.getTime());
			String trendSource = indiSearchService.getTrendSource(paraMap);
			// 获得路径
			paraMap.put("source", trendSource);
			String lj = indiSearchService.getTrendLj(paraMap);
			// 获得指标代码
			paraMap.put("lj", lj);
			System.out.println("lj:" + lj);
			String indexCode;
			if (trendSource.equals("国统")) {
				indexCode = indiDetailService.getIndiCodeG(paraMap);
				System.out.println("indexCode国统:" + indexCode);
			} else {
				indexCode = indiDetailService.getIndiCode(paraMap);
				System.out.println("indexCode湖统:" + indexCode);
			}

			tempMap.put("indexId", indexCode);
			tempMap.put("id", Integer.toString(index));
			if (trendSource.equals("国统")) {
				String temp[] = ((String) me.getKey()).split("::");
				if (temp.length > 1) {
					System.out.println("真正的指标名称:" + temp[1]);
					tempMap.put("name", temp[1]);
				} else
					tempMap.put("name", temp[0]);

			} else
				tempMap.put("name", me.getKey());
			tempMap.put("path", lj);
			tempMap.put("source", trendSource);

			float t = (float) finaMap.get(me.getKey());
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			String tS, type;
			if (judgeList.get(index - 1) >= 0) {
				type = "up";// 代表上升
				tS = decimalFormat.format(t * 100);
			} else {
				type = "down";// 代表下降f.toString();finaMap.get(me.getKey()).toString()
				tS = decimalFormat.format(t * 100);
			}

			tempMap.put("arrow", type);
			tempMap.put("rate", tS + "%");
			paramList.add(tempMap);
			index++;
		}

		Map dataMap = new HashMap();
		if (paramList.size() > 5) {
			dataMap.put("trend", paramList.subList(0, 5));
		} else {
			dataMap.put("trend", paramList);
		}

		map.put("errCode", "0");
		map.put("errMsg", "success");
		map.put("data", dataMap);

		String param = JSON.toJSONString(map);
		return param;
	}

	@RequestMapping(value = "searchIndi", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String searchIndi(@RequestBody String resquestParams) {
		JSONObject requestObject = JSONObject.parseObject(resquestParams);
		String keyWord = "";
		String source = "全部";// 国统、湖统、全部
		System.out.println("进入searchIndi");
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			boolean hasKeyword = requestObject.containsKey("keyword");
			if (!hasKeyword) {
				return this.apiReturn("-1", "需要指定关键词", data);
			}
			keyWord = requestObject.get("keyword").toString();
			boolean hasSource = requestObject.containsKey("source");
			if (!hasSource) {
				return this.apiReturn("-1", "需要指定数据来源", data);
			}
			source = requestObject.get("source").toString();
		} catch (Exception e) {
			return this.apiReturn("-1", "参数获取异常", data);
		}

		/* 将所有关键字小写转换成大写 */
		System.out.println("转换前：" + keyWord);
		keyWord = keyWord.toUpperCase();
		System.out.println("转换后：" + keyWord);
		List<IndexManage> searchIndiListG = new ArrayList();
		List<IndexManage> searchIndiListH = new ArrayList();
		if (source.equals("全部")) {
			// 来自国统的数据
			searchIndiListG = indiSearchService.searchIndiG(keyWord);
			// 查询来自湖统的数据
			searchIndiListH = indiSearchService.searchIndiH(keyWord);
		} else if (source.equals("湖统")) {
			searchIndiListH = indiSearchService.searchIndiH(keyWord);
		} else
			searchIndiListG = indiSearchService.searchIndiG(keyWord);

		List resultList = new ArrayList();
		// 放入来自国统的指标数据
		for (int i = 0; i < searchIndiListG.size(); i++) {
			if (searchIndiListG.get(i).getIs_show().equals("0")) {
				Map teMap = new HashMap();
				teMap.put("id", searchIndiListG.get(i).getIndi_code());
				teMap.put("name", searchIndiListG.get(i).getIndi_name());
				teMap.put("path", searchIndiListG.get(i).getLj());
				teMap.put("isArea", "0");
				teMap.put("source", "国统");
				resultList.add(teMap);
			}
		}
		// 放入来自湖统的数据
		for (int i = 0; i < searchIndiListH.size(); i++) {
			if (searchIndiListH.get(i).getIs_show().equals("0")) {
				Map teMap = new HashMap();
				teMap.put("id", searchIndiListH.get(i).getIndi_code());
				teMap.put("name", searchIndiListH.get(i).getIndi_name());
				teMap.put("path", searchIndiListH.get(i).getLj());
				// 判断是否有地市级数据
				int is_Area = indiSearchService.getIsArea(searchIndiListH.get(i));
				if (is_Area > 0) {
					teMap.put("isArea", "1");
				} else
					teMap.put("isArea", "0");
				teMap.put("source", "湖统");
				resultList.add(teMap);
			}
		}
		Map dataMap = new HashMap();
		dataMap.put("result", resultList);
		Map map = new HashMap();
		map.put("errCode", "0");
		map.put("errMsg", "success");
		map.put("data", dataMap);
		String param = JSON.toJSONString(map);
		return param;
	}

	@RequestMapping(value = "searchDetail", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String indiDetail(@RequestBody String resquestParams) {
		JSONObject requestObject = JSONObject.parseObject(resquestParams);// @RequestBody String resquestParams
		String token = "";
		String indexCode = "0100001";
		String source = "湖统";
		Integer userId = 0;
		String isArea = "1";
		String lj = "地区生产总值(GDP)-三次产业-第一产业";
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			try {
				token = requestObject.containsKey("token") == false ? "" : requestObject.get("token").toString();
			} catch (Exception e) {
				return this.apiReturn("-1", "参数获取异常", data);
			}

			try {
				if (!token.equals("")) {
					String mapString = sessionSQLServiceApp.get(token).getSess_value();
					Map mapS = StringToMap.stringToMap(mapString);
					userId = Integer.valueOf((String) mapS.get("userId"));
				}
			} catch (Exception e) {
				System.out.println("无效的token令牌");
			}

			boolean hasIndexCode = requestObject.containsKey("indexId");
			if (!hasIndexCode) {
				return this.apiReturn("-1", "需要指定栏目id", data);
			}
			indexCode = requestObject.get("indexId").toString();

			boolean hasSource = requestObject.containsKey("source");
			if (!hasSource) {
				return this.apiReturn("-1", "需要指定数据来源", data);
			}
			source = requestObject.get("source").toString();

			isArea = requestObject.get("isArea").toString();// 判断是地市级数据还是全国数据
//		area_name = requestObject.get("area_name").toString();
			lj = requestObject.get("path").toString();
		} catch (Exception e) {
			return this.apiReturn("-1", "参数获取异常", data);
		}
		HistorySearch historySearch = new HistorySearch();
		String appIndiName = "";
		if (source.equals("国统")) {
			appIndiName = indiDetailService.getIndexName(indexCode);
			historySearch.setKeyword(appIndiName);
			String temp[] = appIndiName.split("::");
			if (temp.length > 1) {
				System.out.println("真正的指标名称:" + temp[1]);
				appIndiName = temp[1];
			} else
				appIndiName = temp[0];

		} else {
			appIndiName = indiDetailService.getIndexNameH(indexCode);
			historySearch.setKeyword(appIndiName);
		}

		Map favoriteMap = new HashMap();
		favoriteMap.put("appIndiName", appIndiName);
		favoriteMap.put("source", source);
		favoriteMap.put("userId", userId);
		int isFavorite = indiDetailService.getIsFavorite(favoriteMap);
		boolean isF = false;
		if (isFavorite > 0)
			isF = true;
		Map baseInfoMap = new HashMap();
		baseInfoMap.put("source", source);
		baseInfoMap.put("indexId", indexCode);
		baseInfoMap.put("indexName", appIndiName);
		baseInfoMap.put("isFavorite", isF);
		// 记录历史搜索
		Date date = new Date();

		historySearch.setCreate_time(date);
		historySearch.setUid(userId);
		historySearch.setSource(source);
		historySearch.setLj(lj);
		indiSearchService.addSearchHistory(historySearch);
		// 创建图
		List classInfoList = new ArrayList();
		// timeCondition列表
		List<Map> timeCondition = new ArrayList();
		// 首先判断是展示湖北省全国还是湖北省地级市数据
		/* 国统数据 */

		if (isArea.equals("0")) {
			// 查出频度范围
			Map fcMap = new HashMap();
			fcMap.put("appIndiName", appIndiName);
			fcMap.put("source", source);
//			fcMap.put("area_name", area_name);
			// 判断是全国数据还是湖北省数据,获得频度
			List<String> freqCodeListH = new ArrayList();
			List<String> freqCodeListG = new ArrayList();
			if (source.equals("湖统")) {
				freqCodeListH = indiDetailService.getFreqCodeByIndiName(fcMap);
				List<Map<String, String>> timeRangeList = new ArrayList();

				for (int i = 0; i < freqCodeListH.size(); i++) {
					Map timeMap = new HashMap();
					switch (freqCodeListH.get(i)) {
					case "MM":
						timeMap.put("freqName", "月度");
						break;
					case "SS":
						timeMap.put("freqName", "季度");
						break;
					default:
						timeMap.put("freqName", "年度");
						break;
					}
					// 查询日期范围
					Map ParaMap = new HashMap();// 获取日期参数范围的参数map
					ParaMap.put("freqCode", freqCodeListH.get(i));
					ParaMap.put("appIndiName", appIndiName);
					ParaMap.put("source", source);
//					ParaMap.put("area_name", area_name);
					List<String> indiDateList = indiDetailService.indiDateByFreqName(ParaMap);
					Collections.sort(indiDateList);
					System.out.println("湖统timeRange:" + indiDateList);

					List<String> newindiDateList = new ArrayList<String>();
					for (int k = 0; k < indiDateList.size(); k++) {
						newindiDateList
								.add(indiDateList.get(k).substring(0, 4) + "/" + indiDateList.get(k).substring(4, 6));
					}
					timeMap.put("startArray", newindiDateList);// 开始时间范围
					timeMap.put("endArray", newindiDateList);// 结束时间范围
					if (i == 0) {
						List currentList = new ArrayList();
						if (indiDateList.size() >= 8) {
							currentList.add(indiDateList.size() - 8);
						} else
							currentList.add(0);
						currentList.add(indiDateList.size() - 1);
						timeMap.put("current", currentList);
					}
					timeMap.put("areaName", new ArrayList());// 湖统数据中不加地市级数据没有区域
					timeCondition.add(timeMap);
				}
			} else {
				// 国统数据可能包含各个省
				freqCodeListG = indiDetailService.getFreqCodeByIndiNameG(fcMap);
				List<Map<String, String>> timeRangeList = new ArrayList();
				for (int i = 0; i < freqCodeListG.size(); i++) {
					Map timeMap = new HashMap();
					timeMap.put("freqName", freqCodeListG.get(i));
					// 查询日期范围
					Map ParaMap = new HashMap();// 获取日期参数范围的参数map
					ParaMap.put("freqCode", freqCodeListG.get(i));
					ParaMap.put("appIndiName", appIndiName);
					ParaMap.put("source", source);
//					ParaMap.put("area_name", area_name);
					List<String> indiDateListG = indiDetailService.indiDateByFreqNameG(ParaMap);
					Collections.sort(indiDateListG);
					System.out.println("国统timeRange:" + indiDateListG);

					List<String> newindiDateList = new ArrayList<String>();
					for (int k = 0; k < indiDateListG.size(); k++) {
						newindiDateList.add(indiDateListG.get(k));
					}
					timeMap.put("startArray", newindiDateList);// 开始时间范围
					timeMap.put("endArray", newindiDateList);// 结束时间范围
					if (i == 0) {
						List currentList = new ArrayList();
						if (indiDateListG.size() >= 8) {
							currentList.add(indiDateListG.size() - 8);
						} else
							currentList.add(0);
						currentList.add(indiDateListG.size() - 1);
						timeMap.put("current", currentList);
					}
					List<String> areaNameList = new ArrayList();
					areaNameList = indiDetailService.getAreaNameListG(ParaMap);
					System.out.println("国统的区域名称列表：" + areaNameList);
					timeMap.put("areaName", areaNameList);// 湖统数据中不加地市级数据没有区域
					timeCondition.add(timeMap);
				}
			}
			if (source.equals("湖统")) {
				Map ParameterMap = new HashMap();
				if (freqCodeListH.size() == 0) {
					Map finalMap = new HashMap();
					finalMap.put("errCode", "0");
					finalMap.put("errMsg", "success");
					finalMap.put("data", "没有搜索到相关内容");
					String param = JSON.toJSONString(finalMap);
					return param;
				}
				ParameterMap.put("freqCode", freqCodeListH.get(0));
				ParameterMap.put("appIndiName", appIndiName);
				ParameterMap.put("source", source);
				List<String> indiDateListDefaultH = indiDetailService.indiDateByFreqName(ParameterMap);
				System.out.println("indiDateListDefaultH" + indiDateListDefaultH);
				Collections.sort(indiDateListDefaultH);
				// 创建图例列表和数据列表
				List<String> legendData1 = new ArrayList();
				String endTime1 = indiDateListDefaultH.get(indiDateListDefaultH.size() - 1);
				System.out.println("endTime1" + endTime1);
				String startTime1;
				if (indiDateListDefaultH.size() >= 8) {
					startTime1 = indiDateListDefaultH.get(indiDateListDefaultH.size() - 8);
				} else {
					startTime1 = indiDateListDefaultH.get(0);
				}
				Map defaultMap = new HashMap();
				defaultMap.put("appIndiName", appIndiName);
				System.out.println("appIndiName：" + appIndiName);
				defaultMap.put("freqCode", freqCodeListH.get(0));
				System.out.println("freqCode：" + freqCodeListH.get(0));
				defaultMap.put("startTime", startTime1);
				System.out.println("startTime1：" + startTime1);
				defaultMap.put("endTime", endTime1);
				System.out.println("endTime：" + endTime1);
				defaultMap.put("source", source);
				System.out.println("source：" + source);
				defaultMap.put("lj", lj);
				System.out.println("lj：" + lj);
				List<TPIndiValue> defaultIndiValueList = indiDetailService.getIndiValue(defaultMap);
				Collections.sort(defaultIndiValueList, new Comparator<TPIndiValue>() {
					@Override
					public int compare(TPIndiValue r1, TPIndiValue r2) {
						int nameIndex = r1.getDate_code().compareTo(r2.getDate_code());
						int ageIndex = 0;
						int startIndex = 0;
						return nameIndex + ageIndex + startIndex;
					}
				});
				System.out.println("defaultIndiValueList:" + defaultIndiValueList);
				System.out.println("defaultIndiValueList长度：" + defaultIndiValueList.size());
				// 对查询出的指标值根据不同的时点分类
				TreeMap<String, ArrayList<TPIndiValue>> tm = new TreeMap<String, ArrayList<TPIndiValue>>();
				for (int i = 0; i < defaultIndiValueList.size(); i++) {
					TPIndiValue tv = defaultIndiValueList.get(i);
					if (tm.containsKey(tv.getTime_point())) {
						ArrayList<TPIndiValue> l11 = (ArrayList<TPIndiValue>) tm.get(tv.getTime_point());
						l11.add(tv);
					} else {
						ArrayList tem = new ArrayList();
						tem.add(tv);
						tm.put(tv.getTime_point(), tem);
					}
				}
				Set<String> set = tm.keySet();
				legendData1.addAll(set);

				// 创建数据列表

				for (int i = 0; i < legendData1.size(); i++) {
					List<List<String>> dataV = new ArrayList();
					List<List<String>> dataX = new ArrayList();
					List<TPIndiValue> tempList = (List<TPIndiValue>) tm.get(legendData1.get(i));
					List<String> dataList = new ArrayList();
					List<String> dateList = new ArrayList();
					List<String> legendList = new ArrayList();
					if (tempList.size() >= 8) {
						for (int j = 0; j < 8; j++) {// tempList.size()

							dateList.add(tempList.get(j).getDate_code().substring(0, 4) + "/"
									+ tempList.get(j).getDate_code().substring(4, 6));
							dataList.add(tempList.get(j).getIndi_value());
						}
					} else {
						for (int j = 0; j < tempList.size(); j++) {// tempList.size()

							dateList.add(tempList.get(j).getDate_code().substring(0, 4) + "/"
									+ tempList.get(j).getDate_code().substring(4, 6));
							dataList.add(tempList.get(j).getIndi_value());
						}
					}
					String showName;
					if (legendData1.get(i).equals("104")) {
						showName = "本期";
					} else if (legendData1.get(i).equals("203")) {
						showName = "自年初累计";
					} else {
						showName = "其他";
					}
					legendList.add(appIndiName + "-" + showName);
					dataV.add(dataList);
					dataX.add(dateList);
					List<String> showColor = new ArrayList<String>();
					List<String> showType = new ArrayList<String>();
					if (appIndiName.equals("湖北PMI")) {
						// 创建折线图
						LineType lineType = new LineType();
						LineEntity lineEntity = lineType.getOption(Integer.toString(i + 1),
								appIndiName + "-" + showName, dataX.get(0), legendList, dataV, showColor, showType);

						// 创建表格
						TableType tableType = new TableType();
						TableEntity tableEntity = tableType.getTable(Integer.toString(i + 2),
								appIndiName + "-" + showName, dataX, legendList, dataV);// 表格
						classInfoList.add(lineEntity);
						classInfoList.add(tableEntity);
					} else {
						if (legendData1.get(i).equals("104") || legendData1.get(i).equals("203")) {
							// 创建柱状图
							BarType barType = new BarType();// 柱状图
							System.out.println("legendList长度:" + legendList.size());
							BarEntity barEntity = barType.getOption(Integer.toString(i + 1),
									appIndiName + "-" + showName, dataX.get(0), legendList, dataV, showColor, showType);
							classInfoList.add(barEntity);

							// 创建表格
							TableType tableType = new TableType();
							TableEntity tableEntity = tableType.getTable(Integer.toString(i + 2),
									appIndiName + "-" + showName, dataX, legendList, dataV);// 表格
							System.out.println("yes or no:" + tableEntity.getTableBody());
							classInfoList.add(tableEntity);
						} else {
							// TODO 其他情况
							// 创建折线图
							LineType lineType = new LineType();
							LineEntity lineEntity = lineType.getOption(Integer.toString(i + 1),
									appIndiName + "-" + showName, dataX.get(0), legendList, dataV, showColor, showType);

							// 创建表格
							TableType tableType = new TableType();
							TableEntity tableEntity = tableType.getTable(Integer.toString(i + 2),
									appIndiName + "-" + showName, dataX, legendList, dataV);// 表格
							classInfoList.add(lineEntity);
							classInfoList.add(tableEntity);

						}
					}

				}

			} else {
				Map ParameterMap = new HashMap();
				if (freqCodeListG.size() == 0) {
					Map finalMap = new HashMap();
					finalMap.put("errCode", "0");
					finalMap.put("errMsg", "success");
					finalMap.put("data", "没有搜索到相关内容");
					String param = JSON.toJSONString(finalMap);
					return param;
				}
				ParameterMap.put("freqCode", freqCodeListG.get(0));
				ParameterMap.put("appIndiName", appIndiName);
				ParameterMap.put("source", source);
				List<String> areaNameList = new ArrayList();
				areaNameList = indiDetailService.getAreaNameListG(ParameterMap);
				ParameterMap.put("areaName", areaNameList.get(0));
				List<String> indiDateListDefaultG1 = indiDetailService.indiDateByFreqNameG1(ParameterMap);
				System.out.println("indiDateListDefaultH" + indiDateListDefaultG1);
				Collections.sort(indiDateListDefaultG1);
				// 创建图例列表和数据列表
				List<String> legendData1 = new ArrayList();
				String endTime1 = indiDateListDefaultG1.get(indiDateListDefaultG1.size() - 1);
				System.out.println("endTime1" + endTime1);
				String startTime1;
				if (indiDateListDefaultG1.size() >= 8) {
					startTime1 = indiDateListDefaultG1.get(indiDateListDefaultG1.size() - 8);
				} else {
					startTime1 = indiDateListDefaultG1.get(0);
				}
				Map defaultMap = new HashMap();
				defaultMap.put("appIndiName", appIndiName);
				System.out.println("appIndiName:" + appIndiName);
				defaultMap.put("freqCode", freqCodeListG.get(0));
				System.out.println("freqCode:" + freqCodeListG.get(0));
				defaultMap.put("startTime", startTime1);
				System.out.println("startTime:" + startTime1);
				defaultMap.put("endTime", endTime1);
				System.out.println("endTime:" + endTime1);
				defaultMap.put("source", source);
				System.out.println("source:" + source);
				defaultMap.put("area_name", areaNameList.get(0));
				System.out.println("area_name:" + areaNameList.get(0));
				defaultMap.put("indexCode", indexCode);
				System.out.println("indexCode:" + indexCode);
				List<TPIndiValue> defaultIndiValueListG = indiDetailService.getIndiValueG(defaultMap);
				Collections.sort(defaultIndiValueListG, new Comparator<TPIndiValue>() {
					public int compare(TPIndiValue r1, TPIndiValue r2) {
						int nameIndex = r1.getDate_code().compareTo(r2.getDate_code());
						int ageIndex = 0;
						int startIndex = 0;
						return nameIndex + ageIndex + startIndex;
					}
				});
				List<List<String>> dataV = new ArrayList();
				List<List<String>> dataX = new ArrayList();
				List<String> dataList = new ArrayList();
				List<String> dateList = new ArrayList();
				List<String> legendList = new ArrayList();
				if (defaultIndiValueListG.size() >= 8) {
					for (int j = 0; j < 8; j++) {// tempList.size()

						dateList.add(defaultIndiValueListG.get(j).getDate_code());
						dataList.add(defaultIndiValueListG.get(j).getIndi_value());
					}
				} else {
					for (int j = 0; j < defaultIndiValueListG.size(); j++) {// tempList.size()

						dateList.add(defaultIndiValueListG.get(j).getDate_code());
						dataList.add(defaultIndiValueListG.get(j).getIndi_value());
					}
				}
				legendList.add(appIndiName);
				dataV.add(dataList);
				dataX.add(dateList);
				List<String> showColor = new ArrayList<String>();
				List<String> showType = new ArrayList<String>();
				if (appIndiName.equals("湖北PMI")) {
					// 创建折线图
					LineType lineType = new LineType();
					LineEntity lineEntity = lineType.getOption(Integer.toString(1), appIndiName, dataX.get(0),
							legendList, dataV, showColor, showType);
					// 创建表格
					TableType tableType = new TableType();
					TableEntity tableEntity = tableType.getTable(Integer.toString(2), appIndiName, dataX, legendList,
							dataV);// 表格
					classInfoList.add(lineEntity);
					classInfoList.add(tableEntity);
				} else {
					// 创建柱状图
					BarType barType = new BarType();// 柱状图
					System.out.println("legendList长度:" + legendList.size());
					BarEntity barEntity = barType.getOption(Integer.toString(1), appIndiName, dataX.get(0), legendList,
							dataV, showColor, showType);
					classInfoList.add(barEntity);
					// 创建表格
					TableType tableType = new TableType();
					TableEntity tableEntity = tableType.getTable(Integer.toString(2), appIndiName, dataX, legendList,
							dataV);// 表格
					System.out.println("yes or no:" + tableEntity.getTableBody());
					classInfoList.add(tableEntity);
				}
			}
		} // 判断是否展示地级市指标的if结束

		else {
			// 展示地级市数据
			// 查出频度范围
			Map fcMap = new HashMap();
			fcMap.put("appIndiName", appIndiName);
			fcMap.put("source", source);
			fcMap.put("lj", lj);
			// 获得频度
			List<String> freqCodeListH = new ArrayList();
			freqCodeListH = indiDetailService.getFreqCodeByIndiNameArea(fcMap);
			List<Map<String, String>> timeRangeList = new ArrayList();
			// 选取的时间字符
			List<String> selectTimeList = new ArrayList<String>();
			for (int i = 0; i < freqCodeListH.size(); i++) {
				Map timeMap = new HashMap();
				switch (freqCodeListH.get(i)) {
				case "MM":
					timeMap.put("freqName", "月度");
					break;
				case "SS":
					timeMap.put("freqName", "季度");
					break;
				default:
					timeMap.put("freqName", "年度");
					break;
				}
				// 查询日期范围

				Map ParaMap = new HashMap();// 获取日期参数范围的参数map
				ParaMap.put("freqCode", freqCodeListH.get(i));
				ParaMap.put("appIndiName", appIndiName);
				ParaMap.put("source", source);
				ParaMap.put("lj", lj);
				List<String> indiDateList = indiDetailService.indiDateByFreqNameArea(ParaMap);
				Collections.sort(indiDateList);
				System.out.println("湖统timeRange:" + indiDateList);
				List<String> newindiDateList = new ArrayList<String>();
				for (int k = 0; k < indiDateList.size(); k++) {
					newindiDateList
							.add(indiDateList.get(k).substring(0, 4) + "/" + indiDateList.get(k).substring(4, 6));
				}
				timeMap.put("startArray", newindiDateList);// 开始时间范围
				timeMap.put("endArray", newindiDateList);// 结束时间范围

				if (i == 0) {
					List currentList = new ArrayList();
					if (indiDateList.size() >= 8) {
						currentList.add(indiDateList.size() - 8);
					} else
						currentList.add(0);
					currentList.add(indiDateList.size() - 1);
					timeMap.put("current", currentList);
					selectTimeList.add(newindiDateList.get(indiDateList.size() - 1));
				}

//				List<String> areaList = new ArrayList();
//				areaList = indiDetailService.getIndiAreaList(ParaMap);
//				timeMap.put("areaName", areaList);//湖统数据中不加地市级数据没有区域
				timeCondition.add(timeMap);
			}
			Map ParameterMap = new HashMap();
			if (freqCodeListH.size() == 0) {
				Map finalMap = new HashMap();
				finalMap.put("errCode", "0");
				finalMap.put("errMsg", "success");
				finalMap.put("data", "没有搜索到相关内容");
				String param = JSON.toJSONString(finalMap);
				return param;
			}
			ParameterMap.put("freqCode", freqCodeListH.get(0));
			ParameterMap.put("appIndiName", appIndiName);
			ParameterMap.put("source", source);
			ParameterMap.put("lj", lj);
//			List<String> area_nameList = new ArrayList();
//			area_nameList = indiDetailService.getIndiAreaList(ParameterMap);
//			ParameterMap.put("area_name", area_nameList.get(0));
			List<String> indiDateListDefaultH = indiDetailService.indiDateByFreqNameDefault(ParameterMap);
			System.out.println("indiDateListDefaultH" + indiDateListDefaultH);
			Collections.sort(indiDateListDefaultH);
			// 创建图例列表和数据列表
			List<String> legendData1 = new ArrayList();
//			String endTime1 = indiDateListDefaultH.get(indiDateListDefaultH.size() - 1);
//			System.out.println("endTime1" + endTime1);
//			String startTime1;
//			if (indiDateListDefaultH.size() >= 8) {
//				startTime1 = indiDateListDefaultH.get(indiDateListDefaultH.size() - 8);
//			} else {
//				startTime1 = indiDateListDefaultH.get(0);
//			}
//
//			System.out.println("startTime1" + startTime1);
			Map defaultMap = new HashMap();
			defaultMap.put("appIndiName", appIndiName);
			defaultMap.put("freqCode", freqCodeListH.get(0));
//			defaultMap.put("startTime", startTime1);
//			defaultMap.put("endTime", endTime1);
			defaultMap.put("source", source);
			defaultMap.put("time", indiDateListDefaultH.get(indiDateListDefaultH.size() - 1));
//			defaultMap.put("area_name", area_nameList.get(0));
			defaultMap.put("lj", lj);
			System.out.println("appIndiName" + appIndiName);
			System.out.println("freqCode" + freqCodeListH.get(0));
			System.out.println("source" + source);
			System.out.println("time" + indiDateListDefaultH.get(indiDateListDefaultH.size() - 1));
			System.out.println("lj" + lj);
			List<TPIndiValue> defaultIndiValueList = indiDetailService.getIndiValueArea(defaultMap);
//			Collections.sort(defaultIndiValueList, new Comparator<TPIndiValue>() {
//				@Override
//				public int compare(TPIndiValue r1, TPIndiValue r2) {
//					int nameIndex = r1.getDate_code().compareTo(r2.getDate_code());
//					int ageIndex = 0;
//					int startIndex = 0;
//					return nameIndex + ageIndex + startIndex;
//				}
//			});
			System.out.println("defaultIndiValueList:" + defaultIndiValueList);
			System.out.println("defaultIndiValueList长度：" + defaultIndiValueList.size());
			// 对查询出的指标值根据不同的时点分类
			TreeMap<String, ArrayList<TPIndiValue>> tm = new TreeMap<String, ArrayList<TPIndiValue>>();
			for (int i = 0; i < defaultIndiValueList.size(); i++) {
				TPIndiValue tv = defaultIndiValueList.get(i);
				if (tm.containsKey(tv.getTime_point())) {
					ArrayList<TPIndiValue> l11 = (ArrayList<TPIndiValue>) tm.get(tv.getTime_point());
					l11.add(tv);
				} else {
					ArrayList tem = new ArrayList();
					tem.add(tv);
					tm.put(tv.getTime_point(), tem);
				}
			}
			Set<String> set = tm.keySet();
			legendData1.addAll(set);
			// dataX = time_point; legendData = [area_name1, area_name2];
			// series = [{name = area_name1, data = [value]}, {}]
			// 创建数据列表
			for (int i = 0; i < legendData1.size(); i++) {
				List<List<String>> dataV = new ArrayList();
				List<List<String>> dataX = new ArrayList();
				List<TPIndiValue> tempList = (List<TPIndiValue>) tm.get(legendData1.get(i));
				List<String> dataList = new ArrayList();
				List<String> dateList = new ArrayList();
				List<String> legendList = new ArrayList();
				if (tempList.size() >= 8) {
					for (int j = 0; j < 8; j++) {// tempList.size()

						dateList.add(tempList.get(j).getArea_name());
						dataList.add(tempList.get(j).getIndi_value());
						legendList.add(tempList.get(j).getArea_name());
					}
				} else {
					for (int j = 0; j < tempList.size(); j++) {// tempList.size()

						dateList.add(tempList.get(j).getArea_name());
						dataList.add(tempList.get(j).getIndi_value());
						legendList.add(tempList.get(j).getArea_name());
					}
				}
				String showName;
				if (legendData1.get(i).equals("104")) {
					showName = "本期";
				} else if (legendData1.get(i).equals("203")) {
					showName = "自年初累计";
				} else {
					showName = "其他";
				}
				// legendList.add(appIndiName +"-" + showName);
				dataV.add(dataList);
				dataX.add(selectTimeList);
				List<String> showColor = new ArrayList<String>();
				List<String> showType = new ArrayList<String>();
				if (appIndiName.equals("湖北PMI")) {
					// 创建折线图
					LineType lineType = new LineType();
					LineEntity lineEntity = lineType.getOption(Integer.toString(i + 1), appIndiName + "-" + showName,
							dataX.get(0), legendList, dataV, showColor, showType);

					// 创建表格
					TableType tableType = new TableType();
					TableEntity tableEntity = tableType.getTable(Integer.toString(i + 2), appIndiName + "-" + showName,
							dataX, legendList, dataV);// 表格
					classInfoList.add(lineEntity);
					classInfoList.add(tableEntity);
				} else {
					if (legendData1.get(i).equals("104") || legendData1.get(i).equals("203")) {
						// 创建柱状图
						BarType barType = new BarType();// 柱状图
						System.out.println("legendList长度:" + legendList.size());
						BarEntity barEntity = barType.getOption(Integer.toString(i + 1), appIndiName + "-" + showName,
								dataX.get(0), legendList, dataV, showColor, showType);
						classInfoList.add(barEntity);

						// 创建表格
						TableType tableType = new TableType();
						TableEntity tableEntity = tableType.getTable(Integer.toString(i + 2),
								appIndiName + "-" + showName, dataX, legendList, dataV);// 表格
						System.out.println("yes or no:" + tableEntity.getTableBody());
						classInfoList.add(tableEntity);
					} else {
						// TODO 其他情况
						// 创建折线图
						LineType lineType = new LineType();
						LineEntity lineEntity = lineType.getOption(Integer.toString(i + 1),
								appIndiName + "-" + showName, dataX.get(0), legendList, dataV, showColor, showType);

						// 创建表格
						TableType tableType = new TableType();
						TableEntity tableEntity = tableType.getTable(Integer.toString(i + 2),
								appIndiName + "-" + showName, dataX, legendList, dataV);// 表格
						classInfoList.add(lineEntity);
						classInfoList.add(tableEntity);
					}
				}
			}
		}

		Map finData = new HashMap();
		finData.put("baseInfo", baseInfoMap);
		finData.put("timeCondition", timeCondition);
		finData.put("classInfo", classInfoList);
		Map finalMap = new HashMap();
		finalMap.put("errCode", "0");
		finalMap.put("errMsg", "success");
		finalMap.put("data", finData);
		String param = JSON.toJSONString(finalMap);
		return param;

	}

	@RequestMapping(value = "searchConfirm", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String indiDetail1(@RequestBody String resquestParams) {
		JSONObject requestObject = JSONObject.parseObject(resquestParams);
		String indexCode = "";
		String source = "";
		String freqCode = "";
		String startTime = "";
		String endTime = "";
		String time = "";
		String areaName = "";
		String lj = "";
		String isArea = "";
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			boolean hasIsArea = requestObject.containsKey("isArea");
			if (!hasIsArea) {
				return this.apiReturn("-1", "需要指定是否是地市级", data);
			}
			isArea = requestObject.get("isArea").toString();
		} catch (Exception e) {

		}

		// 创建图
		List classInfoList = new ArrayList();
		if (isArea.equals("1")) {
			try {
				boolean hasIndexCode = requestObject.containsKey("indexId");
				boolean hasSource = requestObject.containsKey("source");
				boolean hasFreqName = requestObject.containsKey("timeFreq");
				boolean hasTime = requestObject.containsKey("startTime");
				boolean hasLj = requestObject.containsKey("path");
				if (!hasIndexCode) {
					return this.apiReturn("-1", "需要指定栏目id", data);
				}
				if (!hasSource) {
					return this.apiReturn("-1", "需要指定数据来源", data);
				}
				if (!hasFreqName) {
					return this.apiReturn("-1", "需要选择时间频度", data);
				}
				if (!hasTime) {
					return this.apiReturn("-1", "需要指定时间", data);
				}
				if (!hasLj) {
					return this.apiReturn("-1", "需要传入路径", data);
				}
				indexCode = requestObject.get("indexId").toString();
				source = requestObject.get("source").toString();
				freqCode = requestObject.get("timeFreq").toString();
				time = requestObject.get("startTime").toString();
				lj = requestObject.get("path").toString();
			} catch (Exception e) {
				return this.apiReturn("-1", "参数获取异常", data);
			}
			String appIndiName = indiDetailService.getIndexNameH(indexCode);
			Map defaultMap = new HashMap();
			defaultMap.put("appIndiName", appIndiName);
			defaultMap.put("freqCode", freqCode);
			defaultMap.put("source", source);
			defaultMap.put("time", time);
			defaultMap.put("lj", lj);
			List<TPIndiValue> defaultIndiValueList = indiDetailService.getIndiValueArea(defaultMap);
			System.out.println("defaultIndiValueList:" + defaultIndiValueList);
			System.out.println("defaultIndiValueList长度：" + defaultIndiValueList.size());
			// 对查询出的指标值根据不同的时点分类
			TreeMap<String, ArrayList<TPIndiValue>> tm = new TreeMap<String, ArrayList<TPIndiValue>>();
			for (int i = 0; i < defaultIndiValueList.size(); i++) {
				TPIndiValue tv = defaultIndiValueList.get(i);
				if (tm.containsKey(tv.getTime_point())) {
					ArrayList<TPIndiValue> l11 = (ArrayList<TPIndiValue>) tm.get(tv.getTime_point());
					l11.add(tv);
				} else {
					ArrayList tem = new ArrayList();
					tem.add(tv);
					tm.put(tv.getTime_point(), tem);
				}
			}
			// 创建图例列表和数据列表
			List<String> legendData1 = new ArrayList();
			Set<String> set = tm.keySet();
			legendData1.addAll(set);
			// 创建数据列表
			for (int i = 0; i < legendData1.size(); i++) {
				List<List<String>> dataV = new ArrayList();
				List<List<String>> dataX = new ArrayList();
				List<TPIndiValue> tempList = (List<TPIndiValue>) tm.get(legendData1.get(i));
				List<String> dataList = new ArrayList();
				List<String> dateList = new ArrayList();
				List<String> legendList = new ArrayList();
				if (tempList.size() >= 8) {
					for (int j = 0; j < 8; j++) {// tempList.size()

						dateList.add(tempList.get(j).getArea_name());
						dataList.add(tempList.get(j).getIndi_value());
						legendList.add(tempList.get(j).getArea_name());
					}
				} else {
					for (int j = 0; j < tempList.size(); j++) {// tempList.size()

						dateList.add(tempList.get(j).getArea_name());
						dataList.add(tempList.get(j).getIndi_value());
						legendList.add(tempList.get(j).getArea_name());
					}
				}
				String showName;
				if (legendData1.get(i).equals("104")) {
					showName = "本期";
				} else if (legendData1.get(i).equals("203")) {
					showName = "自年初累计";
				} else {
					showName = "其他";
				}
				dataV.add(dataList);
				dataX.add(dateList);
				List<String> showColor = new ArrayList<String>();
				List<String> showType = new ArrayList<String>();
				if (appIndiName.equals("湖北PMI")) {
					// 创建折线图
					LineType lineType = new LineType();
					LineEntity lineEntity = lineType.getOption(Integer.toString(i + 1), appIndiName + "-" + showName,
							dataX.get(0), legendList, dataV, showColor, showType);

					// 创建表格
					TableType tableType = new TableType();
					TableEntity tableEntity = tableType.getTable(Integer.toString(i + 2), appIndiName + "-" + showName,
							dataX, legendList, dataV);// 表格
					classInfoList.add(lineEntity);
					classInfoList.add(tableEntity);
				} else {
					if (legendData1.get(i).equals("104") || legendData1.get(i).equals("203")) {
						// 创建柱状图
						BarType barType = new BarType();// 柱状图
						System.out.println("legendList长度:" + legendList.size());
						BarEntity barEntity = barType.getOption(Integer.toString(i + 1), appIndiName + "-" + showName,
								dataX.get(0), legendList, dataV, showColor, showType);
						classInfoList.add(barEntity);

						// 创建表格
						TableType tableType = new TableType();
						TableEntity tableEntity = tableType.getTable(Integer.toString(i + 2),
								appIndiName + "-" + showName, dataX, legendList, dataV);// 表格
						System.out.println("yes or no:" + tableEntity.getTableBody());
						classInfoList.add(tableEntity);
					} else {
						// TODO 其他情况
						// 创建折线图
						LineType lineType = new LineType();
						LineEntity lineEntity = lineType.getOption(Integer.toString(i + 1),
								appIndiName + "-" + showName, dataX.get(0), legendList, dataV, showColor, showType);
						// 创建表格
						TableType tableType = new TableType();
						TableEntity tableEntity = tableType.getTable(Integer.toString(i + 2),
								appIndiName + "-" + showName, dataX, legendList, dataV);// 表格
						classInfoList.add(lineEntity);
						classInfoList.add(tableEntity);
					}
				}
			}

		} else {
			try {
				boolean hasIndexCode = requestObject.containsKey("indexId");
				boolean hasSource = requestObject.containsKey("source");
				boolean hasFreqName = requestObject.containsKey("timeFreq");
				boolean hasStartTime = requestObject.containsKey("startTime");
				boolean hasEndTime = requestObject.containsKey("endTime");
//				boolean hasAreaName = requestObject.containsKey("areaName");
				boolean hasLj = requestObject.containsKey("path");
				if (!hasIndexCode) {
					return this.apiReturn("-1", "需要指定栏目id", data);
				}
				if (!hasSource) {
					return this.apiReturn("-1", "需要指定数据来源", data);
				}
				if (!hasFreqName) {
					return this.apiReturn("-1", "需要选择时间频度", data);
				}
				if (!hasStartTime) {
					return this.apiReturn("-1", "需要选择起始时间", data);
				}
				if (!hasEndTime) {
					return this.apiReturn("-1", "需要选择结束时间", data);
				}
//				if (!hasAreaName) {
//					return this.apiReturn("-1", "需要选择地域维度", data);
//				}
				if (!hasLj) {
					return this.apiReturn("-1", "需要传入路径", data);
				}
				indexCode = requestObject.get("indexId").toString();
				source = requestObject.get("source").toString();
				freqCode = requestObject.get("timeFreq").toString();
				startTime = requestObject.get("startTime").toString();
				endTime = requestObject.get("endTime").toString();
//				areaName = requestObject.get("areaName").toString();
				lj = requestObject.get("path").toString();
			} catch (Exception e) {
				return this.apiReturn("-1", "参数获取异常", data);
			}
			// 判断是查询国统数据还是湖统数据
			String appIndiName = "";
			if (source.equals("国统")) {
				appIndiName = indiDetailService.getIndexName(indexCode);
				String temp[] = appIndiName.split("::");
				if (temp.length > 1)
					appIndiName = temp[1];
				else
					appIndiName = temp[0];
			} else {
				appIndiName = indiDetailService.getIndexNameH(indexCode);
			}
//			String startTime = mapget.get("startTime").toString();
//			String endTime = mapget.get("endTime").toString();
//			String freqCode = mapget.get("timeFreq").toString();
//			String startTime = "201408MM";
//			String endTime = "201610MM";
//			String freqCode = "月度";
			if (source.equals("国统")) {
				// 搜索条件
				Map defaultMap = new HashMap();
				defaultMap.put("appIndiName", appIndiName);
				defaultMap.put("freqCode", freqCode);
				defaultMap.put("startTime", startTime);
				defaultMap.put("endTime", endTime);
				defaultMap.put("source", source);
//				defaultMap.put("area_name", areaName);
				defaultMap.put("indexCode", indexCode);
				List<TPIndiValue> defaultIndiValueList = indiDetailService.getIndiValueG(defaultMap);
				Collections.sort(defaultIndiValueList, new Comparator<TPIndiValue>() {
					@Override
					public int compare(TPIndiValue r1, TPIndiValue r2) {
						int nameIndex = r1.getDate_code().compareTo(r2.getDate_code());
						int ageIndex = 0;
						int startIndex = 0;
						return nameIndex + ageIndex + startIndex;
					}
				});
				System.out.println("defaultIndiValueList:" + defaultIndiValueList);
				System.out.println("defaultIndiValueList长度：" + defaultIndiValueList.size());
				List<List<String>> dataX = new ArrayList();
				List<List<String>> dataV = new ArrayList();
				List<String> dataList = new ArrayList();
				List<String> dateList = new ArrayList();
				List<String> legendList = new ArrayList();
				if (defaultIndiValueList.size() >= 8) {
					for (int j = 0; j < 8; j++) {// tempList.size()

						dateList.add(defaultIndiValueList.get(j).getDate_code());
						dataList.add(defaultIndiValueList.get(j).getIndi_value());
					}
				} else {
					for (int j = 0; j < defaultIndiValueList.size(); j++) {// tempList.size()

						dateList.add(defaultIndiValueList.get(j).getDate_code());
						dataList.add(defaultIndiValueList.get(j).getIndi_value());
					}
				}
				legendList.add(appIndiName);
				dataV.add(dataList);
				dataX.add(dateList);
				List<String> showColor = new ArrayList<String>();
				List<String> showType = new ArrayList<String>();
				if (appIndiName.equals("湖北PMI")) {
					// 创建折线图
					LineType lineType = new LineType();
					LineEntity lineEntity = lineType.getOption(Integer.toString(1), appIndiName, dataX.get(0),
							legendList, dataV, showColor, showType);
					// 创建表格
					TableType tableType = new TableType();
					TableEntity tableEntity = tableType.getTable(Integer.toString(2), appIndiName, dataX, legendList,
							dataV);// 表格
					classInfoList.add(lineEntity);
					classInfoList.add(tableEntity);
				} else {
					// 创建柱状图
					BarType barType = new BarType();// 柱状图
					System.out.println("legendList长度:" + legendList.size());
					BarEntity barEntity = barType.getOption(Integer.toString(1), appIndiName, dataX.get(0), legendList,
							dataV, showColor, showType);
					classInfoList.add(barEntity);
					// 创建表格
					TableType tableType = new TableType();
					TableEntity tableEntity = tableType.getTable(Integer.toString(2), appIndiName, dataX, legendList,
							dataV);// 表格
					System.out.println("yes or no:" + tableEntity.getTableBody());
					classInfoList.add(tableEntity);
				}
			} else {
				// 湖统数据
				String newStartTime = startTime.substring(0, 4) + startTime.substring(5, 7) + freqCode;
				String newEndTime = endTime.substring(0, 4) + endTime.substring(5, 7) + freqCode;
				switch (freqCode) {
				case "月度":
					freqCode = "MM";
					break;
				case "季度":
					freqCode = "SS";
					break;
				default:
					freqCode = "YY";
					break;
				}
				// 搜索条件
				Map defaultMap = new HashMap();
				defaultMap.put("appIndiName", appIndiName);
				defaultMap.put("freqCode", freqCode);
				defaultMap.put("startTime", newStartTime);
				defaultMap.put("endTime", newEndTime);
				defaultMap.put("source", source);
//				defaultMap.put("area_name", areaName);
				defaultMap.put("lj", lj);
				List<TPIndiValue> defaultIndiValueList = indiDetailService.getIndiValue(defaultMap);
				Collections.sort(defaultIndiValueList, new Comparator<TPIndiValue>() {
					@Override
					public int compare(TPIndiValue r1, TPIndiValue r2) {
						int nameIndex = r1.getDate_code().compareTo(r2.getDate_code());
						int ageIndex = 0;
						int startIndex = 0;
						return nameIndex + ageIndex + startIndex;
					}
				});
				System.out.println("defaultIndiValueList:" + defaultIndiValueList);
				System.out.println("defaultIndiValueList长度：" + defaultIndiValueList.size());
				// 对查询出的指标值根据不同的时点分类
				TreeMap<String, ArrayList<TPIndiValue>> tm = new TreeMap<String, ArrayList<TPIndiValue>>();
				for (int i = 0; i < defaultIndiValueList.size(); i++) {
					TPIndiValue tv = defaultIndiValueList.get(i);
					if (tm.containsKey(tv.getTime_point())) {
						ArrayList<TPIndiValue> l11 = (ArrayList<TPIndiValue>) tm.get(tv.getTime_point());
						l11.add(tv);
					} else {
						ArrayList tem = new ArrayList();
						tem.add(tv);
						tm.put(tv.getTime_point(), tem);
					}
				}
				Set<String> set = tm.keySet();
				List<String> legendData = new ArrayList();
				legendData.addAll(set);

				// 创建数据列表

				for (int i = 0; i < legendData.size(); i++) {
					List<List<String>> dataX = new ArrayList();
					List<List<String>> dataV = new ArrayList();
					List<TPIndiValue> tempList = (List<TPIndiValue>) tm.get(legendData.get(i));
					List<String> dataList = new ArrayList();
					List<String> dateList = new ArrayList();
					List<String> legendList = new ArrayList();
					if (tempList.size() > 8) {
						for (int j = 0; j < 8; j++) {// tempList.size()

							dateList.add(tempList.get(j).getDate_code().substring(0, 4) + "/"
									+ tempList.get(j).getDate_code().substring(4, 6));
							dataList.add(tempList.get(j).getIndi_value());
						}
					} else {
						for (int j = 0; j < tempList.size(); j++) {// tempList.size()

							dateList.add(tempList.get(j).getDate_code().substring(0, 4) + "/"
									+ tempList.get(j).getDate_code().substring(4, 6));
							dataList.add(tempList.get(j).getIndi_value());
						}
					}
					String showName;
					if (legendData.get(i).equals("104")) {
						showName = "本期";
					} else if (legendData.get(i).equals("203")) {
						showName = "自年初累计";
					} else {
						showName = "其他";
					}
					legendList.add(appIndiName + "-" + showName);
					dataV.add(dataList);
					dataX.add(dateList);
					List<String> showColor = new ArrayList<String>();
					List<String> showType = new ArrayList<String>();
					System.out.println("legendList" + legendList);
					System.out.println("dataList" + dataList);
					System.out.println("dateList" + dateList);
					if (legendData.get(i).equals("104") || legendData.get(i).equals("203")) {
						// 创建柱状图
						BarType barType = new BarType();// 柱状图
						BarEntity barEntity = barType.getOption(Integer.toString(i + 1), appIndiName + "-" + showName,
								dataX.get(0), legendList, dataV, showColor, showType);
						System.out.println("barEntity" + barEntity.getEchartOption().getSeries());
						// 创建表格

						TableType tableType = new TableType();
						TableEntity tableEntity = tableType.getTable(Integer.toString(i + 2),
								appIndiName + "-" + showName, dataX, legendList, dataV);// 表格
						classInfoList.add(barEntity);
						classInfoList.add(tableEntity);
					} else {
						// TODO 其他情况
						// 创建折线图
						LineType lineType = new LineType();
						LineEntity lineEntity = lineType.getOption(Integer.toString(i + 1),
								appIndiName + "-" + showName, dataX.get(0), legendList, dataV, showColor, showType);

						// 创建表格
						TableType tableType = new TableType();
						TableEntity tableEntity = tableType.getTable(Integer.toString(i + 2),
								appIndiName + "-" + showName, dataX, legendList, dataV);// 表格
						classInfoList.add(lineEntity);
						classInfoList.add(tableEntity);
					}
				}
			}
		}
		Map finData = new HashMap();
		finData.put("classInfo", classInfoList);
		Map finalMap = new HashMap();
		finalMap.put("errCode", "0");
		finalMap.put("errMsg", "success");
		finalMap.put("data", finData);
		String param = JSON.toJSONString(finalMap);
		return param;
	}

	// 用户收藏
//	@RequestMapping(value = "indiCollect", produces = "application/json; charset=utf-8")
//	@ResponseBody
//	public String indiCollect() {
//
//		int uid = 1;// TODO 从session中获得
//		String index_id = "0100001";
//		String source = null;
//		String type = "指标数据";
//		Date date = new Date();
//		String indi_source = source;
//		String index_name = source;
//		Collect collect = new Collect();
//		collect.setIndex_id(index_id);
//		collect.setCreate_time(date);
//		collect.setIndi_source(indi_source);
//		collect.setType(type);
//		collect.setUid(uid);
//		collect.setIndex_name(index_name);
//		indiDetailService.indiCollect(collect);
//
//		Map map = new HashMap();
//		map.put("收藏指标：", "收藏指标成功！");
//		String param = JSON.toJSONString(map);
//		return param;
//	}

	// map按照值排序函数
	public Map<String, Float> sortMapByValue(Map<String, Float> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<String, Float> sortedMap = new LinkedHashMap<String, Float>();
		List<Map.Entry<String, Float>> entryList = new ArrayList<Map.Entry<String, Float>>(oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparator());

		Iterator<Map.Entry<String, Float>> iter = entryList.iterator();
		Map.Entry<String, Float> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}

	public String apiReturn(String errCode, String errMsg, Map<String, Object> data) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("errCode", errCode);
		responseMap.put("errMsg", errMsg);
		responseMap.put("data", data);
		return JSON.toJSONString(responseMap, SerializerFeature.DisableCircularReferenceDetect);
	}

}