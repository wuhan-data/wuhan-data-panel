package com.wuhan_data.app.controller;

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

import javax.servlet.http.HttpServletRequest;

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
import com.wuhan_data.pojo.TPIndiValue;
import com.wuhan_data.tools.MapValueComparator;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

@Controller
@RequestMapping("")
public class IndiSearchAppController {

	@Autowired
	IndiSearchService indiSearchService;
	@Autowired
	IndiDetailService indiDetailService;

	private String source = "";// 搜索来源

	@RequestMapping(value = "searchsource", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String searchSource() {

//		//获得搜索的所有来源（如湖北统计局，国家统计局等）
//		List<String> indisourceList=indiSearchService.searchSource();

		// 获得历史搜素
		int uid = 1;// 应从session中获取
		List<HistorySearch> historySearchList = indiSearchService.getHistorySearch(uid);
		System.out.println(historySearchList.size());

		Map map = new HashMap();
//		map.put("indisourceList", indisourceList);
//		map.put("historySearchList", historySearchList);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 获取当前时间，设置日期格式
		String nowDate = df.format(new Date());
		List<String> trendKeywordList = indiSearchService.getTrendList(nowDate);
		List<String> trendCreateTimeList = indiSearchService.getTrendList1(nowDate);
		System.out.println("查询完毕！");

		int trendLen = trendKeywordList.size();
		if (trendLen < 1) {
			map.put("errCode", "-1");
			map.put("errMsg", "no history data");
			map.put("data", "");
			String param = JSON.toJSONString(map);
			return param;
		}
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
		Set<String> s2 = mapNum.keySet();
		for (String mapNumStr : s2) {
			if (mapNum2.containsKey(mapNumStr))
				finaMap.put(mapNumStr, mapNum.get(mapNumStr) - mapNum2.get(mapNumStr));
			else
				finaMap.put(mapNumStr, mapNum.get(mapNumStr));
		}
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
			tempMap.put("title", me.getKey());
			float t = (float) finaMap.get(me.getKey());
			BigDecimal bd = new BigDecimal(t);
			t = bd.setScale(2, BigDecimal.ROUND_FLOOR).floatValue();
			new java.text.DecimalFormat("#.00").format(t);
			String type;
			if (t > 0) {
				type = "up";// 代表上升
			} else {
				type = "down";// 代表下降f.toString();finaMap.get(me.getKey()).toString()
				t = -t;
			}

			tempMap.put("arrow", type);
			tempMap.put("rate", String.valueOf(t) + "%");
			paramList.add(tempMap);
			index++;
		}

		Map dataMap = new HashMap();
		dataMap.put("trend", paramList.subList(0, 5));

		map.put("errCode", "0");
		map.put("errMsg", "success");
		map.put("data", dataMap);

		String param = JSON.toJSONString(map);
		return param;
	}

	@RequestMapping(value = "searchIndi", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	public String searchIndi(@RequestBody String json) {
//		BufferedReader br = null;
//        try {
//            br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        String line = null;
//        StringBuilder sb = new StringBuilder();
//        try {
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        System.out.println(sb);
//		System.out.println(request.getParameter("keyword"));
//        
//        String keyWord ="12";
//        String source = "123";
//		request.setCharacterEncoding("UTF-8");
//		String keyWord = request.getParameter("keyword");
//		String source = request.getParameter("source");
//		System.out.println(keyWord + source);

		// 获得搜索的所有来源（如湖北统计局，国家统计局等）@RequestBody String json
//		String keyWord="社会";
//		source="湖北省统计局-";

		 JSONObject jsonObject = JSONObject.fromObject(json);
		 Map<String, Object> mapget = (Map<String, Object>)
		 JSONObject.toBean(jsonObject, Map.class);
		 System.out.println("json" + json);

		 String keyWord = mapget.get("keyWord").toString();
		 source = mapget.get("source").toString();

		Map paraMap = new HashMap();
		paraMap.put("keyWord", keyWord);
		paraMap.put("source", source);
		List<String> searchIndiList = indiSearchService.searchIndi(paraMap);
		List resultList = new ArrayList();
		for (int i = 0; i < searchIndiList.size(); i++) {
			Map teMap = new HashMap();
			teMap.put("id", Integer.toString(i + 1));
			teMap.put("name", searchIndiList.get(i));
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
	public String indiDetail(String appIndiName) {

		// 获得指标的年季度范围
		appIndiName = "地区生产总值";// 应从app获得
		// source="湖北省统计局-";//指标来源
		// 记录历史搜索
		int uid = 1;// 从session中获得
		Date date = new Date();
		HistorySearch historySearch = new HistorySearch();
		historySearch.setCreate_time(date);
		historySearch.setUid(uid);
		historySearch.setKeyword(appIndiName);
		indiSearchService.addSearchHistory(historySearch);

		Map fcMap = new HashMap();
		fcMap.put("appIndiName", appIndiName);
		fcMap.put("source", source);
		List<String> freqCodeList = indiDetailService.getFreqCodeByIndiName(fcMap);
		Map map = new HashMap();
//		map.put("freqCodeList", freqCodeList);
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
		TreeSet<String> setx = new TreeSet();
		for (int i = 0; i < legendData.size(); i++) {
			List<TPIndiValue> tempList = (List<TPIndiValue>) tm.get(legendData.get(i));
			List<String> timePointDataList = new ArrayList();
			for (int j = 0; j < tempList.size(); j++) {
				if (j > 0) {
					if (setx.contains(tempList.get(j).getDate_code())) {
						timePointDataList.add(tempList.get(j).getIndi_value());
					}
				} else {
					setx.add(tempList.get(j).getDate_code());
					timePointDataList.add(tempList.get(j).getIndi_value());
				}
			}
			data.add(timePointDataList);
			// data.add((List) tm.get(legendData.get(i)));
		}
		List<String> dataxList = new ArrayList();// 坐标轴数据列表
		dataxList.addAll(setx);
		Collections.sort(dataxList);
		dataX.add(dataxList);

		Map showMap = new HashMap();
		showMap.put("appIndiName", appIndiName);
		showMap.put("source", source);

		String indiShowType = indiDetailService.getIndiShowType(showMap);// 获得指标展示类型
		if (indiShowType.equals("折线图")) {

			// 创建折线图
			LineType lineType = new LineType();
			LineEntity lineEntity = lineType.getOption("2", "折线图", dataX, legendData, data);

			// 创建表格
			List<List<String>> tableBodyList = new ArrayList();
			List<String> tableHead = new ArrayList();// 表头
			tableHead.add(" ");
			for (int i = 0; i < legendData.size(); i++) {
				tableHead.add(legendData.get(i));
			}
			tableBodyList.add(tableHead);

			for (int i = 0; i < data.get(0).size(); i++) {
				List<String> tableData = new ArrayList();// 表中数据
				tableData.add((String) dataX.get(0).get(i));
				int dataLen = data.size();
				for (int j = 0; j < dataLen; j++) {
					if (i >= data.get(j).size())
						tableData.add(" ");
					else
						tableData.add((String) data.get(j).get(i));
				}
				tableBodyList.add(tableData);
			}

			TableEntity tableEntity = new TableEntity(id, "表格示例", tableBodyList);// 表格
			classInfoList.add(lineEntity);
			classInfoList.add(tableEntity);
		}

		else {
			BarType barType = new BarType();// 柱状图
			BarEntity barEntity = barType.getOption(id, title, dataX, legendData, data);

			// 创建表格
			List<List<String>> tableBodyList = new ArrayList();
			List<String> tableHead = new ArrayList();// 表头
			tableHead.add(" ");
			for (int i = 0; i < legendData.size(); i++) {
				tableHead.add(legendData.get(i));
			}
			tableBodyList.add(tableHead);

			for (int i = 0; i < data.get(0).size(); i++) {
				List<String> tableData = new ArrayList();// 表中数据
				tableData.add((String) dataX.get(0).get(i));
				int dataLen = data.size();
				for (int j = 0; j < dataLen; j++) {
					if (i >= data.get(j).size())
						tableData.add(" ");
					else
						tableData.add((String) data.get(j).get(i));
				}
				tableBodyList.add(tableData);
			}

			TableEntity tableEntity = new TableEntity(id, "表格示例", tableBodyList);// 表格
			classInfoList.add(barEntity);
			classInfoList.add(tableEntity);
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
	public String indiDetail1(String appIndiName, String startTime, String endTime, String freqCode) {

		// 获得指标的年季度范围
		appIndiName = "地区生产总值";// 应从app获得
		startTime = "199804SS";
		endTime = "200000SS";
		freqCode = "SS";
		Map fcMap = new HashMap();
		fcMap.put("appIndiName", appIndiName);
		fcMap.put("source", source);
		List<String> freqCodeList = indiDetailService.getFreqCodeByIndiName(fcMap);
		Map map = new HashMap();
//		map.put("freqCodeList", freqCodeList);
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
		TreeSet<String> setx = new TreeSet();
		for (int i = 0; i < legendData.size(); i++) {
			List<TPIndiValue> tempList = (List<TPIndiValue>) tm.get(legendData.get(i));
			List<String> timePointDataList = new ArrayList();
			for (int j = 0; j < tempList.size(); j++) {
				if (j > 0) {
					if (setx.contains(tempList.get(j).getDate_code())) {
						timePointDataList.add(tempList.get(j).getIndi_value());
					}
				} else {
					setx.add(tempList.get(j).getDate_code());
					timePointDataList.add(tempList.get(j).getIndi_value());
				}

			}
			data.add(timePointDataList);
			// data.add((List) tm.get(legendData.get(i)));
		}
		List<String> dataxList = new ArrayList();// 坐标轴数据列表
		dataxList.addAll(setx);
		Collections.sort(dataxList);
		dataX.add(dataxList);

		Map showMap = new HashMap();
		showMap.put("appIndiName", appIndiName);
		showMap.put("source", source);

		String indiShowType = indiDetailService.getIndiShowType(showMap);// 获得指标展示类型
		if (indiShowType.equals("折线图")) {

			// 创建折线图
			LineType lineType = new LineType();
			LineEntity lineEntity = lineType.getOption("2", "折线图", dataX, legendData, data);

			// 创建表格
			List<List<String>> tableBodyList = new ArrayList();
			List<String> tableHead = new ArrayList();// 表头
			tableHead.add(" ");
			for (int i = 0; i < legendData.size(); i++) {
				tableHead.add(legendData.get(i));
			}
			tableBodyList.add(tableHead);

			for (int i = 0; i < data.get(0).size(); i++) {
				List<String> tableData = new ArrayList();// 表中数据
				tableData.add((String) dataX.get(0).get(i));
				int dataLen = data.size();
				for (int j = 0; j < dataLen; j++) {
					if (i >= data.get(j).size())
						tableData.add(" ");
					else
						tableData.add((String) data.get(j).get(i));
				}
				tableBodyList.add(tableData);
			}

			TableEntity tableEntity = new TableEntity(id, "表格示例", tableBodyList);// 表格
			classInfoList.add(lineEntity);
			classInfoList.add(tableEntity);
		}

		else {
			BarType barType = new BarType();// 柱状图
			BarEntity barEntity = barType.getOption(id, title, dataX, legendData, data);

			// 创建表格
			List<List<String>> tableBodyList = new ArrayList();
			List<String> tableHead = new ArrayList();// 表头
			tableHead.add(" ");
			for (int i = 0; i < legendData.size(); i++) {
				tableHead.add(legendData.get(i));
			}
			tableBodyList.add(tableHead);

			for (int i = 0; i < data.get(0).size(); i++) {
				List<String> tableData = new ArrayList();// 表中数据
				tableData.add((String) dataX.get(0).get(i));
				int dataLen = data.size();
				for (int j = 0; j < dataLen; j++) {
					if (i >= data.get(j).size())
						tableData.add(" ");
					else
						tableData.add((String) data.get(j).get(i));
				}
				tableBodyList.add(tableData);
			}

			TableEntity tableEntity = new TableEntity(id, "表格示例", tableBodyList);// 表格
			classInfoList.add(barEntity);
			classInfoList.add(tableEntity);
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

//		//获得指标的年季度范围
//		appIndiName="地区生产总值";//应从app获得
//		startTime="199804SS";
//		endTime="200000SS";
//		freqCode="SS";
//		//source="统计局数据库-国研网";
//		Map<String,Object> map = new HashMap<String, Object>(); 
//		map.put("appIndiName", appIndiName);
//		map.put("startTime", startTime);
//		map.put("endTime", endTime);
//		map.put("freqCode", freqCode);
//		map.put("source", source);
//		List<TPIndiValue> indiValueList=indiDetailService.getIndiValue(map);
//		//对查询出的指标值根据不同的时点分类
//		TreeMap tm=new TreeMap();
//		for(int i=0;i<indiValueList.size();i++)
//		{
//			TPIndiValue tv=indiValueList.get(i);
//			if(tm.containsKey(tv.getTime_point()))
//			{
//				ArrayList l11=(ArrayList) tm.get(tv.getTime_point());
//				l11.add(tv.getIndi_value());
//			}
//			else
//			{
//				ArrayList tem=new ArrayList();
//				tem.add(tv.getIndi_value());
//				tm.put(tv.getTime_point(), tem);
//			}
//		}
//		
//		Map indiValueMap=new HashMap();
//		indiValueMap.put("indiValue", tm);
//		String  param= JSON.toJSONString(indiValueMap);
//		return param;
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
