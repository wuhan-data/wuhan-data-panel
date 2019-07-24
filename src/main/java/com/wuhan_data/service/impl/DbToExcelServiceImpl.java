package com.wuhan_data.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.DbToExcelMapper;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.IndiAll;
import com.wuhan_data.service.DbToExcelService;

import cn.hutool.core.map.MapUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

@Service
public class DbToExcelServiceImpl implements DbToExcelService{
	
	@Autowired
	DbToExcelMapper dbToExcelMapper;

	@Override
	public byte[] exportOrderData(String id) {
		ExcelWriter writer = ExcelUtil.getWriter(); // 通过工具类创建writer，默认创建xls格式
		Map<String, String> map = MapUtil.newHashMap(true);
	    map.put("indi_code", "指标代码");
	    map.put("indi_name", "指标名字");
	    map.put("date_code", "日期代码");
	    map.put("kjwdm", "空间维度码");
	    map.put("area_code", "区域代码");
	    map.put("area_name", "区域名字");
	    map.put("freq_code", "频度代码");
	    map.put("time_point", "时点");
	    map.put("indi_value", "指标值");
	    
	    writer.setHeaderAlias(map);
	    List<IndiAll> exports = new ArrayList<>();// 一次性写出内容，使用默认样式
	   
	    List<IndiAll> indiAllList = dbToExcelMapper.selectIndi(id);
	    	
	    	
	    for (int j = 0; j < indiAllList.size(); j++) {
	          exports.add(indiAllList.get(j));
	     }

	    
	    writer.write(exports);
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    writer.flush(out);
	    // 关闭writer，释放内存
	    writer.close();
	    IOUtils.closeQuietly(out);
	    return out.toByteArray();
	}

	@Override
	public List<IndexManage> getIndi(String keyword) {
		// TODO Auto-generated method stub
		return dbToExcelMapper.getIndi(keyword);
	}

	@Override
	public List<String> getIndiSourceByIndiName(String indiName) {
		// TODO Auto-generated method stub
		return dbToExcelMapper.getIndiSourceByIndiName(indiName);
	}

	

}
