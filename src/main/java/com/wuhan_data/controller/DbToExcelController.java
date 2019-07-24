package com.wuhan_data.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.service.DbToExcelService;

import cn.hutool.core.date.DateTime;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("")
public class DbToExcelController {
	@Autowired
	DbToExcelService dbToExcelService;
	
	
	
	 @RequestMapping("dbToEcxel")
		public ModelAndView toDbToExcel() {
//		 ModelAndView mav = new ModelAndView();1600020
//		 mav.setViewName("metaDataManage");
		 String keyword="地区生产总值";
		 List<IndexManage> indexList= dbToExcelService.getIndi(keyword);
		 
		 ModelAndView mav = new ModelAndView();
		 mav.addObject("indexList", indexList);
		 mav.setViewName("dbToEcxel");
	     return mav;
		}
	 
	 @RequestMapping("getIndiSourceByIndiName")
		public void getIndiSourceByIndiName(HttpServletRequest request,HttpServletResponse response) throws IOException {
		 System.out.println("进入根据指标名称查询指标来源！");
		 PrintWriter out = response.getWriter();
		 String indiName = request.getParameter("indiName");
		 List<String> indexSourceList= dbToExcelService.getIndiSourceByIndiName(indiName);
		 System.out.println("indexSourceList："+indexSourceList);
		 JSONArray json = JSONArray.fromObject(indexSourceList);
		 out.print(json.toString());
		}
	 
	
	@ResponseBody
	@RequestMapping("export")
	public void export(HttpServletResponse response, @RequestParam("id") String id) throws Exception {

		System.out.println("开始导出！");
		System.out.println("id:"+id);
	    byte[] data = dbToExcelService.exportOrderData(id);
	    response.reset();
	    String fileName = new DateTime().toString("yyyyMMddHHmm") + "指标数据" + ".xls";
	    System.out.println("fileName:"+fileName);
	    response.setContentType("application/octet-stream; charset=UTF-8");
//	    response.setHeader("Content-Disposition", "attachment; fileName=" + fileName + ";filename*=ISO8859-1''" + URLEncoder.encode(fileName, "ISO8859-1"));
	    response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes("gb2312"), "ISO8859-1"));
	    response.addHeader("Content-Length", "" + data.length);
	    IOUtils.write(data, response.getOutputStream());
	}
	
}
