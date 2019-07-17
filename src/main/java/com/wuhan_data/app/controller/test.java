package com.wuhan_data.app.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.AdminService;
import com.wuhan_data.service.AdminService;
import com.wuhan_data.service.MenuService;
import com.wuhan_data.service.SysLogService;
import com.wuhan_data.service.impl.SysLogServiceImpl;
import com.wuhan_data.tools.MenuList;
import com.wuhan_data.tools.Page;

import net.sf.jsqlparser.util.AddAliasesVisitor;;

@Controller
@RequestMapping("")
public class test {
	
	@RequestMapping("apptest")
	@ResponseBody
	public String apptest() {
		Map map=new HashMap();
		List list3 =new ArrayList();
		list3.add("list3");
		list3.add(11);
		map.put("page", list3);
		String param=JSON.toJSONString(map);
		return param;
		
		
	}

}



