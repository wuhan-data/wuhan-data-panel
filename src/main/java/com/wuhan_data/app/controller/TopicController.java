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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.app.service.TopicService;
import com.wuhan_data.app.showType.BarType;
import com.wuhan_data.app.showType.LineAndBarType;
import com.wuhan_data.app.showType.LineType;
import com.wuhan_data.app.showType.TopicBarType;
import com.wuhan_data.app.showType.TopicLineAndBarType;
import com.wuhan_data.app.showType.TopicLineType;
import com.wuhan_data.app.showType.pojo.BarEntity;
import com.wuhan_data.app.showType.pojo.CardEntity;
import com.wuhan_data.app.showType.pojo.LineAndBarEntity;
import com.wuhan_data.app.showType.pojo.LineEntity;
import com.wuhan_data.pojo.IndexSpecial;
import com.wuhan_data.pojo.Plate;
import com.wuhan_data.pojo.indi_TF;

import net.sf.json.JSONObject;


@Controller
public class TopicController {


	@Autowired
	TopicService topicService;	
	
	@RequestMapping(value="getTopicData",produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	 public String getTopicData(@RequestBody String json) throws UnsupportedEncodingException{

		 //从json中获得topicId
         JSONObject object= JSONObject.fromObject(json);
         int id=object.getInt("topicId");
		 Map<String,Object> map = new HashMap<String,Object>();//最外层map
		 Map<String,Object> mapBaseInfo = new HashMap<String,Object>();
		 
		 map.put("errCode", "0");//错误码
		 map.put("errMsg", "success");//错误消息--成功
		 
		 IndexSpecial indexSpecial = topicService.getTopicById(id);
		 Map<String,Object> topicPojoMap = new HashMap<String,Object>();
			topicPojoMap.put("indexId", indexSpecial.getId());
			topicPojoMap.put("indexName", indexSpecial.getTitle());
			topicPojoMap.put("showType", indexSpecial.getShow_type());
			topicPojoMap.put("file", indexSpecial.getFile());
		if(indexSpecial.getShow_type().equals("vis")) {	
		 switch(id) {
		 case 1: return topic1(topicPojoMap);
		 case 2: return topic2(topicPojoMap); 
		 case 3: return topic3(topicPojoMap);
		 case 4: return topic4(topicPojoMap);	
		 }
		}else {
			System.out.print("hha");
			mapBaseInfo.put("baseInfo", topicPojoMap);
			map.put("data", mapBaseInfo);
			String  param= JSON.toJSONString(map); 
	        return param;
		}		 
		 return null;
	 }
	
//	高质量发展指数（季度）

		@RequestMapping(value="topic1",produces = "text/plain;charset=utf-8")
		@ResponseBody
	    public String topic1(Map<String,Object> topicPojoMap) throws UnsupportedEncodingException{
			
			
			System.out.print("结束");
			
			Map<String,Object> map = new HashMap<String,Object>();//最外层map
			map.put("errCode", "0");//错误码
			map.put("errMsg", "success");//错误消息--成功
		
			Plate plate = new Plate(1, "高质量发展"); //创建专题信息（Id，title）
			
			Map<String,Object> map1 = new HashMap<String,Object>();
			map1.put("baseInfo", topicPojoMap);//baseInfo为专题基本信息
//第一部分			

			List<indi_TF> list1_1 = topicService.getValue1_1();//高质量发展指数
			List<String> dataX = new ArrayList<String>();//X轴数据（时间轴）
			List<List<String>> dataV = new ArrayList<List<String>>();//指标值
			List<String> showColor = new ArrayList<String>();
			List<String> showType1 = new ArrayList<String>();
			List<String> dataVv = new ArrayList<String>();//指标值
			for(int i=(list1_1.size()-1);i>=0;i--) {
				dataX.add(list1_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
				dataVv.add(list1_1.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
			}
			showType1.add("line");
			dataV.add(dataVv);
			List<String> legendData1 = new ArrayList<String>();//图例列表（指标名称）
			legendData1.add(list1_1.get(0).getIndi_name());//
			LineType lineType = new LineType();
			LineEntity lineEntity1 = lineType.getOption("1", "湖北省高质量发展指数变化趋势", dataX, legendData1, dataV,showColor,showType1);
//第二部分			
//			String cardText = 
//			"<li>"+topicService.getTopic1Card1_1()+";"+"</li>"
//			+"<li>"+topicService.getTopic1Card1_2()+";"+"</li>"
//			+"<li>"+topicService.getTopic1Card1_3()+";"+"</li>"
//			+"<li>"+topicService.getTopic1Card1_4()+"</li>";
			String cardText = topicService.getTopic1Card1_1()+topicService.getTopic1Card1_2()+topicService.getTopic1Card1_3()+topicService.getTopic1Card1_4();
			CardEntity cardEntity = new CardEntity("2", "发展质效分析", "card", cardText);
		
//第三部分		
			List<String> showType2 = new ArrayList<String>();
			List<String> showColor2 = new ArrayList<String>();
			List<indi_TF> list2_1 = topicService.getValue2_1();//税收收入占地方一般公共预算收入比重
			List<indi_TF> list2_2 = topicService.getValue2_2();//金融机构存贷比
			List<indi_TF> list2_3 = topicService.getValue2_3();//规模以上工业企业利润率
			List<indi_TF> list2_4 = topicService.getValue2_4();//居民人均可支配收入
			showType2.add("line");//税收收入占地方一般公共预算收入比重
			showType2.add("line");//金融机构存贷比
			showType2.add("line");//规模以上工业企业利润率
			showType2.add("bar");//居民人均可支配收入
			
			List<String> dataX2 = new ArrayList<String>();
			List<List<String>> dataV2 = new ArrayList<List<String>>();
			List<String> dataVV2_1 = new ArrayList<String>();
			List<String> dataVV2_2 = new ArrayList<String>();
			List<String> dataVV2_3 = new ArrayList<String>();
			List<String> dataVV2_4 = new ArrayList<String>();
			for(int i=(list2_1.size()-1);i>0;i--) {
				dataX2.add(list2_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
			}
			for(int i=(list2_1.size()-1);i>0;i--) {
				dataVV2_1.add(list2_1.get(i).getIndi_value());	
			}
			for(int i=(list2_2.size()-1);i>0;i--) {
				dataVV2_2.add(list2_2.get(i).getIndi_value());	
			}
			for(int i=(list2_3.size()-1);i>0;i--) {
				dataVV2_3.add(list2_3.get(i).getIndi_value());	
			}
			for(int i=(list2_4.size()-1);i>0;i--) {
				dataVV2_4.add(list2_4.get(i).getIndi_value());	
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
			
			LineAndBarType LineAndBarType = new LineAndBarType();
			LineAndBarEntity lineAndBarEntity2 = LineAndBarType.getOption("3", "湖北省规模以上工业企业利润率变化趋势", dataX2, legendData2, dataV2, showColor,showType2);
//第四部分			
			String cardText2 = topicService.getTopic1Card2_1();
			CardEntity cardEntity2 = new CardEntity("4", "风险防控", "card", cardText2);
//第五部分			
			List<indi_TF> list3_1 = topicService.getValue3_1();
			List<String> showColor3 = new ArrayList<String>();
			List<String> showType3 = new ArrayList<String>();
			List<String> dataX3 = new ArrayList<String>();
			List<List<String>> dataV3 = new ArrayList<List<String>>();
			List<String> dataVv3 = new ArrayList<String>();
			for(int i=(list3_1.size()-1);i>0;i--) {
				dataX3.add(list3_1.get(i).getDate_code());
				dataVv3.add(list3_1.get(i).getIndi_value());
			}
			dataV3.add(dataVv3);
			showType3.add("line");
			List<String> legendData3 = new ArrayList<String>();
			legendData3.add(list3_1.get(0).getIndi_name());
			LineType lineType3 = new LineType();
			LineEntity lineEntity3 = lineType3.getOption("5", "湖北省企业资产负债率趋势", dataX3, legendData3, dataV3,showColor3,showType3);
//第6部分		
			String cardText3 = topicService.getTopic1Card3_1();
			CardEntity cardEntity3 = new CardEntity("6", "风险防控", "card", cardText3);

//第7部分	
			List<indi_TF> list4_1 = topicService.getValue4_1();
			List<String> showColor4 = new ArrayList<String>();
			List<String> showType4 = new ArrayList<String>();
			List<String> dataX4 = new ArrayList<String>();
			List<List<String>> dataV4 = new ArrayList<List<String>>();
			List<String> dataVv4 = new ArrayList<String>();
			for(int i=(list4_1.size()-1);i>0;i--) {
				dataX4.add(list4_1.get(i).getDate_code());
				dataVv4.add(list4_1.get(i).getIndi_value());
			}
			dataV4.add(dataVv4);
			showType4.add("line");
			List<String> legendData4 = new ArrayList<String>();
			legendData4.add(list4_1.get(0).getIndi_name());
			LineType lineType4 = new LineType();
			LineEntity lineEntity4 = lineType4.getOption("7", "湖北省企业资产负债率趋势", dataX4, legendData4, dataV4,showColor4,showType4);
//第8部分		
			String cardText4 = topicService.getTopic1Card4_1();
			CardEntity cardEntity4 = new CardEntity("8", "产业转型升级分析", "card", cardText4);
//第9部分		
			List<indi_TF> list5_1 = topicService.getValue5_1();
			List<String> showColor5 = new ArrayList<String>();
			List<String> showType5 = new ArrayList<String>();
			List<String> dataX5 = new ArrayList<String>();
			List<List<String>> dataV5 = new ArrayList<List<String>>();
			List<String> dataVv5 = new ArrayList<String>();
			for(int i=(list5_1.size()-1);i>0;i--) {
				dataX5.add(list5_1.get(i).getDate_code());
				dataVv5.add(list5_1.get(i).getIndi_value());
			}
			dataV5.add(dataVv5);
			showType5.add("line");
			List<String> legendData5 = new ArrayList<String>();
			legendData5.add(list5_1.get(0).getIndi_name());
			LineType lineType5 = new LineType();
			LineEntity lineEntity5 = lineType5.getOption("9", "湖北产业转型升级", dataX5, legendData5, dataV5,showColor5,showType5);
//第10部分
			String cardText5 = topicService.getTopic1Card5_1();
			CardEntity cardEntity5 = new CardEntity("10", "新经济发展分析", "card", cardText5);
//第11部分			
			List<indi_TF> list6_1 = topicService.getValue6_1();
			List<String> dataX6 = new ArrayList<String>();
			List<String> showColor6 = new ArrayList<String>();
			List<String> showType6 = new ArrayList<String>();
			List<List<String>> dataV6 = new ArrayList<List<String>>();
			List<String> dataVv6 = new ArrayList<String>();
			for(int i=(list6_1.size()-1);i>0;i--) {
				dataX6.add(list6_1.get(i).getDate_code());
				dataVv6.add(list6_1.get(i).getIndi_value());
			}
			dataV6.add(dataVv6);
			showType6.add("line");
			List<String> legendData6 = new ArrayList<String>();
			legendData6.add(list6_1.get(0).getIndi_name());
			LineType lineType6 = new LineType();
			LineEntity lineEntity6 = lineType6.getOption("11", "湖北省新经济发展变化情况", dataX6, legendData6, dataV6,showColor6,showType6);
			
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
			System.out.print("结束");
			
	        return param;
	    }
	
//		宏观经济
			@RequestMapping(value="topic2",produces = "text/plain;charset=utf-8")
			@ResponseBody
		    public String topic2(Map<String,Object> topicPojoMap) throws UnsupportedEncodingException{
				Map<String,Object> map = new HashMap<String,Object>();//最外层map
				map.put("errCode", "0");//错误码
				map.put("errMsg", "success");//错误消息--成功
				
				Plate plate = new Plate(2, "宏观经济"); //创建专题信息（Id，title）
				
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("baseInfo", topicPojoMap);//baseInfo为专题基本信息
	//第一部分-图表1						
				List<indi_TF> list1_1 = topicService.getTopic2Value1_1();
				List<String> dataX = new ArrayList<String>();//X轴数据（时间轴）
				List<String> showColor1 = new ArrayList<String>();
				List<String> showType1 = new ArrayList<String>();
				List<List<String>> dataV = new ArrayList<List<String>>();//指标值
				List<String> dataV_1 = new ArrayList<String>();//指标值
				for(int i=(list1_1.size()-1);i>0;i--) {
					dataX.add(list1_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
					dataV_1.add(list1_1.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
				}
				dataV.add(dataV_1);
				showType1.add("line");
				List<String> legendData1 = new ArrayList<String>();//图例列表（指标名称）
				legendData1.add(list1_1.get(0).getIndi_name());//
				LineType lineType = new LineType();
				LineEntity lineEntity1 = lineType.getOption("1", "湖北省宏观预警指数变化趋势", dataX, legendData1, dataV,showColor1,showType1);
	
	//第二部分-文字1
				String cardText1 = topicService.getTopic2Card1_1();
				CardEntity cardEntity1 = new CardEntity("2", "宏观经济分析", "card", cardText1);
	
	//第三部分-图表2				
				List<indi_TF> list2_1 = topicService.getTopic2Value2_1();
				List<indi_TF> list2_2 = topicService.getTopic2Value2_2();
				List<String> showColor2 = new ArrayList<String>();
				List<String> showType2 = new ArrayList<String>();
				List<String> dataX2 = new ArrayList<String>();//X轴数据（时间轴）
				List<List<String>> dataV2 = new ArrayList<List<String>>();//指标值
				List<String> dataV2_1 = new ArrayList<String>();
				List<String> dataV2_2 = new ArrayList<String>();
				for(int i=(list2_1.size()-1);i>0;i--) {
					dataX2.add(list2_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
					dataV2_1.add(list2_1.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
				}
				for(int i=(list2_2.size()-1);i>0;i--) {
					dataV2_2.add(list2_2.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
				}
				dataV2.add(dataV2_1);
				dataV2.add(dataV2_2);
				showType2.add("line");
				showType2.add("line");
				List<String> legendData2 = new ArrayList<String>();//图例列表（指标名称）
				legendData2.add(list2_1.get(0).getIndi_name()+"-湖北省");//
				legendData2.add(list2_2.get(0).getIndi_name()+"-全国");//
				LineType lineType2 = new LineType();
				LineEntity lineEntity2 = lineType2.getOption("3", "地区生产总值运行趋势", dataX2, legendData2, dataV2,showColor2,showType2);
				
	//第四部分-文字2
				String cardText2 = topicService.getTopic2Card2_1();
				CardEntity cardEntity2 = new CardEntity("4", "经济发展质量分析", "card", cardText2);
	
	//第五部分-图表3
				List<String> showColor3 = new ArrayList<String>();
				List<String> showType3 = new ArrayList<String>();
				List<indi_TF> list3_1 = topicService.getTopic2Value3_1();
				List<String> dataX3 = new ArrayList<String>();//X轴数据（时间轴）
				List<List<String>> dataV3 = new ArrayList<List<String>>();//指标值
				List<String> dataV_3 = new ArrayList<String>();//指标值
				for(int i=(list3_1.size()-1);i>0;i--) {
					dataX3.add(list3_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
					dataV_3.add(list3_1.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
				}
				showType3.add("line");
				dataV3.add(dataV_3);
				List<String> legendData3 = new ArrayList<String>();//图例列表（指标名称）
				legendData3.add(list3_1.get(0).getIndi_name());//
				LineType lineType3 = new LineType();
				LineEntity lineEntity3 = lineType3.getOption("5", "税收收入占地方一般公共预算收入比重变化", dataX3, legendData3, dataV3,showColor3,showType3);
				
	//第6部分-文字3
				String cardText3 = topicService.getTopic2Card3_1()+topicService.getTopic2Card3_2()+topicService.getTopic2Card3_3();
				CardEntity cardEntity3 = new CardEntity("6", "经济发展质量分析", "card", cardText3);
	
				
	//第7部分-图表4
				List<String> showColor4 = new ArrayList<String>();
				List<String> showType4 = new ArrayList<String>();
				List<indi_TF> list4_1 = topicService.getTopic2Value4_1();
				List<indi_TF> list4_2 = topicService.getTopic2Value4_2();
				List<indi_TF> list4_3 = topicService.getTopic2Value4_3();				
				List<String> dataX4 = new ArrayList<String>();//X轴数据（时间轴）
				List<List<String>> dataV4 = new ArrayList<List<String>>();//指标值				
				List<String> dataV4_1 = new ArrayList<String>();//指标值
				List<String> dataV4_2 = new ArrayList<String>();//指标值
				List<String> dataV4_3 = new ArrayList<String>();//指标值
				for(int i=(list4_1.size()-1);i>0;i--) {
					dataX4.add(list4_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
					dataV4_1.add(list4_1.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
				}
				for(int i=(list4_2.size()-1);i>0;i--) {
					dataV4_2.add(list4_2.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
				}
				for(int i=(list4_3.size()-1);i>0;i--) {
					dataV4_3.add(list4_3.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
				}
				dataV4.add(dataV4_1);
				dataV4.add(dataV4_2);
				dataV4.add(dataV4_3);
				showType4.add("line");
				showType4.add("line");
				showType4.add("line");
				List<String> legendData4 = new ArrayList<String>();//图例列表（指标名称）
				legendData4.add(list4_1.get(0).getIndi_name());//
				legendData4.add(list4_2.get(0).getIndi_name());//
				legendData4.add(list4_3.get(0).getIndi_name());//
				LineType lineType4 = new LineType();
				LineEntity lineEntity4 = lineType4.getOption("7", "湖北省三大需求增速走势变动", dataX4, legendData4, dataV4,showColor4,showType4);
	
	//第8部分-文字4
				
				String cardText4 = topicService.getTopic2Card4_1()+topicService.getTopic2Card4_2();
				CardEntity cardEntity4 = new CardEntity("8", "需求对经济的拉动作用分析", "card", cardText4);
				
	//第9部分-图表5
				List<String> showColor5 = new ArrayList<String>();
				List<String> showType5 = new ArrayList<String>();
				List<indi_TF> list5_1 = topicService.getTopic2Value5_1();
				List<indi_TF> list5_2 = topicService.getTopic2Value5_2();		
				List<String> dataX5 = new ArrayList<String>();//X轴数据（时间轴）
				List<List<String>> dataV5 = new ArrayList<List<String>>();//指标值				
				List<String> dataV5_1 = new ArrayList<String>();//指标值
				List<String> dataV5_2 = new ArrayList<String>();//指标值
				for(int i=(list5_1.size()-1);i>0;i--) {
					dataX5.add(list5_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
					dataV5_1.add(list5_1.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
				}
				for(int i=(list5_2.size()-1);i>0;i--) {
					dataV5_2.add(list5_2.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
				}

				dataV5.add(dataV5_1);
				dataV5.add(dataV5_2);
				List<String> legendData5 = new ArrayList<String>();//图例列表（指标名称）
				legendData5.add(list5_1.get(0).getIndi_name());//
				legendData5.add(list5_2.get(0).getIndi_name());//
				LineType lineType5 = new LineType();
				LineEntity lineEntity5 = lineType5.getOption("9", "湖北省三大需求增速走势变动", dataX5, legendData5, dataV5,showColor5,showType5);
	
	 //第10部分-文字5
				String cardText5 = topicService.getTopic2Card5_1()+topicService.getTopic2Card5_2();
				CardEntity cardEntity5 = new CardEntity("10", "需求对经济的拉动作用分析", "card", cardText5);
		
	//第11部分-图表6
				List<String> showColor6 = new ArrayList<String>();
				List<String> showType6 = new ArrayList<String>();
				List<indi_TF> list6_1 = topicService.getTopic2Value6_1();
				List<indi_TF> list6_2 = topicService.getTopic2Value6_2();		
				List<String> dataX6 = new ArrayList<String>();//X轴数据（时间轴）
				List<List<String>> dataV6 = new ArrayList<List<String>>();//指标值				
				List<String> dataV6_1 = new ArrayList<String>();//指标值
				List<String> dataV6_2 = new ArrayList<String>();//指标值
				for(int i=(list6_1.size()-1);i>0;i--) {
					dataX6.add(list6_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
					dataV6_1.add(list6_1.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
				}
				for(int i=(list6_1.size()-1);i>0;i--) {
					dataV6_2.add(list6_2.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
				}

				dataV6.add(dataV6_1);
				dataV6.add(dataV6_2);
				showType6.add("line");
				showType6.add("line");
				List<String> legendData6 = new ArrayList<String>();//图例列表（指标名称）
				legendData6.add(list6_1.get(0).getIndi_name());//
				legendData6.add(list6_2.get(0).getIndi_name());//
				LineType lineType6 = new LineType();
				LineEntity lineEntity6 = lineType6.getOption("11", "湖北省三大产业对GDP的拉动", dataX6, legendData6, dataV6,showColor6,showType6);
	
	//第12部分-文字6		
				String cardText6 = topicService.getTopic2Card6_1();
				CardEntity cardEntity6 = new CardEntity("12", "经济效益分析1", "card", cardText6);
			
	//第13部分-图表7
				List<String> showColor7 = new ArrayList<String>();
				List<String> showType7 = new ArrayList<String>();
				List<indi_TF> list7_1 = topicService.getTopic2Value7_1();
				List<indi_TF> list7_2 = topicService.getTopic2Value7_2();
				showType7.add("bar");
				showType7.add("line");
				
				List<String> dataX7 = new ArrayList<String>();
				List<List<String>> dataV7 = new ArrayList<List<String>>();
				List<String> dataVV7_1 = new ArrayList<String>();
				List<String> dataVV7_2 = new ArrayList<String>();
				for(int i=(list7_1.size()-1);i>0;i--) {
					dataX7.add(list7_1.get(i).getDate_code());
					dataVV7_1.add(list7_1.get(i).getIndi_value());	
				}
				for(int i=(list7_2.size()-1);i>0;i--) {
					dataVV7_2.add(list7_2.get(i).getIndi_value());	
				}
			
				dataV7.add(dataVV7_1);
				dataV7.add(dataVV7_2);

				
				List<String> legendData7 = new ArrayList<String>();
				legendData7.add(list7_1.get(0).getIndi_name()+"-累计值");
				legendData7.add(list7_2.get(0).getIndi_name()+"-增速");

				
				LineAndBarType topicLineAndBarType7 = new LineAndBarType();
				LineAndBarEntity lineAndBarEntity7 = topicLineAndBarType7.getOption("13", "湖北省居民部门经济效益", dataX7, legendData7, dataV7, showColor7,showType7);
				
	//第14部分-文字7
				String cardText7 = topicService.getTopic2Card7_1();
				CardEntity cardEntity7 = new CardEntity("14", "经济效益分析2", "card", cardText7);
				
	//第15部分-图表8
				List<String> showColor8 = new ArrayList<String>();
				List<String> showType8 = new ArrayList<String>();
				List<indi_TF> list8_1 = topicService.getTopic2Value8_1();
				List<indi_TF> list8_2 = topicService.getTopic2Value8_2();
				showType8.add("bar");
				showType8.add("line");
				
				List<String> dataX8 = new ArrayList<String>();
				List<List<String>> dataV8 = new ArrayList<List<String>>();
				List<String> dataVV8_1 = new ArrayList<String>();
				List<String> dataVV8_2 = new ArrayList<String>();
				for(int i=(list8_1.size()-1);i>0;i--) {
					dataX8.add(list8_1.get(i).getDate_code());
					dataVV8_1.add(list8_1.get(i).getIndi_value());	
				}
				for(int i=(list8_1.size()-1);i>0;i--) {
					dataVV8_2.add(list8_2.get(i).getIndi_value());	
				}
			
				dataV8.add(dataVV8_1);
				dataV8.add(dataVV8_2);

				
				List<String> legendData8 = new ArrayList<String>();
				legendData8.add(list8_1.get(0).getIndi_name()+"-累计值");
				legendData8.add(list8_2.get(0).getIndi_name()+"-增速");

				
				LineAndBarType topicLineAndBarType8 = new LineAndBarType();
				LineAndBarEntity lineAndBarEntity8 = topicLineAndBarType8.getOption("15", "湖北省企业部门经济效益", dataX8, legendData8, dataV8, showColor8,showType8);
				
	//第16部分-文字8
				String cardText8 = topicService.getTopic2Card8_1();
				CardEntity cardEntity8 = new CardEntity("16", "经济效益分析3", "card", cardText8);	
	//第17部分-图表9
				List<String> showColor9 = new ArrayList<String>();
				List<String> showType9 = new ArrayList<String>();
				List<indi_TF> list9_1 = topicService.getTopic2Value9_1();
				List<indi_TF> list9_2 = topicService.getTopic2Value9_2();
				showType9.add("bar");
				showType9.add("line");
				
				List<String> dataX9 = new ArrayList<String>();
				List<List<String>> dataV9 = new ArrayList<List<String>>();
				List<String> dataVV9_1 = new ArrayList<String>();
				List<String> dataVV9_2 = new ArrayList<String>();
				for(int i=(list9_1.size()-1);i>0;i--) {
					dataX9.add(list9_1.get(i).getDate_code());
					dataVV9_1.add(list9_1.get(i).getIndi_value());	
				}
				for(int i=(list9_2.size()-1);i>0;i--) {
					dataVV9_2.add(list9_2.get(i).getIndi_value());	
				}
			
				dataV9.add(dataVV9_1);
				dataV9.add(dataVV9_2);

				
				List<String> legendData9 = new ArrayList<String>();
				legendData9.add(list9_1.get(0).getIndi_name()+"-累计值");
				legendData9.add(list9_2.get(0).getIndi_name()+"-增速");

				
				LineAndBarType topicLineAndBarType9 = new LineAndBarType();
				LineAndBarEntity lineAndBarEntity9 = topicLineAndBarType9.getOption("17", "湖北省政府部门经济效益", dataX9, legendData9, dataV9, showColor9,showType9);
				
				
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
			    public String topic3(Map<String,Object> topicPojoMap) throws UnsupportedEncodingException{
					Map<String,Object> map = new HashMap<String,Object>();//最外层map
					map.put("errCode", "0");//错误码
					map.put("errMsg", "success");//错误消息--成功
					
					Plate plate = new Plate(3, "产业经济"); //创建专题信息（Id，title）
					
					Map<String,Object> map1 = new HashMap<String,Object>();
					map1.put("baseInfo", topicPojoMap);//baseInfo为专题基本信息
		//第一部分-图表1	
					List<String> showColor1 = new ArrayList<String>();
					List<String> showType1 = new ArrayList<String>();
					List<indi_TF> list1_1 = topicService.getTopic3Value1_1();
					List<String> dataX = new ArrayList<String>();//X轴数据（时间轴）
					List<List<String>> dataV = new ArrayList<List<String>>();//指标值
					List<String> dataV_1 = new ArrayList<String>();//指标值
					for(int i=(list1_1.size()-1);i>0;i--) {
						dataX.add(list1_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
						dataV_1.add(list1_1.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
					}
					dataV.add(dataV_1);
					showType1.add("line");
					List<String> legendData1 = new ArrayList<String>();//图例列表（指标名称）
					legendData1.add(list1_1.get(0).getIndi_name());//
					LineType lineType = new LineType();
					LineEntity lineEntity1 = lineType.getOption("1", "湖北省工业预警指数变化趋势", dataX, legendData1, dataV,showColor1,showType1);
			
		//第2部分-文字1
					String cardText1 = topicService.getTopic3Card1_1();
					CardEntity cardEntity1 = new CardEntity("2", "产业规模分析", "card", cardText1);
			
		//第3部分-图表2	
					List<String> showColor2 = new ArrayList<String>();
					List<String> showType2 = new ArrayList<String>();
					List<indi_TF> list2_1 = topicService.getTopic3Value2_1();
					List<String> dataX2 = new ArrayList<String>();//X轴数据（时间轴）
					List<List<String>> dataV2 = new ArrayList<List<String>>();//指标值
					List<String> dataV2_1 = new ArrayList<String>();//指标值
					for(int i=(list2_1.size()-1);i>0;i--) {
						dataX2.add(list2_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
						dataV2_1.add(list2_1.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
					}
					dataV2.add(dataV2_1);
					showType2.add("line");
					List<String> legendData2 = new ArrayList<String>();//图例列表（指标名称）
					legendData2.add(list2_1.get(0).getIndi_name());//
					LineType lineType2 = new LineType();
					LineEntity lineEntity2 = lineType2.getOption("3", "湖北省产业规模变化趋势", dataX2, legendData2, dataV2,showColor2,showType2);
		
		//第4部分-文字2
					String cardText2 = topicService.getTopic3Card2_1()+topicService.getTopic3Card2_2();
					CardEntity cardEntity2 = new CardEntity("4", "重点产业效益分析", "card", cardText2);
					
					
		//第5部分-图表3	
					List<String> showColor3 = new ArrayList<String>();
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
					List<List<String>> dataV3 = new ArrayList<List<String>>();
					List<String> dataVV3_1 = new ArrayList<String>();
					List<String> dataVV3_2 = new ArrayList<String>();
					List<String> dataVV3_3 = new ArrayList<String>();
					List<String> dataVV3_4 = new ArrayList<String>();
					List<String> dataVV3_5 = new ArrayList<String>();
					List<String> dataVV3_6 = new ArrayList<String>();
					for(int i=(list3_1.size()-1);i>0;i--) {
						dataX3.add(list3_1.get(i).getDate_code());
						dataVV3_1.add(list3_1.get(i).getIndi_value());	
					}
					for(int i=(list3_2.size()-1);i>0;i--) {
						dataVV3_2.add(list3_2.get(i).getIndi_value());	
					}
					for(int i=(list3_3.size()-1);i>0;i--) {
						dataVV3_3.add(list3_3.get(i).getIndi_value());	
					}
					for(int i=(list3_4.size()-1);i>0;i--) {
						dataVV3_4.add(list3_4.get(i).getIndi_value());	
					}
					for(int i=(list3_5.size()-1);i>0;i--) {
						dataVV3_5.add(list3_5.get(i).getIndi_value());	
					}
					for(int i=(list3_6.size()-1);i>0;i--) {
						dataVV3_6.add(list3_6.get(i).getIndi_value());	
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

					
					LineAndBarType topicLineAndBarType3 = new LineAndBarType();
					LineAndBarEntity lineAndBarEntity3 = topicLineAndBarType3.getOption("5", "重点产业经济效益", dataX3, legendData3, dataV3, showColor3,showType3);										
	
	//第6部分-文字3
					String cardText3 = topicService.getTopic3Card3_1();
					CardEntity cardEntity3 = new CardEntity("6", "产业环境分析（信息）", "card", cardText3);
	//第7部分-图表4
					List<String> showColor4 = new ArrayList<String>();
					List<String> showType4 = new ArrayList<String>();
					List<indi_TF> list4_1 = topicService.getTopic3Value4_1();
					List<String> dataX4 = new ArrayList<String>();//X轴数据（时间轴）
					List<List<String>> dataV4 = new ArrayList<List<String>>();//指标值
					List<String> dataV4_1 = new ArrayList<String>();//指标值
					for(int i=(list4_1.size()-1);i>0;i--) {
						dataX4.add(list4_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
						dataV4_1.add(list4_1.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
					}
					dataV4.add(dataV4_1);
					showType4.add("bar");
					List<String> legendData4 = new ArrayList<String>();//图例列表（指标名称）
					legendData4.add(list4_1.get(0).getIndi_name());//
					BarType barType4 = new BarType();
					BarEntity barEntity4 = barType4.getOption("7", "互联网宽带接入用户数", dataX4, legendData4, dataV4,showColor4,showType4);
	//第8部分-文字4
					String cardText4 = topicService.getTopic3Card4_1();
					CardEntity cardEntity4 = new CardEntity("8", "产业环境分析（资金）", "card", cardText4);
   //第9部分-图表5
					List<String> showColor5 = new ArrayList<String>();
					List<String> showType5 = new ArrayList<String>();
					List<indi_TF> list5_1 = topicService.getTopic3Value5_1();
					List<String> dataX5 = new ArrayList<String>();//X轴数据（时间轴）
					List<List<String>> dataV5 = new ArrayList<List<String>>();//指标值
					List<String> dataV5_1 = new ArrayList<String>();//指标值
					for(int i=(list5_1.size()-1);i>0;i--) {
						dataX5.add(list5_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
						dataV5_1.add(list5_1.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
					}
					dataV5.add(dataV5_1);
					showType5.add("bar");
					List<String> legendData5 = new ArrayList<String>();//图例列表（指标名称）
					legendData5.add(list5_1.get(0).getIndi_name());//
					BarType barType5 = new BarType();
					BarEntity barEntity5 = barType5.getOption("9", "金融机构本外币贷款余额", dataX5, legendData5, dataV5,showColor5,showType5);
		
										
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
			    public String topic4(Map<String,Object> topicPojoMap) throws UnsupportedEncodingException{
					Map<String,Object> map = new HashMap<String,Object>();//最外层map
					map.put("errCode", "0");//错误码
					map.put("errMsg", "success");//错误消息--成功
					
					Plate plate = new Plate(4, "固定资产投资"); //创建专题信息（Id，title）
					
					Map<String,Object> map1 = new HashMap<String,Object>();
					map1.put("baseInfo", topicPojoMap);//baseInfo为专题基本信息
//第一部分-图表1		
					List<String> showColor1 = new ArrayList<String>();
					List<String> showType1 = new ArrayList<String>();
					List<indi_TF> list1_1 = topicService.getTopic4Value1_1();
					List<String> dataX = new ArrayList<String>();//X轴数据（时间轴）
					List<List<String>> dataV = new ArrayList<List<String>>();//指标值
					List<String> dataV_1 = new ArrayList<String>();//指标值
					for(int i=(list1_1.size()-1);i>0;i--) {
						dataX.add(list1_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
						dataV_1.add(list1_1.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
					}
					dataV.add(dataV_1);
					showType1.add("line");
					List<String> legendData1 = new ArrayList<String>();//图例列表（指标名称）
					legendData1.add(list1_1.get(0).getIndi_name());//
					LineType lineType = new LineType();
					LineEntity lineEntity1 = lineType.getOption("1", "湖北省投资先行指数变化趋势", dataX, legendData1, dataV,showColor1,showType1);

//第2部分-文字1	
					String cardText1 = topicService.getTopic4Card1_1();
					CardEntity cardEntity1 = new CardEntity("2", "全省投资分析", "card", cardText1);
//第3部分-图表2
					List<String> showColor2 = new ArrayList<String>();
					List<String> showType2 = new ArrayList<String>();
					List<indi_TF> list2_1 = topicService.getTopic4Value2_1();
					List<indi_TF> list2_2 = topicService.getTopic4Value2_2();
					showType2.add("bar");
					showType2.add("line");
					
					List<String> dataX2 = new ArrayList<String>();
					List<List<String>> dataV2 = new ArrayList<List<String>>();
					List<String> dataVV2_1 = new ArrayList<String>();
					List<String> dataVV2_2 = new ArrayList<String>();
					for(int i=(list2_1.size()-1);i>0;i--) {
						dataX2.add(list2_1.get(i).getDate_code());
						dataVV2_1.add(list2_1.get(i).getIndi_value());	
					}
					for(int i=(list2_2.size()-1);i>0;i--) {
						dataVV2_2.add(list2_2.get(i).getIndi_value());	
					}
				
					dataV2.add(dataVV2_1);
					dataV2.add(dataVV2_2);

					
					List<String> legendData2 = new ArrayList<String>();
					legendData2.add(list2_1.get(0).getIndi_name()+"-累计值");
					legendData2.add(list2_2.get(0).getIndi_name()+"-增速");

					
					LineAndBarType topicLineAndBarType2 = new LineAndBarType();
					LineAndBarEntity lineAndBarEntity2 = topicLineAndBarType2.getOption("3", "湖北省固定资产投资变化趋势", dataX2, legendData2, dataV2,showColor2, showType2);
//第4部分-文字2
					String cardText2 = topicService.getTopic4Card2_1();
					CardEntity cardEntity2 = new CardEntity("4", "本年投资分析", "card", cardText2);
					
//第5部分-图表3
					List<String> showColor3 = new ArrayList<String>();
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
					List<List<String>> dataV3 = new ArrayList<List<String>>();
					List<String> dataVV3_1 = new ArrayList<String>();
					List<String> dataVV3_2 = new ArrayList<String>();
					List<String> dataVV3_3 = new ArrayList<String>();
					List<String> dataVV3_4 = new ArrayList<String>();
					for(int i=(list3_1.size()-1);i>0;i--) {
						dataX3.add(list3_1.get(i).getDate_code());
						dataVV3_1.add(list3_1.get(i).getIndi_value());	
					}
					for(int i=(list3_2.size()-1);i>0;i--) {
						dataVV3_2.add(list3_2.get(i).getIndi_value());	
					}
					
					for(int i=(list3_3.size()-1);i>0;i--) {
						dataVV3_3.add(list3_3.get(i).getIndi_value());	
					}
					for(int i=(list3_4.size()-1);i>0;i--) {
						dataVV3_4.add(list3_4.get(i).getIndi_value());	
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

					
					LineAndBarType topicLineAndBarType3 = new LineAndBarType();
					LineAndBarEntity lineAndBarEntity3 = topicLineAndBarType3.getOption("5", "湖北省项目个数变化趋势", dataX3, legendData3, dataV3, showColor3,showType3);		

//第6部分-文字3
					String cardText3 = topicService.getTopic4Card3_1();
					CardEntity cardEntity3 = new CardEntity("6", "投资效益分析", "card", cardText3);

//第7部分-图表4
					List<String> showColor4 = new ArrayList<String>();
					List<String> showType4 = new ArrayList<String>();
					List<indi_TF> list4_1 = topicService.getTopic4Value4_1();
					List<String> dataX4 = new ArrayList<String>();//X轴数据（时间轴）
					List<List<String>> dataV4 = new ArrayList<List<String>>();//指标值
					List<String> dataV4_1 = new ArrayList<String>();//指标值
					for(int i=(list4_1.size()-1);i>0;i--) {
						dataX4.add(list4_1.get(i).getDate_code());//从指标列表获取时间数据存入 X轴列表
						dataV4_1.add(list4_1.get(i).getIndi_value());//从指标列表获取指标值 并存入dataV
					}
					dataV4.add(dataV4_1);
					showType4.add("line");
					List<String> legendData4 = new ArrayList<String>();//图例列表（指标名称）
					legendData4.add(list4_1.get(0).getIndi_name());//
					LineType lineType4 = new LineType();
					LineEntity lineEntity4 = lineType4.getOption("7", "湖北省投资效益系数变化趋势", dataX4, legendData4, dataV4,showColor4,showType4);

//第8部分-文字4
					String cardText4 = topicService.getTopic4Card4_1();
					CardEntity cardEntity4 = new CardEntity("8", "投资环境", "card", cardText4);
					
//第9部分-图表5				
					List<String> showColor5 = new ArrayList<String>();
					List<String> showType5 = new ArrayList<String>();
					List<indi_TF> list5_1 = topicService.getTopic4Value51_1();
					List<indi_TF> list5_2 = topicService.getTopic4Value51_2();
					showType5.add("bar");
					showType5.add("line");
					
					List<String> dataX5 = new ArrayList<String>();
					List<List<String>> dataV5 = new ArrayList<List<String>>();
					List<String> dataVV5_1 = new ArrayList<String>();
					List<String> dataVV5_2 = new ArrayList<String>();
					for(int i=(list5_1.size()-1);i>0;i--) {
						dataX5.add(list5_1.get(i).getDate_code());
						dataVV5_1.add(list5_1.get(i).getIndi_value());	
					}
					for(int i=(list5_2.size()-1);i>0;i--) {
						dataVV5_2.add(list5_2.get(i).getIndi_value());	
					}

				
					dataV5.add(dataVV5_1);
					dataV5.add(dataVV5_2);
					


					
					List<String> legendData5 = new ArrayList<String>();
					legendData5.add(list5_1.get(0).getIndi_name());
					legendData5.add(list5_2.get(0).getIndi_name());

					
					LineAndBarType topicLineAndBarType5 = new LineAndBarType();
					LineAndBarEntity lineAndBarEntity5 = topicLineAndBarType5.getOption("9", "湖北省非公经济发展变化趋势", dataX5, legendData5, dataV5,showColor5,showType5);		

					
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
