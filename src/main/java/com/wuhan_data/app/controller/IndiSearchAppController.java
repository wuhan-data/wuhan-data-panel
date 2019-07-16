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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.app.service.IndiDetailService;
import com.wuhan_data.app.service.IndiSearchService;
import com.wuhan_data.app.showType.BarType;
import com.wuhan_data.app.showType.LineType;
import com.wuhan_data.app.showType.pojo.BarEntity;
import com.wuhan_data.app.showType.pojo.LineEntity;
import com.wuhan_data.app.showType.pojo.TableEntity;
import com.wuhan_data.pojo.Collect;
import com.wuhan_data.pojo.HistorySearch;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.TPIndiValue;
import com.wuhan_data.tools.MapValueComparator;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("")
public class IndiSearchAppController {

	@Autowired
	IndiSearchService indiSearchService;
	@Autowired
	IndiDetailService indiDetailService;

	String source = "统计局数据库-国研网";// 搜索来源

	@RequestMapping(value = "searchTrend", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String searchSource() {

		// //获得搜索的所有来源（如湖北统计局，国家统计局等）
		// List<String> indisourceList=indiSearchService.searchSource();

		// 获得历史搜素
		int uid = 1;// 应从session中获取
		List<HistorySearch> historySearchList = indiSearchService.getHistorySearch(uid);

		Map map = new HashMap();
		// map.put("indisourceList", indisourceList);
		// map.put("historySearchList", historySearchList);

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
			if (mapNum2.containsKey(mapNumStr))
			{
				judgeList.add(mapNum.get(mapNumStr) - mapNum2.get(mapNumStr));
			}
				
			else
			{
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
			tempMap.put("id", Integer.toString(index));
			tempMap.put("name", me.getKey());
			//获得搜索指标的来源
			Map paraMap = new HashMap();
			paraMap.put("indi_name", me.getKey());
			paraMap.put("nowDate", nowDate);
			calendar.set(calendar.DATE, calendar.get(calendar.DATE) - 8);
			paraMap.put("beforeDate", calendar.getTime());
			String trendSource = indiSearchService.getTrendSource(paraMap);
			tempMap.put("source", trendSource);
			
			float t = (float) finaMap.get(me.getKey());
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			String tS,type;
			if (judgeList.get(index-1) > 0) {
				type = "up";// 代表上升
				tS = decimalFormat.format(t*100);
			} else {
				type = "down";// 代表下降f.toString();finaMap.get(me.getKey()).toString()
				tS = decimalFormat.format(t*100);
			}
				
			tempMap.put("arrow", type);
			tempMap.put("rate", tS + "%");
			paramList.add(tempMap);
			index++;
		}

		Map dataMap = new HashMap();
		if(paramList.size()>5)
		{
			dataMap.put("trend", paramList.subList(0, 5));
		}
		else
		{
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
	public String searchIndi(@RequestBody String json) {

		// 获得搜索的所有来源（如湖北统计局，国家统计局等）@RequestBody String json
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		System.out.println("json" + json);

		String keyWord = mapget.get("keyWord").toString();
		source = mapget.get("source").toString();
//		String keyWord="社会";
//		source="湖统";//指标来源
		switch(source)
		{
		case "大数据": source = "湖北指数-统一模板数据入库工具下湖北指数入库";break;
		case "国统": source = "国家统计局-";break;
		case "湖统": source = "湖北省统计局-";break;
		default: source = "全部";break;
		}


		List<IndexManage> searchIndiList;
		if(source.equals("全部"))
		{
			searchIndiList = indiSearchService.searchIndiAll(keyWord);
		}
		
		else
		{
			Map paraMap = new HashMap();
			paraMap.put("keyWord", keyWord);
			paraMap.put("source", source);
			searchIndiList = indiSearchService.searchIndi(paraMap);
		}
		
		List resultList = new ArrayList();
		for (int i = 0; i < searchIndiList.size(); i++) {
			Map teMap = new HashMap();
			teMap.put("id", Integer.toString(i + 1));
			teMap.put("name", searchIndiList.get(i).getIndi_name());
			teMap.put("source", searchIndiList.get(i).getSjly());
			resultList.add(teMap);
			System.out.println(searchIndiList.get(i));
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

	@RequestMapping(value = "indiDetail", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String indiDetail(@RequestBody String json) {

		// 获得指标的年季度范围
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		System.out.println("json" + json);

		String appIndiName = mapget.get("appIndiName").toString();
		source = mapget.get("source").toString();
		
//		appIndiName = "地区生产总值";// 应从app获得
//		source="湖统";//指标来源
		switch(source)
		{
		case "大数据": source = "湖北指数-统一模板数据入库工具下湖北指数入库";break;
		case "国统": source = "国家统计局-";break;
		default: source = "湖北省统计局-";break;
		}
		
		// 记录历史搜索
		int uid = 1;// 从session中获得
		Date date = new Date();
		HistorySearch historySearch = new HistorySearch();
		historySearch.setCreate_time(date);
		historySearch.setUid(uid);
		historySearch.setKeyword(appIndiName);
		historySearch.setSource(source);
		indiSearchService.addSearchHistory(historySearch);

		Map fcMap = new HashMap();
		fcMap.put("appIndiName", appIndiName);
		fcMap.put("source", source);
		fcMap.put("area_name", "全国");
		List<String> freqCodeList = indiDetailService.getFreqCodeByIndiName(fcMap);
		Map map = new HashMap();
		// map.put("freqCodeList", freqCodeList);
		List<Map<String, String>> timeRangeList = new ArrayList();

		// timeCondition列表
		List<Map> timeCondition = new ArrayList();
		for (int i = 0; i < freqCodeList.size(); i++) {
			Map timeMap = new HashMap();
			timeMap.put("freqName", freqCodeList.get(i));
			// 查询日期范围
			Map ParaMap = new HashMap();// 获取日期参数范围的参数map
			ParaMap.put("freqCode", freqCodeList.get(i));
			ParaMap.put("appIndiName", appIndiName);
			ParaMap.put("source", source);
			ParaMap.put("area_name", "全国");
			List<String> indiDateList = indiDetailService.indiDateByFreqName(ParaMap);
			Collections.sort(indiDateList);
			System.out.println("timeRange:" + indiDateList);
			timeMap.put("startArray", indiDateList);// 开始时间范围
			timeMap.put("endArray", indiDateList);// 结束时间范围
			timeCondition.add(timeMap);

		}

		// 创建图
		List classInfoList = new ArrayList();

		String id = "1";
		String title = "柱状图";
		// 创建横坐标
		List<List> dataX = new ArrayList();
		Map ParameterMap = new HashMap();
		ParameterMap.put("freqCode", freqCodeList.get(0));
		ParameterMap.put("appIndiName", appIndiName);
		ParameterMap.put("source", source);
		ParameterMap.put("area_name", "全国");
		List<String> indiDateList1 = indiDetailService.indiDateByFreqName(ParameterMap);
		Collections.sort(indiDateList1);
		// dataX.add(indiDateList1);
		// 创建图例列表和数据列表
		List<String> legendData = new ArrayList();
		String startTime1 = indiDateList1.get(0);
		System.out.println("startTime1" + startTime1);
		String endTime1 = indiDateList1.get(indiDateList1.size() - 1);
		System.out.println("endTime1" + endTime1);
		Map defaultMap = new HashMap();
		defaultMap.put("appIndiName", appIndiName);
		defaultMap.put("freqCode", freqCodeList.get(0));
		defaultMap.put("startTime", startTime1);
		defaultMap.put("endTime", endTime1);
		defaultMap.put("source", source);
		defaultMap.put("area_name", "全国");
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
		legendData.addAll(set);
		
		// 创建数据列表
		List<List> data = new ArrayList();
		for (int i = 0; i < legendData.size(); i++) {
			List<TPIndiValue> tempList = (List<TPIndiValue>) tm.get(legendData.get(i));
			List<String> dataList = new ArrayList();
			List<String> dateList = new ArrayList();
			List<String> legendList = new ArrayList();
			if(tempList.size()>8)
			{
				for (int j = 0; j < 8; j++) {//tempList.size()
					
					dateList.add(tempList.get(j).getDate_code());
					dataList.add(tempList.get(j).getIndi_value());
				}
			}
			else{
					for (int j = 0; j < tempList.size(); j++) {//tempList.size()
					
						dateList.add(tempList.get(j).getDate_code());
						dataList.add(tempList.get(j).getIndi_value());
				}
			}
			
			legendList.add(legendData.get(i));
			data.add(dataList);
			dataX.add(dateList);
			if(legendData.get(i).equals("104") || legendData.get(i).equals("203") )
			{
				// 创建柱状图
				BarType barType = new BarType();// 柱状图
				BarEntity barEntity = barType.getOption(id, title, dataX, legendList, data);
				
				// 创建表格
				List<List<String>> tableBodyList = new ArrayList();
				List<String> tableHead = new ArrayList();// 表头
				tableHead.add(" ");
				for (int i1 = 0; i1 < legendList.size(); i1++) {
					tableHead.add(legendList.get(i1));
				}
				tableBodyList.add(tableHead);

				for (int i2 = 0; i2 < dataX.get(0).size(); i2++) {
					List<String> tableData = new ArrayList();// 表中数据
					tableData.add((String) dataX.get(0).get(i2));
					int dataLen = data.size();
					for (int j1 = 0; j1 < dataLen; j1++) {
						if (i2 >= data.get(j1).size())
							tableData.add(" ");
						else
							tableData.add((String) data.get(j1).get(i2));
					}
					tableBodyList.add(tableData);
				}

				TableEntity tableEntity = new TableEntity(Integer.toString(i+1), "表格示例", tableBodyList);// 表格
				classInfoList.add(barEntity);
				classInfoList.add(tableEntity);
			}
			else{
				//TODO 其他情况
				// 创建折线图
				LineType lineType = new LineType();
				LineEntity lineEntity = lineType.getOption("2", "折线图", dataX, legendList, data);
	
				// 创建表格
				List<List<String>> tableBodyList = new ArrayList();
				List<String> tableHead = new ArrayList();// 表头
				tableHead.add(" ");
				for (int i2 = 0; i2 < legendList.size(); i2++) {
					tableHead.add(legendList.get(i2));
				}
				tableBodyList.add(tableHead);
	
				for (int i2 = 0; i2 < dataX.get(0).size(); i2++) {
					List<String> tableData = new ArrayList();// 表中数据
					tableData.add((String) dataX.get(0).get(i2));
					int dataLen = data.size();
					for (int j = 0; j < dataLen; j++) {
						if (i2 >= data.get(j).size())
							tableData.add(" ");
						else
							tableData.add((String) data.get(j).get(i2));
					}
					tableBodyList.add(tableData);
				}
	
				TableEntity tableEntity = new TableEntity(id, "表格示例", tableBodyList);// 表格
				classInfoList.add(lineEntity);
				classInfoList.add(tableEntity);
				
			}
		}

		Map finData = new HashMap();
		finData.put("timeCondition", timeCondition);
		finData.put("classInfo", classInfoList);

		Map finalMap = new HashMap();
		finalMap.put("errCode", "0");
		finalMap.put("errMsg", "success");
		finalMap.put("data", finData);

		String param = JSON.toJSONString(finalMap);
		return param;
	}

	@RequestMapping(value = "indiDetail1", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String indiDetail1(@RequestBody String json) {

		// 获得指标的年季度范围
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		System.out.println("json" + json);

		String appIndiName = mapget.get("appIndiName").toString();
		source = mapget.get("source").toString();
//		source="湖统";//指标来源
		switch(source)
		{
		case "大数据": source = "湖北指数-统一模板数据入库工具下湖北指数入库";break;
		case "国统": source = "国家统计局-";break;
		default: source = "湖北省统计局-";break;
		}
		String startTime=mapget.get("startTime").toString();
		String endTime=mapget.get("endTime").toString();
		String freqCode=mapget.get("freqCode").toString();
//		appIndiName = "地区生产总值";// 应从app获得
//		startTime = "199804SS";
//		endTime = "201800SS";
//		freqCode = "SS";
		Map fcMap = new HashMap();
		fcMap.put("appIndiName", appIndiName);
		fcMap.put("source", source);
		fcMap.put("area_name", "全国");
		List<String> freqCodeList = indiDetailService.getFreqCodeByIndiName(fcMap);
		Map map = new HashMap();
		// map.put("freqCodeList", freqCodeList);
		List<Map<String, String>> timeRangeList = new ArrayList();

		// timeCondition列表
		List<Map> timeCondition = new ArrayList();
		for (int i = 0; i < freqCodeList.size(); i++) {
			Map timeMap = new HashMap();
			timeMap.put("freqName", freqCodeList.get(i));
			// 查询日期范围
			Map ParaMap = new HashMap();// 获取日期参数范围的参数map
			ParaMap.put("freqCode", freqCodeList.get(i));
			ParaMap.put("appIndiName", appIndiName);
			ParaMap.put("source", source);
			ParaMap.put("area_name", "全国");
			List<String> indiDateList = indiDetailService.indiDateByFreqName(ParaMap);
			Collections.sort(indiDateList);
			System.out.println("timeRange:" + indiDateList);
			timeMap.put("startArray", indiDateList);// 开始时间范围
			timeMap.put("endArray", indiDateList);// 结束时间范围
			timeCondition.add(timeMap);
		}

		// 创建图
		List classInfoList = new ArrayList();
		String id = "1";
		String title = "柱状图";
		// 创建横坐标
		List<List> dataX = new ArrayList();

		// 搜索条件
		Map defaultMap = new HashMap();
		defaultMap.put("appIndiName", appIndiName);
		defaultMap.put("freqCode", freqCode);
		defaultMap.put("startTime", startTime);
		defaultMap.put("endTime", endTime);
		defaultMap.put("source", source);
		defaultMap.put("area_name", "全国");
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
		List<List> data = new ArrayList();
		for (int i = 0; i < legendData.size(); i++) {
			List<TPIndiValue> tempList = (List<TPIndiValue>) tm.get(legendData.get(i));
			List<String> dataList = new ArrayList();
			List<String> dateList = new ArrayList();
			List<String> legendList = new ArrayList();
			if (tempList.size() > 8) {
				for (int j = 0; j < 8; j++) {// tempList.size()

					dateList.add(tempList.get(j).getDate_code());
					dataList.add(tempList.get(j).getIndi_value());
				}
			} else {
				for (int j = 0; j < tempList.size(); j++) {// tempList.size()

					dateList.add(tempList.get(j).getDate_code());
					dataList.add(tempList.get(j).getIndi_value());
				}
			}

			legendList.add(legendData.get(i));
			data.add(dataList);
			dataX.add(dateList);
			if (legendData.get(i).equals("104") || legendData.get(i).equals("203")) {
				// 创建柱状图
				BarType barType = new BarType();// 柱状图
				BarEntity barEntity = barType.getOption(id, title, dataX, legendList, data);

				// 创建表格
				List<List<String>> tableBodyList = new ArrayList();
				List<String> tableHead = new ArrayList();// 表头
				tableHead.add(" ");
				for (int i1 = 0; i1 < legendList.size(); i1++) {
					tableHead.add(legendList.get(i1));
				}
				tableBodyList.add(tableHead);

				for (int i2 = 0; i2 < dataX.get(0).size(); i2++) {
					List<String> tableData = new ArrayList();// 表中数据
					tableData.add((String) dataX.get(0).get(i2));
					int dataLen = data.size();
					for (int j1 = 0; j1 < dataLen; j1++) {
						if (i2 >= data.get(j1).size())
							tableData.add(" ");
						else
							tableData.add((String) data.get(j1).get(i2));
					}
					tableBodyList.add(tableData);
				}

				TableEntity tableEntity = new TableEntity(Integer.toString(i + 1), "表格示例", tableBodyList);// 表格
				classInfoList.add(barEntity);
				classInfoList.add(tableEntity);
			} else {
				// TODO 其他情况
				// 创建折线图
				LineType lineType = new LineType();
				LineEntity lineEntity = lineType.getOption("2", "折线图", dataX, legendList, data);

				// 创建表格
				List<List<String>> tableBodyList = new ArrayList();
				List<String> tableHead = new ArrayList();// 表头
				tableHead.add(" ");
				for (int i2 = 0; i2 < legendList.size(); i2++) {
					tableHead.add(legendList.get(i2));
				}
				tableBodyList.add(tableHead);

				for (int i2 = 0; i2 < dataX.get(0).size(); i2++) {
					List<String> tableData = new ArrayList();// 表中数据
					tableData.add((String) dataX.get(0).get(i2));
					int dataLen = data.size();
					for (int j = 0; j < dataLen; j++) {
						if (i2 >= data.get(j).size())
							tableData.add(" ");
						else
							tableData.add((String) data.get(j).get(i2));
					}
					tableBodyList.add(tableData);
				}

				TableEntity tableEntity = new TableEntity(id, "表格示例", tableBodyList);// 表格
				classInfoList.add(lineEntity);
				classInfoList.add(tableEntity);

			}
		}

		Map finData = new HashMap();
		finData.put("timeCondition", timeCondition);
		finData.put("classInfo", classInfoList);

		Map finalMap = new HashMap();
		finalMap.put("errCode", "0");
		finalMap.put("errMsg", "success");
		finalMap.put("data", finData);
		String param = JSON.toJSONString(finalMap);
		return param;
	}

	// 用户收藏
	@RequestMapping(value = "indiCollect", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String indiCollect() {

		int uid = 1;// TODO 从session中获得
		String index_id = "0100001";

		String type = "指标数据";
		Date date = new Date();
		String indi_source = source;
		Collect collect = new Collect();
		collect.setIndex_id(index_id);
		collect.setCreate_time(date);
		collect.setIndi_source(indi_source);
		collect.setType(type);
		collect.setUid(uid);
		indiDetailService.indiCollect(collect);

		Map map = new HashMap();
		map.put("收藏指标：", "收藏指标成功！");
		String param = JSON.toJSONString(map);
		return param;
	}

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

}
