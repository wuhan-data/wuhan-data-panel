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
import com.wuhan_data.app.showType.TableType;
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
			String indexCode = indiDetailService.getIndiCode((String) me.getKey());
			tempMap.put("indexId", indexCode);
			tempMap.put("id", Integer.toString(index));
			tempMap.put("name", me.getKey());
			//获得搜索指标的来源
			Map paraMap = new HashMap();
			paraMap.put("indi_name", me.getKey());
			paraMap.put("nowDate", nowDate);
			calendar.set(calendar.DATE, calendar.get(calendar.DATE) - 8);
			paraMap.put("beforeDate", calendar.getTime());
			String trendSource = indiSearchService.getTrendSource(paraMap);
			switch(trendSource)
			{
			case "湖北指数-统一模板数据入库工具下湖北指数入库": trendSource = "大数据";break;
			case "国家统计局-": trendSource = "国统";break;
			case "湖北省统计局-": trendSource = "湖统";break;
			default: trendSource = "全部";break;
			}
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

		String keyWord = mapget.get("keyword").toString();
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
			String indexCode = indiDetailService.getIndiCode(searchIndiList.get(i).getIndi_name());
			teMap.put("id", indexCode);
			teMap.put("name", searchIndiList.get(i).getIndi_name());
			switch(searchIndiList.get(i).getSjly())
			{
			case "湖北指数-统一模板数据入库工具下湖北指数入库": teMap.put("source", "大数据");break;
			case "国家统计局-": teMap.put("source", "国统");break;
			case "湖北省统计局-": teMap.put("source", "湖统");break;
			default: teMap.put("source", "其他");break;
			}
		
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

	@RequestMapping(value = "searchDetail", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String indiDetail(@RequestBody String json) {

		// 获得指标的年季度范围@RequestBody String json
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		System.out.println("json" + json);
		String indexCode = mapget.get("indexId").toString();
		source = mapget.get("source").toString();
		
//		String indexCode = "2200309";
//		source="湖统";//指标来源
		
		String appIndiName = indiDetailService.getIndexName(indexCode);
		String area_name = null;
		switch(source)
		{
			case "大数据": source = "湖北指数-统一模板数据入库工具下湖北指数入库";area_name="湖北省";break;
			case "国统": source = "国家统计局-";area_name="全国";break;
			case "湖统": source = "湖北省统计局-";area_name="湖北省";break;
		}
		
		String indiCode = indiDetailService.getIndiCode(appIndiName);
		Map baseInfoMap = new HashMap();
		baseInfoMap.put("indexId", indiCode);
		baseInfoMap.put("indexName", appIndiName);
		baseInfoMap.put("isFavorite", false);
		
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
		fcMap.put("area_name", area_name);
		List<String> freqCodeList = indiDetailService.getFreqCodeByIndiName(fcMap);
		Map map = new HashMap();
		// map.put("freqCodeList", freqCodeList);
		List<Map<String, String>> timeRangeList = new ArrayList();

		// timeCondition列表
		List<Map> timeCondition = new ArrayList();
		for (int i = 0; i < freqCodeList.size(); i++) {
			Map timeMap = new HashMap();
			switch(freqCodeList.get(i))
			{
			case "MM": timeMap.put("freqName", "月度");break;
			case "SS": timeMap.put("freqName", "季度");break;
			default : timeMap.put("freqName", "年度");break;
			}
			
			// 查询日期范围
			Map ParaMap = new HashMap();// 获取日期参数范围的参数map
			ParaMap.put("freqCode", freqCodeList.get(i));
			ParaMap.put("appIndiName", appIndiName);
			ParaMap.put("source", source);
			ParaMap.put("area_name", area_name);
			List<String> indiDateList = indiDetailService.indiDateByFreqName(ParaMap);
			Collections.sort(indiDateList);
			System.out.println("timeRange:" + indiDateList);
			
			List<String> newindiDateList = new ArrayList<String>();
			for(int k=indiDateList.size()-1;k>=0;k--)
			{
				newindiDateList.add(indiDateList.get(k).substring(0, 4)+"/"+indiDateList.get(k).substring(4, 6));
			}
			timeMap.put("startArray", newindiDateList);// 开始时间范围
			timeMap.put("endArray", newindiDateList);// 结束时间范围
			if(i==0)
			{
				List currentList = new ArrayList();
				if(indiDateList.size()>=8)
				{
					currentList.add(indiDateList.size()-8);
				}
				else
					currentList.add(0);
				currentList.add(indiDateList.size()-1);
				timeMap.put("current", currentList);
			}
			timeCondition.add(timeMap);

		}

		// 创建图
		List classInfoList = new ArrayList();

		String id = "1";
		String title = "柱状图";
		// 创建横坐标
		
		Map ParameterMap = new HashMap();
		if(freqCodeList.size()==0)
		{
			Map finalMap = new HashMap();
			finalMap.put("errCode", "0");
			finalMap.put("errMsg", "success");
			finalMap.put("data", "没有搜索到相关内容");

			String param = JSON.toJSONString(finalMap);
			return param;
		}
		ParameterMap.put("freqCode", freqCodeList.get(0));
		ParameterMap.put("appIndiName", appIndiName);
		ParameterMap.put("source", source);
		ParameterMap.put("area_name", area_name);
		List<String> indiDateList1 = indiDetailService.indiDateByFreqName(ParameterMap);
		System.out.println("indiDateList1" + indiDateList1);
		Collections.sort(indiDateList1);
		// dataX.add(indiDateList1);
		// 创建图例列表和数据列表
		List<String> legendData1 = new ArrayList();
		String endTime1 = indiDateList1.get(indiDateList1.size()-1);
		System.out.println("endTime1" + endTime1);
		String startTime1;
		if(indiDateList1.size()>=8)
		{
			startTime1 = indiDateList1.get(indiDateList1.size()-8);
		}
		else
		{
			startTime1 = indiDateList1.get(0);
		}
		
		System.out.println("startTime1" + startTime1);
		Map defaultMap = new HashMap();
		defaultMap.put("appIndiName", appIndiName);
		defaultMap.put("freqCode", freqCodeList.get(0));
		defaultMap.put("startTime", startTime1);
		defaultMap.put("endTime", endTime1);
		defaultMap.put("source", source);
		defaultMap.put("area_name", area_name);
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
			List<List> data = new ArrayList();
			List<List> dataX = new ArrayList();
			List<TPIndiValue> tempList = (List<TPIndiValue>) tm.get(legendData1.get(i));
			List<String> dataList = new ArrayList();
			List<String> dateList = new ArrayList();
			List<String> legendList = new ArrayList();
			if(tempList.size()>=8)
			{
				for (int j = 0; j < 8; j++) {//tempList.size()
					
					dateList.add(tempList.get(j).getDate_code().substring(0, 4)+"/"+tempList.get(j).getDate_code().substring(4, 6));
					dataList.add(tempList.get(j).getIndi_value());
				}
			}
			else{
					for (int j = 0; j < tempList.size(); j++) {//tempList.size()
					
						dateList.add(tempList.get(j).getDate_code().substring(0, 4)+"/"+tempList.get(j).getDate_code().substring(4, 6));
						dataList.add(tempList.get(j).getIndi_value());
				}
			}
			String showName;
			if(legendData1.get(i).equals("104"))
			{
				showName="本期";
			}
			else if(legendData1.get(i).equals("203"))
			{
				showName="自年初累计";
			}
			else
			{
				showName="其他";
			}
			legendList.add(appIndiName+"-"+showName);
			data.add(dataList);
			dataX.add(dateList);
			if(legendData1.get(i).equals("104") || legendData1.get(i).equals("203") )
			{
				// 创建柱状图
				BarType barType = new BarType();// 柱状图
				System.out.println("legendList长度:"+legendList.size());
				BarEntity barEntity = barType.getOption(Integer.toString(i+1), appIndiName+"-"+showName, dataX, legendList, data);
				classInfoList.add(barEntity);
				
				// 创建表格
				TableType tableType = new TableType();
				TableEntity tableEntity = tableType.getTable(Integer.toString(i+2), appIndiName+"-"+showName, dataX, legendList, data);// 表格
				System.out.println("yes or no:"+tableEntity.getTableBody());
				classInfoList.add(tableEntity);
			}
			else{
				//TODO 其他情况
				// 创建折线图
				LineType lineType = new LineType();
				LineEntity lineEntity = lineType.getOption(Integer.toString(i+1), appIndiName+"-"+showName, dataX, legendList, data);
	
				// 创建表格
				TableType tableType = new TableType();
				TableEntity tableEntity = tableType.getTable(Integer.toString(i+2), appIndiName+"-"+showName, dataX, legendList, data);// 表格
				classInfoList.add(lineEntity);
				classInfoList.add(tableEntity);
				
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
	public String indiDetail1(@RequestBody String json) {

		// 获得指标的年季度范围@RequestBody String json
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		System.out.println("json" + json);

		String indexCode = mapget.get("indexId").toString();
		source = mapget.get("source").toString();
		
		String appIndiName = indiDetailService.getIndexName(indexCode);
//		source="湖统";//指标来源
//		String appIndiName = "湖北PMI";
		String area_name = null;
		switch(source)
		{
			case "大数据": source = "湖北指数-统一模板数据入库工具下湖北指数入库";area_name="湖北省";break;
			case "国统": source = "国家统计局-";area_name="全国";break;
			case "湖统": source = "湖北省统计局-";area_name="湖北省";break;
		}
		String startTime=mapget.get("startTime").toString();
		String endTime=mapget.get("endTime").toString();
		String freqCode=mapget.get("timeFreq").toString();
//		String startTime = "201408MM";
//		String endTime = "201610MM";
//		String freqCode = "月度";
		switch(freqCode)
		{
		case "月度": freqCode="MM";break;
		case "季度": freqCode="SS";break;
		default : freqCode="YY";break;
		}
		
		String newStartTime = startTime.substring(0,4)+startTime.substring(5,7)+freqCode;
		String newEndTime = endTime.substring(0,4)+endTime.substring(5,7)+freqCode;
//		appIndiName = "地区生产总值";// 应从app获得
//		startTime = "199804SS";
//		endTime = "201800SS";
//		freqCode = "SS";
		Map fcMap = new HashMap();
		fcMap.put("appIndiName", appIndiName);
		fcMap.put("source", source);
		fcMap.put("area_name", area_name);
		List<String> freqCodeList = indiDetailService.getFreqCodeByIndiName(fcMap);
		Map map = new HashMap();
		// map.put("freqCodeList", freqCodeList);
		List<Map<String, String>> timeRangeList = new ArrayList();

		// timeCondition列表
		List<Map> timeCondition = new ArrayList();
		for (int i = 0; i < freqCodeList.size(); i++) {
			Map timeMap = new HashMap();
			switch(freqCodeList.get(i))
			{
			case "MM": timeMap.put("freqName", "月度");break;
			case "SS": timeMap.put("freqName", "季度");break;
			default : timeMap.put("freqName", "年度");break;
			}
			
			// 查询日期范围
			Map ParaMap = new HashMap();// 获取日期参数范围的参数map
			ParaMap.put("freqCode", freqCodeList.get(i));
			ParaMap.put("appIndiName", appIndiName);
			ParaMap.put("source", source);
			ParaMap.put("area_name", area_name);
			List<String> indiDateList = indiDetailService.indiDateByFreqName(ParaMap);
			Collections.sort(indiDateList);
			System.out.println("timeRange:" + indiDateList);
			timeMap.put("startArray", indiDateList);// 开始时间范围
			timeMap.put("endArray", indiDateList);// 结束时间范围
			timeCondition.add(timeMap);
		}

		// 创建图
		List classInfoList = new ArrayList();

		// 创建横坐标
		

		// 搜索条件
		Map defaultMap = new HashMap();
		defaultMap.put("appIndiName", appIndiName);
		defaultMap.put("freqCode", freqCode);
		defaultMap.put("startTime", newStartTime);
		defaultMap.put("endTime", newEndTime);
		defaultMap.put("source", source);
		defaultMap.put("area_name", area_name);
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
			List<List> dataX = new ArrayList();
			List<List> data = new ArrayList();
			List<TPIndiValue> tempList = (List<TPIndiValue>) tm.get(legendData.get(i));
			List<String> dataList = new ArrayList();
			List<String> dateList = new ArrayList();
			List<String> legendList = new ArrayList();
			if (tempList.size() > 8) {
				for (int j = 0; j < 8; j++) {// tempList.size()

					dateList.add(tempList.get(j).getDate_code().substring(0, 4)+"/"+tempList.get(j).getDate_code().substring(4, 6));
					dataList.add(tempList.get(j).getIndi_value());
				}
			} else {
				for (int j = 0; j < tempList.size(); j++) {// tempList.size()

					dateList.add(tempList.get(j).getDate_code().substring(0, 4)+"/"+tempList.get(j).getDate_code().substring(4, 6));
					dataList.add(tempList.get(j).getIndi_value());
				}
			}
			String showName;
			if(legendData.get(i).equals("104"))
			{
				showName="本期";
			}
			else if(legendData.get(i).equals("203"))
			{
				showName="自年初累计";
			}
			else
			{
				showName="其他";
			}
			legendList.add(appIndiName+"-"+showName);
			data.add(dataList);
			dataX.add(dateList);
			System.out.println("legendList"+legendList);
			System.out.println("dataList"+dataList);
			System.out.println("dateList"+dateList);
			if (legendData.get(i).equals("104") || legendData.get(i).equals("203")) {
				// 创建柱状图
				BarType barType = new BarType();// 柱状图
				BarEntity barEntity = barType.getOption(Integer.toString(i+1), appIndiName+"-"+showName, dataX, legendList, data);
				System.out.println("barEntity"+barEntity.getEchartOption().getSeries());
				// 创建表格

				TableType tableType = new TableType();
				TableEntity tableEntity = tableType.getTable(Integer.toString(i+2), appIndiName+"-"+showName, dataX, legendList, data);// 表格
				classInfoList.add(barEntity);
				classInfoList.add(tableEntity);
			} else {
				// TODO 其他情况
				// 创建折线图
				LineType lineType = new LineType();
				LineEntity lineEntity = lineType.getOption(Integer.toString(i+1), appIndiName+"-"+showName, dataX, legendList, data);

				// 创建表格
				TableType tableType = new TableType();
				TableEntity tableEntity = tableType.getTable(Integer.toString(i+2), appIndiName+"-"+showName, dataX, legendList, data);// 表格
				classInfoList.add(lineEntity);
				classInfoList.add(tableEntity);

			}
		
		}

		Map finData = new HashMap();
//		finData.put("timeCondition", timeCondition);
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
