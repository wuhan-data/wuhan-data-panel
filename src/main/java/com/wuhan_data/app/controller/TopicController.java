package com.wuhan_data.app.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.app.service.TopicService;
import com.wuhan_data.app.showType.TopicBarType;
import com.wuhan_data.app.showType.TopicLineAndBarType;
import com.wuhan_data.app.showType.TopicLineType;
import com.wuhan_data.app.showType.pojo.BarEntity;
import com.wuhan_data.app.showType.pojo.CardEntity;
import com.wuhan_data.app.showType.pojo.LineAndBarEntity;
import com.wuhan_data.app.showType.pojo.LineEntity;
import com.wuhan_data.pojo.Plate;
import com.wuhan_data.pojo.indi_TF;


@Controller
public class TopicController {


	@Autowired
	TopicService topicService;	
	
//	高质量发展指数（季度）

		@RequestMapping(value="topic1",produces = "text/plain;charset=utf-8")
		@ResponseBody
	    public String topic1(HttpServletRequest request, 
	            HttpServletResponse response) throws UnsupportedEncodingException{
			System.out.println("拦截到了" + request.getRequestURL());
			 response.addHeader("Access-Control-Allow-Origin", "*");
	          response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	          response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
	          response.addHeader("Access-Control-Max-Age", "1800");//30 min
			
			Map<String,Object> map = new HashMap<String,Object>();//最外层map
			map.put("errCode", "0");//错误码
			map.put("errMsg", "success");//错误消息--成功
			
			Plate plate = new Plate(1, "高质量发展"); //创建专题信息（Id，title）
			
			Map<String,Object> map1 = new HashMap<String,Object>();
			map1.put("baseInfo", plate);//baseInfo为专题基本信息
//第一部分			
			List<indi_TF> list1_1 = topicService.getValue1_1();//高质量发展指数
			List<String> dataX = new ArrayList<String>();//X轴数据（时间轴）
			List<List<Float>> dataV = new ArrayList<List<Float>>();//指标值
			List<Float> dataVv = new ArrayList<Float>();//指标值
			for(int i=0;i<list1_1.size();i++) {
				dataX.add(list1_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
				dataVv.add(Float.parseFloat(list1_1.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
			}
			dataV.add(dataVv);
			List<String> legendData1 = new ArrayList<String>();//图例列表（指标名称）
			legendData1.add(list1_1.get(0).getIndi_name());//
			TopicLineType lineType = new TopicLineType();
			LineEntity lineEntity1 = lineType.getOption("1", "湖北省高质量发展指数变化趋势", dataX, legendData1, dataV,60,140);
//第二部分			
			String cardText = topicService.getTopic1Card1_1()+";"+topicService.getTopic1Card1_2()+";"+topicService.getTopic1Card1_3()+";"+topicService.getTopic1Card1_4()+"。";
			CardEntity cardEntity = new CardEntity("2", "发展质效分析", "card", cardText);
		
//第三部分		
			List<String> showType = new ArrayList<String>();
			List<indi_TF> list2_1 = topicService.getValue2_1();//税收收入占地方一般公共预算收入比重
			List<indi_TF> list2_2 = topicService.getValue2_2();//金融机构存贷比
			List<indi_TF> list2_3 = topicService.getValue2_3();//规模以上工业企业利润率
			List<indi_TF> list2_4 = topicService.getValue2_4();//居民人均可支配收入
			showType.add("line");//税收收入占地方一般公共预算收入比重
			showType.add("line");//金融机构存贷比
			showType.add("line");//规模以上工业企业利润率
			showType.add("bar");//居民人均可支配收入
			
			List<String> dataX2 = new ArrayList<String>();
			List<List<Float>> dataV2 = new ArrayList<List<Float>>();
			List<Float> dataVV2_1 = new ArrayList<Float>();
			List<Float> dataVV2_2 = new ArrayList<Float>();
			List<Float> dataVV2_3 = new ArrayList<Float>();
			List<Float> dataVV2_4 = new ArrayList<Float>();
			for(int i=0;i<list2_1.size();i++) {
				dataX2.add(list2_1.get(i).getDate_code());
				dataVV2_1.add(Float.parseFloat(list2_1.get(i).getIndi_value()));	
			}
			for(int i=0;i<list2_2.size();i++) {
				dataVV2_2.add(Float.parseFloat(list2_2.get(i).getIndi_value()));	
			}
			for(int i=0;i<list2_3.size();i++) {
				dataVV2_3.add(Float.parseFloat(list2_3.get(i).getIndi_value()));	
			}
			for(int i=0;i<list2_4.size();i++) {
				dataVV2_4.add(Float.parseFloat(list2_4.get(i).getIndi_value()));	
			}
			dataV2.add(dataVV2_1);
			dataV2.add(dataVV2_2);
			dataV2.add(dataVV2_3);
			dataV2.add(dataVV2_4);
			
			List<String> legendData2 = new ArrayList<String>();
			legendData2.add(list2_1.get(0).getIndi_name());
			legendData2.add(list2_2.get(0).getIndi_name());
			legendData2.add(list2_3.get(0).getIndi_name());
			legendData2.add(list2_4.get(0).getIndi_name());
			
			TopicLineAndBarType topicLineAndBarType = new TopicLineAndBarType();
			LineAndBarEntity lineAndBarEntity2 = topicLineAndBarType.getOption("3", "湖北省规模以上工业企业利润率变化趋势", dataX2, legendData2, dataV2, showType);
//第四部分			
			String cardText2 = topicService.getTopic1Card2_1()+"。";
			CardEntity cardEntity2 = new CardEntity("4", "风险防控", "card", cardText2);
//第五部分			
			List<indi_TF> list3_1 = topicService.getValue3_1();
			List<String> dataX3 = new ArrayList<String>();
			List<List<Float>> dataV3 = new ArrayList<List<Float>>();
			List<Float> dataVv3 = new ArrayList<Float>();
			for(int i=0;i<list3_1.size();i++) {
				dataX3.add(list3_1.get(i).getDate_code());
				dataVv3.add(Float.parseFloat(list3_1.get(i).getIndi_value()));
			}
			dataV3.add(dataVv3);
			List<String> legendData3 = new ArrayList<String>();
			legendData3.add(list3_1.get(0).getIndi_name());
			TopicLineType lineType3 = new TopicLineType();
			LineEntity lineEntity3 = lineType3.getOption("5", "湖北省企业资产负债率趋势", dataX3, legendData3, dataV3,40,80);
//第6部分		
			String cardText3 = topicService.getTopic1Card3_1()+"。";
			CardEntity cardEntity3 = new CardEntity("6", "风险防控", "card", cardText3);

//第7部分	
			List<indi_TF> list4_1 = topicService.getValue4_1();
			List<String> dataX4 = new ArrayList<String>();
			List<List<Float>> dataV4 = new ArrayList<List<Float>>();
			List<Float> dataVv4 = new ArrayList<Float>();
			for(int i=0;i<list4_1.size();i++) {
				dataX4.add(list4_1.get(i).getDate_code());
				dataVv4.add(Float.parseFloat(list4_1.get(i).getIndi_value()));
			}
			dataV4.add(dataVv4);
			List<String> legendData4 = new ArrayList<String>();
			legendData4.add(list4_1.get(0).getIndi_name());
			TopicLineType lineType4 = new TopicLineType();
			LineEntity lineEntity4 = lineType4.getOption("7", "湖北省企业资产负债率趋势", dataX4, legendData4, dataV4,80,500);
//第8部分		
			String cardText4 = topicService.getTopic1Card4_1()+"。";
			CardEntity cardEntity4 = new CardEntity("8", "产业转型升级分析", "card", cardText4);
//第9部分		
			List<indi_TF> list5_1 = topicService.getValue5_1();
			List<String> dataX5 = new ArrayList<String>();
			List<List<Float>> dataV5 = new ArrayList<List<Float>>();
			List<Float> dataVv5 = new ArrayList<Float>();
			for(int i=0;i<list5_1.size();i++) {
				dataX5.add(list5_1.get(i).getDate_code());
				dataVv5.add(Float.parseFloat(list5_1.get(i).getIndi_value()));
			}
			dataV5.add(dataVv5);
			List<String> legendData5 = new ArrayList<String>();
			legendData5.add(list5_1.get(0).getIndi_name());
			TopicLineType lineType5 = new TopicLineType();
			LineEntity lineEntity5 = lineType5.getOption("9", "湖北产业转型升级", dataX5, legendData5, dataV5,30,70);
//第10部分
			String cardText5 = topicService.getTopic1Card5_1();
			CardEntity cardEntity5 = new CardEntity("10", "新经济发展分析", "card", cardText5);
//第11部分			
			List<indi_TF> list6_1 = topicService.getValue6_1();
			List<String> dataX6 = new ArrayList<String>();
			List<List<Float>> dataV6 = new ArrayList<List<Float>>();
			List<Float> dataVv6 = new ArrayList<Float>();
			for(int i=0;i<list6_1.size();i++) {
				dataX6.add(list6_1.get(i).getDate_code());
				dataVv6.add(Float.parseFloat(list6_1.get(i).getIndi_value()));
			}
			dataV6.add(dataVv6);
			List<String> legendData6 = new ArrayList<String>();
			legendData6.add(list6_1.get(0).getIndi_name());
			TopicLineType lineType6 = new TopicLineType();
			LineEntity lineEntity6 = lineType6.getOption("11", "湖北省新经济发展变化情况", dataX6, legendData6, dataV6,1000,8000);
			
			List<Object> classInfoList = new ArrayList<Object>();
			classInfoList.add(lineEntity1);//图1
			classInfoList.add(cardEntity);
			classInfoList.add(lineAndBarEntity2);//图2
			classInfoList.add(cardEntity2);
			classInfoList.add(lineEntity3);//图3
			classInfoList.add(cardEntity3);
			classInfoList.add(lineEntity4);//图4
			classInfoList.add(cardEntity4);
			classInfoList.add(lineEntity5);//图5
			classInfoList.add(cardEntity5);
			classInfoList.add(lineEntity6);//图6
			
			map1.put("classInfo", classInfoList);
			map.put("data", map1);
			String  param= JSON.toJSONString(map); 
	        return param;
	    }
	
//		宏观经济
			@RequestMapping(value="topic2",produces = "text/plain;charset=utf-8")
			@ResponseBody
		    public String topic2(HttpServletRequest request, 
		            HttpServletResponse response) throws UnsupportedEncodingException{
				Map<String,Object> map = new HashMap<String,Object>();//最外层map
				map.put("errCode", "0");//错误码
				map.put("errMsg", "success");//错误消息--成功
				
				Plate plate = new Plate(2, "宏观经济"); //创建专题信息（Id，title）
				
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("baseInfo", plate);//baseInfo为专题基本信息
	//第一部分-图表1						
				List<indi_TF> list1_1 = topicService.getTopic2Value1_1();
				List<String> dataX = new ArrayList<String>();//X轴数据（时间轴）
				List<List<Float>> dataV = new ArrayList<List<Float>>();//指标值
				List<Float> dataV_1 = new ArrayList<Float>();//指标值
				for(int i=0;i<list1_1.size();i++) {
					dataX.add(list1_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
					dataV_1.add(Float.parseFloat(list1_1.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
				}
				dataV.add(dataV_1);
				List<String> legendData1 = new ArrayList<String>();//图例列表（指标名称）
				legendData1.add(list1_1.get(0).getIndi_name());//
				TopicLineType lineType = new TopicLineType();
				LineEntity lineEntity1 = lineType.getOption("1", "湖北省宏观预警指数变化趋势", dataX, legendData1, dataV,0,100);
	
	//第二部分-文字1
				String cardText1 = topicService.getTopic2Card1_1();
				CardEntity cardEntity1 = new CardEntity("2", "宏观经济分析", "card", cardText1);
	
	//第三部分-图表2				
				List<indi_TF> list2_1 = topicService.getTopic2Value2_1();
				List<indi_TF> list2_2 = topicService.getTopic2Value2_2();
				List<String> dataX2 = new ArrayList<String>();//X轴数据（时间轴）
				List<List<Float>> dataV2 = new ArrayList<List<Float>>();//指标值
				List<Float> dataV2_1 = new ArrayList<Float>();
				List<Float> dataV2_2 = new ArrayList<Float>();
				for(int i=0;i<list2_1.size();i++) {
					dataX2.add(list2_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
					dataV2_1.add(Float.parseFloat(list2_1.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
				}
				for(int i=0;i<list2_2.size();i++) {
					dataV2_2.add(Float.parseFloat(list2_2.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
				}
				dataV2.add(dataV2_1);
				dataV2.add(dataV2_2);
				List<String> legendData2 = new ArrayList<String>();//图例列表（指标名称）
				legendData2.add(list2_1.get(0).getIndi_name()+"-湖北省");//
				legendData2.add(list2_2.get(0).getIndi_name()+"-全国");//
				TopicLineType lineType2 = new TopicLineType();
				LineEntity lineEntity2 = lineType2.getOption("3", "地区生产总值运行趋势", dataX2, legendData2, dataV2,0,100);
				
	//第四部分-文字2
				String cardText2 = topicService.getTopic2Card2_1();
				CardEntity cardEntity2 = new CardEntity("4", "经济发展质量分析", "card", cardText2);
	
	//第五部分-图表3
				List<indi_TF> list3_1 = topicService.getTopic2Value3_1();
				List<String> dataX3 = new ArrayList<String>();//X轴数据（时间轴）
				List<List<Float>> dataV3 = new ArrayList<List<Float>>();//指标值
				List<Float> dataV_3 = new ArrayList<Float>();//指标值
				for(int i=0;i<list3_1.size();i++) {
					dataX3.add(list3_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
					dataV_3.add(Float.parseFloat(list3_1.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
				}
				dataV3.add(dataV_3);
				List<String> legendData3 = new ArrayList<String>();//图例列表（指标名称）
				legendData3.add(list3_1.get(0).getIndi_name());//
				TopicLineType lineType3 = new TopicLineType();
				LineEntity lineEntity3 = lineType3.getOption("5", "税收收入占地方一般公共预算收入比重变化", dataX3, legendData3, dataV3,0,100);
				
	//第6部分-文字3
				String cardText3 = topicService.getTopic2Card3_1()+topicService.getTopic2Card3_2()+topicService.getTopic2Card3_3();
				CardEntity cardEntity3 = new CardEntity("6", "经济发展质量分析", "card", cardText3);
	
				
	//第7部分-图表4
				List<indi_TF> list4_1 = topicService.getTopic2Value4_1();
				List<indi_TF> list4_2 = topicService.getTopic2Value4_2();
				List<indi_TF> list4_3 = topicService.getTopic2Value4_3();				
				List<String> dataX4 = new ArrayList<String>();//X轴数据（时间轴）
				List<List<Float>> dataV4 = new ArrayList<List<Float>>();//指标值				
				List<Float> dataV4_1 = new ArrayList<Float>();//指标值
				List<Float> dataV4_2 = new ArrayList<Float>();//指标值
				List<Float> dataV4_3 = new ArrayList<Float>();//指标值
				for(int i=0;i<list4_1.size();i++) {
					dataX4.add(list4_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
					dataV4_1.add(Float.parseFloat(list4_1.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
				}
				for(int i=0;i<list4_2.size();i++) {
					dataV4_2.add(Float.parseFloat(list4_2.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
				}
				for(int i=0;i<list4_3.size();i++) {
					dataV4_3.add(Float.parseFloat(list4_3.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
				}
				dataV4.add(dataV4_1);
				dataV4.add(dataV4_2);
				dataV4.add(dataV4_3);
				List<String> legendData4 = new ArrayList<String>();//图例列表（指标名称）
				legendData4.add(list4_1.get(0).getIndi_name());//
				legendData4.add(list4_2.get(0).getIndi_name());//
				legendData4.add(list4_3.get(0).getIndi_name());//
				TopicLineType lineType4 = new TopicLineType();
				LineEntity lineEntity4 = lineType4.getOption("7", "湖北省三大需求增速走势变动", dataX4, legendData4, dataV4,0,100);
	
	//第8部分-文字4
				
				String cardText4 = topicService.getTopic2Card4_1()+topicService.getTopic2Card4_2();
				CardEntity cardEntity4 = new CardEntity("8", "需求对经济的拉动作用分析", "card", cardText4);
				
	//第9部分-图表5
				List<indi_TF> list5_1 = topicService.getTopic2Value5_1();
				List<indi_TF> list5_2 = topicService.getTopic2Value5_2();		
				List<String> dataX5 = new ArrayList<String>();//X轴数据（时间轴）
				List<List<Float>> dataV5 = new ArrayList<List<Float>>();//指标值				
				List<Float> dataV5_1 = new ArrayList<Float>();//指标值
				List<Float> dataV5_2 = new ArrayList<Float>();//指标值
				for(int i=0;i<list5_1.size();i++) {
					dataX5.add(list5_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
					dataV5_1.add(Float.parseFloat(list5_1.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
				}
				for(int i=0;i<list5_2.size();i++) {
					dataV5_2.add(Float.parseFloat(list5_2.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
				}

				dataV5.add(dataV5_1);
				dataV5.add(dataV5_2);
				List<String> legendData5 = new ArrayList<String>();//图例列表（指标名称）
				legendData5.add(list5_1.get(0).getIndi_name());//
				legendData5.add(list5_2.get(0).getIndi_name());//
				TopicLineType lineType5 = new TopicLineType();
				LineEntity lineEntity5 = lineType5.getOption("9", "湖北省三大需求增速走势变动", dataX5, legendData5, dataV5,0,100);
	
	 //第10部分-文字5
				String cardText5 = topicService.getTopic2Card5_1()+topicService.getTopic2Card5_2();
				CardEntity cardEntity5 = new CardEntity("10", "需求对经济的拉动作用分析", "card", cardText5);
		
	//第11部分-图表6
				List<indi_TF> list6_1 = topicService.getTopic2Value6_1();
				List<indi_TF> list6_2 = topicService.getTopic2Value6_2();		
				List<String> dataX6 = new ArrayList<String>();//X轴数据（时间轴）
				List<List<Float>> dataV6 = new ArrayList<List<Float>>();//指标值				
				List<Float> dataV6_1 = new ArrayList<Float>();//指标值
				List<Float> dataV6_2 = new ArrayList<Float>();//指标值
				for(int i=0;i<list6_1.size();i++) {
					dataX6.add(list6_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
					dataV6_1.add(Float.parseFloat(list6_1.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
				}
				for(int i=0;i<list6_2.size();i++) {
					dataV6_2.add(Float.parseFloat(list6_2.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
				}

				dataV6.add(dataV6_1);
				dataV6.add(dataV6_2);
				List<String> legendData6 = new ArrayList<String>();//图例列表（指标名称）
				legendData6.add(list6_1.get(0).getIndi_name());//
				legendData6.add(list6_2.get(0).getIndi_name());//
				TopicLineType lineType6 = new TopicLineType();
				LineEntity lineEntity6 = lineType6.getOption("11", "湖北省三大产业对GDP的拉动", dataX6, legendData6, dataV6,0,100);
	
	//第12部分-文字6		
				String cardText6 = topicService.getTopic2Card6_1();
				CardEntity cardEntity6 = new CardEntity("12", "经济效益分析1", "card", cardText6);
			
	//第13部分-图表7
				List<String> showType7 = new ArrayList<String>();
				List<indi_TF> list7_1 = topicService.getTopic2Value7_1();
				List<indi_TF> list7_2 = topicService.getTopic2Value7_2();
				showType7.add("bar");
				showType7.add("line");
				
				List<String> dataX7 = new ArrayList<String>();
				List<List<Float>> dataV7 = new ArrayList<List<Float>>();
				List<Float> dataVV7_1 = new ArrayList<Float>();
				List<Float> dataVV7_2 = new ArrayList<Float>();
				for(int i=0;i<list7_1.size();i++) {
					dataX7.add(list7_1.get(i).getDate_code());
					dataVV7_1.add(Float.parseFloat(list7_1.get(i).getIndi_value()));	
				}
				for(int i=0;i<list7_2.size();i++) {
					dataVV7_2.add(Float.parseFloat(list7_2.get(i).getIndi_value()));	
				}
			
				dataV7.add(dataVV7_1);
				dataV7.add(dataVV7_2);

				
				List<String> legendData7 = new ArrayList<String>();
				legendData7.add(list7_1.get(0).getIndi_name()+"-累计值");
				legendData7.add(list7_2.get(0).getIndi_name()+"-增速");

				
				TopicLineAndBarType topicLineAndBarType7 = new TopicLineAndBarType();
				LineAndBarEntity lineAndBarEntity7 = topicLineAndBarType7.getOption("13", "湖北省居民部门经济效益", dataX7, legendData7, dataV7, showType7);
				
	//第14部分-文字7
				String cardText7 = topicService.getTopic2Card7_1();
				CardEntity cardEntity7 = new CardEntity("14", "经济效益分析2", "card", cardText7);
				
	//第15部分-图表8
				List<String> showType8 = new ArrayList<String>();
				List<indi_TF> list8_1 = topicService.getTopic2Value8_1();
				List<indi_TF> list8_2 = topicService.getTopic2Value8_2();
				showType8.add("bar");
				showType8.add("line");
				
				List<String> dataX8 = new ArrayList<String>();
				List<List<Float>> dataV8 = new ArrayList<List<Float>>();
				List<Float> dataVV8_1 = new ArrayList<Float>();
				List<Float> dataVV8_2 = new ArrayList<Float>();
				for(int i=0;i<list8_1.size();i++) {
					dataX8.add(list8_1.get(i).getDate_code());
					dataVV8_1.add(Float.parseFloat(list8_1.get(i).getIndi_value()));	
				}
				for(int i=0;i<list8_2.size();i++) {
					dataVV8_2.add(Float.parseFloat(list8_2.get(i).getIndi_value()));	
				}
			
				dataV8.add(dataVV8_1);
				dataV8.add(dataVV8_2);

				
				List<String> legendData8 = new ArrayList<String>();
				legendData8.add(list8_1.get(0).getIndi_name()+"-累计值");
				legendData8.add(list8_2.get(0).getIndi_name()+"-增速");

				
				TopicLineAndBarType topicLineAndBarType8 = new TopicLineAndBarType();
				LineAndBarEntity lineAndBarEntity8 = topicLineAndBarType8.getOption("15", "湖北省企业部门经济效益", dataX8, legendData8, dataV8, showType8);
				
	//第16部分-文字8
				String cardText8 = topicService.getTopic2Card8_1();
				CardEntity cardEntity8 = new CardEntity("16", "经济效益分析3", "card", cardText8);	
	//第17部分-图表9
				List<String> showType9 = new ArrayList<String>();
				List<indi_TF> list9_1 = topicService.getTopic2Value9_1();
				List<indi_TF> list9_2 = topicService.getTopic2Value9_2();
				showType9.add("bar");
				showType9.add("line");
				
				List<String> dataX9 = new ArrayList<String>();
				List<List<Float>> dataV9 = new ArrayList<List<Float>>();
				List<Float> dataVV9_1 = new ArrayList<Float>();
				List<Float> dataVV9_2 = new ArrayList<Float>();
				for(int i=0;i<list9_1.size();i++) {
					dataX9.add(list9_1.get(i).getDate_code());
					dataVV9_1.add(Float.parseFloat(list9_1.get(i).getIndi_value()));	
				}
				for(int i=0;i<list9_2.size();i++) {
					dataVV9_2.add(Float.parseFloat(list9_2.get(i).getIndi_value()));	
				}
			
				dataV9.add(dataVV9_1);
				dataV9.add(dataVV9_2);

				
				List<String> legendData9 = new ArrayList<String>();
				legendData9.add(list9_1.get(0).getIndi_name()+"-累计值");
				legendData9.add(list9_2.get(0).getIndi_name()+"-增速");

				
				TopicLineAndBarType topicLineAndBarType9 = new TopicLineAndBarType();
				LineAndBarEntity lineAndBarEntity9 = topicLineAndBarType9.getOption("17", "湖北省政府部门经济效益", dataX9, legendData9, dataV9, showType9);
				
				
				List<Object> classInfoList = new ArrayList<Object>();
				classInfoList.add(lineEntity1);//图1
				classInfoList.add(cardEntity1);
				classInfoList.add(lineEntity2);//图2
				classInfoList.add(cardEntity2);
				classInfoList.add(lineEntity3);//图3
				classInfoList.add(cardEntity3);
				classInfoList.add(lineEntity4);//图4
				classInfoList.add(cardEntity4);
				classInfoList.add(lineEntity5);//图5
				classInfoList.add(cardEntity5);
				classInfoList.add(lineEntity6);//图6
				
				classInfoList.add(cardEntity6);
				classInfoList.add(lineAndBarEntity7);//图7
				classInfoList.add(cardEntity7);
				classInfoList.add(lineAndBarEntity8);//图8
				classInfoList.add(cardEntity8);
				classInfoList.add(lineAndBarEntity9);//图9
				
				map1.put("classInfo", classInfoList);
				map.put("data", map1);
				String  param= JSON.toJSONString(map); 
		        return param;
			}
//		产业经济
				@RequestMapping(value="topic3",produces = "text/plain;charset=utf-8")
				@ResponseBody
			    public String topic3(HttpServletRequest request, 
			            HttpServletResponse response) throws UnsupportedEncodingException{
					Map<String,Object> map = new HashMap<String,Object>();//最外层map
					map.put("errCode", "0");//错误码
					map.put("errMsg", "success");//错误消息--成功
					
					Plate plate = new Plate(3, "产业经济"); //创建专题信息（Id，title）
					
					Map<String,Object> map1 = new HashMap<String,Object>();
					map1.put("baseInfo", plate);//baseInfo为专题基本信息
		//第一部分-图表1						
					List<indi_TF> list1_1 = topicService.getTopic3Value1_1();
					List<String> dataX = new ArrayList<String>();//X轴数据（时间轴）
					List<List<Float>> dataV = new ArrayList<List<Float>>();//指标值
					List<Float> dataV_1 = new ArrayList<Float>();//指标值
					for(int i=0;i<list1_1.size();i++) {
						dataX.add(list1_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
						dataV_1.add(Float.parseFloat(list1_1.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
					}
					dataV.add(dataV_1);
					List<String> legendData1 = new ArrayList<String>();//图例列表（指标名称）
					legendData1.add(list1_1.get(0).getIndi_name());//
					TopicLineType lineType = new TopicLineType();
					LineEntity lineEntity1 = lineType.getOption("1", "湖北省工业预警指数变化趋势", dataX, legendData1, dataV,0,100);
			
		//第2部分-文字1
					String cardText1 = topicService.getTopic3Card1_1();
					CardEntity cardEntity1 = new CardEntity("2", "产业规模分析", "card", cardText1);
			
		//第3部分-图表2	
					List<indi_TF> list2_1 = topicService.getTopic3Value2_1();
					List<String> dataX2 = new ArrayList<String>();//X轴数据（时间轴）
					List<List<Float>> dataV2 = new ArrayList<List<Float>>();//指标值
					List<Float> dataV2_1 = new ArrayList<Float>();//指标值
					for(int i=0;i<list2_1.size();i++) {
						dataX2.add(list2_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
						dataV2_1.add(Float.parseFloat(list2_1.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
					}
					dataV2.add(dataV2_1);
					List<String> legendData2 = new ArrayList<String>();//图例列表（指标名称）
					legendData2.add(list2_1.get(0).getIndi_name());//
					TopicLineType lineType2 = new TopicLineType();
					LineEntity lineEntity2 = lineType2.getOption("3", "湖北省产业规模变化趋势", dataX2, legendData2, dataV2,0,100);
		
		//第4部分-文字2
					String cardText2 = topicService.getTopic3Card2_1()+topicService.getTopic3Card2_2();
					CardEntity cardEntity2 = new CardEntity("4", "重点产业效益分析", "card", cardText2);
					
					
		//第5部分-图表3							
					List<String> showType3 = new ArrayList<String>();
					List<indi_TF> list3_1 = topicService.getTopic3Value3_1();
					List<indi_TF> list3_2 = topicService.getTopic3Value3_2();
					List<indi_TF> list3_3 = topicService.getTopic3Value3_3();
					List<indi_TF> list3_4 = topicService.getTopic3Value3_4();
					List<indi_TF> list3_5 = topicService.getTopic3Value3_5();
					List<indi_TF> list3_6 = topicService.getTopic3Value3_6();
					showType3.add("bar");
					showType3.add("line");
					showType3.add("bar");
					showType3.add("line");
					showType3.add("line");
					showType3.add("line");
					
					List<String> dataX3 = new ArrayList<String>();
					List<List<Float>> dataV3 = new ArrayList<List<Float>>();
					List<Float> dataVV3_1 = new ArrayList<Float>();
					List<Float> dataVV3_2 = new ArrayList<Float>();
					List<Float> dataVV3_3 = new ArrayList<Float>();
					List<Float> dataVV3_4 = new ArrayList<Float>();
					List<Float> dataVV3_5 = new ArrayList<Float>();
					List<Float> dataVV3_6 = new ArrayList<Float>();
					for(int i=0;i<list3_1.size();i++) {
						dataX3.add(list3_1.get(i).getDate_code());
						dataVV3_1.add(Float.parseFloat(list3_1.get(i).getIndi_value()));	
					}
					for(int i=0;i<list3_2.size();i++) {
						dataVV3_2.add(Float.parseFloat(list3_2.get(i).getIndi_value()));	
					}
					for(int i=0;i<list3_3.size();i++) {
						dataVV3_3.add(Float.parseFloat(list3_3.get(i).getIndi_value()));	
					}
					for(int i=0;i<list3_4.size();i++) {
						dataVV3_4.add(Float.parseFloat(list3_4.get(i).getIndi_value()));	
					}
					for(int i=0;i<list3_5.size();i++) {
						dataVV3_5.add(Float.parseFloat(list3_5.get(i).getIndi_value()));	
					}
					for(int i=0;i<list3_6.size();i++) {
						dataVV3_6.add(Float.parseFloat(list3_6.get(i).getIndi_value()));	
					}
				
					dataV3.add(dataVV3_1);
					dataV3.add(dataVV3_2);
					dataV3.add(dataVV3_3);
					dataV3.add(dataVV3_4);
					dataV3.add(dataVV3_5);
					dataV3.add(dataVV3_6);

					
					List<String> legendData3 = new ArrayList<String>();
					legendData3.add(list3_1.get(0).getIndi_name());
					legendData3.add(list3_2.get(0).getIndi_name()+"-累计增速");
					legendData3.add(list3_3.get(0).getIndi_name());
					legendData3.add(list3_4.get(0).getIndi_name()+"-累计增速");
					legendData3.add(list3_5.get(0).getIndi_name());
					legendData3.add(list3_6.get(0).getIndi_name());

					
					TopicLineAndBarType topicLineAndBarType3 = new TopicLineAndBarType();
					LineAndBarEntity lineAndBarEntity3 = topicLineAndBarType3.getOption("5", "重点产业经济效益", dataX3, legendData3, dataV3, showType3);										
	
	//第6部分-文字3
					String cardText3 = topicService.getTopic3Card3_1();
					CardEntity cardEntity3 = new CardEntity("6", "产业环境分析（信息）", "card", cardText3);
	//第7部分-图表4
					List<indi_TF> list4_1 = topicService.getTopic3Value4_1();
					List<String> dataX4 = new ArrayList<String>();//X轴数据（时间轴）
					List<List<Float>> dataV4 = new ArrayList<List<Float>>();//指标值
					List<Float> dataV4_1 = new ArrayList<Float>();//指标值
					for(int i=0;i<list4_1.size();i++) {
						dataX4.add(list4_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
						dataV4_1.add(Float.parseFloat(list4_1.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
					}
					dataV4.add(dataV4_1);
					List<String> legendData4 = new ArrayList<String>();//图例列表（指标名称）
					legendData4.add(list4_1.get(0).getIndi_name());//
					TopicBarType barType4 = new TopicBarType();
					BarEntity barEntity4 = barType4.getOption("7", "互联网宽带接入用户数", dataX4, legendData4, dataV4);
	//第8部分-文字4
					String cardText4 = topicService.getTopic3Card4_1();
					CardEntity cardEntity4 = new CardEntity("8", "产业环境分析（资金）", "card", cardText4);
   //第9部分-图表5
					List<indi_TF> list5_1 = topicService.getTopic3Value5_1();
					List<String> dataX5 = new ArrayList<String>();//X轴数据（时间轴）
					List<List<Float>> dataV5 = new ArrayList<List<Float>>();//指标值
					List<Float> dataV5_1 = new ArrayList<Float>();//指标值
					for(int i=0;i<list5_1.size();i++) {
						dataX5.add(list5_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
						dataV5_1.add(Float.parseFloat(list5_1.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
					}
					dataV5.add(dataV5_1);
					List<String> legendData5 = new ArrayList<String>();//图例列表（指标名称）
					legendData5.add(list5_1.get(0).getIndi_name());//
					TopicBarType barType5 = new TopicBarType();
					BarEntity barEntity5 = barType5.getOption("9", "金融机构本外币贷款余额", dataX5, legendData5, dataV5);
		
										
					List<Object> classInfoList = new ArrayList<Object>();
					classInfoList.add(lineEntity1);//图1
					classInfoList.add(cardEntity1);
					classInfoList.add(lineEntity2);//图2
					classInfoList.add(cardEntity2);
					classInfoList.add(lineAndBarEntity3);//图3
					classInfoList.add(cardEntity3);
					classInfoList.add(barEntity4);//图4
					classInfoList.add(cardEntity4);
					classInfoList.add(barEntity5);//图5
					
	
					
					map1.put("classInfo", classInfoList);
					map.put("data", map1);
					String  param= JSON.toJSONString(map); 
			        return param;
				}
//		固定资产投资
				@RequestMapping(value="topic4",produces = "text/plain;charset=utf-8")
				@ResponseBody
			    public String topic4(HttpServletRequest request, 
			            HttpServletResponse response) throws UnsupportedEncodingException{
					Map<String,Object> map = new HashMap<String,Object>();//最外层map
					map.put("errCode", "0");//错误码
					map.put("errMsg", "success");//错误消息--成功
					
					Plate plate = new Plate(4, "固定资产投资"); //创建专题信息（Id，title）
					
					Map<String,Object> map1 = new HashMap<String,Object>();
					map1.put("baseInfo", plate);//baseInfo为专题基本信息
//第一部分-图表1		
					List<indi_TF> list1_1 = topicService.getTopic4Value1_1();
					List<String> dataX = new ArrayList<String>();//X轴数据（时间轴）
					List<List<Float>> dataV = new ArrayList<List<Float>>();//指标值
					List<Float> dataV_1 = new ArrayList<Float>();//指标值
					for(int i=0;i<list1_1.size();i++) {
						dataX.add(list1_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
						dataV_1.add(Float.parseFloat(list1_1.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
					}
					dataV.add(dataV_1);
					List<String> legendData1 = new ArrayList<String>();//图例列表（指标名称）
					legendData1.add(list1_1.get(0).getIndi_name());//
					TopicLineType lineType = new TopicLineType();
					LineEntity lineEntity1 = lineType.getOption("1", "湖北省投资先行指数变化趋势", dataX, legendData1, dataV,0,100);

//第2部分-文字1	
					String cardText1 = topicService.getTopic4Card1_1();
					CardEntity cardEntity1 = new CardEntity("2", "全省投资分析", "card", cardText1);
//第3部分-图表2
					List<String> showType2 = new ArrayList<String>();
					List<indi_TF> list2_1 = topicService.getTopic4Value2_1();
					List<indi_TF> list2_2 = topicService.getTopic4Value2_2();
					showType2.add("bar");
					showType2.add("line");
					
					List<String> dataX2 = new ArrayList<String>();
					List<List<Float>> dataV2 = new ArrayList<List<Float>>();
					List<Float> dataVV2_1 = new ArrayList<Float>();
					List<Float> dataVV2_2 = new ArrayList<Float>();
					for(int i=0;i<list2_1.size();i++) {
						dataX2.add(list2_1.get(i).getDate_code());
						dataVV2_1.add(Float.parseFloat(list2_1.get(i).getIndi_value()));	
					}
					for(int i=0;i<list2_2.size();i++) {
						dataVV2_2.add(Float.parseFloat(list2_2.get(i).getIndi_value()));	
					}
				
					dataV2.add(dataVV2_1);
					dataV2.add(dataVV2_2);

					
					List<String> legendData2 = new ArrayList<String>();
					legendData2.add(list2_1.get(0).getIndi_name()+"-累计值");
					legendData2.add(list2_2.get(0).getIndi_name()+"-增速");

					
					TopicLineAndBarType topicLineAndBarType2 = new TopicLineAndBarType();
					LineAndBarEntity lineAndBarEntity2 = topicLineAndBarType2.getOption("3", "湖北省固定资产投资变化趋势", dataX2, legendData2, dataV2, showType2);
//第4部分-文字2
					String cardText2 = topicService.getTopic4Card2_1();
					CardEntity cardEntity2 = new CardEntity("4", "本年投资分析", "card", cardText2);
					
//第5部分-图表3
					List<String> showType3 = new ArrayList<String>();
					List<indi_TF> list3_1 = topicService.getTopic4Value3_1();
					List<indi_TF> list3_2 = topicService.getTopic4Value3_2();
					List<indi_TF> list3_3 = topicService.getTopic4Value3_3();
					List<indi_TF> list3_4 = topicService.getTopic4Value3_4();
					showType3.add("bar");
					showType3.add("line");
					showType3.add("bar");
					showType3.add("line");
					
					List<String> dataX3 = new ArrayList<String>();
					List<List<Float>> dataV3 = new ArrayList<List<Float>>();
					List<Float> dataVV3_1 = new ArrayList<Float>();
					List<Float> dataVV3_2 = new ArrayList<Float>();
					List<Float> dataVV3_3 = new ArrayList<Float>();
					List<Float> dataVV3_4 = new ArrayList<Float>();
					for(int i=0;i<list3_1.size();i++) {
						dataX3.add(list3_1.get(i).getDate_code());
						dataVV3_1.add(Float.parseFloat(list3_1.get(i).getIndi_value()));	
					}
					for(int i=0;i<list3_2.size();i++) {
						dataVV3_2.add(Float.parseFloat(list3_2.get(i).getIndi_value()));	
					}
					
					for(int i=0;i<list3_3.size();i++) {
						dataVV3_3.add(Float.parseFloat(list3_3.get(i).getIndi_value()));	
					}
					for(int i=0;i<list3_4.size();i++) {
						dataVV3_4.add(Float.parseFloat(list3_4.get(i).getIndi_value()));	
					}
				
					dataV3.add(dataVV3_1);
					dataV3.add(dataVV3_2);
					dataV3.add(dataVV3_3);
					dataV3.add(dataVV3_4);

					
					List<String> legendData3 = new ArrayList<String>();
					legendData3.add(list3_1.get(0).getIndi_name()+"-累计值");
					legendData3.add(list3_2.get(0).getIndi_name()+"-增速");
					legendData3.add(list3_3.get(0).getIndi_name()+"-累计值");
					legendData3.add(list3_4.get(0).getIndi_name()+"-增速");

					
					TopicLineAndBarType topicLineAndBarType3 = new TopicLineAndBarType();
					LineAndBarEntity lineAndBarEntity3 = topicLineAndBarType3.getOption("5", "湖北省项目个数变化趋势", dataX3, legendData3, dataV3, showType3);		

//第6部分-文字3
					String cardText3 = topicService.getTopic4Card3_1();
					CardEntity cardEntity3 = new CardEntity("6", "投资效益分析", "card", cardText3);

//第7部分-图表4
					
					List<indi_TF> list4_1 = topicService.getTopic4Value4_1();
					List<String> dataX4 = new ArrayList<String>();//X轴数据（时间轴）
					List<List<Float>> dataV4 = new ArrayList<List<Float>>();//指标值
					List<Float> dataV4_1 = new ArrayList<Float>();//指标值
					for(int i=0;i<list4_1.size();i++) {
						dataX4.add(list4_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
						dataV4_1.add(Float.parseFloat(list4_1.get(i).getIndi_value()));//从指标列表获取指标值 并存入dataV
					}
					dataV4.add(dataV4_1);
					List<String> legendData4 = new ArrayList<String>();//图例列表（指标名称）
					legendData4.add(list4_1.get(0).getIndi_name());//
					TopicLineType lineType4 = new TopicLineType();
					LineEntity lineEntity4 = lineType4.getOption("7", "湖北省投资效益系数变化趋势", dataX4, legendData4, dataV4,0,100);

//第8部分-文字4
					String cardText4 = topicService.getTopic4Card4_1();
					CardEntity cardEntity4 = new CardEntity("8", "投资环境", "card", cardText4);
					
//第9部分-图表5				
					List<String> showType5 = new ArrayList<String>();
					List<indi_TF> list5_1 = topicService.getTopic4Value51_1();
					List<indi_TF> list5_2 = topicService.getTopic4Value51_2();
					showType5.add("bar");
					showType5.add("line");
					
					List<String> dataX5 = new ArrayList<String>();
					List<List<Float>> dataV5 = new ArrayList<List<Float>>();
					List<Float> dataVV5_1 = new ArrayList<Float>();
					List<Float> dataVV5_2 = new ArrayList<Float>();
					for(int i=0;i<list5_1.size();i++) {
						dataX5.add(list5_1.get(i).getDate_code());
						dataVV5_1.add(Float.parseFloat(list5_1.get(i).getIndi_value()));	
					}
					for(int i=0;i<list5_2.size();i++) {
						dataVV5_2.add(Float.parseFloat(list5_2.get(i).getIndi_value()));	
					}

				
					dataV5.add(dataVV5_1);
					dataV5.add(dataVV5_2);


					
					List<String> legendData5 = new ArrayList<String>();
					legendData5.add(list5_1.get(0).getIndi_name());
					legendData5.add(list5_2.get(0).getIndi_name());

					
					TopicLineAndBarType topicLineAndBarType5 = new TopicLineAndBarType();
					LineAndBarEntity lineAndBarEntity5 = topicLineAndBarType5.getOption("9", "湖北省非公经济发展变化趋势", dataX5, legendData5, dataV5, showType5);		

					
					List<Object> classInfoList = new ArrayList<Object>();
					classInfoList.add(lineEntity1);//图1
					classInfoList.add(cardEntity1);
					classInfoList.add(lineAndBarEntity2);//图2
					classInfoList.add(cardEntity2);
					classInfoList.add(lineAndBarEntity3);//图3
					classInfoList.add(cardEntity3);
					classInfoList.add(lineEntity4);//图4
					classInfoList.add(cardEntity4);
					classInfoList.add(lineAndBarEntity5);//图5
					
					
					
					map1.put("classInfo", classInfoList);
					map.put("data", map1);
					String  param= JSON.toJSONString(map); 
			        return param;
				}
}
