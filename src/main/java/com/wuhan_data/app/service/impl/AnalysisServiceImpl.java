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

import com.wuhan_data.app.mapper.AuthorityMapper;
import com.wuhan_data.pojo.*;
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
import com.wuhan_data.service.UserService;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Autowired
    AnalysisMapper analysisMapper;

    @Autowired
    CollectMapperApp collectMapperApp;

    @Autowired
    UserService userService;

    @Autowired
    AuthorityMapper authorityMapper;

    @Override
    public ArrayList<Object> getAnalysisList(int userId) {
        System.out.println("用户Id:" + userId);
        // 处理经济分析栏目列表
        ArrayList<Object> result = authorityMapper.getAnalysisListByUserId(userId);
		/*ArrayList<Object> result = new ArrayList<Object>();
		List<AnalysisType> typeList = analysisMapper.getAnalysisTypeList();
		for (int i = 0; i < typeList.size(); i++) {
			Map<String, Object> typeListMap = new HashMap<String, Object>();
			typeListMap.put("typeId", typeList.get(i).getType_id());
			typeListMap.put("typeName", typeList.get(i).getType_name());
			ArrayList<Object> labelList = this.getAnalysisLabelList(userId, typeList.get(i).getType_id());
			typeListMap.put("labelList", labelList);
			result.add(typeListMap);
		}*/
        return result;
    }

    @Override
    public ArrayList<Object> getAnalysisLabelList(int userId, int typeId) {
        ArrayList<Object> result = new ArrayList<Object>();
        // 从数据库获取分类标签数据
        List<AnalysisLabel> labelList = analysisMapper.getAnalysisLabelList(typeId);
        for (int i = 0; i < labelList.size(); i++) {
            Map<String, Object> labelListMap = new HashMap<String, Object>();
            String labelName = labelList.get(i).getLabel_name().toString();
            labelListMap.put("labelName", labelName);
            ArrayList<Object> themeList = this.getAnalysisThemeList(userId, labelList.get(i).getLabel_id());
            labelListMap.put("themeList", themeList);
            result.add(labelListMap);
        }
        return result;
    }

    @Override
    public ArrayList<Object> getAnalysisThemeList(int userId, int labelId) {
        ArrayList<Object> result = new ArrayList<Object>();
        // 从数据库获取二级栏目数据
        List<AnalysisTheme> themeListTemp = analysisMapper.getAnalysisThemeList(labelId);
        // 根据roleList 选择性的给栏目数据
        ArrayList<String> roleList = new ArrayList<String>();
        if (userId != 0) {
            try {
                // 根据用户userId获取对应的role_list
                Map<String, List<String>> allPowerMap = new HashMap<String, List<String>>();
                allPowerMap = userService.getAllPower(userId);
                roleList = (ArrayList<String>) allPowerMap.get("powerThemes");
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("权限列表获取失败");
            }
        }
        System.out.println("权限列表:" + roleList);
        // 根据权限列表筛选二级栏目数据
        List<AnalysisTheme> themeList = this.getAnalysisRoleList(themeListTemp, roleList);
        System.out.println("权限筛选后列表:" + themeList);
        // 获取收藏信息，构建二级栏目数据集
        for (int i = 0; i < themeList.size(); i++) {
            Map<String, Object> themeListMap = new HashMap<String, Object>();
            String indexId = String.valueOf(themeList.get(i).getTheme_id());
            String indexName = themeList.get(i).getTheme_name().toString();
            themeListMap.put("indexId", indexId);
            themeListMap.put("indexName", indexName);
            result.add(themeListMap);
        }
        return result;
    }

    @Override
    public ArrayList<Object> searchAnalysis(int userId, String keyword) {
        ArrayList<Object> result = new ArrayList<Object>();
        List<AnalysisSearch> searchResult = analysisMapper.searchAnalysis(keyword);
        for (int i = 0; i < searchResult.size(); i++) {
            Map<String, Object> themeListMap = new HashMap<String, Object>();
            String indexId = searchResult.get(i).getThemeId().toString();
            String indexName = searchResult.get(i).getThemeName().toString();
            String typeName = searchResult.get(i).getTypeName().toString();
            String labelName = searchResult.get(i).getLabelName().toString();
            themeListMap.put("indexId", indexId);
            themeListMap.put("indexName", indexName);
            themeListMap.put("typeName", typeName);
            themeListMap.put("labelName", labelName);
            result.add(themeListMap);
        }
        return result;
    }

    /**
     * 根据用户权限筛选栏目数据
     *
     * @return
     */
    @Override
    public List<AnalysisTheme> getAnalysisRoleList(List<AnalysisTheme> themeList, ArrayList<String> roleList) {
        List<AnalysisTheme> result = new ArrayList<AnalysisTheme>();
        if (roleList.isEmpty()) {
            return themeList;
        }
        for (int j = 0; j < themeList.size(); j++) {
            String themeId = String.valueOf(themeList.get(j).getTheme_id());
            String isShow = String.valueOf(themeList.get(j).getIs_show());
//			System.out.println("themeId" + themeId);
            if (isShow.equals("0")) {
                result.add(themeList.get(j));
            } else if (isShow.equals("9")) {
                for (int k = 0; k < roleList.size(); k++) {
                    if (roleList.get(k).toString().equals(themeId)) {
                        result.add(themeList.get(j));
                        break;
                    }
                }
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
        String indexName = baseInfoList.get(0).getTheme_name();
        Map<String, Object> baseInfo = new HashMap<String, Object>();
        baseInfo.put("indexId", themeId);
        baseInfo.put("indexName", indexName);
        // 根据userId/type/indexId查询收藏信息
        try {
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
        } catch (Exception e) {
            baseInfo.put("isFavorite", false);
        }
        System.out.println("版块数据获取成功:" + df.format(new Date()));

        // 获取时间可取区间数据
        //TODO 贼慢
        ArrayList<Map<String, Object>> timeCondition = this.getTimeCondition(analysisPlate);
        String errorTimeCondition = "[{current=[0, 0], startArray=[], freqName=月度, endArray=[]}, {startArray=[], freqName=季度, endArray=[]}, {startArray=[], freqName=年度, endArray=[]}]";
        if (timeCondition.toString().equals(errorTimeCondition)) {
            return result;
        }
        System.out.println("时间区间数据获取成功:" + df.format(new Date()));

        // 获取地区可取区间数据
        ArrayList<String> areaCondition = new ArrayList<String>();
        areaCondition.add("武汉市");
        areaCondition.add("黄石市");
        areaCondition.add("十堰市");
        areaCondition.add("宜昌市");
        areaCondition.add("襄阳市");
        areaCondition.add("鄂州市");
        areaCondition.add("荆门市");
        areaCondition.add("孝感市");
        areaCondition.add("荆州市");
        areaCondition.add("黄冈市");
        areaCondition.add("咸宁市");
        areaCondition.add("随州市");
        areaCondition.add("恩施自治州");
        areaCondition.add("仙桃市");
        areaCondition.add("潜江市");
        areaCondition.add("天门市");
        areaCondition.add("神农架林区");

        // 构建查询条件
        Map<String, Object> freqObject = new HashMap<String, Object>();
        try {
            freqObject = timeCondition.get(0);
        } catch (Exception e) {
            System.out.println("时间区间数据获取异常:" + timeCondition.toString());
        }
        ArrayList<Integer> current = (ArrayList<Integer>) freqObject.get("current");
        List<String> startTimeList = (List<String>) freqObject.get("startArray");
        List<String> endTimeList = (List<String>) freqObject.get("endArray");
        String freqName = (String) freqObject.get("freqName");
        System.out.println("TimeList:" + startTimeList + "\n current" + current);
        List<String> xAxis = startTimeList.subList(current.get(0), current.get(1) + 1);
        String startTime = startTimeList.get(current.get(0)).toString();
        String startTimeRadar = endTimeList.get(startTimeList.size() - 4).toString();
        String startTimePoint = endTimeList.get(0).toString();
        String endTime = endTimeList.get(current.get(1)).toString();
        String endTimeRadar = endTimeList.get(endTimeList.size() - 1).toString();
        String endTimePoint = endTimeList.get(endTimeList.size() - 1).toString();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("freqName", freqName);
        queryMap.put("area", areaCondition.get(0).toString());
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
        result.put("areaCondition", areaCondition);
        result.put("classInfo", classInfo);

        return result;
    }

    public Map<String, Object> initAnalysisPlateByTime(int themeId, String startTime, String endTime, String freqName,
                                                       String area) {
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
        List<String> xAxis = startTimeList.subList(startFlag, endFlag + 1);

        String startTimeRadar = endTimeList.get(startTimeList.size() - 4).toString();
        String startTimePoint = endTimeList.get(0).toString();
        String endTimeRadar = endTimeList.get(endTimeList.size() - 1).toString();
        String endTimePoint = endTimeList.get(endTimeList.size() - 1).toString();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("freqName", freqName);
        queryMap.put("area", area);
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
            System.out.println("进入版块" + pid);
            for (int j = 0; j < indiList.size(); j++) {
//				if (pid == 19 || pid == 20|| pid == 24 || pid == 25 || pid == 26|| pid == 238){
                if (j > 0) {
                    break;
                }
//				}
                System.out.println("进入指标" + j);
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
        System.out.println("时间频度数据获取成功:" + timeFreq.toString() + df.format(new Date()));

        // 获取时间选择器区间，取指标的并集
        for (int i = 0; i < timeFreq.size(); i++) {
            String freqName = timeFreq.get(i);
            Map<String, Object> timeConditionMap = new HashMap<String, Object>();
            Set<String> timeSpanFinal = new HashSet<String>();
            for (int j = 0; j < analysisPlate.size(); j++) {
                Integer pid = analysisPlate.get(j).getPlateId();
                System.out.println("进入版块" + pid);
                List<AnalysisIndi> indiList = analysisMapper.getIndiByPid(pid);
                for (int k = 0; k < indiList.size(); k++) {
//					if (pid == 19 || pid == 20|| pid == 24 || pid == 25 || pid == 26|| pid == 238){
                    if (k > 0) {
                        break;
                    }
//					}
                    System.out.println("进入指标" + k);
                    Map<String, Object> queryMap = new HashMap<String, Object>();
                    String indiCode = indiList.get(k).getIndiCode();
                    queryMap.put("indiCode", indiCode);
                    queryMap.put("freqName", freqName);
                    // TOOD 从app_analysis_indi_time视图中直接取出start_time/end_time并组成数组
                    // List<String> timeList = analysisMapper.getTimeByFreqname(queryMap);
                    List<AnalysisIndiTime> timeList1 = analysisMapper.getTimeByFreq(queryMap);
                    // System.out.println("特定指标时间区间为:" + timeList1.toString());
                    String startTime = "";
                    String endTime = "";
                    // 对无法查询到时间区间的指标进行异常处理
                    if (timeList1.size() == 0) {
                        continue;
                    }
                    startTime = timeList1.get(0).getStartTime();
                    endTime = timeList1.get(0).getEndTime();
                    List<String> timeList2 = this.fillTimeList(freqName, startTime, endTime);
                    Set<String> timeSpanSet = new HashSet<String>(timeList2);
                    timeSpanFinal.addAll(timeSpanSet);
                }
                // System.out.println("版块" + pid + "的" + freqName + "区间插入后，时间选择器为:" +
                // timeSpanFinal);
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

            // 对需要进行计算的特殊图例进行单独配置
            int flagPlate = 0;
            switch (id) {
                case "25":
                case "26": {
                    if (id.equals("25")) {
                        System.out.println("进入特殊指标——各市州GDP-25");
                        List<List<String>> dataValue = new ArrayList<List<String>>();
                        List<String> legend = new ArrayList<String>();
                        List<String> showColor = new ArrayList<String>();
                        List<String> showType = new ArrayList<String>();
                        List<String> unitName = new ArrayList<String>();
                        String area = queryMap.get("area").toString();
                        // 配置指标图例
                        LineAndBarType lineAndBarType = new LineAndBarType();
                        for (int j = 0; j < indiList.size(); j++) {
                            if (indiList.get(j).getIndiName().toString().indexOf(area) != -1) {
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
                                showColor.add(indiList.get(j).getShowColor());
                                showType.add(indiList.get(j).getShowType());
                                unitName.add(indiInfoList.get(0).getUnitName().toString());
                            }
                        }
                        LineAndBarEntity lineAndBarEntity = lineAndBarType.getOption(id, title, xAxis, legend, dataValue,
                                showColor, showType, unitName);
                        TotalList.add(lineAndBarEntity);

                    }
                    if (id.equals("26")) {
                        System.out.println("进入特殊指标——各市州GDP-26");
                        List<List<String>> dataValue = new ArrayList<List<String>>();
                        List<String> legend = new ArrayList<String>();
                        List<String> showColor = new ArrayList<String>();
                        List<String> showType = new ArrayList<String>();
                        List<String> unitName = new ArrayList<String>();
                        String area = queryMap.get("area").toString();
                        // 配置指标图例
                        LineType lineType = new LineType();
                        for (int j = 0; j < indiList.size(); j++) {
                            if (indiList.get(j).getIndiName().toString().indexOf(area) != -1
                                    || indiList.get(j).getIndiName().toString().indexOf("湖北") != -1) {
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
                                showColor.add(indiList.get(j).getShowColor());
                                showType.add(indiList.get(j).getShowType());
                                unitName.add(indiInfoList.get(0).getUnitName());
                            }
                        }
                        LineEntity lineEntity = lineType.getOption(id, title, xAxis, legend, dataValue, showColor, showType,
                                unitName);
                        TotalList.add(lineEntity);
                    }
                    flagPlate = 1;
                }
                break;

                case "238": {
                    // 绘制表格数据
                    System.out.println("进入特殊指标——各市州GDP-表格");
                    List<List<String>> dataValueTable = new ArrayList<List<String>>();
                    List<String> legendTable = new ArrayList<String>();
                    String dataXTempTime = "";
                    // indiList是版块下的指标
                    for (int j = 0; j < indiList.size(); j++) {
                        Map<String, Object> queryMap1 = new HashMap<String, Object>();
                        queryMap1.put("freqName", queryMap.get("freqName"));
                        queryMap1.put("endTime", queryMap.get("endTime"));
                        queryMap1.put("indiCode", indiList.get(j).getIndiCode());
                        System.out.println("进入特殊指标——各市州GDP-表格-queryMap1" + queryMap1);
                        // 单独优化单时间取数
                        List<AnalysisIndiValue> indiInfoList = analysisMapper.getIndiValue2(queryMap1);
                        // TODO 这里的xAxis.size()可能需要改，改成长度为1，只取一个时间点的数据
                        List<String> dataIndiValue = new ArrayList<String>();

                        System.out.println("进入特殊指标——各市州GDP-表格-单指标数据" + dataIndiValue);
                        dataIndiValue.add(indiInfoList.get(0).getIndiValue());
                        dataValueTable.add(dataIndiValue);
                        legendTable.add(indiList.get(j).getIndiName());
                    }
                    System.out.println("进入特殊指标——各市州GDP-表格-数据" + dataValueTable);
                    System.out.println("进入特殊指标——各市州GDP-表格-legend" + legendTable);

                    // 配置表格数据
                    List<List<String>> dataTable = new ArrayList<List<String>>();
                    List<String> tableRow1 = new ArrayList<String>();
                    tableRow1.add("地区");
                    tableRow1.add("湖北省");
                    tableRow1.add("武汉市");
                    tableRow1.add("黄石市");
                    tableRow1.add("十堰市");
                    tableRow1.add("宜昌市");
                    tableRow1.add("襄阳市");
                    tableRow1.add("鄂州市");
                    tableRow1.add("荆门市");
                    tableRow1.add("孝感市");
                    tableRow1.add("荆州市");
                    tableRow1.add("黄冈市");
                    tableRow1.add("咸宁市");
                    tableRow1.add("随州市");
                    tableRow1.add("恩施自治州");
                    tableRow1.add("仙桃市");
                    tableRow1.add("潜江市");
                    tableRow1.add("天门市");
                    tableRow1.add("神农架林区");

                    List<String> tableRow2 = new ArrayList<String>();
                    tableRow2.add("累计值(亿元)");
                    for (int j = 1; j < tableRow1.size(); j++) {
                        String tableRow2Value = "-";
                        String areaName = tableRow1.get(j).toString();
                        String funcName = "累计值";
                        for (int k = 0; k < legendTable.size(); k++) {
                            if (legendTable.get(k).toString().indexOf(areaName) != -1
                                    && legendTable.get(k).toString().indexOf(funcName) != -1) {
                                tableRow2Value = dataValueTable.get(k).get(0);
                                tableRow2.add(tableRow2Value);
                                break;
                            }
                        }
                    }

                    List<String> tableRow3 = new ArrayList<String>();
                    tableRow3.add("累计增长(%)");
                    for (int j = 1; j < tableRow1.size(); j++) {
                        String tableRow3Value = "-";
                        String areaName = tableRow1.get(j).toString();
                        String funcName = "累计增长";
                        for (int k = 0; k < legendTable.size(); k++) {
                            if (legendTable.get(k).toString().indexOf(areaName) != -1
                                    && legendTable.get(k).toString().indexOf(funcName) != -1) {
                                tableRow3Value = dataValueTable.get(k).get(0);
                                tableRow3.add(tableRow3Value);
                                break;
                            }
                        }
                    }
                    System.out.println(tableRow2);
                    System.out.println(tableRow3);

                    Double totalGDPDouble = 0.0;
                    for (int j = 1; j < tableRow2.size(); j++) {
                        Double dataValue = Double.parseDouble(tableRow2.get(j));
                        totalGDPDouble = totalGDPDouble + dataValue;
                    }

                    List<String> tableRow4 = new ArrayList<String>();
                    tableRow4.add("占比(%)");
                    for (int j = 1; j < tableRow2.size(); j++) {
                        Double dataValue = (Double.parseDouble(tableRow2.get(j)) / totalGDPDouble) * 100;
                        tableRow4.add(String.format("%.2f", dataValue));
                    }

                    dataTable.add(tableRow1);
                    dataTable.add(tableRow2);
                    dataTable.add(tableRow3);
                    dataTable.add(tableRow4);

                    String titleTable = dataXTempTime + "GDP累计值及增速";
                    String tableId = id + "table";
                    TableEntity tableEntity = new TableEntity(tableId, titleTable, dataTable);
                    TotalList.add(tableEntity);
                    flagPlate = 1;
                }
                break;

                case "202":
                case "203": {
                    System.out.println("进入特殊图例——异常数据源特殊处理");
                    List<List<String>> dataValue = new ArrayList<List<String>>();
                    List<String> legend = new ArrayList<String>();
                    List<String> showColor = new ArrayList<String>();
                    List<String> showType = new ArrayList<String>();
                    List<String> unitName = new ArrayList<String>();
                    // 配置指标图例
                    LineType lineType = new LineType();
                    for (int j = 0; j < indiList.size(); j++) {
                        queryMap.put("indiCode", indiList.get(j).getIndiCode());
                        List<AnalysisIndiValue> indiInfoList = analysisMapper.getIndiValue(queryMap);
                        List<String> dataIndiValue = Arrays.asList(new String[xAxis.size()]);
                        for (int m = 0; m < indiInfoList.size(); m++) {
                            String dataXTemp = indiInfoList.get(m).getTime();
                            if (xAxis.contains(dataXTemp)) {
                                int index = xAxis.indexOf(dataXTemp);
                                // 对数据源异常的处理
                                if (indiList.get(j).getIndiCode().toString()
                                        .equals("GM0201;400:101585152;363:102387482;62:42")) {
                                    Double dataValueDouble = 0.0;
                                    try {
                                        dataValueDouble = Double.parseDouble(indiInfoList.get(m).getIndiValue());
                                    } catch (Exception e) {
                                        dataValueDouble = 0.0;
                                    }
                                    if (dataValueDouble > 100.00) {
                                        dataValueDouble -= 100;
                                    }
                                    dataIndiValue.set(index, String.format("%.2f", dataValueDouble));
                                } else {
                                    dataIndiValue.set(index, indiInfoList.get(m).getIndiValue());
                                }
                            }
                        }
                        dataValue.add(dataIndiValue);
                        legend.add(indiList.get(j).getIndiName());
                        showColor.add(indiList.get(j).getShowColor());
                        showType.add(indiList.get(j).getShowType());
                        unitName.add(indiInfoList.get(0).getUnitName());
                    }
                    if (id.equals("202")) {
                        // 配置指标图例
                        LineAndBarType lineAndBarType = new LineAndBarType();
                        LineAndBarEntity lineAndBarEntity = lineAndBarType.getOption(id, title, xAxis, legend, dataValue,
                                showColor, showType, unitName);
                        TotalList.add(lineAndBarEntity);
                        // 配置表格数据
                        TableType tableType = new TableType();
                        List<List<String>> dataXaisTable = new ArrayList<List<String>>();
                        for (int q = 0; q < indiList.size(); q++) {
                            dataXaisTable.add(xAxis);
                        }
                        TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legend, dataValue);
                        TotalList.add(tableEntity);
                    }
                    if (id.equals("203")) {
                        LineEntity lineEntity = lineType.getOption(id, title, xAxis, legend, dataValue, showColor, showType,
                                unitName);
                        TotalList.add(lineEntity);
                        // 配置表格数据
                        TableType tableType = new TableType();
                        List<List<String>> dataXaisTable = new ArrayList<List<String>>();
                        for (int q = 0; q < indiList.size(); q++) {
                            dataXaisTable.add(xAxis);
                        }
                        TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legend, dataValue);
                        TotalList.add(tableEntity);
                    }
                    flagPlate = 1;
                }
                break;
                case "17":
                case "18":
                case "23":
                case "208":
                case "211":
                case "85":
                case "86": {
                    System.out.println("进入特殊图例——拉动率计算");
                    List<List<String>> dataValue = new ArrayList<List<String>>();
                    List<String> legend = new ArrayList<String>();
                    List<String> showColor = new ArrayList<String>();
                    List<String> showType = new ArrayList<String>();
                    List<String> unitName = new ArrayList<String>();
                    unitName.add("%");
                    unitName.add("%");
                    unitName.add("%");
                    unitName.add("%");
                    for (int j = 0; j < indiList.size(); j++) {
                        // 处理配置表中配置数据
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
                    }

                    // 构建新的数据值集合
                    List<List<String>> dataValue1 = new ArrayList<List<String>>();
                    // 取出GDP总量数据及增速数据(第一二三产业)
                    List<String> indexGDPValueList = new ArrayList<String>();
                    List<String> indexGDPSpeedList = new ArrayList<String>();
                    List<String> indexFirstValueList = new ArrayList<String>();
                    List<String> indexFirstSpeedList = new ArrayList<String>();
                    List<String> indexSecondValueList = new ArrayList<String>();
                    List<String> indexSecondSpeedList = new ArrayList<String>();
                    List<String> indexThirdValueList = new ArrayList<String>();
                    List<String> indexThirdSpeedList = new ArrayList<String>();
                    for (int j = 0; j < indiList.size(); j++) {
                        String indexCode = indiList.get(j).getIndiCode().toString();
                        // GDP_累计值
                        if (indexCode.equals("GM0101;400:101585152;363:706403;62:42")) {
                            indexGDPValueList = dataValue.get(j);
                        }
                        // GDP_累计增长
                        if (indexCode.equals("GM0101;400:101585152;363:706404;62:42")) {
                            indexGDPSpeedList = dataValue.get(j);
                        }
                        // 第一产业_累计值
                        if (indexCode.equals("GM0101;400:101585152;363:706403;62:42;100:700101")) {
                            indexFirstValueList = dataValue.get(j);
                        }
                        // 第一产业_增长率
                        if (indexCode.equals("GM0101;400:101585152;363:706404;62:42;100:700101")) {
                            indexFirstSpeedList = dataValue.get(j);
                        }
                        // 第二产业_累计值
                        if (indexCode.equals("GM0101;400:101585152;363:706403;62:42;100:700102")) {
                            indexSecondValueList = dataValue.get(j);
                        }
                        // 第二产业_增长率
                        if (indexCode.equals("GM0101;400:101585152;363:706404;62:42;100:700102")) {
                            indexSecondSpeedList = dataValue.get(j);
                        }
                        // 第三产业_累计值
                        if (indexCode.equals("GM0101;400:101585152;363:706403;62:42;100:700103")) {
                            indexThirdValueList = dataValue.get(j);
                        }
                        // 第三产业_增长率
                        if (indexCode.equals("GM0101;400:101585152;363:706404;62:42;100:700103")) {
                            indexThirdSpeedList = dataValue.get(j);
                        }
                    }

                    // 计算三产对GDP的拉动率
                    // 计算方法(以第一产业为例): 当期第一产业增长率*(当期第一产业累计值/当期GDP累计值)
                    List<String> dataIndiValue1 = new ArrayList<String>();
                    List<String> dataIndiValue2 = new ArrayList<String>();
                    List<String> dataIndiValue3 = new ArrayList<String>();
                    for (int j = 0; j < indexFirstSpeedList.size(); j++) {
                        Double indexFirstSpeedDouble = 0.0;
                        Double indexFirstValueDouble = 0.0;
                        Double indexSecondSpeedDouble = 0.0;
                        Double indexSecondValueDouble = 0.0;
                        Double indexThirdSpeedDouble = 0.0;
                        Double indexThirdValueDouble = 0.0;
                        try {
                            indexFirstSpeedDouble = Double.parseDouble(indexFirstSpeedList.get(j));
                            indexSecondSpeedDouble = Double.parseDouble(indexSecondSpeedList.get(j));
                            indexThirdSpeedDouble = Double.parseDouble(indexThirdSpeedList.get(j));
                            indexFirstValueDouble = Double.parseDouble(indexFirstValueList.get(j));
                            indexSecondValueDouble = Double.parseDouble(indexSecondValueList.get(j));
                            indexThirdValueDouble = Double.parseDouble(indexThirdValueList.get(j));
                        } catch (Exception e) {
                            System.out.println("第" + j + "列" + "double转换错误:" + indexFirstSpeedList.toString()
                                    + indexSecondSpeedList.toString() + indexThirdSpeedList.toString()
                                    + indexFirstValueList.toString() + indexSecondValueList.toString()
                                    + indexThirdValueList.toString());
                        }
                        try {
                            // 第一产业
                            Double dataValueDouble1 = indexFirstSpeedDouble * (indexFirstValueDouble
                                    / (indexFirstValueDouble + indexSecondValueDouble + indexThirdValueDouble));
                            dataIndiValue1.add(j, String.format("%.2f", dataValueDouble1));
                            // 第二产业
                            Double dataValueDouble2 = indexSecondSpeedDouble * (indexSecondValueDouble
                                    / (indexFirstValueDouble + indexSecondValueDouble + indexThirdValueDouble));
                            dataIndiValue2.add(j, String.format("%.2f", dataValueDouble2));
                            // 第三产业
                            Double dataValueDouble3 = indexThirdSpeedDouble * (indexThirdValueDouble
                                    / (indexFirstValueDouble + indexSecondValueDouble + indexThirdValueDouble));
                            dataIndiValue3.add(j, String.format("%.2f", dataValueDouble3));
                        } catch (Exception e) {
                            dataIndiValue1.add(j, "0.0");
                            dataIndiValue2.add(j, "0.0");
                            dataIndiValue3.add(j, "0.0");
                            System.out.println("第" + j + "列" + "double计算错误:" + indexFirstSpeedList.toString()
                                    + indexSecondSpeedList.toString() + indexThirdSpeedList.toString()
                                    + indexFirstValueList.toString() + indexSecondValueList.toString()
                                    + indexThirdValueList.toString());
                        }
                    }

                    // 计算三产占GDP的比重
                    // 计算方法(以第一产业为例): 当期第一产业累计值/(当期第一产业累计值+当期第二产业累计值+当期第三产业累计值)
                    List<String> dataIndiValue11 = new ArrayList<String>();
                    List<String> dataIndiValue22 = new ArrayList<String>();
                    List<String> dataIndiValue33 = new ArrayList<String>();
                    for (int j = 0; j < indexFirstValueList.size(); j++) {
                        Double indexFirstValueDouble = 0.0;
                        Double indexSecondValueDouble = 0.0;
                        Double indexThirdValueDouble = 0.0;
                        try {
                            indexFirstValueDouble = Double.parseDouble(indexFirstValueList.get(j));
                            indexSecondValueDouble = Double.parseDouble(indexSecondValueList.get(j));
                            indexThirdValueDouble = Double.parseDouble(indexThirdValueList.get(j));
                        } catch (Exception e) {
                            System.out.println("第" + j + "列" + "double转换错误:" + indexFirstValueDouble.toString()
                                    + indexSecondValueList.toString() + indexThirdValueList.toString());
                        }
                        try {
                            // 第一产业
                            Double dataValueDouble11 = indexFirstValueDouble
                                    / (indexFirstValueDouble + indexSecondValueDouble + indexThirdValueDouble);
                            dataIndiValue11.add(j, String.format("%.2f", dataValueDouble11));
                            // 第二产业
                            Double dataValueDouble22 = indexSecondValueDouble
                                    / (indexFirstValueDouble + indexSecondValueDouble + indexThirdValueDouble);
                            dataIndiValue22.add(j, String.format("%.2f", dataValueDouble22));
                            // 第三产业
                            Double dataValueDouble33 = indexThirdValueDouble
                                    / (indexFirstValueDouble + indexSecondValueDouble + indexThirdValueDouble);
                            dataIndiValue33.add(j, String.format("%.2f", dataValueDouble33));
                        } catch (Exception e) {
                            dataIndiValue11.add(j, "0.0");
                            dataIndiValue22.add(j, "0.0");
                            dataIndiValue33.add(j, "0.0");
                            System.out.println("第" + j + "列" + "double计算错误:" + indexFirstValueDouble.toString()
                                    + indexSecondValueList.toString() + indexThirdValueList.toString());
                        }
                    }

                    // 配置指标图例
                    if (id.equals("17")) {
                        legend.add("第一产业");
                        showColor.add("#77C87B");
                        showType.add("line");
                        dataValue1.add(dataIndiValue1);
                        legend.add("第二产业");
                        showColor.add("#545657");
                        showType.add("line");
                        dataValue1.add(dataIndiValue2);
                        legend.add("第三产业");
                        showColor.add("#F0805F");
                        showType.add("line");
                        dataValue1.add(dataIndiValue3);
                        legend.add("GDP_累计增长");
                        showColor.add("#6DB2E3");
                        showType.add("line");
                        dataValue1.add(indexGDPSpeedList);
                        System.out.println("17版块:" + dataValue1.toString());
                        LineType lineType = new LineType();
                        LineEntity lineEntity = lineType.getOption(id, title, xAxis, legend, dataValue1, showColor,
                                showType, unitName);
                        TotalList.add(lineEntity);
                        // 配置表格数据
                        TableType tableType = new TableType();
                        List<List<String>> dataXaisTable = new ArrayList<List<String>>();
                        for (int q = 0; q < 4; q++) {
                            dataXaisTable.add(xAxis);
                        }
                        TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legend, dataValue1);
                        TotalList.add(tableEntity);
                    }
                    if (id.equals("18")) {
                        List<String> dataV = new ArrayList<String>();
                        Double dataFirstValueDouble = 0.0;
                        Double dataSecondValueDouble = 0.0;
                        Double dataThirdValueDouble = 0.0;
                        try {
                            Double dataGDPDouble = Double.parseDouble(indexGDPSpeedList.get(indexGDPSpeedList.size() - 1));
                            Double dataFirstDouble = Double.parseDouble(dataIndiValue1.get(dataIndiValue1.size() - 1));
                            Double dataSecondDouble = Double.parseDouble(dataIndiValue2.get(dataIndiValue2.size() - 1));
                            Double dataThirdDouble = Double.parseDouble(dataIndiValue3.get(dataIndiValue3.size() - 1));

                            dataFirstValueDouble = (dataFirstDouble / dataGDPDouble) * 100;
                            dataSecondValueDouble = (dataSecondDouble / dataGDPDouble) * 100;
                            dataThirdValueDouble = (dataThirdDouble / dataGDPDouble) * 100;
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        legend.add(xAxis.get(xAxis.size() - 1) + "第一产业");
                        showColor.add("#77C87B");
                        dataV.add(String.format("%.2f", dataFirstValueDouble));
                        legend.add(xAxis.get(xAxis.size() - 1) + "第二产业");
                        showColor.add("#545657");
                        dataV.add(String.format("%.2f", dataSecondValueDouble));
                        legend.add(xAxis.get(xAxis.size() - 1) + "第三产业");
                        showColor.add("#F0805F");
                        dataV.add(String.format("%.2f", dataThirdValueDouble));
                        PieType pieType = new PieType();
                        PieEntity pieEntity = pieType.getOption(id, title, dataV, legend, showColor);
                        TotalList.add(pieEntity);
                    }
                    if (id.equals("23") || id.equals("85") || id.equals("86")) {
                        legend.add("第一产业");
                        showColor.add("#77C87B");
                        showType.add("line");
                        dataValue1.add(dataIndiValue11);
                        legend.add("第二产业");
                        showColor.add("#545657");
                        showType.add("line");
                        dataValue1.add(dataIndiValue22);
                        legend.add("第三产业");
                        showColor.add("#F0805F");
                        showType.add("line");
                        dataValue1.add(dataIndiValue33);
                        LineType lineType = new LineType();
                        LineEntity lineEntity = lineType.getOption(id, title, xAxis, legend, dataValue1, showColor,
                                showType, unitName);
                        TotalList.add(lineEntity);
                        // 配置表格数据
                        TableType tableType = new TableType();
                        List<List<String>> dataXaisTable = new ArrayList<List<String>>();
                        for (int q = 0; q < 3; q++) {
                            dataXaisTable.add(xAxis);
                        }
                        TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legend, dataValue1);
                        TotalList.add(tableEntity);
                    }
                    if (id.equals("208")) {
                        List<String> dataV = new ArrayList<String>();
                        legend.add(xAxis.get(xAxis.size() - 1) + "第一产业");
                        showColor.add("#77C87B");
                        dataV.add(dataIndiValue11.get(dataIndiValue11.size() - 1));
                        legend.add(xAxis.get(xAxis.size() - 1) + "第二产业");
                        showColor.add("#545657");
                        dataV.add(dataIndiValue22.get(dataIndiValue22.size() - 1));
                        legend.add(xAxis.get(xAxis.size() - 1) + "第三产业");
                        showColor.add("#F0805F");
                        dataV.add(dataIndiValue33.get(dataIndiValue33.size() - 1));
                        PieType pieType = new PieType();
                        PieEntity pieEntity = pieType.getOption(id, title, dataV, legend, showColor);
                        TotalList.add(pieEntity);
                    }
                    flagPlate = 1;
                }
                break;
                case "93": {
                    System.out.println("进入特殊图例——指标相加");
                    List<List<String>> dataValue = new ArrayList<List<String>>();
                    List<String> legend = new ArrayList<String>();
                    List<String> legendTable = new ArrayList<String>();
                    List<String> showColor = new ArrayList<String>();
                    List<String> showType = new ArrayList<String>();
                    List<String> unitName = new ArrayList<String>();
                    for (int j = 0; j < indiList.size(); j++) {
                        // 处理配置表中配置数据
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
                        legendTable.add(indiList.get(j).getIndiName());
                        showColor.add(indiList.get(j).getShowColor());
                        showType.add(indiList.get(j).getShowType());
                        unitName.add(indiInfoList.get(0).getUnitName());
                    }
                    List<List<String>> dataValue1 = new ArrayList<List<String>>();
                    // 获取核准类项目个数,备案类项目个数,核准类投资总额,备案类投资总额
                    List<String> indexNumberList1 = new ArrayList<String>();
                    List<String> indexNumberList2 = new ArrayList<String>();
                    List<String> indexTotalList1 = new ArrayList<String>();
                    List<String> indexTotalList2 = new ArrayList<String>();
                    for (int j = 0; j < indiList.size(); j++) {
                        String indexCode = indiList.get(j).getIndiCode().toString();
                        // 核准类项目个数
                        if (indexCode.equals("HBTJ0797;400:101585152;363:706401;62:42")) {
                            indexNumberList1 = dataValue.get(j);
                        }
                        // 备案类项目个数
                        if (indexCode.equals("HBTJ0798;400:101585152;363:706401;62:42")) {
                            indexNumberList2 = dataValue.get(j);
                        }
                        // 核准类投资总额
                        if (indexCode.equals("HBTJ0793;400:101585152;363:706401;62:42")) {
                            indexTotalList1 = dataValue.get(j);
                        }
                        // 备案类投资总额
                        if (indexCode.equals("HBTJ0794;400:101585152;363:706401;62:42")) {
                            indexTotalList2 = dataValue.get(j);
                        }
                    }
                    // 进行加法
                    if (id.equals("93")) {
                        List<String> dataIndiValue1 = new ArrayList<String>();
                        for (int j = 0; j < indexNumberList1.size(); j++) {
                            try {
                                Double dataValueDouble = Double.parseDouble(indexNumberList1.get(j))
                                        + Double.parseDouble(indexNumberList2.get(j));
                                dataIndiValue1.add(String.format("%.2f", dataValueDouble));
                            } catch (Exception e) {
                                dataIndiValue1.add("0.00");
                            }

                        }
                        dataValue1.add(dataIndiValue1);
                        legend.add("项目核准备案个数_当期值");

                        List<String> dataIndiValue2 = new ArrayList<String>();
                        for (int j = 0; j < indexTotalList2.size(); j++) {
                            try {
                                Double dataValueDouble = Double.parseDouble(indexTotalList1.get(j))
                                        + Double.parseDouble(indexTotalList2.get(j));
                                dataIndiValue2.add(String.format("%.2f", dataValueDouble));
                            } catch (Exception e) {
                                dataIndiValue2.add("0.00");
                            }

                        }
                        dataValue1.add(dataIndiValue2);
                        legend.add("项目核准备案金额_当期值");
                    }
                    // 配置指标图例
                    switch (analysisPlate.get(i).getShowType()) {
                        case "折线图": {
                            LineType lineType = new LineType();
                            LineEntity lineEntity = lineType.getOption(id, title, xAxis, legend, dataValue1, showColor,
                                    showType, unitName);
                            TotalList.add(lineEntity);
                            break;
                        }
                        case "柱状图": {
                            BarType barType = new BarType();
                            BarEntity barEntity = barType.getOption(id, title, xAxis, legend, dataValue1, showColor, showType, unitName);
                            TotalList.add(barEntity);
                            break;
                        }
                        default:
                            System.out.println("未知图例无法求指标相减值" + analysisPlate.get(i).getShowType());
                            break;
                    }
                    // 配置表格数据
                    TableType tableType = new TableType();
                    List<List<String>> dataXaisTable = new ArrayList<List<String>>();
                    // 表格依然是展示指标计算后的数据，因此数量要除2
                    for (int q = 0; q < indiList.size() / 2; q++) {
                        dataXaisTable.add(xAxis);
                    }
                    TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legend, dataValue1);
                    TotalList.add(tableEntity);
                    flagPlate = 1;
                }
                break;
                case "29":
                case "30": {
                    System.out.println("进入特殊图例——指标相减");
                    List<List<String>> dataValue = new ArrayList<List<String>>();
                    List<String> legend = new ArrayList<String>();
                    List<String> legendTable = new ArrayList<String>();
                    List<String> showColor = new ArrayList<String>();
                    List<String> showType = new ArrayList<String>();
                    List<String> unitName = new ArrayList<String>();
                    for (int j = 0; j < indiList.size(); j++) {
                        // 处理配置表中配置数据
                        queryMap.put("indiCode", indiList.get(j).getIndiCode());
                        List<AnalysisIndiValue> indiInfoList = analysisMapper.getIndiValue(queryMap);
                        List<String> dataIndiValue = Arrays.asList(new String[xAxis.size()]);
                        for (int m = 0; m < indiInfoList.size(); m++) {
                            String dataXTemp = indiInfoList.get(m).getTime();
                            if (xAxis.contains(dataXTemp)) {
                                int index = xAxis.indexOf(dataXTemp);
                                Double dataValueDouble = Double.parseDouble(indiInfoList.get(m).getIndiValue()) - 100.00;
                                dataIndiValue.set(index, String.format("%.2f", dataValueDouble));
                                // dataIndiValue.set(index, indiInfoList.get(m).getIndiValue());
                            }
                        }
                        dataValue.add(dataIndiValue);
                        legendTable.add(indiList.get(j).getIndiName());
                        showColor.add(indiList.get(j).getShowColor());
                        showType.add(indiList.get(j).getShowType());
                        unitName.add(indiInfoList.get(0).getUnitName());
                    }
                    List<List<String>> dataValue1 = new ArrayList<List<String>>();
                    // 获取CPI/PPI/IPI
                    List<String> indexCPIList = new ArrayList<String>();
                    List<String> indexPPIList = new ArrayList<String>();
                    List<String> indexIPIList = new ArrayList<String>();
                    for (int j = 0; j < indiList.size(); j++) {
                        String indexCode = indiList.get(j).getIndiCode().toString();
                        // CPI
                        if (indexCode.equals("JG0301;400:101585152;363:706401;62:42")) {
                            indexCPIList = dataValue.get(j);
                        }
                        // PPI
                        if (indexCode.equals("JG040101;400:101585152;363:706401;62:42")) {
                            indexPPIList = dataValue.get(j);
                        }
                        // IPI
                        if (indexCode.equals("JG040102;400:101585152;363:706401;62:42")) {
                            indexIPIList = dataValue.get(j);
                        }
                    }
                    // 进行减法
                    if (id.equals("29")) {
                        List<String> dataIndiValue = new ArrayList<String>();
                        for (int j = 0; j < indexCPIList.size(); j++) {
                            try {
                                Double dataValueDouble = Double.parseDouble(indexCPIList.get(j))
                                        - Double.parseDouble(indexPPIList.get(j));
                                dataIndiValue.add(String.format("%.2f", dataValueDouble));
                            } catch (Exception e) {
                                dataIndiValue.add("0.00");
                                System.out.println("第" + j + "列" + "double数据转换计算null错误:" + indexCPIList.toString()
                                        + indexPPIList.toString());
                            }
                        }
                        dataValue1.add(dataIndiValue);
                        System.out.println("dataValue-11111:" + dataValue);
                        dataValue.add(dataIndiValue);
                        System.out.println("dataValue-22222:" + dataValue);
                        legend.add("CPI-PPI_同比剪刀差");
                        legendTable.add("CPI-PPI_同比剪刀差");
                    }
                    if (id.equals("30")) {
                        List<String> dataIndiValue = new ArrayList<String>();
                        for (int j = 0; j < indexPPIList.size(); j++) {
                            try {
                                Double dataValueDouble = Double.parseDouble(indexPPIList.get(j))
                                        - Double.parseDouble(indexIPIList.get(j));
                                dataIndiValue.add(String.format("%.2f", dataValueDouble));
                            } catch (NullPointerException e) {
                                dataIndiValue.add("0.00");
                                System.out.println("第" + j + "列" + "double数据转换计算null错误:" + indexPPIList.toString()
                                        + indexIPIList.toString());
                            }
                        }
                        dataValue1.add(dataIndiValue);
                        dataValue.add(dataIndiValue);
                        legend.add("PPI-IPI_同比剪刀差");
                        legendTable.add("PPI-IPI_同比剪刀差");
                    }
                    // 配置指标图例
                    switch (analysisPlate.get(i).getShowType()) {
                        case "折线图": {
                            LineType lineType = new LineType();
                            LineEntity lineEntity = lineType.getOption(id, title, xAxis, legend, dataValue1, showColor,
                                    showType, unitName);
                            TotalList.add(lineEntity);
                            break;
                        }
                        case "柱状图": {
                            BarType barType = new BarType();
                            BarEntity barEntity = barType.getOption(id, title, xAxis, legend, dataValue1, showColor, showType, unitName);
                            TotalList.add(barEntity);
                            break;
                        }
                        default:
                            System.out.println("未知图例无法求指标相减值" + analysisPlate.get(i).getShowType());
                            break;
                    }
                    // 配置表格数据
                    TableType tableType = new TableType();
                    List<List<String>> dataXaisTable = new ArrayList<List<String>>();
                    // 表格依然是展示指标原始数据
                    for (int q = 0; q < indiList.size() + 1; q++) {
                        dataXaisTable.add(xAxis);
                    }
                    TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legendTable, dataValue);
                    TotalList.add(tableEntity);
                    flagPlate = 1;
                }
                break;

                case "33":
                case "168":
                case "170":
                case "171":
                case "227":
                case "178":
                case "179":
                case "180": {
                    System.out.println("进入特殊图例——指标相除");
                    List<List<String>> dataValue = new ArrayList<List<String>>();
                    List<String> legend = new ArrayList<String>();
                    List<String> legendTable = new ArrayList<String>();
                    List<String> showColor = new ArrayList<String>();
                    List<String> showType = new ArrayList<String>();
                    List<String> unitName = new ArrayList<String>();
                    for (int j = 0; j < indiList.size(); j++) {
                        // 处理配置表中配置数据
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
                        legendTable.add(indiList.get(j).getIndiName());
                        showColor.add(indiList.get(j).getShowColor());
                        showType.add(indiList.get(j).getShowType());
                        unitName.add("");// 指标相除不带单位！
                    }

                    // 处理指标相除
                    List<List<String>> dataValue1 = new ArrayList<List<String>>();

                    if (id.equals("33")) {
                        List<String> indexList1 = new ArrayList<String>();
                        List<String> indexList2 = new ArrayList<String>();
                        for (int j = 0; j < indiList.size(); j++) {
                            String indexCode = indiList.get(j).getIndiCode().toString();
                            // 除数-M1
                            if (indexCode.equals("JYBX090101;400:706501;363:100000060;62:1")) {
                                indexList1 = dataValue.get(j);
                            }
                            // 被除数-M2
                            if (indexCode.equals("JYBX0901;400:706501;363:100000060;62:1")) {
                                indexList2 = dataValue.get(j);
                            }
                        }
                        // 进行相除
                        List<String> dataIndiValue = new ArrayList<String>();
                        for (int j = 0; j < indexList1.size(); j++) {
                            try {
                                Double dataValueDouble = Double.parseDouble(indexList1.get(j))
                                        / Double.parseDouble(indexList2.get(j));
                                dataIndiValue.add(String.format("%.2f", dataValueDouble));
                            } catch (Exception e) {
                                dataIndiValue.add(null);
                            }

                        }
                        dataValue1.add(dataIndiValue);
                        legend.add("M1/M2");
                        dataValue.add(dataIndiValue);
                        legendTable.add("M1/M2");
                    }
                    if (id.equals("168")) {
                        List<String> indexList1 = new ArrayList<String>();
                        List<String> indexList2 = new ArrayList<String>();
                        for (int j = 0; j < indiList.size(); j++) {
                            String indexCode = indiList.get(j).getIndiCode().toString();
                            // 除数-专利批准量(项)
                            if (indexCode.equals("HBTJ0843;400:101585152;363:706401;62:42")) {
                                indexList1 = dataValue.get(j);
                            }
                            // 被除数-常住人口(万人)
                            if (indexCode.equals("SH030201;400:101585152;363:706401;62:42")) {
                                indexList2 = dataValue.get(j);
                            }
                        }
                        // 进行相除
                        List<String> dataIndiValue = new ArrayList<String>();
                        for (int j = 0; j < indexList1.size(); j++) {
                            try {
                                Double dataValueDouble = Double.parseDouble(indexList1.get(j))
                                        / Double.parseDouble(indexList2.get(j));
                                dataIndiValue.add(String.format("%.2f", dataValueDouble));
                            } catch (Exception e) {
                                dataIndiValue.add(null);
                            }

                        }
                        dataValue1.add(dataIndiValue);
                        legend.add("每万人口发明专利拥有量(项)");
                        dataValue.add(dataIndiValue);
                        legendTable.add("每万人口发明专利拥有量(项)");
                    }
                    if (id.equals("170")) {
                        List<String> indexList1 = new ArrayList<String>();
                        List<String> indexList2 = new ArrayList<String>();
                        for (int j = 0; j < indiList.size(); j++) {
                            String indexCode = indiList.get(j).getIndiCode().toString();
                            // 除数-科技活动人员数(人)
                            if (indexCode.equals("KJ0102;400:101585152;363:706401;62:42")) {
                                indexList1 = dataValue.get(j);
                            }
                            // 被除数-常住人口(万人)
                            if (indexCode.equals("SH030201;400:101585152;363:706401;62:42")) {
                                indexList2 = dataValue.get(j);
                            }
                        }
                        // 进行相除
                        List<String> dataIndiValue = new ArrayList<String>();
                        for (int j = 0; j < indexList1.size(); j++) {
                            try {
                                Double dataValueDouble = Double.parseDouble(indexList1.get(j))
                                        / Double.parseDouble(indexList2.get(j));
                                if (dataValueDouble == 0.00) {
                                    dataIndiValue.add("-");
                                } else {
                                    dataIndiValue.add(String.format("%.2f", dataValueDouble));
                                }
                            } catch (Exception e) {
                                dataIndiValue.add(null);
                            }
                        }
                        dataValue1.add(dataIndiValue);
                        legend.add("千人拥有研发人员数(人)");
                        dataValue.add(dataIndiValue);
                        legendTable.add("千人拥有研发人员数(人)");
                    }
                    if (id.equals("171")) {
                        List<String> indexList1 = new ArrayList<String>();
                        List<String> indexList2 = new ArrayList<String>();
                        for (int j = 0; j < indiList.size(); j++) {
                            String indexCode = indiList.get(j).getIndiCode().toString();
                            // 除数-研究与发展经费内部支出额(万元)
                            if (indexCode.equals("KJ010404;400:101585152;363:706401;62:42")) {
                                indexList1 = dataValue.get(j);
                            }
                            // 被除数-支出法地区生产总值(亿元)
                            if (indexCode.equals("GM0301;400:101585152;62:42")) {
                                indexList2 = dataValue.get(j);
                            }
                        }
                        // 进行相除
                        List<String> dataIndiValue = new ArrayList<String>();
                        for (int j = 0; j < indexList1.size(); j++) {
                            try {
                                // 这里要多除以100,因为计算的是百分比,而且单位是万元与亿元
                                Double dataValueDouble = Double.parseDouble(indexList1.get(j))
                                        / (Double.parseDouble(indexList2.get(j)) * 100);
                                dataIndiValue.add(String.format("%.2f", dataValueDouble));
                            } catch (Exception e) {
                                dataIndiValue.add(null);
                            }

                        }
                        dataValue1.add(dataIndiValue);
                        legend.add("R&D经费支出占GDP比重(%)");
                        dataValue.add(dataIndiValue);
                        legendTable.add("R&D经费支出占GDP比重(%)");
                    }
                    if (id.equals("227")) {
                        List<String> indexList1 = new ArrayList<String>();
                        List<String> indexList2 = new ArrayList<String>();
                        for (int j = 0; j < indiList.size(); j++) {
                            String indexCode = indiList.get(j).getIndiCode().toString();
                            // 除数-高新技术产业增加值
                            if (indexCode.equals("HBTJ0200;400:101585152;363:706403;62:42")) {
                                indexList1 = dataValue.get(j);
                            }
                            // 被除数-地区生产总值
                            if (indexCode.equals("GM0101;400:101585152;363:706403;62:42")) {
                                indexList2 = dataValue.get(j);
                            }
                        }
                        // 进行相除
                        List<String> dataIndiValue = new ArrayList<String>();
                        for (int j = 0; j < indexList1.size(); j++) {
                            try {
                                // 这里要多乘100,因为求的是百分比
                                Double dataValueDouble = Double.parseDouble(indexList1.get(j))
                                        / Double.parseDouble(indexList2.get(j)) * 100;
                                dataIndiValue.add(String.format("%.2f", dataValueDouble));
                            } catch (Exception e) {
                                dataIndiValue.add(null);
                            }

                        }
                        dataValue1.add(dataIndiValue);
                        legend.add("高新技术产业增加值占GDP比重(%)");
                        dataValue.add(dataIndiValue);
                        legendTable.add("高新技术产业增加值占GDP比重(%)");
                    }
                    if (id.equals("178")) {
                        List<String> indexList1 = new ArrayList<String>();
                        List<String> indexList2 = new ArrayList<String>();
                        for (int j = 0; j < indiList.size(); j++) {
                            String indexCode = indiList.get(j).getIndiCode().toString();
                            // 除数-能源消费总量
                            if (indexCode.equals("NYUAN0306;400:101585152;363:706401;62:42")) {
                                indexList1 = dataValue.get(j);
                            }
                            // 被除数-地区生产总值
                            if (indexCode.equals("GM0101;400:101585152;62:42")) {
                                indexList2 = dataValue.get(j);
                            }
                        }
                        // 进行相除
                        List<String> dataIndiValue = new ArrayList<String>();
                        for (int j = 0; j < indexList1.size(); j++) {
                            try {
                                Double dataValueDouble = Double.parseDouble(indexList1.get(j))
                                        / Double.parseDouble(indexList2.get(j));
                                dataIndiValue.add(String.format("%.2f", dataValueDouble));
                            } catch (Exception e) {
                                dataIndiValue.add(null);
                            }

                        }
                        dataValue1.add(dataIndiValue);
                        legend.add("单位生产总值能耗(吨标准煤/万元)");
                        dataValue.add(dataIndiValue);
                        legendTable.add("单位生产总值能耗(吨标准煤/万元)");
                    }
                    if (id.equals("179")) {
                        List<String> indexList1 = new ArrayList<String>();
                        List<String> indexList2 = new ArrayList<String>();
                        for (int j = 0; j < indiList.size(); j++) {
                            String indexCode = indiList.get(j).getIndiCode().toString();
                            // 除数-全社会用电量
                            if (indexCode.equals("NYUAN0601;400:101585152;363:706401;62:42")) {
                                indexList1 = dataValue.get(j);
                            }
                            // 被除数-地区生产总值
                            if (indexCode.equals("GM0101;400:101585152;62:42")) {
                                indexList2 = dataValue.get(j);
                            }
                        }
                        // 进行相除
                        List<String> dataIndiValue = new ArrayList<String>();
                        for (int j = 0; j < indexList1.size(); j++) {
                            try {
                                Double dataValueDouble = Double.parseDouble(indexList1.get(j))
                                        / Double.parseDouble(indexList2.get(j));
                                dataIndiValue.add(String.format("%.2f", dataValueDouble));
                            } catch (Exception e) {
                                dataIndiValue.add(null);
                            }

                        }
                        dataValue1.add(dataIndiValue);
                        legend.add("单位生产总值全社会用电量(亿千瓦时/亿元)");
                        dataValue.add(dataIndiValue);
                        legendTable.add("单位生产总值全社会用电量(亿千瓦时/亿元)");
                    }
                    if (id.equals("180")) {
                        List<String> indexList1 = new ArrayList<String>();
                        List<String> indexList2 = new ArrayList<String>();
                        for (int j = 0; j < indiList.size(); j++) {
                            String indexCode = indiList.get(j).getIndiCode().toString();
                            // 除数-能源消费总量
                            if (indexCode.equals("NYUAN0306;400:101585152;363:706401;62:42")) {
                                indexList1 = dataValue.get(j);
                            }
                            // 被除数-地区生产总值
                            if (indexCode.equals("GM0101;400:101585152;62:42")) {
                                indexList2 = dataValue.get(j);
                            }
                        }
                        // 进行相除
                        List<String> dataIndiValue = new ArrayList<String>();
                        for (int j = 0; j < indexList1.size(); j++) {
                            try {
                                Double dataValueDouble = Double.parseDouble(indexList1.get(j))
                                        / Double.parseDouble(indexList2.get(j));
                                dataIndiValue.add(String.format("%.2f", dataValueDouble));
                            } catch (Exception e) {
                                dataIndiValue.add(null);
                            }

                        }
                        dataValue1.add(dataIndiValue);
                        legend.add("单位生产总值煤炭消耗量(万吨标准煤/亿元)");
                        dataValue.add(dataIndiValue);
                        legendTable.add("单位生产总值煤炭消耗量(万吨标准煤/亿元)");
                    }
                    // 配置指标图例
                    switch (analysisPlate.get(i).getShowType()) {
                        case "折线图": {
                            LineType lineType = new LineType();
                            LineEntity lineEntity = lineType.getOption(id, title, xAxis, legend, dataValue1, showColor,
                                    showType, unitName);
                            TotalList.add(lineEntity);
                            break;
                        }
                        case "柱状图": {
                            BarType barType = new BarType();
                            BarEntity barEntity = barType.getOption(id, title, xAxis, legend, dataValue1, showColor, showType, unitName);
                            TotalList.add(barEntity);
                            break;
                        }
                        default:
                            System.out.println("未知图例无法求指标相除值" + analysisPlate.get(i).getShowType());
                            break;
                    }
                    // 配置表格数据
                    TableType tableType = new TableType();
                    List<List<String>> dataXaisTable = new ArrayList<List<String>>();
                    // 表格依然是展示指标原始数据
                    for (int q = 0; q < indiList.size() + 1; q++) {
                        dataXaisTable.add(xAxis);
                    }
                    TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legendTable, dataValue);
                    TotalList.add(tableEntity);
                    flagPlate = 1;
                }
                break;

                case "58":
                case "59":
                case "60":
                case "61":
                case "62":
                case "63":
                case "64":
                case "65":
                case "66":
                case "67":
                case "131":
                case "132":
                case "133":
                case "134":
                case "135":
                case "141":
                case "146":
                case "164": {
                    System.out.println("进入特殊图例——去年同期比较");
                    List<List<String>> dataValue = new ArrayList<List<String>>();
                    List<String> legend = new ArrayList<String>();
                    List<String> showColor = new ArrayList<String>();
                    List<String> showType = new ArrayList<String>();
                    List<String> unitName = new ArrayList<String>();

                    for (int j = 0; j < indiList.size(); j++) {
                        // 处理配置表中配置数据
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
                        showColor.add(indiList.get(j).getShowColor());
                        showType.add(indiList.get(j).getShowType());

                        // 获取上年同期数据
                        // 配置新的queryMap查询条件
                        String freqName = queryMap.get("freqName").toString();
                        String startTime = queryMap.get("startTime").toString();
                        String endTime = queryMap.get("endTime").toString();
                        Map<String, Object> queryMap1 = this.calTimeQueryMap(freqName, startTime, endTime);
                        queryMap1.put("indiCode", indiList.get(j).getIndiCode());
                        System.out.println("特殊图例同期配置项：" + queryMap1.toString());
                        // 处理上年同期数据
                        List<AnalysisIndiValue> indiInfoList1 = analysisMapper.getIndiValue(queryMap1);
                        List<String> dataIndiValue1 = Arrays.asList(new String[xAxis.size()]);
                        List<String> xAxis1 = this.fillTimeList(queryMap1.get("freqName").toString(),
                                queryMap1.get("startTime").toString(), queryMap1.get("endTime").toString());
                        for (int m = 0; m < indiInfoList1.size(); m++) {
                            String dataXTemp = indiInfoList1.get(m).getTime();
                            if (xAxis1.contains(dataXTemp)) {
                                int index = xAxis1.indexOf(dataXTemp);
                                dataIndiValue1.set(index, indiInfoList1.get(m).getIndiValue());
                            }
                        }
                        dataValue.add(dataIndiValue1);
                        legend.add(indiList.get(j).getIndiName() + "去年同期");
                        showColor.add(indiList.get(j).getShowColor());
                        showType.add(indiList.get(j).getShowType());
//					unitName.add(indiInfoList.get(0).getUnitName());
                        if (indiInfoList.size() > 0) {
                            unitName.add(indiInfoList.get(0).getUnitName());
                        } else {
                            unitName.add(null);
                        }
                    }
                    // 配置指标图例——柱状
                    switch (analysisPlate.get(i).getShowType()) {
                        case "折线图": {
                            LineType lineType = new LineType();
                            LineEntity lineEntity = lineType.getOption(id, title, xAxis, legend, dataValue, showColor, showType,
                                    unitName);
                            TotalList.add(lineEntity);
                            break;
                        }
                        case "柱状图": {
                            BarType barType = new BarType();
                            BarEntity barEntity = barType.getOption(id, title, xAxis, legend, dataValue, showColor, showType, unitName);
                            TotalList.add(barEntity);
                            break;
                        }
                        default:
                            System.out.println("未知图例无法求上年同期值" + analysisPlate.get(i).getShowType());
                            break;
                    }
                    // 配置表格数据
                    TableType tableType = new TableType();
                    List<List<String>> dataXaisTable = new ArrayList<List<String>>();
                    // 需要多循环去年同期的数据
                    for (int q = 0; q < indiList.size() * 2; q++) {
                        dataXaisTable.add(xAxis);
                    }
                    TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legend, dataValue);
                    TotalList.add(tableEntity);
                    flagPlate = 1;
                }
                break;
                default:
                    break;
            }

            // 如果是特殊图例，就不进行常规图例的判断
            if (flagPlate != 0) {
                continue;
            }

            // 对常规图例类型进行配置
            switch (analysisPlate.get(i).getShowType()) {
                case "折线图": {
                    System.out.println("进入折线图");
                    List<List<String>> dataValue = new ArrayList<List<String>>();
                    List<String> legend = new ArrayList<String>();
                    List<String> showColor = new ArrayList<String>();
                    List<String> showType = new ArrayList<String>();
                    List<String> unitName = new ArrayList<String>();
                    // 配置指标图例
                    LineType lineType = new LineType();
                    for (int j = 0; j < indiList.size(); j++) {
                        queryMap.put("indiCode", indiList.get(j).getIndiCode());
                        List<AnalysisIndiValue> indiInfoList = analysisMapper.getIndiValue(queryMap);
                        List<String> dataIndiValue = Arrays.asList(new String[xAxis.size()]);
                        for (int m = 0; m < indiInfoList.size(); m++) {
                            String dataXTemp = indiInfoList.get(m).getTime();
                            if (xAxis.contains(dataXTemp)) {
                                int index = xAxis.indexOf(dataXTemp);
                                if (title.contains("互联网大数据") || title.contains("新经济行业岗位占比")) {
                                    // 167-新经济行业岗位占比-折线图
                                    Double dataValueDouble = Double.parseDouble(indiInfoList.get(m).getIndiValue());
                                    System.out.println(String.format("%.2f", dataValueDouble));
                                    dataIndiValue.set(index, String.format("%.2f", dataValueDouble));
                                } else {
                                    dataIndiValue.set(index, indiInfoList.get(m).getIndiValue());
                                }
                            }
                        }
                        dataValue.add(dataIndiValue);
                        legend.add(indiList.get(j).getIndiName());
                        showColor.add(indiList.get(j).getShowColor());
                        showType.add(indiList.get(j).getShowType());
                        unitName.add(indiInfoList.get(0).getUnitName());
                    }
                    System.out.println(unitName.toString());
                    LineEntity lineEntity = lineType.getOption(id, title, xAxis, legend, dataValue, showColor, showType,
                            unitName);
                    TotalList.add(lineEntity);
                    // 配置表格数据
                    TableType tableType = new TableType();
                    List<List<String>> dataXaisTable = new ArrayList<List<String>>();
                    for (int q = 0; q < indiList.size(); q++) {
                        dataXaisTable.add(xAxis);
                    }
                    TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legend, dataValue);
                    TotalList.add(tableEntity);
                }
                break;
                case "柱状图": {
                    System.out.println("进入柱状图");
                    List<List<String>> dataValue = new ArrayList<List<String>>();
                    List<String> legend = new ArrayList<String>();
                    List<String> showColor = new ArrayList<String>();
                    List<String> showType = new ArrayList<String>();
                    List<String> unitName = new ArrayList<String>();
                    // 配置指标图例
                    BarType barType = new BarType();
                    for (int j = 0; j < indiList.size(); j++) {
                        queryMap.put("indiCode", indiList.get(j).getIndiCode());
                        List<AnalysisIndiValue> indiInfoList = analysisMapper.getIndiValue(queryMap);
                        List<String> dataIndiValue = Arrays.asList(new String[xAxis.size()]);
                        for (int m = 0; m < indiInfoList.size(); m++) {
                            String dataXTemp = indiInfoList.get(m).getTime();
                            if (xAxis.contains(dataXTemp)) {
                                int index = xAxis.indexOf(dataXTemp);
                                if (title.contains("互联网大数据")) {
                                    // 97-中长期贷款余额-柱状图
                                    // 166-新经济行业风险投资比例（互联网大数据）-柱状图
                                    Double dataValueDouble = Double.parseDouble(indiInfoList.get(m).getIndiValue());
                                    System.out.println(String.format("%.2f", dataValueDouble));
                                    dataIndiValue.set(index, String.format("%.2f", dataValueDouble));
                                } else {
                                    dataIndiValue.set(index, indiInfoList.get(m).getIndiValue());
                                }
                            }
                        }
                        dataValue.add(dataIndiValue);
                        legend.add(indiList.get(j).getIndiName());
                        showColor.add(indiList.get(j).getShowColor());
                        showType.add(indiList.get(j).getShowType());
//					unitName.add(indiInfoList.get(0).getUnitName());
                        if (indiInfoList.size() > 0) {
                            unitName.add(indiInfoList.get(0).getUnitName());
                        } else {
                            unitName.add(null);
                        }
                    }
                    System.out.println(unitName.toString());
                    BarEntity barEntity = barType.getOption(id, title, xAxis, legend, dataValue, showColor, showType, unitName);
                    TotalList.add(barEntity);
                    // 配置表格数据
                    TableType tableType = new TableType();
                    List<List<String>> dataXaisTable = new ArrayList<List<String>>();
                    for (int q = 0; q < indiList.size(); q++) {
                        dataXaisTable.add(xAxis);
                    }
                    TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legend, dataValue);
                    TotalList.add(tableEntity);
                }
                break;
                case "雷达图": {
                    System.out.println("进入雷达图");
                    List<List<String>> dataValue = new ArrayList<List<String>>();
                    List<String> dataName = new ArrayList<String>();
                    List<List<String>> dataByTime = new ArrayList<List<String>>();
                    // 配置指标图例
                    RadarType radarType = new RadarType();
                    // 雷达图取最近的两期数据进行展示
                    List<String> xAxisRadar = xAxis.subList(xAxis.size() - 3, xAxis.size());
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
                                dataIndiValue.set(index, indiInfoList.get(m).getIndiValue());
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
                    List<String> showColor = new ArrayList<String>();
                    // 配置指标图例
                    PieType pieType = new PieType();
                    for (int j = 0; j < indiList.size(); j++) {
                        String indiName = indiList.get(j).getIndiName().toString();
                        System.out.println(indiName);
                        // 饼状图只有一个指标，不用for循环
                        Map<String, Object> queryMapPie = new HashMap<String, Object>();
                        queryMapPie.put("freqName", queryMap.get("freqName"));
                        queryMapPie.put("startTime", queryMap.get("endTime"));
                        queryMapPie.put("endTime", queryMap.get("endTime"));
                        queryMapPie.put("indiCode", indiList.get(j).getIndiCode());
                        List<AnalysisIndiValue> indiInfoList = analysisMapper.getIndiValue(queryMapPie);
                        String indiValue = "无数据";
                        if (indiInfoList.size() > 0) {
                            indiValue = indiInfoList.get(0).getIndiValue();
                        }
                        legend.add(j, indiName);
                        dataV.add(j, indiValue);
                        showColor.add(indiList.get(j).getShowColor());
                    }
                    PieEntity pieEntity = pieType.getOption(id, title, dataV, legend, showColor);
                    TotalList.add(pieEntity);
                }
                break;
                case "散点图": {
                    System.out.println("进入散点图");
                    List<List<String>> dataValue = new ArrayList<List<String>>();
                    List<String> legend = new ArrayList<String>();
                    // 配置指标图例
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
                    List<String> showColor = new ArrayList<String>();
                    List<String> showType = new ArrayList<String>();
                    List<String> unitName = new ArrayList<String>();
                    // 配置指标图例
                    LineAndBarType lineAndBarType = new LineAndBarType();
                    for (int j = 0; j < indiList.size(); j++) {
                        queryMap.put("indiCode", indiList.get(j).getIndiCode());
                        List<AnalysisIndiValue> indiInfoList = analysisMapper.getIndiValue(queryMap);
                        List<String> dataIndiValue = Arrays.asList(new String[xAxis.size()]);
                        for (int m = 0; m < indiInfoList.size(); m++) {
                            String dataXTemp = indiInfoList.get(m).getTime();
                            if (xAxis.contains(dataXTemp)) {
                                int index = xAxis.indexOf(dataXTemp);
                                if (indiList.get(j).getIndiCode().toString()
                                        .equals("SCZT010201;400:101585152;363:706403;62:42")) {
                                    // 外商投资企业总数-万户
                                    Double dataValueDouble = Double.parseDouble(indiInfoList.get(m).getIndiValue()) * 10000;
                                    dataIndiValue.set(index, String.valueOf(dataValueDouble));
                                } else {
                                    dataIndiValue.set(index, indiInfoList.get(m).getIndiValue());
                                }
                            }
                        }
                        dataValue.add(dataIndiValue);
                        legend.add(indiList.get(j).getIndiName());
                        showColor.add(indiList.get(j).getShowColor());
                        showType.add(indiList.get(j).getShowType());
                        unitName.add(indiInfoList.get(0).getUnitName());
                    }
                    System.out.println(unitName.toString());
                    LineAndBarEntity lineAndBarEntity = lineAndBarType.getOption(id, title, xAxis, legend, dataValue,
                            showColor, showType, unitName);
                    TotalList.add(lineAndBarEntity);
                    // 配置表格数据
                    TableType tableType = new TableType();
                    List<List<String>> dataXaisTable = new ArrayList<List<String>>();
                    for (int q = 0; q < indiList.size(); q++) {
                        dataXaisTable.add(xAxis);
                    }
                    TableEntity tableEntity = tableType.getTable(id, title, dataXaisTable, legend, dataValue);
                    TotalList.add(tableEntity);
                }
                break;
                case "柱状堆积图": {
                    System.out.println("进入柱状堆叠图");
                    List<List<String>> dataValue = new ArrayList<List<String>>();
                    List<String> legend = new ArrayList<String>();
                    List<String> showColor = new ArrayList<String>();
                    List<String> showType = new ArrayList<String>();
                    List<String> unitName = new ArrayList<String>();
                    // 配置指标图例
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
                        legend.add(indiList.get(j).getIndiName());
                        showColor.add(indiList.get(j).getShowColor());
                        showType.add(indiList.get(j).getShowType());
                        unitName.add(indiInfoList.get(0).getUnitName());
                    }
                    System.out.println(unitName.toString());
                    BarStoreEntity barStoreEntity = barStoreType.getOption(id, title, xAxis, legend, dataValue, showColor,
                            showType, unitName);
                    TotalList.add(barStoreEntity);
                    // 配置表格数据
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
                    List<String> showColor = new ArrayList<String>();
                    List<String> showType = new ArrayList<String>();
                    List<String> unitName = new ArrayList<String>();
                    // 配置指标图例
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
                        showColor.add(indiList.get(j).getShowColor());
                        showType.add(indiList.get(j).getShowType());
                        unitName.add(indiInfoList.get(0).getUnitName());
                    }
                    System.out.println(unitName.toString());
                    BarStackLineEntity barStackLineEntity = barStackLineType.getOption(id, title, xAxis, legend, dataValue,
                            showColor, showType, unitName);
                    TotalList.add(barStackLineEntity);
                    // 配置表格数据
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
                    System.out.println("未匹配到图例函数");
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

    public Map<String, Object> calTimeQueryMap(String freqName, String startTime, String endTime) {
        Map<String, Object> queryMap1 = new HashMap<String, Object>();
        String startTime1 = "";
        String endTime1 = "";
        switch (freqName) {
            case "月度":
                int startYear1 = Integer.parseInt(startTime.substring(0, 4));
                int startMonth1 = Integer.parseInt(startTime.substring(5, 7));
                int endYear1 = Integer.parseInt(endTime.substring(0, 4));
                int endMonth1 = Integer.parseInt(endTime.substring(5, 7));
                startTime1 = String.valueOf(startYear1 - 1) + '/' + String.format("%02d", Integer.valueOf(startMonth1));
                endTime1 = String.valueOf(endYear1 - 1) + '/' + String.format("%02d", Integer.valueOf(endMonth1));
                break;
            case "季度":
                int startYear2 = Integer.parseInt(startTime.substring(0, 4));
                int startQuarter2 = Integer.parseInt(startTime.substring(6, 7));
                int endYear2 = Integer.parseInt(endTime.substring(0, 4));
                int endQuarter2 = Integer.parseInt(endTime.substring(6, 7));
                startTime1 = String.valueOf(startYear2 - 1) + "/Q" + String.valueOf(startQuarter2);
                endTime1 = String.valueOf(endYear2 - 1) + "/Q" + String.valueOf(endQuarter2);
                break;
            case "年度":
                int startYear3 = Integer.parseInt(startTime.substring(0, 4));
                int endYear3 = Integer.parseInt(endTime.substring(0, 4));
                startTime1 = String.valueOf(startYear3 - 1);
                endTime1 = String.valueOf(endYear3 - 1);
                break;
            default:
                break;
        }
        queryMap1.put("freqName", freqName);
        queryMap1.put("startTime", startTime1);
        queryMap1.put("endTime", endTime1);
        return queryMap1;
    }

}