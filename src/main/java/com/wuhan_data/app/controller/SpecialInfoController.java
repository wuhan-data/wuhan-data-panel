package com.wuhan_data.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wuhan_data.app.service.IndiDetailService;
import com.wuhan_data.app.service.PlateInfoService;
import com.wuhan_data.app.service.SpecialInfoService;
import com.wuhan_data.app.showType.BarStackLineType;
import com.wuhan_data.app.showType.BarStoreType;
import com.wuhan_data.app.showType.BarType;
import com.wuhan_data.app.showType.LineAndBarType;
import com.wuhan_data.app.showType.LineType;
import com.wuhan_data.app.showType.PieType;
import com.wuhan_data.app.showType.PointType;
import com.wuhan_data.app.showType.RadarType;
import com.wuhan_data.app.showType.pojo.BarEntity;
import com.wuhan_data.app.showType.pojo.BarStackLineEntity;
import com.wuhan_data.app.showType.pojo.BarStoreEntity;
import com.wuhan_data.app.showType.pojo.LineAndBarEntity;
import com.wuhan_data.app.showType.pojo.LineEntity;
import com.wuhan_data.app.showType.pojo.PieEntity;
import com.wuhan_data.app.showType.pojo.PointEntity;
import com.wuhan_data.app.showType.pojo.RadarEntity;
import com.wuhan_data.pojo.ColPlate;
import com.wuhan_data.pojo.ColPlateIndi;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.indi_TF;



@Controller
@RequestMapping("")
public class SpecialInfoController {
	@Autowired
	SpecialInfoService specialInfoService;
	
//	@Autowired
//	IndiDetailService indiDetailService;
	//***********************************************************************//
	@RequestMapping(value="special",produces = "text/plain;charset=utf-8")
	@ResponseBody
		public String special() {
		int indexId=1001; //从app获得栏目id
		Map cmap = new HashMap();
		System.out.println("i am ok");
		List<ColPlate> cpList = specialInfoService.getPlateInfo(indexId);//查询板块
		System.out.println(cpList.size());
//		List<LineEntity> leList = new ArrayList<LineEntity>();
//		List<BarEntity> beList=new ArrayList<BarEntity>();
		List TotalList=new ArrayList();
		//记录整个栏目的频度信息
		List<String> OldFreq=new ArrayList();
		//此处顺序不能调换，此处顺序与栏目中的数据恰好相反
		
		
		
		
		OldFreq.add("MM");
		OldFreq.add("SS");
		OldFreq.add("YY");
		
		//获得频度信息
		for(int i=0;i<cpList.size();i++) {
			//判断板块展示的图类型
//			switch(cpList.get(i).getShow_type()) {
//			case "折线图":
				//查询每个板块下的指标数据
				List<ColPlateIndi> indiList=specialInfoService.getIndiByPid(cpList.get(i).getPid());
				
				
				for(int j=0;j<indiList.size();j++) {
					List<String> NewFreq=specialInfoService.getFreqCodeByIndiId(indiList.get(j).getIndi_id());
					OldFreq.retainAll(NewFreq);
//				}
				
			}
			
			
		}
		if(OldFreq.size()==0) {
			String error=JSON.toJSONString("没有公共的频度信息");
					
			return error;
		}
//		List<String> startTime=new ArrayList();
//		List<String> endTime=new ArrayList();
		String sTime="000000";
		String eTime="999999";
		for(int i=0;i<cpList.size();i++) {
			int term = cpList.get(i).getTerm();//获取期数 期数是最新
			//查询每个板块下的指标数据
			List<ColPlateIndi> indiList=specialInfoService.getIndiByPid(cpList.get(i).getPid());
			
			System.out.print("size:"+indiList.size());
			for(int j=0;j<indiList.size();j++) {
				Map map = new HashMap();
//				System.out.print(indiList.get(j));
				
				map.put("indi_code", indiList.get(j).getIndi_id());
				map.put("time_point", indiList.get(j).getTime_point());
				map.put("sjly", indiList.get(j).getSjly());
				map.put("term", term);
				map.put("freq_code",OldFreq.get(0));
//				System.out.print("freq_code"+OldFreq.get(0));
				List<String> timeSpan = specialInfoService.getDateCodeByFreq(map);
				String maxTime=timeSpan.get(0).substring(0, 6);
//				System.out.println("what the error"+maxTime);
				String minTime=timeSpan.get(timeSpan.size()-1).substring(0, 6);
				if(sTime.compareTo(minTime)<0)
					sTime=minTime;
				if(eTime.compareTo(maxTime)>0)
					eTime=maxTime;
			}
//			startTime.add(sTime);
//			endTime.add(eTime);
			
		}
					
				
		
//		for(int m=0;m<OldFreq.size();m++) {
//			System.out.print(OldFreq.get(m));
//		}
//		
//		OldFreq.add("SS");
//		List listTimeCondition= new ArrayList();
		//记录后缀信息
//		String suffix = null;
		//记录最小粒度时间信息
//		List<String> s_time=new ArrayList();
//		List<String> e_time=new ArrayList();
		//获得时间期数
//		for(int k=0;k<OldFreq.size();k++) {
////			Map timeConditionMap=new HashMap();
////			timeConditionMap.put("freqName",OldFreq.get(k));
//			//记录时间跨度
////			List<String> startTime=new ArrayList();
////			List<String> endTime=new ArrayList();
//			
//			for(int i=0;i<cpList.size();i++) {
//				int term = cpList.get(i).getTerm();//获取期数 期数是最新
//				//查询每个板块下的指标数据
//				List<ColPlateIndi> indiList=specialInfoService.getIndiByPid(cpList.get(i).getPid());
//				String sTime="000000";
//				String eTime="999999";
//				System.out.print("size:"+indiList.size());
//				for(int j=0;j<indiList.size();j++) {
//					Map map = new HashMap();
//					System.out.print(indiList.get(j));
//					
//					map.put("indi_code", indiList.get(j).getIndi_id());
//					map.put("time_point", indiList.get(j).getTime_point());
//					map.put("sjly", indiList.get(j).getSjly());
//					map.put("term", term);
//					System.out.print("indi_code"+indiList.get(j).getIndi_id());
//					System.out.print("time_point"+indiList.get(j).getTime_point());
//					System.out.print("sjly"+indiList.get(j).getSjly());
//					System.out.print("term"+term);
//					//************************************
////					map.put("freq_code","SS");
//					map.put("freq_code",OldFreq.get(k));
//					System.out.print("freq_code"+OldFreq.get(k));
//					List<String> timeSpan = specialInfoService.getDateCodeByFreq(map);
//					String maxTime=timeSpan.get(0).substring(0, 6);
//					System.out.println("what the error"+maxTime);
//					String minTime=timeSpan.get(timeSpan.size()-1).substring(0, 6);
//					if(sTime.compareTo(minTime)<0)
//						sTime=minTime;
//					if(eTime.compareTo(maxTime)>0)
//						eTime=maxTime;
//						
//					
//				}
//				startTime.add(sTime);
//				endTime.add(eTime);
//				
//			}
//			
//			timeConditionMap.put("startArray",startTime);
//			timeConditionMap.put("endArray",endTime);
//			s_time=startTime;
//			e_time=endTime;
//			suffix=OldFreq.get(k);
//			listTimeCondition.add(timeConditionMap);
//			
//			
//		}
		//输出主体数据部分
		for(int i=0;i<cpList.size();i++) {
//			int term = cpList.get(i).getTerm();
//			switch(cpList.get(i).getShow_type()) {
//			case "折线图":
			
				String id = String.valueOf(cpList.get(i).getPid());//获取板块id
				String title = cpList.get(i).getPname();	//获取板块名称	
				List<ColPlateIndi> indiList=specialInfoService.getIndiByPid(cpList.get(i).getPid());
//				if(cpList.get(i).getShow_type().equals("折线图")) {
				switch(cpList.get(i).getShow_type()) {
				case "折线图":{
					List dataXX=new ArrayList();
					List dataVV=new ArrayList();
					List legend=new ArrayList();
					LineType lt = new LineType();	
//					List<LineEntity> leList = new ArrayList<LineEntity>();
				for(int j=0;j<indiList.size();j++) {
//					Map map = new HashMap();
//					map.put("freq_code", "SS");    //*****？
//					map.put("indi_code", indiList.get(j).getIndi_id());
//					map.put("time_point", indiList.get(j).getTime_point());
//					map.put("sjly", indiList.get(j).getSjly());
//					map.put("term", term);
//					LineType lt = new LineType();	//因为是折线图 所以创建对应的LineType
					cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
					cmap.put("time_point", indiList.get(j).getTime_point());
//					Map dFreq=(Map) listTimeCondition.get(0);
					
					cmap.put("startTime",sTime+OldFreq.get(0));
					cmap.put("endTime",eTime+OldFreq.get(0));
					cmap.put("freq_code",OldFreq.get(0));
//					cmap.put("endTime","201804");
					List<indi_TF> indiInfoList=specialInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
					
					List dataX=new ArrayList();//折线图X轴的数据
					List dataV=new ArrayList();//指标数据
					for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
						dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
						dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
						
					}	
					dataXX.add(dataX);
					dataVV.add(dataV);
					legend.add(indiList.get(j).getIndi_name());
					
//						LineEntity le=lt.getOption(id,title,dataX,dataV);//根据板块id，板块名称，X轴，指标值 来实现返回的json数据格式		
//						leList.add(le);
						
					
				}
				LineEntity le=lt.getOption(id,title,dataXX,legend,dataVV);
//				leList.add(le);
				TotalList.add(le);
				}
		
				break;
				case "柱状图":{
					List dataXX=new ArrayList();
					List dataVV=new ArrayList();
					List legend=new ArrayList();
//					LineType lt = new LineType();
					BarType bt=new BarType();
					for(int j=0;j<indiList.size();j++) {
//						BarType bt=new BarType();
						cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
						cmap.put("time_point", indiList.get(j).getTime_point());
						cmap.put("startTime",sTime+OldFreq.get(0));
						cmap.put("endTime",eTime+OldFreq.get(0));
						cmap.put("freq_code",OldFreq.get(0));
						List<indi_TF> indiInfoList=specialInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
//						
						List dataX=new ArrayList();
						List dataV=new ArrayList();
//						List dataXX=new ArrayList();
//						List dataVV=new ArrayList();
						for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
							dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
							dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
//							
						}
						dataXX.add(dataX);
						dataVV.add(dataV);
						legend.add(indiList.get(j).getIndi_name());
//						BarEntity be=bt.getOption(id,title,dataXX,dataVV);
//						beList.add(be);
//						
//						
					}
					BarEntity be=bt.getOption(id,title,dataXX,legend,dataVV);
//					beList.add(be);
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
					cmap.put("startTime",eTime+OldFreq.get(0));
					cmap.put("endTime",eTime+OldFreq.get(0));
					cmap.put("freq_code",OldFreq.get(0));
					List<indi_TF> indiInfoList=specialInfoService.getIndiInfoByTime(cmap);
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
//						BarType bt=new BarType();
						cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
						cmap.put("time_point", indiList.get(j).getTime_point());
						cmap.put("startTime",sTime+OldFreq.get(0));
						cmap.put("endTime",eTime+OldFreq.get(0));
						cmap.put("freq_code",OldFreq.get(0));
						List<indi_TF> indiInfoList=specialInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
						List dataV=new ArrayList();
						for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
//							dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
							dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
//							
						}
						dataVV.add(dataV);
						legend.add(indiList.get(j).getIndi_name());
						
					
				}
					PointEntity pe=pt.getOption(id, title, legend, dataVV);
//					peList.add(pe);
					TotalList.add(pe);
					
			
				}break;
				case "折柱混搭图":{
					List dataXX=new ArrayList();
					List dataVV=new ArrayList();
					List legend=new ArrayList();
					List showType=new ArrayList();
//					LineType lt = new LineType();
					LineAndBarType lbt=new LineAndBarType();
					for(int j=0;j<indiList.size();j++) {
//						BarType bt=new BarType();
						cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
						cmap.put("time_point", indiList.get(j).getTime_point());
						cmap.put("startTime",sTime+OldFreq.get(0));
						cmap.put("endTime",eTime+OldFreq.get(0));
						cmap.put("freq_code",OldFreq.get(0));
						List<indi_TF> indiInfoList=specialInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
						String sType=specialInfoService.getIndiShowType(indiList.get(j).getIndi_id());
						List dataX=new ArrayList();
						List dataV=new ArrayList();
//						List dataXX=new ArrayList();
//						List dataVV=new ArrayList();
						for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
							dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
							dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
//							
						}
						dataXX.add(dataX);
						dataVV.add(dataV);
						legend.add(indiList.get(j).getIndi_name());
						showType.add(sType);
//						BarEntity be=bt.getOption(id,title,dataXX,dataVV);
//						beList.add(be);
//						
//						
					}
				LineAndBarEntity lbe=lbt.getOption(id,title,dataXX,legend,dataVV,showType);
//					beList.add(be);
					TotalList.add(lbe);
					
				}break;
				case "双X轴折线图":{
					List dataXX=new ArrayList();
					List dataVV=new ArrayList();
					List legend=new ArrayList();
					LineType dxt = new LineType();	
//					List<LineEntity> leList = new ArrayList<LineEntity>();
				    for(int j=0;j<indiList.size();j++) {

					cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
					cmap.put("time_point", indiList.get(j).getTime_point());
//					Map dFreq=(Map) listTimeCondition.get(0);
					
					cmap.put("startTime",sTime+OldFreq.get(0));
					cmap.put("endTime",eTime+OldFreq.get(0));
					cmap.put("freq_code",OldFreq.get(0));
//					cmap.put("endTime","201804");
					List<indi_TF> indiInfoList=specialInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
					
					List dataX=new ArrayList();//折线图X轴的数据
					List dataV=new ArrayList();//指标数据
					for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
						dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
						dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
						
					}	
					dataXX.add(dataX);
					dataVV.add(dataV);
					legend.add(indiList.get(j).getIndi_name());
					
//						LineEntity le=lt.getOption(id,title,dataX,dataV);//根据板块id，板块名称，X轴，指标值 来实现返回的json数据格式		
//						leList.add(le);
						
					
				}
				LineEntity dxe=dxt.getOption(id,title,dataXX,legend,dataVV);
//				leList.add(le);
				TotalList.add(dxe);
					
				}break;
				case "柱状堆叠折线图":{
					List dataXX=new ArrayList();
					List dataVV=new ArrayList();
					List legend=new ArrayList();
					List showType=new ArrayList();
//					LineType lt = new LineType();
					BarStackLineType bslt=new BarStackLineType();
					for(int j=0;j<indiList.size();j++) {
//						BarType bt=new BarType();
						cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
						cmap.put("time_point", indiList.get(j).getTime_point());
						cmap.put("startTime",sTime+OldFreq.get(0));
						cmap.put("endTime",eTime+OldFreq.get(0));
						cmap.put("freq_code",OldFreq.get(0));
						List<indi_TF> indiInfoList=specialInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
						String sType=specialInfoService.getIndiShowType(indiList.get(j).getIndi_id());
						List dataX=new ArrayList();
						List dataV=new ArrayList();
//						List dataXX=new ArrayList();
//						List dataVV=new ArrayList();
						for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
							dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
							dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
//							
						}
						dataXX.add(dataX);
						dataVV.add(dataV);
						legend.add(indiList.get(j).getIndi_name());
						showType.add(sType);
//						BarEntity be=bt.getOption(id,title,dataXX,dataVV);
//						beList.add(be);
//						
//						
					}
				BarStackLineEntity bsle=bslt.getOption(id,title,dataXX,legend,dataVV,showType);
//					beList.add(be);
					TotalList.add(bsle);
					
				}break;
				case "柱状堆叠图":{
					List dataXX=new ArrayList();
					List dataVV=new ArrayList();
					List legend=new ArrayList();
					BarStoreType bst = new BarStoreType();	
//					List<LineEntity> leList = new ArrayList<LineEntity>();
				for(int j=0;j<indiList.size();j++) {

					cmap.put("indi_code", indiList.get(j).getIndi_id());//获取当前指标对象的指标代码
					cmap.put("time_point", indiList.get(j).getTime_point());
//					Map dFreq=(Map) listTimeCondition.get(0);
					
					cmap.put("startTime",sTime+OldFreq.get(0));
					cmap.put("endTime",eTime+OldFreq.get(0));
					cmap.put("freq_code",OldFreq.get(0));
//					cmap.put("endTime","201804");
					List<indi_TF> indiInfoList=specialInfoService.getIndiInfoByTime(cmap);//根据查询条件（开始时间，结束时间，指标代码，时点）查询indi_all（也就是同方的表）得到具体指标数据
					
					List dataX=new ArrayList();//折线图X轴的数据
					List dataV=new ArrayList();//指标数据
					for(int m=0;m<indiInfoList.size();m++) {//循环指标列表
						dataX.add(indiInfoList.get(m).getDate_code().substring(0, 6));//获取X轴的数据，因为数据库里的数据为“201801SS”这样类似的，而APP展示的时候仅需要“201801”，所以需要对字符串进行分割
						dataV.add(indiInfoList.get(m).getIndi_value());//将对应日期的指标值加入到list中
						
					}	
					dataXX.add(dataX);
					dataVV.add(dataV);
					legend.add(indiList.get(j).getIndi_name());
					
//						LineEntity le=lt.getOption(id,title,dataX,dataV);//根据板块id，板块名称，X轴，指标值 来实现返回的json数据格式		
//						leList.add(le);
						
					
				}
				BarStoreEntity bse=bst.getOption(id,title,dataXX,legend,dataVV);
//				leList.add(le);
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
						cmap.put("startTime",sTime+OldFreq.get(0));
						cmap.put("endTime",eTime+OldFreq.get(0));
						cmap.put("freq_code",OldFreq.get(0));
						List<indi_TF> indiInfoList=specialInfoService.getIndiInfoByTime(cmap);
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
		
//		//获取相关指标
//		List<IndiCorrelative> icList = specialInfoService.getIndiCorrelative(indexId);
//		List listRelative= new ArrayList();
//		System.out.println("the length"+icList.size());
//		for(int i=0;i<icList.size();i++) {
//			
//			
//			Map map2 = new HashMap();
//			map2.put("indi_id", icList.get(i).getIndi_id());//存放相关指标的id
//			map2.put("indi_name", icList.get(i).getIndi_name());//存放相关指标的名称
//			listRelative.add(map2);//循环添加到列表中
//		}
//		
		Map mapAll=new HashMap();
//		mapAll.put("timeCondition",listTimeCondition);//初始化的时间信息
//		mapAll.put("classInfo", leList);//板块信息
		mapAll.put("classInfo",TotalList);
//		mapAll.put("relatedData", listRelative);//相关指标信息
		String  param= JSON.toJSONString(mapAll, SerializerFeature.DisableCircularReferenceDetect);
		return param;
	
	
	}

}
