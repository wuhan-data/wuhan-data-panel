package com.wuhan_data.app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.app.service.IndiDetailService;
import com.wuhan_data.app.service.IndiSearchService;
import com.wuhan_data.app.showType.BarStackLineType;
import com.wuhan_data.app.showType.BarStoreType;
import com.wuhan_data.app.showType.BarType;
import com.wuhan_data.app.showType.DoubleXaxisLineType;
import com.wuhan_data.app.showType.LineAndBarType;
import com.wuhan_data.app.showType.PointType;
import com.wuhan_data.app.showType.pojo.BarEntity;
import com.wuhan_data.app.showType.pojo.BarStackLineEntity;
import com.wuhan_data.app.showType.pojo.BarStoreEntity;
import com.wuhan_data.app.showType.pojo.DoubleXaxisLineEntity;
import com.wuhan_data.app.showType.pojo.LineAndBarEntity;
import com.wuhan_data.app.showType.pojo.PointEntity;
import com.wuhan_data.pojo.Collect;
import com.wuhan_data.pojo.HistorySearch;
import com.wuhan_data.pojo.TPIndiValue;

@Controller
@RequestMapping("")
public class IndiDetailAppController {
	@Autowired
	IndiDetailService indiDetailService;
	@Autowired
	IndiSearchService indiSearchService;	
	
	@RequestMapping(value="indiDetail-",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String indiDetail(String appIndiName,String source) {
		
		
		//获得指标的年季度范围
		appIndiName="地区生产总值";//应从app获得
		source="湖北省统计局-";//指标来源
		//记录历史搜索
		int uid=1;//从session中获得
		Date date=new Date();
		HistorySearch historySearch= new HistorySearch();
		historySearch.setCreate_time(date);
		historySearch.setUid(uid);
		historySearch.setKeyword(appIndiName);
		indiSearchService.addSearchHistory(historySearch);
		
		Map fcMap=new HashMap();
		fcMap.put("appIndiName", appIndiName);
		fcMap.put("source", source);
		List<String> freqCodeList=indiDetailService.getFreqCodeByIndiName(fcMap);
		Map map=new HashMap();
		map.put("freqCodeList", freqCodeList);
		List<Map<String,String>> timeRangeList= new ArrayList();
		for(int i=0;i<freqCodeList.size();i++)
		{
			Map ParaMap=new HashMap();
			ParaMap.put("freqCode", freqCodeList.get(i));
			ParaMap.put("appIndiName", appIndiName);
			ParaMap.put("source", source);
			List<String> indiDateList = indiDetailService.indiDateByFreqName(ParaMap);
			String startTime=indiDateList.get(0);
			String endTime=indiDateList.get(indiDateList.size()-1);
			Map temMap=new HashMap();
			temMap.put("IndiName", appIndiName);
			temMap.put("freqCode", freqCodeList.get(i));
			temMap.put("startTime", startTime);
			temMap.put("endTime", endTime);
			timeRangeList.add(temMap);
		}
		
		Map defaultMap = new HashMap();
		defaultMap.put("appIndiName", appIndiName);
		defaultMap.put("freqCode", freqCodeList.get(0));
		Map ParameterMap=new HashMap();
		ParameterMap.put("freqCode", freqCodeList.get(0));
		ParameterMap.put("appIndiName", appIndiName);
		ParameterMap.put("source", source);
		List<String> indiDateList1 = indiDetailService.indiDateByFreqName(ParameterMap);	
		String endTime=indiDateList1.get(0);
		String startTime=indiDateList1.get(indiDateList1.size()-1);
		defaultMap.put("startTime", startTime);
		defaultMap.put("endTime", endTime);
		defaultMap.put("source", source);
		
		List<TPIndiValue> defaultIndiValueList=indiDetailService.getIndiValue(defaultMap);
		System.out.println("defaultIndiValueList长度："+defaultIndiValueList.size());
		//对查询出的指标值根据不同的时点分类
		TreeMap tm=new TreeMap();
		for(int i=0;i<defaultIndiValueList.size();i++)
		{
			TPIndiValue tv=defaultIndiValueList.get(i);
			if(tm.containsKey(tv.getTime_point()))
			{
				ArrayList l11=(ArrayList) tm.get(tv.getTime_point());
				l11.add(tv.getIndi_value());
			}
			else
			{
				ArrayList tem=new ArrayList();
				tem.add(tv.getIndi_value());
				tm.put(tv.getTime_point(), tem);
			}
		}
		
		
		
		map.put("timeRangeList", timeRangeList);
		map.put("defaultMap", defaultMap);
		map.put("defaultIndiValueList", tm);
		
		String  param= JSON.toJSONString(map);
		return param;
	}
	
	@RequestMapping(value="indiDetail1-",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String indiDetail1(String appIndiName,String startTime,String endTime,String freqCode,String source) {
		
		appIndiName="地区生产总值";//应从app获得
		startTime="199804SS";
		endTime="200000SS";
		freqCode="SS";
		//source="统计局数据库-国研网";
		Map<String,Object> map = new HashMap<String, Object>(); 
		map.put("appIndiName", appIndiName);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("freqCode", freqCode);
		map.put("source", source);
		List<TPIndiValue> indiValueList=indiDetailService.getIndiValue(map);
		//对查询出的指标值根据不同的时点分类
		TreeMap tm=new TreeMap();
		for(int i=0;i<indiValueList.size();i++)
		{
			TPIndiValue tv=indiValueList.get(i);
			if(tm.containsKey(tv.getTime_point()))
			{
				ArrayList l11=(ArrayList) tm.get(tv.getTime_point());
				l11.add(tv.getIndi_value());
			}
			else
			{
				ArrayList tem=new ArrayList();
				tem.add(tv.getIndi_value());
				tm.put(tv.getTime_point(), tem);
			}
		}
		
		Map indiValueMap=new HashMap();
		indiValueMap.put("indiValue", tm);
		String  param= JSON.toJSONString(indiValueMap);
		return param;
	}
	
	
	
	//用户收藏
	@RequestMapping(value="indiCollect-",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String indiCollect(String keyWord) {	
		
		int uid=1;//从session中获得
		String type="指标数据";
		Date date=new Date();
		
		String index_id="0100001";
		String indi_source="湖北省统计局-";
		Collect collect=new Collect();
		collect.setIndex_id(index_id);
		collect.setCreate_time(date);
		collect.setIndi_source(indi_source);
		collect.setType(type);
		collect.setUid(uid);
		indiDetailService.indiCollect(collect);
	
		Map map=new HashMap();
		map.put("收藏指标：", "收藏指标成功！");
		String  param= JSON.toJSONString(map);
		return param;
	}
	
	
	
	@RequestMapping(value="testM",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String test(String keyWord) {	
		String id="5";
		String title="柱状堆叠图";
		
		List<List> dataX = new ArrayList();
		List<String> datax=Arrays.asList("周一", "周二", "周三", "周四", "周五");
		System.out.println("datax:"+datax);
		dataX.add(datax);
		
//		List showType=Arrays.asList("line","bar");
		
		List<String> legendData=Arrays.asList("邮件营销", "联盟广告", "视频广告");
		
		List<List> data =new ArrayList();
		data.add(Arrays.asList(120, 132, 101, 134, 90, 230, 210));
		data.add(Arrays.asList(220, 182, 191, 234, 290, 330, 310));
		data.add(Arrays.asList(150, 232, 201, 154, 190, 330, 410));
//		List<String> show=Arrays.asList("bar", "bar", "bar", "line");
		
		BarStoreType barStoreType= new BarStoreType();
		
		BarStoreEntity barStoreEntity = barStoreType.getOption(id, title, dataX, legendData, data);
	
		Map map=new HashMap();
		map.put("barStoreEntity",barStoreEntity);
		String  param= JSON.toJSONString(map);
		return param;
	}
	
	@RequestMapping(value="testPoint",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String testPoint() {	
		String id="7";
		String title="散点图";
		List<String> nameData=Arrays.asList("谷歌","必应");
		
		List<List> data =new ArrayList();
		
		List point1= new ArrayList();point1.add(14.0);point1.add(9.96);
		List point2= new ArrayList();point2.add(14.0);point2.add(9.96);
		List point3= new ArrayList();point3.add(14.0);point3.add(9.96);
		List<List> data1 = new ArrayList();
		data1.add(point1);data1.add(point2);data1.add(point1);
		List<List> data2 = new ArrayList();
		data2.add(point1);data2.add(point2);data2.add(point1);
		
		data.add(data1);
		data.add(data2);
		
		System.out.println(data1);
		System.out.println(data2);
//		List<String> show=Arrays.asList("bar", "bar", "bar", "line");
		
		PointType pointType= new PointType();
		
		PointEntity pointEntity = pointType.getOption(id, title,nameData,data);
	
		Map map=new HashMap();
		map.put("pointEntity",pointEntity);
		String  param= JSON.toJSONString(map);
		return param;
	}
	
	
	@RequestMapping(value="testTable",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String testTable() {	
		String id="7";
		String title="表格示例";
		List<String> nameData=Arrays.asList("谷歌","必应");
		
		List<List> data =new ArrayList();
		
		List point1= new ArrayList();point1.add(14.0);point1.add(9.96);
		List point2= new ArrayList();point2.add(14.0);point2.add(9.96);
		List point3= new ArrayList();point3.add(14.0);point3.add(9.96);
		List<List> data1 = new ArrayList();
		data1.add(point1);data1.add(point2);data1.add(point1);
		List<List> data2 = new ArrayList();
		data2.add(point1);data2.add(point2);data2.add(point1);
		
		data.add(data1);
		data.add(data2);
		
		System.out.println(data1);
		System.out.println(data2);
//		List<String> show=Arrays.asList("bar", "bar", "bar", "line");
		
		PointType pointType= new PointType();
		
		PointEntity pointEntity = pointType.getOption(id, title,nameData,data);
	
		Map map=new HashMap();
		map.put("pointEntity",pointEntity);
		String  param= JSON.toJSONString(map);
		return param;
	}
	

}
