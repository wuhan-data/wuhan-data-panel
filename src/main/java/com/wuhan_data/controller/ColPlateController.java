package com.wuhan_data.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.tools.Page;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("")
public class ColPlateController {
	
	@RequestMapping("tt")
	@ResponseBody
    public String initIndi(){
        Map map=new HashMap();
        Map map1 = new HashMap();
        List list1 = new ArrayList();
        list1.add("aaa");
        list1.add(11);
        List list2 = new ArrayList();
        list2.add("bbb");
        list2.add(0.2);
        map1.put("list1", list1);
        map1.put("list2", list2);                
        List list3 = new ArrayList();
        list3.add("list3");
        list3.add(11);
        map.put("page_size",map1);
        map.put("page_index",list3);
        String  param= JSON.toJSONString(map);        
        return param;
    }
	
}
