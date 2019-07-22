package com.wuhan_data.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wuhan_data.app.service.IndiDetailService;
import com.wuhan_data.app.service.PlateInfoService;
import com.wuhan_data.app.showType.BarStackLineType;
import com.wuhan_data.app.showType.BarStoreType;
import com.wuhan_data.app.showType.BarType;
import com.wuhan_data.app.showType.DoubleXaxisLineType;
import com.wuhan_data.app.showType.LineAndBarType;
import com.wuhan_data.app.showType.LineType;
import com.wuhan_data.app.showType.PieType;
import com.wuhan_data.app.showType.PointType;
import com.wuhan_data.app.showType.RadarType;
import com.wuhan_data.app.showType.TableType;
import com.wuhan_data.app.showType.pojo.BarEntity;
import com.wuhan_data.app.showType.pojo.BarStackLineEntity;
import com.wuhan_data.app.showType.pojo.BarStoreEntity;
import com.wuhan_data.app.showType.pojo.DoubleXaxisLineEntity;
import com.wuhan_data.app.showType.pojo.LineAndBarEntity;
import com.wuhan_data.app.showType.pojo.LineEntity;
import com.wuhan_data.app.showType.pojo.PieEntity;
import com.wuhan_data.app.showType.pojo.PointEntity;
import com.wuhan_data.app.showType.pojo.RadarEntity;
import com.wuhan_data.app.showType.pojo.TableEntity;
import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.pojo.ColPlate;
import com.wuhan_data.pojo.ColPlateIndi;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.User;
import com.wuhan_data.pojo.indi_TF;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("")
public class PlateController {
	
	@Autowired
	PlateInfoService plateInfoService;
	
	@Autowired
	IndiDetailService indiDetailService;
	
//	@RequestMapping(value="initColumn",produces = "text/plain;charset=utf-8",method = RequestMethod.POST)
//	@ResponseBody
//    public String initColumn(@RequestBody String json){
//		JSONObject jsonObject=JSONObject.fromObject(json);
//		ColPlate cp=(ColPlate)JSONObject.toBean(jsonObject, ColPlate.class);
//		int indexid=cp.getCid();
//		List<ColPlate> cpList = plateInfoService.getPlateInfo(indexid);		
//		List<IndiCorrelative> icList = plateInfoService.getIndiCorrelative(indexid);
//		List list = new ArrayList();
//		list.add(cpList);
//		list.add(icList);	
//        String  param= JSON.toJSONString(list);
//        return param;
//    }

	
	@RequestMapping(value="aa",produces = "text/plain;charset=utf-8")
	@ResponseBody
    public String aa(){		
		//获得指标的年季度范围
//				String appIndiName="地区生产总值";//应从app获得
//				List<String> freqCodeList=indiDetailService.getFreqCodeByIndiName(appIndiName);
//				Map map=new HashMap();
//				map.put("freqCodeList", freqCodeList);		
//				String freqCode="SS";//用户选择获得频度代码和指标名字，获得指标的最小时间和最大时间
//				Map ParaMap=new HashMap();
//				ParaMap.put("freqCode", freqCode);
//				ParaMap.put("appIndiName", appIndiName);
//				List<String> indiDateList = indiDetailService.indiDateByFreqName(ParaMap);		
//				String startTime=indiDateList.get(0).substring(0, 6);
//				String endTime=indiDateList.get(indiDateList.size()-1).substring(0, 6);
//				map.put("startTime", startTime);
//				map.put("endTime", endTime);	
		//获得二级栏目id
		int indexId=2; //从app获得

		Map cmap = new HashMap();
		cmap.put("startTime", "201401SS");
		cmap.put("endTime", "201804SS");
		cmap.put("time_point", 203);
		List<ColPlate> cpList = plateInfoService.getPlateInfo(indexId);//查询板块
		List list1= new ArrayList();
		for(int i=0;i<cpList.size();i++) {
			Map map1 = new HashMap();//板块信息
			map1.put("id", cpList.get(i).getPid());
			map1.put("name", cpList.get(i).getPname());
			map1.put("show_type", cpList.get(i).getShow_type());
			Map map2 = new HashMap();//指标信息
			
			//查询每个板块下的指标数据
			List<ColPlateIndi> indiList=plateInfoService.getIndiByPid(cpList.get(i).getPid());
			cmap.put("pid", cpList.get(i).getPid());
			for(int j=0;j<indiList.size();j++) {
				Map map3 = new HashMap();//指标信息
				cmap.put("indi_code", indiList.get(j).getIndi_id());
				List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);
				List x=new ArrayList();
				List indi_v=new ArrayList();
				for(int m=0;m<indiInfoList.size();m++) {
					x.add(indiInfoList.get(m).getDate_code().substring(0, 6));
					indi_v.add(indiInfoList.get(m).getIndi_value());
				}
				map3.put("indi_code", indiList.get(j).getIndi_id());
				map3.put("x-axis",x);
				map3.put("indi_value",indi_v);
				map3.put("time_point", indiInfoList.get(0).getTime_point());
				//map3.put("unitname", indiInfoList.get(0).getUnitname());
				map3.put("show_type",  indiList.get(j).getShow_type());
				map2.put(indiList.get(j).getIndi_name(), map3);
			}					
			map1.put("indi_info", map2);
			list1.add(map1);
		}
		
		//获取栏目的相关指标
		List<IndiCorrelative> icList = plateInfoService.getIndiCorrelative(2);
		List list2= new ArrayList();
		for(int i=0;i<cpList.size();i++) {
			Map map2 = new HashMap();
			map2.put("indi_id", icList.get(i).getIndi_id());
			map2.put("indi_name", icList.get(i).getIndi_name());
			list2.add(map2);
		}
				
		List list = new ArrayList();
		Map map1 = new HashMap();
		map1.put("板块", list1);
		map1.put("相关指标", list2);
//		list.add(list1);
//		list.add(list2);	
        String  param= JSON.toJSONString(map1);
        return param;
    }
	
	
	@RequestMapping(value="bb",produces = "text/plain;charset=utf-8")
	@ResponseBody
    public String bb(){		
		int indexId=2; //从app获得
		Map cmap = new HashMap();
		cmap.put("startTime", "201401SS");
		cmap.put("endTime", "201804SS");
		cmap.put("time_point", 203);
		List<ColPlate> cpList = plateInfoService.getPlateInfo(indexId);//查询板块
		List list1= new ArrayList();
		for(int i=0;i<cpList.size();i++) {
			Map map1 = new HashMap();//板块信息
			map1.put("id", cpList.get(i).getPid());
			map1.put("name", cpList.get(i).getPname());
			map1.put("show_type", cpList.get(i).getShow_type());
			Map map2 = new HashMap();//指标信息
			
			//查询每个板块下的指标数据
			List<ColPlateIndi> indiList=plateInfoService.getIndiByPid(cpList.get(i).getPid());
			cmap.put("pid", cpList.get(i).getPid());
			for(int j=0;j<indiList.size();j++) {
				Map map3 = new HashMap();//指标信息
				cmap.put("indi_code", indiList.get(j).getIndi_id());
				List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);
				List x=new ArrayList();
				List indi_v=new ArrayList();
				for(int m=0;m<indiInfoList.size();m++) {
					x.add(indiInfoList.get(m).getDate_code().substring(0, 6));
					indi_v.add(indiInfoList.get(m).getIndi_value());
				}
				map3.put("indi_code", indiList.get(j).getIndi_id());
				map3.put("x-axis",x);
				map3.put("indi_value",indi_v);
//				map3.put("time_point", indiInfoList.get(0).getTime_point());
			//	map3.put("unitname", indiInfoList.get(0).getUnitname());
				map3.put("show_type",  indiList.get(j).getShow_type());
				map2.put(indiList.get(j).getIndi_name(), map3);
			}					
			map1.put("indi_info", map2);
			list1.add(map1);
		}
		
		//获取栏目的相关指标
		List<IndiCorrelative> icList = plateInfoService.getIndiCorrelative(2);
		List list2= new ArrayList();
		for(int i=0;i<cpList.size();i++) {
			Map map2 = new HashMap();
			map2.put("indi_id", icList.get(i).getIndi_id());
			map2.put("indi_name", icList.get(i).getIndi_name());
			list2.add(map2);
		}
				
		List list = new ArrayList();
		Map map1 = new HashMap();
		map1.put("板块", list1);
		map1.put("相关指标", list2);
//		list.add(list1);
//		list.add(list2);	
        String  param= JSON.toJSONString(map1);
        return param;
    }
	

	//需要两个接口，1.初始化界面接口，接收的参数仅为栏目id，根据栏目id初始化时间，板块，以及相关指标
	//2.由于app端查询条件的变更 需要重新查询板块下的指标数据（接收的参数为开始时间，结束时间，频度）
	@RequestMapping(value="cc",produces = "text/plain;charset=utf-8")
	@ResponseBody
    public String cc(){		
		int indexId=2; //从app获得栏目id
		Map cmap = new HashMap();
		//term期数n ，从当前最新的时间往前推n期            *****/默认展示的是查得的期数吗？
		cmap.put("startTime", "201401SS");//开始时间 从app获得的参数 此处仅是模拟 后续需要接收参数
		cmap.put("endTime", "201804SS");//结束时间 从app获得的参数 此处仅是模拟 后续需要接收参数
		cmap.put("time_point", 203);//时点 此处不需要从app获得 此处仅是模拟数据 应该通过查询index_manage表获得
		List<ColPlate> cpList = plateInfoService.getPlateInfo(indexId);//查询板块
		List<LineEntity> leList = new ArrayList<LineEntity>();   // ****？
		List<String> timeMMList = new ArrayList<String>();//所有指标月度的时间跨度的列表
		List<String> timeYYList = new ArrayList<String>();//所有指标年度的时间跨度的列表   ***为什么没有季度的，是根据查得的有哪些来判断吧？
		for(int i=0;i<cpList.size();i++) {
			//在此需要判断板块中的指标是什么展现形式 ！！！！！！！					
			if(cpList.get(i).getShow_type().equals("折线图")) { //需要判断板块展示的是什么类型的图（实际上需要判断板块与指标均展示什么类型的图例）
				String id = String.valueOf(cpList.get(i).getPid());//获取板块id
				String title = cpList.get(i).getPname();	//获取板块名称	
				int term = cpList.get(i).getTerm();//获取期数 期数是最新
				List<ColPlateIndi> indiList=plateInfoService.getIndiByPid(cpList.get(i).getPid());//查询每个板块下的指标数据
				cmap.put("pid", cpList.get(i).getPid());//获得当前循环下的板块id
				
			for(int j=0;j<indiList.size();j++) {//循环板块下的指标列表
				Map map = new HashMap();
				//String freq_code,String indi_code,int time_point,String source
				map.put("freq_code", "SS");    //*****？
				map.put("indi_code", indiList.get(j).getIndi_id());
				map.put("time_point", indiList.get(j).getTime_point());
				map.put("sjly", indiList.get(j).getSjly());
				map.put("term", term);
				List<String> timeMM = plateInfoService.getDateCodeByFreq(map);//查询频度为月度的时间跨度
				map.put("freq_code", "MM");
				List<String> timeYY = plateInfoService.getDateCodeByFreq(map);//查询频度为年度的时间跨度
				
				LineType lt = new LineType();	//因为是折线图 所以创建对应的LineType			
				cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
				List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
				List dataX=new ArrayList();//折线图X轴的数据
				List dataV=new ArrayList();//指标数据
				for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
					dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
					dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
				}				
//				LineEntity le=lt.getOption(id,title,dataX,dataV);//根据板块id，板块名称，X轴，指标值 来实现返回的json数据格式		
//				leList.add(le);			
			}			
		}				
		  
	}		
				
		//获取栏目的相关指标
		List<IndiCorrelative> icList = plateInfoService.getIndiCorrelative(2);
		List list2= new ArrayList();//存放相关指标信息的列表
		for(int i=0;i<cpList.size();i++) {
			Map map2 = new HashMap();
			map2.put("indi_id", icList.get(i).getIndi_id());//存放相关指标的id
			map2.put("indi_name", icList.get(i).getIndi_name());//存放相关指标的名称
			list2.add(map2);//循环添加到列表中
		}
		
		Map map1 = new HashMap();		
//		map1.put("timeCondition", )//初始化的时间信息
		map1.put("classInfo", leList);//板块信息
		map1.put("relatedData", list2);//相关指标信息
	
		String  param= JSON.toJSONString(map1);
		 
//		JSONObject json = JSONObject.fromObject(le);
//		String strJson=json.toString();
		
	return param;
	}



//***********************************************************************//
	@RequestMapping(value="dd",produces = "text/plain;charset=utf-8")
	@ResponseBody
		public String dd() {
		int indexId=2; //从app获得栏目id
		Map cmap = new HashMap();
		List<ColPlate> cpList = plateInfoService.getPlateInfo(indexId);//查询板块
		List<LineEntity> leList = new ArrayList<LineEntity>();
		List<BarEntity> beList=new ArrayList<BarEntity>();
		//记录整个栏目的频度信息
		List<String> OldFreq=new ArrayList();
		//此处顺序不能调换，关系到后面取最小粒度数据
		
		OldFreq.add("YY");
		
		OldFreq.add("SS");
		OldFreq.add("MM");
		
		//获得频度信息
		for(int i=0;i<cpList.size();i++) {
			//判断板块展示的图类型
//			switch(cpList.get(i).getShow_type()) {
//			case "折线图":
				//查询每个板块下的指标数据
				List<ColPlateIndi> indiList=plateInfoService.getIndiByPid(cpList.get(i).getPid());
				
				
				for(int j=0;j<indiList.size();j++) {
					List<String> NewFreq=plateInfoService.getFreqCodeByIndiId(indiList.get(j).getIndi_id());
					OldFreq.retainAll(NewFreq);
//				}
				
			}
			
			
		}
//		for(int m=0;m<OldFreq.size();m++) {
//			System.out.print(OldFreq.get(m));
//		}
//		
//		OldFreq.add("SS");
		List listTimeCondition= new ArrayList();
		//记录后缀信息
		String suffix = null;
		//记录最小粒度时间信息
		List<String> s_time=new ArrayList();
		List<String> e_time=new ArrayList();
		//获得时间期数
		for(int k=0;k<OldFreq.size();k++) {
			Map timeConditionMap=new HashMap();
			timeConditionMap.put("freqName",OldFreq.get(k));
			//记录时间跨度
			List<String> startTime=new ArrayList();
			List<String> endTime=new ArrayList();
			
			for(int i=0;i<cpList.size();i++) {
				int term = cpList.get(i).getTerm();//获取期数 期数是最新
				//查询每个板块下的指标数据
				List<ColPlateIndi> indiList=plateInfoService.getIndiByPid(cpList.get(i).getPid());
				String sTime="000000";
				String eTime="999999";
				
				for(int j=0;j<indiList.size();j++) {
					Map map = new HashMap();
					map.put("indi_code", indiList.get(j).getIndi_id());
					map.put("time_point", indiList.get(j).getTime_point());
					map.put("sjly", indiList.get(j).getSjly());
					map.put("term", term);
					
					//************************************
//					map.put("freq_code","SS");
					map.put("freq_code",OldFreq.get(k));
					List<String> timeSpan = plateInfoService.getDateCodeByFreq(map);
					String maxTime=timeSpan.get(0).substring(0, 6);
					String minTime=timeSpan.get(timeSpan.size()-1).substring(0, 6);
					if(sTime.compareTo(minTime)<0)
						sTime=minTime;
					if(eTime.compareTo(maxTime)>0)
						eTime=maxTime;
						
					
				}
				startTime.add(sTime);
				endTime.add(eTime);
				
			}
			
			timeConditionMap.put("startArray",startTime);
			timeConditionMap.put("endArray",endTime);
			s_time=startTime;
			e_time=endTime;
			suffix=OldFreq.get(k);
			listTimeCondition.add(timeConditionMap);
			
			
		}
		//输出主体数据部分
		for(int i=0;i<cpList.size();i++) {
//			int term = cpList.get(i).getTerm();
//			switch(cpList.get(i).getShow_type()) {
//			case "折线图":
			
				String id = String.valueOf(cpList.get(i).getPid());//获取板块id
				String title = cpList.get(i).getPname();	//获取板块名称	
				List<ColPlateIndi> indiList=plateInfoService.getIndiByPid(cpList.get(i).getPid());
//				if(cpList.get(i).getShow_type().equals("折线图")) {
				switch(cpList.get(i).getShow_type()) {
				case "折线图":
//					List<LineEntity> leList = new ArrayList<LineEntity>();
				for(int j=0;j<indiList.size();j++) {
//					Map map = new HashMap();
//					map.put("freq_code", "SS");    //*****？
//					map.put("indi_code", indiList.get(j).getIndi_id());
//					map.put("time_point", indiList.get(j).getTime_point());
//					map.put("sjly", indiList.get(j).getSjly());
//					map.put("term", term);
					LineType lt = new LineType();	//因为是折线图 所以创建对应的LineType
					cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
					cmap.put("time_point", indiList.get(j).getTime_point());
//					Map dFreq=(Map) listTimeCondition.get(0);
					
					cmap.put("startTime",s_time.get(i)+suffix);
					cmap.put("endTime",e_time.get(i)+suffix);
//					cmap.put("endTime","201804");
					List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
					
					List dataX=new ArrayList();//折线图X轴的数据
					List dataV=new ArrayList();//指标数据
					for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
						dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
						dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
						
					}	
//						LineEntity le=lt.getOption(id,title,dataX,dataV);//根据板块id，板块名称，X轴，指标值 来实现返回的json数据格式		
//						leList.add(le);
						
					
				}
		
				break;
//				case "柱状图":
//					for(int j=0;j<indiList.size();j++) {
//						BarType bt=new BarType();
//						cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
//						cmap.put("time_point", indiList.get(j).getTime_point());
//						cmap.put("startTime",s_time.get(i)+suffix);
//						cmap.put("endTime",e_time.get(i)+suffix);
//						List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
//						
//						List dataX=new ArrayList();
//						List dataV=new ArrayList();
//						List dataXX=new ArrayList();
//						List dataVV=new ArrayList();
//						for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
//							dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
//							dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
//							
//						}
//						dataXX.add(dataX);
//						dataVV.add(dataV);
//						BarEntity be=bt.getOption(id,title,dataXX,dataVV);
//						beList.add(be);
//						
//						
//					}
					
				
				}
			
			
			
		}
		
		//获取相关指标
		List<IndiCorrelative> icList = plateInfoService.getIndiCorrelative(indexId);
		List listRelative= new ArrayList();
		for(int i=0;i<cpList.size();i++) {
			
			
			Map map2 = new HashMap();
			map2.put("indi_id", icList.get(i).getIndi_id());//存放相关指标的id
			map2.put("indi_name", icList.get(i).getIndi_name());//存放相关指标的名称
			listRelative.add(map2);//循环添加到列表中
		}
		
		Map mapAll=new HashMap();
		Map mapBack=new HashMap();
		mapAll.put("timeCondition",listTimeCondition);//初始化的时间信息
		mapAll.put("classInfo", leList);//板块信息
		mapAll.put("relatedData", listRelative);//相关指标信息
		mapBack.put("data",mapAll);
		mapBack.put("errCode","0");
		mapBack.put("errMsg","success");
		String  param= JSON.toJSONString(mapBack);
		return param;
	
	
	}
	
	
	
	
	//***********************************************************************//
		@RequestMapping(value="ee",produces = "text/plain;charset=utf-8")
//	@RequestMapping(value="lanmuee",produces = "application/json;charset=utf-8")
		@ResponseBody
		//@RequestBody String json,method = RequestMethod.POST
			public String ee() {
//		public String lanmuee() {
//			JSONObject jsonObject = JSONObject.fromObject(json);
//			  Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
//			  System.out.println("json" + json);
//
//			  String indexId1 = mapget.get("indexId").toString();
//			
//			int indexId=analysisManage.getId();
			int indexId=91; //从app获得栏目id
//			int  indexId = Integer.parseInt(indexId1);
			
			List<ColPlate> cpList = plateInfoService.getPlateInfo(indexId);//查询板块
			System.out.println("Cplist"+cpList.size());
			System.out.println("show_type"+cpList.get(0).getShow_type());
//			List<LineEntity> leList = new ArrayList<LineEntity>();
//			List<BarEntity> beList=new ArrayList<BarEntity>();
			List TotalList=new ArrayList();
			//记录整个栏目的频度信息
			List<String> OldFreq=new ArrayList();
			//此处顺序不能调换，关系到后面取最小粒度数据
			
			
			OldFreq.add("MM");
			OldFreq.add("SS");
			OldFreq.add("YY");
			
			
			//获得频度信息
			for(int i=0;i<cpList.size();i++) {
				//判断板块展示的图类型
//				switch(cpList.get(i).getShow_type()) {
//				case "折线图":
					//查询每个板块下的指标数据
					List<ColPlateIndi> indiList=plateInfoService.getIndiByPid(cpList.get(i).getPid());
					
					
					for(int j=0;j<indiList.size();j++) {
						List<String> NewFreq=plateInfoService.getFreqCodeByIndiId(indiList.get(j).getIndi_id());
						OldFreq.retainAll(NewFreq);
//					}
					
				}
				
				
			}
//			for(int m=0;m<OldFreq.size();m++) {
//				System.out.print(OldFreq.get(m));
//			}
			System.out.println("OldFreq:"+OldFreq);
//			
//			OldFreq.add("SS");
			List listTimeCondition= new ArrayList();
			//记录后缀信息
//			String suffix = null;
			//记录最小粒度时间信息
//			List<String> s_time=new ArrayList();
//			List<String> e_time=new ArrayList();
			//获得时间期数
			for(int k=0;k<OldFreq.size();k++) {
				Map timeConditionMap=new HashMap();
				timeConditionMap.put("freqName",OldFreq.get(k));
				//记录时间跨度
				List<String> startTime=new ArrayList();
				List<String> endTime=new ArrayList();
				String sTime="000000";
				String eTime="999999";
				System.out.println("获得某一频度下指标公共时间段");
				System.out.println("现在使用的是用两端时间节点的方法");
				for(int i=0;i<cpList.size();i++) {
					int term = cpList.get(i).getTerm();//获取期数 期数是最新
					//查询每个板块下的指标数据
					List<ColPlateIndi> indiList=plateInfoService.getIndiByPid(cpList.get(i).getPid());
					
					System.out.println("指标size:"+indiList.size());
					for(int j=0;j<indiList.size();j++) {
						Map map = new HashMap();
						System.out.print(indiList.get(j));
						
						map.put("indi_code", indiList.get(j).getIndi_id());
						map.put("time_point", indiList.get(j).getTime_point());
						map.put("sjly", indiList.get(j).getSjly());
						map.put("term", term);
						map.put("area_name","湖北省");
						map.put("group_count","0");
						System.out.println("indi_code"+indiList.get(j).getIndi_id());
						System.out.println("time_point"+indiList.get(j).getTime_point());
						System.out.println("sjly"+indiList.get(j).getSjly());
						System.out.println("term"+term);
						//************************************
//						map.put("freq_code","SS");
						map.put("freq_code",OldFreq.get(k));
						System.out.print("freq_code"+OldFreq.get(k));
						List<String> timeSpan = plateInfoService.getDateCodeByFreq(map);
						System.out.println("timeSpansize:"+timeSpan.size());
						String maxTime=timeSpan.get(0).substring(0, 6);
						System.out.println("what the error"+maxTime);
						String minTime=timeSpan.get(timeSpan.size()-1).substring(0, 6);
						if(sTime.compareTo(minTime)<0)
							sTime=minTime;
						if(eTime.compareTo(maxTime)>0)
							eTime=maxTime;
							
						
					}
//					startTime.add(sTime);
//					endTime.add(eTime);
					
				}
				
				
//				timeConditionMap.put("startArray",startTime);
//				timeConditionMap.put("endArray",endTime);
//				s_time=startTime;
//				e_time=endTime;
//				suffix=OldFreq.get(k);
				System.out.println("现在进入查找公共数据模块");
				List<ColPlateIndi> indi=plateInfoService.getIndiByPid(cpList.get(0).getPid());
				Map timeMap=new HashMap();
				timeMap.put("indi_code", indi.get(0).getIndi_id());
				timeMap.put("time_point", indi.get(0).getTime_point());
				timeMap.put("startTime",sTime+OldFreq.get(k));
				timeMap.put("endTime",eTime+OldFreq.get(k));
				timeMap.put("freq_code",OldFreq.get(k));
				
				timeMap.put("area_name","湖北省");
				timeMap.put("group_count","0");
				//临时配置
				timeMap.put("sjly","湖北省统计局-");
				
				System.out.println("freq_code"+OldFreq.get(k));
				System.out.println("before timeSpan");
				System.out.println("indi_code"+indi.get(0).getIndi_id());
				System.out.println("time_point"+indi.get(0).getTime_point());
				System.out.println("startTime"+sTime+OldFreq.get(k));
				System.out.println("endTime"+eTime+OldFreq.get(k));
				System.out.println("freq_code"+OldFreq.get(k));
				
				List<String> timeSPAN = plateInfoService.getTimeSpan(timeMap);
				System.out.println("timeSpanSize"+timeSPAN.size());
				//去掉时间后缀
				List<String> timeSPAN_NOT=new ArrayList();
				for(int l=0;l<timeSPAN.size();l++) {
					timeSPAN_NOT.add(timeSPAN.get(l).substring(0,6));
				}
				timeConditionMap.put("startArray",timeSPAN_NOT);
				timeConditionMap.put("endArray",timeSPAN_NOT);
				timeConditionMap.put("freq_code",OldFreq.get(k));
				if(timeSPAN.size()>8) {
					List<Integer> subIndex=new ArrayList();
					subIndex.add(timeSPAN.size()-8);
					subIndex.add(timeSPAN.size()-1);
					timeConditionMap.put("current",subIndex);
					
				}
				else 
				{
					List<Integer> subIndex=new ArrayList();
					subIndex.add(0);
					subIndex.add(timeSPAN.size()-1);
					timeConditionMap.put("current",subIndex);
					
					
					
				}
				
				
				listTimeCondition.add(timeConditionMap);
				
				
			}
			String e_time;
			String s_time;//记录起始和结束时间
//			int e_num;
//			int s_num;
			
			Map timeMap=new HashMap();
			timeMap=(Map) listTimeCondition.get(0);
			
			List<String> minTimeSpan=(List<String>) timeMap.get("startArray");
			System.out.println("timeSpan"+minTimeSpan.size());
			if(minTimeSpan.size()>8) {
				e_time=minTimeSpan.get(minTimeSpan.size()-1).substring(0,6);
				s_time=minTimeSpan.get(minTimeSpan.size()-8).substring(0,6);
				
			}
			else
			{
				e_time=minTimeSpan.get(minTimeSpan.size()-1).substring(0,6);
				s_time=minTimeSpan.get(0).substring(0,6);
				
			}
			
			String suffix=(String) timeMap.get("freq_code");
			//输出主体数据部分
			System.out.println("starttime"+s_time);
			System.out.println("endtime"+e_time);
			System.out.println("freq_code"+suffix);
			for(int i=0;i<cpList.size();i++) {
//				int term = cpList.get(i).getTerm();
//				switch(cpList.get(i).getShow_type()) {
//				case "折线图":
				Map cmap = new HashMap();
					String id = String.valueOf(cpList.get(i).getPid());//获取板块id
					String title = cpList.get(i).getPname();	//获取板块名称	
					List<ColPlateIndi> indiList=plateInfoService.getIndiByPid(cpList.get(i).getPid());
//					if(cpList.get(i).getShow_type().equals("折线图")) {
					switch(cpList.get(i).getShow_type()) {
					case "折线图":{
						System.out.println("进入折线图");
						List dataXX=new ArrayList();
						List dataVV=new ArrayList();
						List legend=new ArrayList();
						LineType lt = new LineType();	
//						List<LineEntity> leList = new ArrayList<LineEntity>();
					for(int j=0;j<indiList.size();j++) {
//						Map map = new HashMap();
//						map.put("freq_code", "SS");    //*****？
//						map.put("indi_code", indiList.get(j).getIndi_id());
//						map.put("time_point", indiList.get(j).getTime_point());
//						map.put("sjly", indiList.get(j).getSjly());
//						map.put("term", term);
//						LineType lt = new LineType();	//因为是折线图 所以创建对应的LineType
						cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
						cmap.put("time_point", indiList.get(j).getTime_point());
//						Map dFreq=(Map) listTimeCondition.get(0);
						
						cmap.put("startTime",s_time+suffix);
						cmap.put("endTime",e_time+suffix);
						cmap.put("freq_code",suffix);
						cmap.put("area_name","湖北省");
						cmap.put("group_count","0");
						//临时配置
						cmap.put("sjly","湖北省统计局-");
//						cmap.put("endTime","201804");
						List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
						System.out.println("indiInfoListSize"+indiInfoList.size());
						List dataX=new ArrayList();//折线图X轴的数据
						List dataV=new ArrayList();//指标数据
						for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
							dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
							dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
							
						}	
						dataXX.add(dataX);
						dataVV.add(dataV);
						legend.add(indiList.get(j).getIndi_name());
						
//							LineEntity le=lt.getOption(id,title,dataX,dataV);//根据板块id，板块名称，X轴，指标值 来实现返回的json数据格式		
//							leList.add(le);
							
						
					}
					TableType tableType=new TableType();
					System.out.println("Legend:"+legend);
					System.out.println("legendsize:"+legend.size());
					LineEntity le=lt.getOption(id,title,dataXX,legend,dataVV);
					TableEntity tableEntity=tableType.getTable(id, title, dataXX, legend, dataVV);
					
//					leList.add(le);
					TotalList.add(le);
					TotalList.add(tableEntity);
					System.out.println("i am here");
					}
			
					break;
					case "柱状图":{
						List dataXX=new ArrayList();
						List dataVV=new ArrayList();
						List legend=new ArrayList();
//						LineType lt = new LineType();
						BarType bt=new BarType();
						for(int j=0;j<indiList.size();j++) {
//							BarType bt=new BarType();
							cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
							cmap.put("time_point", indiList.get(j).getTime_point());
							cmap.put("startTime",s_time+suffix);
							cmap.put("endTime",e_time+suffix);
							cmap.put("freq_code",suffix);
							List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
//							
							List dataX=new ArrayList();
							List dataV=new ArrayList();
//							List dataXX=new ArrayList();
//							List dataVV=new ArrayList();
							for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
								dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
								dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
//								
							}
							dataXX.add(dataX);
							dataVV.add(dataV);
							legend.add(indiList.get(j).getIndi_name());
//							BarEntity be=bt.getOption(id,title,dataXX,dataVV);
//							beList.add(be);
//							
//							
						}
						BarEntity be=bt.getOption(id,title,dataXX,legend,dataVV);
//						beList.add(be);
						TotalList.add(be);
						
					
					}
					break;
					case "饼状图":{
						List dataV=new ArrayList();
						List legend=new ArrayList();
						PieType pt=new PieType();
						//饼状图只有一个指标
						cmap.put("indi_code",indiList.get(0).getIndi_id());
						cmap.put("time_point",indiList.get(0).getTime_point());
						cmap.put("startTime",e_time+suffix);
						cmap.put("endTime",e_time+suffix);
						cmap.put("freq_code",suffix);
						System.out.println("indi_code"+indiList.get(0).getIndi_id());
						System.out.println("time_point"+indiList.get(0).getTime_point());
						System.out.println("startTim"+e_time+suffix);
//						System.out.println("endtime"+)
						List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);
						System.out.println("饼状图："+indiInfoList.size());
						for(int m=0;m<indiInfoList.size();m++) {
							dataV.add(indiInfoList.get(m).getIndi_value());
							legend.add(indiInfoList.get(m).getGroupName());
						}
						PieEntity pieEntity=pt.getOption(id, title, dataV, legend);
						TotalList.add(pieEntity);
						
					}break;
					case "散点图":{
						List dataVV=new ArrayList();
						List legend=new ArrayList();
						PointType pt=new PointType();
						for(int j=0;j<indiList.size();j++) {
//							BarType bt=new BarType();
							cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
							cmap.put("time_point", indiList.get(j).getTime_point());
							cmap.put("startTime",s_time+suffix);
							cmap.put("endTime",e_time+suffix);
							cmap.put("freq_code",suffix);
							List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
							List dataV=new ArrayList();
							for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
//								dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
								dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
//								
							}
							dataVV.add(dataV);
							legend.add(indiList.get(j).getIndi_name());
							
						
					}
						PointEntity pe=pt.getOption(id, title, legend, dataVV);
//						peList.add(pe);
						TotalList.add(pe);
						
				
					}break;
					case "折柱混搭图":{
						List dataXX=new ArrayList();
						List dataVV=new ArrayList();
						List legend=new ArrayList();
						List showType=new ArrayList();
//						LineType lt = new LineType();
						LineAndBarType lbt=new LineAndBarType();
						List dataX=new ArrayList();
						for(int j=0;j<indiList.size();j++) {
//							BarType bt=new BarType();
							cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
							cmap.put("time_point", indiList.get(j).getTime_point());
							cmap.put("startTime",s_time+suffix);
							cmap.put("endTime",e_time+suffix);
							cmap.put("freq_code",suffix);
							List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
//							String sType=plateInfoService.getIndiShowType(indiList.get(j).getIndi_id());
							String sType=indiList.get(j).getShow_type();
							dataX=new ArrayList();
							List dataV=new ArrayList();
//							List dataXX=new ArrayList();
//							List dataVV=new ArrayList();
							for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
								dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
								dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
//								
							}
							dataXX.add(dataX);
							dataVV.add(dataV);
							legend.add(indiList.get(j).getIndi_name()+"-"+indiList.get(j).getTime_point());
							showType.add(sType);
							System.out.println("showType:"+sType);
//							BarEntity be=bt.getOption(id,title,dataXX,dataVV);
//							beList.add(be);
//							
//							
						}
					LineAndBarEntity lbe=lbt.getOption(id,title,dataX,legend,dataVV,showType);
//						beList.add(be);
						TotalList.add(lbe);
						
					}break;
					case "双X轴折线图":{
						List dataXX=new ArrayList();
						List dataVV=new ArrayList();
						List legend=new ArrayList();
						DoubleXaxisLineType dxt = new DoubleXaxisLineType();	
//						List<LineEntity> leList = new ArrayList<LineEntity>();
					    for(int j=0;j<indiList.size();j++) {

						cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
						cmap.put("time_point", indiList.get(j).getTime_point());
//						Map dFreq=(Map) listTimeCondition.get(0);
						
						cmap.put("startTime",s_time+suffix);
						cmap.put("endTime",e_time+suffix);
						cmap.put("freq_code",suffix);
//						cmap.put("endTime","201804");
						List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
						
						List dataX=new ArrayList();//折线图X轴的数据
						List dataV=new ArrayList();//指标数据
						for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
							dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
							dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
							
						}	
						dataXX.add(dataX);
						dataVV.add(dataV);
						legend.add(indiList.get(j).getIndi_name());
						
//							LineEntity le=lt.getOption(id,title,dataX,dataV);//根据板块id，板块名称，X轴，指标值 来实现返回的json数据格式		
//							leList.add(le);
							
						
					}
					DoubleXaxisLineEntity dxe=dxt.getOption(id,title,dataXX,legend,dataVV);
//					leList.add(le);
					TotalList.add(dxe);
						
					}break;
					case "柱状堆叠折线图":{
						List dataXX=new ArrayList();
						List dataVV=new ArrayList();
						List legend=new ArrayList();
						List showType=new ArrayList();
//						LineType lt = new LineType();
						BarStackLineType bslt=new BarStackLineType();
						for(int j=0;j<indiList.size();j++) {
//							BarType bt=new BarType();
							cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
							cmap.put("time_point", indiList.get(j).getTime_point());
							cmap.put("startTime",s_time+suffix);
							cmap.put("endTime",e_time+suffix);
							cmap.put("freq_code",suffix);
							List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
							String sType=plateInfoService.getIndiShowType(indiList.get(j).getIndi_id());
							List dataX=new ArrayList();
							List dataV=new ArrayList();
//							List dataXX=new ArrayList();
//							List dataVV=new ArrayList();
							for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
								dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
								dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
//								
							}
							dataXX.add(dataX);
							dataVV.add(dataV);
							legend.add(indiList.get(j).getIndi_name());
							showType.add(sType);
//							BarEntity be=bt.getOption(id,title,dataXX,dataVV);
//							beList.add(be);
//							
//							
						}
					BarStackLineEntity bsle=bslt.getOption(id,title,dataXX,legend,dataVV,showType);
//						beList.add(be);
						TotalList.add(bsle);
						
					}break;
					case "柱状堆叠图":{
						List dataXX=new ArrayList();
						List dataVV=new ArrayList();
						List legend=new ArrayList();
						BarStoreType bst = new BarStoreType();	
//						List<LineEntity> leList = new ArrayList<LineEntity>();
					for(int j=0;j<indiList.size();j++) {

						cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
						cmap.put("time_point", indiList.get(j).getTime_point());
//						Map dFreq=(Map) listTimeCondition.get(0);
						
						cmap.put("startTime",s_time+suffix);
						cmap.put("endTime",e_time+suffix);
						cmap.put("freq_code",suffix);
//						cmap.put("endTime","201804");
						List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
						
						List dataX=new ArrayList();//折线图X轴的数据
						List dataV=new ArrayList();//指标数据
						for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
							dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
							dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
							
						}	
						dataXX.add(dataX);
						dataVV.add(dataV);
						legend.add(indiList.get(j).getIndi_name());
						
//							LineEntity le=lt.getOption(id,title,dataX,dataV);//根据板块id，板块名称，X轴，指标值 来实现返回的json数据格式		
//							leList.add(le);
							
						
					}
					BarStoreEntity bse=bst.getOption(id,title,dataXX,legend,dataVV);
//					leList.add(le);
					TotalList.add(bse);
						
					}break;
					case "雷达图":{
						//记录图例，此处为时间
						List legendData=new ArrayList();
						//分指标记录值
						List<List> dataValue=new ArrayList();
						//记录指标名
						List dataName=new ArrayList();
						//记录以时间跨度的值
						List<List> dataByTime=new ArrayList();
						RadarType radarType=new RadarType();
						for(int j=0;j<indiList.size();j++) {
							cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
							cmap.put("time_point", indiList.get(j).getTime_point());
							cmap.put("startTime",s_time+suffix);
							cmap.put("endTime",e_time+suffix);
							System.out.print("startTime:"+s_time);
							System.out.println("endTime:"+e_time);
							cmap.put("freq_code",suffix);
							List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);
							List dataX=new ArrayList();
							List dataV=new ArrayList();
							System.out.println("indisize"+indiInfoList.size());
							for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
								dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
								dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
								
							}	
							//此处有点浪费计算资源，最好改一下
							legendData=dataX;
							dataValue.add(dataV);
							dataName.add(indiList.get(j).getIndi_name());
							
						}
					
						//获取以时间为单位的值
						for(int k=0;k<legendData.size();k++) {
							List dataOfTime=new ArrayList();
							for(int n=0;n<dataValue.size();n++) {
								List dataTem=dataValue.get(n);
								dataOfTime.add(dataTem.get(k));
								
								
							}
							dataByTime.add(dataOfTime);
							
						}
						System.out.println("dataName"+dataName);
						System.out.println("dataValue"+dataValue);
						
						RadarEntity radarEntity=radarType.getOption(id, title, legendData, dataName, dataValue, dataByTime);
						TotalList.add(radarEntity);
						
					}break;
						
				
					}
			}
			
			//获取相关指标
			List<IndiCorrelative> icList = plateInfoService.getIndiCorrelative(indexId);
			List listRelative= new ArrayList();
//			for(int i=0;i<cpList.size();i++) {
				
			for(int i=0;i<icList.size();i++) {	
				Map map2 = new HashMap();
				map2.put("indexId", icList.get(i).getIndi_id());//存放相关指标的id
				map2.put("indexName", icList.get(i).getIndi_name());//存放相关指标的名称
				listRelative.add(map2);//循环添加到列表中
			}
			
			Map mapAll=new HashMap();
			Map mapBack=new HashMap();
			mapAll.put("timeCondition",listTimeCondition);//初始化的时间信息
//			mapAll.put("classInfo", leList);//板块信息
			mapAll.put("classInfo",TotalList);
			mapAll.put("relatedData", listRelative);//相关指标信息
			mapBack.put("data",mapAll);
			mapBack.put("errCode","0");
			mapBack.put("errMsg","success");
			String  param= JSON.toJSONString(mapBack, SerializerFeature.DisableCircularReferenceDetect);
			return param;
			
		
		
		}
	
	
	
	
	//********************************************************//
		//第二个接口
		@RequestMapping(value="ff",produces = "text/plain;charset=utf-8",method = RequestMethod.POST)
		@ResponseBody
		public String ff(@RequestBody String json) {
			//,method = RequestMethod.POST，@RequestBody String json
//			JSONObject jsonObject=JSONObject.fromObject(json);
//			AnalysisManage analysisManage=(AnalysisManage)JSONObject.toBean(jsonObject, AnalysisManage.class);
			
//			int indexId=analysisManage.getId();
//			int indexId=2;//从app获取
			
			JSONObject jsonObject = JSONObject.fromObject(json);
			  Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
			  System.out.println("json" + json);

			  String indexId1 = mapget.get("indexId").toString();
			String startTime=mapget.get("startTime").toString();
			String endTime=mapget.get("endTime").toString();
			String freq=mapget.get("timeFreq").toString();
//			int indexId=analysisManage.getId();
		
//			int indexId=6; //从app获得栏目id
			int  indexId = Integer.parseInt(indexId1);
//			int indexId=2;
//			String startTime="201702SS";
//			String endTime="201802SS";
//			String freq="SS";
			
			System.out.println("indexid"+indexId);
			System.out.println("st"+startTime);
			System.out.println("edt"+endTime);
			System.out.println("freq"+freq);
//			String startTime="201709";//从app获取
//			String endTime="201804";//从app获取
//			String freq="SS";//从app获取
			
			
			Map cmap = new HashMap();
			List<ColPlate> cpList = plateInfoService.getPlateInfo(indexId);//查询板块
//			List<LineEntity> leList = new ArrayList<LineEntity>();
//			List<BarEntity> beList=new ArrayList<BarEntity>();
			List TotalList=new ArrayList();
			System.out.println("cpList"+cpList.size());
			
			//输出主体数据部分
			for(int i=0;i<cpList.size();i++) {
//				int term = cpList.get(i).getTerm();
//				switch(cpList.get(i).getShow_type()) {
//				case "折线图":
				
					String id = String.valueOf(cpList.get(i).getPid());//获取板块id
					String title = cpList.get(i).getPname();	//获取板块名称	
					List<ColPlateIndi> indiList=plateInfoService.getIndiByPid(cpList.get(i).getPid());
					System.out.println("indiList"+indiList.size());
//					if(cpList.get(i).getShow_type().equals("折线图")) {
					switch(cpList.get(i).getShow_type()) {
					case "折线图":{
						List dataXX=new ArrayList();
						List dataVV=new ArrayList();
						List legend=new ArrayList();
						LineType lt = new LineType();	
//						List<LineEntity> leList = new ArrayList<LineEntity>();
					for(int j=0;j<indiList.size();j++) {
//						Map map = new HashMap();
//						map.put("freq_code", "SS");    //*****？
//						map.put("indi_code", indiList.get(j).getIndi_id());
//						map.put("time_point", indiList.get(j).getTime_point());
//						map.put("sjly", indiList.get(j).getSjly());
//						map.put("term", term);
//						LineType lt = new LineType();	//因为是折线图 所以创建对应的LineType
						cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
						cmap.put("time_point", indiList.get(j).getTime_point());
						
//						Map dFreq=(Map) listTimeCondition.get(0);
						cmap.put("freq_code",freq);
						cmap.put("startTime",startTime);
						cmap.put("endTime",endTime);
//						cmap.put("endTime","201804");
						List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
						
						List dataX=new ArrayList();//折线图X轴的数据
						List dataV=new ArrayList();//指标数据
						for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
							dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
							dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
							
						}	
						dataXX.add(dataX);
						dataVV.add(dataV);
						legend.add(indiList.get(j).getIndi_name());
						
//							LineEntity le=lt.getOption(id,title,dataX,dataV);//根据板块id，板块名称，X轴，指标值 来实现返回的json数据格式		
//							leList.add(le);
							
						
					}
					LineEntity le=lt.getOption(id,title,dataXX,legend,dataVV);
//					leList.add(le);
					TotalList.add(le);
					}
			
					break;
					case "柱状图":{
						List dataXX=new ArrayList();
						List dataVV=new ArrayList();
						List legend=new ArrayList();
//						LineType lt = new LineType();
						BarType bt=new BarType();
						for(int j=0;j<indiList.size();j++) {
//							BarType bt=new BarType();
							cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
							cmap.put("time_point", indiList.get(j).getTime_point());
							cmap.put("startTime",startTime+freq);
							cmap.put("endTime",endTime+freq);
							List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
//							
							List dataX=new ArrayList();
							List dataV=new ArrayList();
//							List dataXX=new ArrayList();
//							List dataVV=new ArrayList();
							for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
								dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
								dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
//								
							}
							dataXX.add(dataX);
							dataVV.add(dataV);
							legend.add(indiList.get(j).getIndi_name());
//							BarEntity be=bt.getOption(id,title,dataXX,dataVV);
//							beList.add(be);
//							
//							
						}
						BarEntity be=bt.getOption(id,title,dataXX,legend,dataVV);
//						beList.add(be);
						TotalList.add(be);
						
					
					}
					break;
					case "饼状图":{
						List dataV=new ArrayList();
						List legend=new ArrayList();
						PieType pt=new PieType();
						//饼状图只有一个指标
						cmap.put("indi_code",indiList.get(0).getIndi_id());
						cmap.put("time_point",indiList.get(0).getTime_point());
						cmap.put("startTime",endTime+freq);
						cmap.put("endTime",endTime+freq);
						List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);
						for(int m=0;m<indiInfoList.size();m++) {
							dataV.add(indiInfoList.get(m).getIndi_value());
							legend.add(indiInfoList.get(m).getGroupName());
						}
						PieEntity pieEntity=pt.getOption(id, title, dataV, legend);
						TotalList.add(pieEntity);
						
					}break;
					case "散点图":{
						List dataVV=new ArrayList();
						List legend=new ArrayList();
						PointType pt=new PointType();
						for(int j=0;j<indiList.size();j++) {
//							BarType bt=new BarType();
							cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
							cmap.put("time_point", indiList.get(j).getTime_point());
							cmap.put("startTime",startTime+freq);
							cmap.put("endTime",endTime+freq);
							List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
							List dataV=new ArrayList();
							for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
//								dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
								dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
//								
							}
							dataVV.add(dataV);
							legend.add(indiList.get(j).getIndi_name());
							
						
					}
						PointEntity pe=pt.getOption(id, title, legend, dataVV);
//						peList.add(pe);
						TotalList.add(pe);
						
				
					}break;
					case "折柱混搭图":{
						List dataXX=new ArrayList();
						List dataVV=new ArrayList();
						List legend=new ArrayList();
						List showType=new ArrayList();
//						LineType lt = new LineType();
						LineAndBarType lbt=new LineAndBarType();
						for(int j=0;j<indiList.size();j++) {
//							BarType bt=new BarType();
							cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
							cmap.put("time_point", indiList.get(j).getTime_point());
							cmap.put("startTime",startTime+freq);
							cmap.put("endTime",endTime+freq);
							List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
							String sType=plateInfoService.getIndiShowType(indiList.get(j).getIndi_id());
							List dataX=new ArrayList();
							List dataV=new ArrayList();
//							List dataXX=new ArrayList();
//							List dataVV=new ArrayList();
							for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
								dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
								dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
//								
							}
							dataXX.add(dataX);
							dataVV.add(dataV);
							legend.add(indiList.get(j).getIndi_name());
							showType.add(sType);
//							BarEntity be=bt.getOption(id,title,dataXX,dataVV);
//							beList.add(be);
//							
//							
						}
					LineAndBarEntity lbe=lbt.getOption(id,title,dataXX,legend,dataVV,showType);
//						beList.add(be);
						TotalList.add(lbe);
						
					}break;
					case "双X轴折线图":{
						List dataXX=new ArrayList();
						List dataVV=new ArrayList();
						List legend=new ArrayList();
						LineType dxt = new LineType();	
//						List<LineEntity> leList = new ArrayList<LineEntity>();
					    for(int j=0;j<indiList.size();j++) {

						cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
						cmap.put("time_point", indiList.get(j).getTime_point());
//						Map dFreq=(Map) listTimeCondition.get(0);
						
						cmap.put("startTime",startTime+freq);
						cmap.put("endTime",endTime+freq);
//						cmap.put("endTime","201804");
						List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
						
						List dataX=new ArrayList();//折线图X轴的数据
						List dataV=new ArrayList();//指标数据
						for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
							dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
							dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
							
						}	
						dataXX.add(dataX);
						dataVV.add(dataV);
						legend.add(indiList.get(j).getIndi_name());
						
//							LineEntity le=lt.getOption(id,title,dataX,dataV);//根据板块id，板块名称，X轴，指标值 来实现返回的json数据格式		
//							leList.add(le);
							
						
					}
					LineEntity dxe=dxt.getOption(id,title,dataXX,legend,dataVV);
//					leList.add(le);
					TotalList.add(dxe);
						
					}break;
					case "柱状堆叠折线图":{
						List dataXX=new ArrayList();
						List dataVV=new ArrayList();
						List legend=new ArrayList();
						List showType=new ArrayList();
//						LineType lt = new LineType();
						BarStackLineType bslt=new BarStackLineType();
						for(int j=0;j<indiList.size();j++) {
//							BarType bt=new BarType();
							cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
							cmap.put("time_point", indiList.get(j).getTime_point());
							cmap.put("startTime",startTime+freq);
							cmap.put("endTime",endTime+freq);
							List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
							String sType=plateInfoService.getIndiShowType(indiList.get(j).getIndi_id());
							List dataX=new ArrayList();
							List dataV=new ArrayList();
//							List dataXX=new ArrayList();
//							List dataVV=new ArrayList();
							for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
								dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
								dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
//								
							}
							dataXX.add(dataX);
							dataVV.add(dataV);
							legend.add(indiList.get(j).getIndi_name());
							showType.add(sType);
//							BarEntity be=bt.getOption(id,title,dataXX,dataVV);
//							beList.add(be);
//							
//							
						}
					BarStackLineEntity bsle=bslt.getOption(id,title,dataXX,legend,dataVV,showType);
//						beList.add(be);
						TotalList.add(bsle);
						
					}break;
					case "柱状堆叠图":{
						List dataXX=new ArrayList();
						List dataVV=new ArrayList();
						List legend=new ArrayList();
						BarStoreType bst = new BarStoreType();	
//						List<LineEntity> leList = new ArrayList<LineEntity>();
					for(int j=0;j<indiList.size();j++) {

						cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
						cmap.put("time_point", indiList.get(j).getTime_point());
//						Map dFreq=(Map) listTimeCondition.get(0);
						
						cmap.put("startTime",startTime+freq);
						cmap.put("endTime",endTime+freq);
//						cmap.put("endTime","201804");
						List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
						
						List dataX=new ArrayList();//折线图X轴的数据
						List dataV=new ArrayList();//指标数据
						for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
							dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
							dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
							
						}	
						dataXX.add(dataX);
						dataVV.add(dataV);
						legend.add(indiList.get(j).getIndi_name());
						
//							LineEntity le=lt.getOption(id,title,dataX,dataV);//根据板块id，板块名称，X轴，指标值 来实现返回的json数据格式		
//							leList.add(le);
							
						
					}
					BarStoreEntity bse=bst.getOption(id,title,dataXX,legend,dataVV);
//					leList.add(le);
					TotalList.add(bse);
						
					}break;
					case "雷达图":{
						//记录图例，此处为时间
						List legendData=new ArrayList();
						//分指标记录值
						List<List> dataValue=new ArrayList();
						//记录指标名
						List dataName=new ArrayList();
						//记录以时间跨度的值
						List<List> dataByTime=new ArrayList();
						RadarType radarType=new RadarType();
						for(int j=0;j<indiList.size();j++) {
							cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
							cmap.put("time_point", indiList.get(j).getTime_point());
							cmap.put("startTime",startTime+freq);
							cmap.put("endTime",endTime+freq);
							List<indi_TF> indiInfoList=plateInfoService.getIndiInfoByTime(cmap);
							List dataX=new ArrayList();
							List dataV=new ArrayList();
							for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
								dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
								dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
								
							}	
							//此处有点浪费计算资源，最好改一下
							legendData=dataX;
							dataValue.add(dataV);
							dataName.add(indiList.get(j).getIndi_name());
							
						}
						//获取以时间为单位的值
						for(int k=0;k<legendData.size();k++) {
							List dataOfTime=new ArrayList();
							for(int n=0;n<dataValue.size();n++) {
								List dataTem=dataValue.get(n);
								dataOfTime.add(dataTem.get(k));
								
								
							}
							dataByTime.add(dataOfTime);
							
						}
						
						RadarEntity radarEntity=radarType.getOption(id, title, legendData, dataName, dataValue, dataByTime);
						TotalList.add(radarEntity);
						
					}break;
						
				
					}
			}
			Map mapAll=new HashMap();
			mapAll.put("classInfo",TotalList);
			Map mapBack=new HashMap();
			mapBack.put("data",mapAll);
			mapBack.put("errCode","0");
			mapBack.put("errMsg","success");
			String  param= JSON.toJSONString(mapBack);
			return param;
			
			
		}
		

}

