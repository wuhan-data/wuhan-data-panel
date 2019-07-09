package com.wuhan_data.app.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.ColPlate;
import com.wuhan_data.pojo.ColPlateIndi;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.indi_TF;

public interface PlateInfoMapper {
	List<ColPlate> getPlateInfo(int indexid);//获取大板块信息
	List<IndiCorrelative> getIndiCorrelative(int indexid);//获取相关指标
	List<ColPlateIndi> getIndiByPid(int pid);//获取板块下的指标ID与指标name
	List<indi_TF> getIndiInfoByTime(Map map);//根据时间、频度、获取相应指标信息
//	String freq_code,String indi_code,int time_point,String source
	List<String> getDateCodeByFreq(Map map);//根据频度,指标的代码，时点，数据来源 获取时间信息
	
	//*************************************************************//
	//根据indi_id获得频度列表
	List<String> getFreqCodeByIndiId(String indi_code);
	//获取指标show_type
	String getIndiShowType(String indi_code);
	
	
	
}
