package com.wuhan_data.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.IndiAll;
import com.wuhan_data.service.DbToExcelService;

import cn.hutool.core.date.DateTime;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("")
public class DbToExcelController {
	@Autowired
	DbToExcelService dbToExcelService;

	@RequestMapping(value = "dbToEcxel", produces = "text/plain;charset=utf-8")
	public ModelAndView toDbToExcel() {
		// ModelAndView mav = new ModelAndView();1600020
		// mav.setViewName("metaDataManage");
		String keyword = "亏损";
		List<IndexManage> indexList = dbToExcelService.getIndi(keyword);

		ModelAndView mav = new ModelAndView();
		System.out.println("后台的指标代码：" + indexList.get(0).getIndi_code());
		System.out.println("后台的指标名字：" + indexList.get(0).getIndi_name());
		mav.addObject("indexList", indexList);
		mav.setViewName("dbToEcxel");
		return mav;
	}

	@RequestMapping(value = "getIndiSourceByIndiName", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public void getIndiSourceByIndiName(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("进入根据指标名称查询指标来源！");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String indiName = request.getParameter("indiName");
		System.out.println("后台接受到的指标名称：" + indiName);
		List<String> indexSourceList = dbToExcelService.getIndiSourceByIndiName(indiName);
		System.out.println("indexSourceList：" + indexSourceList);
		JSONArray json = JSONArray.fromObject(indexSourceList);
		out.print(json.toString());
	}

	@RequestMapping(value = "getIndiFreqCode", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public void getIndiFreqCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("进入根据指标名称和来源查询指标频度！");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String indiName = request.getParameter("indiName");
		String indiSource = request.getParameter("indiSource");
		System.out.println("后台接受到的指标名称：" + indiName);
		System.out.println("后台接受到的指标来源：" + indiSource);
		Map<String, String> indiNameSourceMap = new HashMap<String, String>();
		indiNameSourceMap.put("indiName", indiName);
		indiNameSourceMap.put("indiSource", indiSource);
		List<String> indexFreqCodeList = dbToExcelService.getIndiFreqCode(indiNameSourceMap);
		System.out.println("indexFreqCodeList：" + indexFreqCodeList);
		JSONArray json = JSONArray.fromObject(indexFreqCodeList);
		out.print(json.toString());
	}

	@RequestMapping(value = "getIndiStartTime", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public void getIndiStartTime(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("进入获得指标考试时间controller！");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String indiName = request.getParameter("indiName");
		String indiSource = request.getParameter("indiSource");
		String freqCode = request.getParameter("freqCode");
		System.out.println("后台接受到的指标名称：" + indiName);
		System.out.println("后台接受到的指标来源：" + indiSource);
		System.out.println("后台接受到的指标频度：" + freqCode);
		Map<String, String> indiNameSourceFreqMap = new HashMap<String, String>();
		indiNameSourceFreqMap.put("indiName", indiName);
		indiNameSourceFreqMap.put("indiSource", indiSource);
		indiNameSourceFreqMap.put("freqCode", freqCode);
		List<String> indexStartTimeList = dbToExcelService.getIndiStartTime(indiNameSourceFreqMap);
		System.out.println("indexStartTimeList：" + indexStartTimeList);
		JSONArray json = JSONArray.fromObject(indexStartTimeList);
		out.print(json.toString());
	}
	
	@RequestMapping(value = "getIndiEndTime", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public void getIndiEndTime(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("进入获得指标考试时间controller！");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String indiName = request.getParameter("indiName");
		String indiSource = request.getParameter("indiSource");
		String freqCode = request.getParameter("freqCode");
		String startTime = request.getParameter("startTime");
		System.out.println("后台接受到的指标开始事件：" + startTime);
		
		Map<String, String> indiNameSourceFreqSTimeMap = new HashMap<String, String>();
		indiNameSourceFreqSTimeMap.put("indiName", indiName);
		indiNameSourceFreqSTimeMap.put("indiSource", indiSource);
		indiNameSourceFreqSTimeMap.put("freqCode", freqCode);
		indiNameSourceFreqSTimeMap.put("startTime", startTime);
		List<String> indexEndTimeList = dbToExcelService.getIndiEndTime(indiNameSourceFreqSTimeMap);
		System.out.println("indexEndTimeList：" + indexEndTimeList);
		JSONArray json = JSONArray.fromObject(indexEndTimeList);
		out.print(json.toString());
	}
	
	@RequestMapping(value = "getSelectIndex", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public void getSelectIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("进入获得全部指标的controller！");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String indiName = request.getParameter("indiName");
		String indiSource = request.getParameter("indiSource");
		String freqCode = request.getParameter("freqCode");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		System.out.println("后台接受到的指标开始事件：" + startTime);
		
		Map<String, String> indiConditionMap = new HashMap<String, String>();
		indiConditionMap.put("indiName", indiName);
		indiConditionMap.put("indiSource", indiSource);
		indiConditionMap.put("freqCode", freqCode);
		indiConditionMap.put("startTime", startTime);
		indiConditionMap.put("endTime", endTime);
		List<IndiAll> indexAllList = dbToExcelService.getSelectIndex(indiConditionMap);
		System.out.println("indexAllList：" + indexAllList.get(0).getDate_code());
		JSONArray json = JSONArray.fromObject(indexAllList);
		out.print(json.toString());
	}
	
	
	
	
	
	
	
	

	@ResponseBody
	@RequestMapping("export")
	public void export(HttpServletResponse response, @RequestParam("id") String id) throws Exception {

		System.out.println("开始导出！");
		System.out.println("id:" + id);
		byte[] data = dbToExcelService.exportOrderData(id);
		response.reset();
		String fileName = new DateTime().toString("yyyyMMddHHmm") + "指标数据" + ".xls";
		System.out.println("fileName:" + fileName);
		response.setContentType("application/octet-stream; charset=UTF-8");
	
		response.setHeader("content-disposition",
				"attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
		response.addHeader("Content-Length", "" + data.length);
		IOUtils.write(data, response.getOutputStream());
	}

}