package com.wuhan_data.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.pojo.IndexSpecial;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.SpecialDetail;
import com.wuhan_data.service.IndiColumnService;
import com.wuhan_data.service.SpecialService;
import com.wuhan_data.tools.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("")
public class SpecialController {
	
	@Autowired
	SpecialService specialService;
	
	@RequestMapping("specialInit")
    public ModelAndView specialInit(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");  	
        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();     
        Page page=new Page(); //分页类
        int count = specialService.total();//每一个一级栏目下面二极栏目的数量
        System.out.print("每一个一级栏目下面二极栏目的数量"+count);
        Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数      
        String currentPage=request.getParameter("currentPage");
        Pattern pattern = Pattern.compile("[0-9]{1,9}");
        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
            page.setCurrentPage(1);
        } else {
            page.setCurrentPage(Integer.valueOf(currentPage));
        }
        page.setTotalNumber(count);
        page.count();
        System.out.println(page.getDbIndex());
        System.out.println(page.getDbNumber());
        map.put("page", page);
        List<IndexSpecial> specialByPage=specialService.getlist(map);

        JSONArray json = new JSONArray();
        for(IndexSpecial a : specialByPage){
            JSONObject jo = new JSONObject();
            jo.put("id", a.getTopic_weight());
            jo.put("name", a.getTitle());
            jo.put("num", a.getTopic_weight());
            json.add(jo);
        }
        
        String jsondata = request.getParameter("jsondata");
        if(jsondata!=null) {
        	System.out.println("************************************");
        	System.out.println(jsondata);
        	System.out.println("************************************");
        	 JSONArray jsonArray = JSONArray.fromObject(jsondata);
             Object[] os = jsonArray .toArray();
             for(int i=0; i<os.length; i++) {
            	 IndexSpecial o = new IndexSpecial();
                 JSONObject jsonObj = JSONObject.fromObject(os[i]);
                 System.out.println("解析后："+jsonObj.get("name").toString()+":"+(jsonObj.getInt("num")));
                 o.setTitle(jsonObj.get("name").toString());
                 o.setTopic_weight(jsonObj.get("num").toString());
                 specialService.reOrderByTitle(o);             
             } 
             specialByPage=specialService.getlist(map);
        }else {
        	System.out.print("无法获取jsondata");
        }
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+specialByPage.size());
        
        mav.addObject("json", json);
        mav.addObject("specialByPage", specialByPage);
        mav.addObject("page", page);    
        
        mav.setViewName("specialManage");
        return mav;
    }
	
	
	@RequestMapping("specialAdd")
    public ModelAndView specialAdd(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
        Page page=new Page(); //分页类      
        String title=request.getParameter("special_name");

        IndexSpecial sd = new IndexSpecial();
        sd.setTitle(title);
        
        specialService.add(sd);
        
        int count = specialService.total();//每一个一级栏目下面二极栏目的数量
        System.out.print("每一个一级栏目下面二极栏目的数量"+count);
        Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数
       
        String currentPage=request.getParameter("currentPage");
        Pattern pattern = Pattern.compile("[0-9]{1,9}");
        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
            page.setCurrentPage(1);
        } else {
            page.setCurrentPage(Integer.valueOf(currentPage));
        }
        page.setTotalNumber(count);
        page.count();
        System.out.println(page.getDbIndex());
        System.out.println(page.getDbNumber());
        map.put("page", page);
        List<IndexSpecial> specialByPage=specialService.getlist(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+specialByPage.size());
        mav.addObject("specialByPage", specialByPage);
        mav.addObject("page", page);    
        
        
        
        mav.setViewName("specialManage");
        return mav;
    }
	

	@RequestMapping("specialDel")
    public ModelAndView specialDel(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
      
        Page page=new Page(); //分页类

        int special_id=Integer.parseInt(request.getParameter("special_id"));
        specialService.delete(special_id);

        int count = specialService.total();
        System.out.print("每一个一级栏目下面二极栏目的数量"+count);
        Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数
       
        String currentPage=request.getParameter("currentPage");
        Pattern pattern = Pattern.compile("[0-9]{1,9}");
        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
            page.setCurrentPage(1);
        } else {
            page.setCurrentPage(Integer.valueOf(currentPage));
        }
        page.setTotalNumber(count);
        page.count();
        System.out.println(page.getDbIndex());
        System.out.println(page.getDbNumber());
        map.put("page", page);
        List<IndexSpecial> specialByPage=specialService.getlist(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+specialByPage.size());
        mav.addObject("specialByPage", specialByPage);
        mav.addObject("page", page);    
     
        mav.setViewName("specialManage");
        return mav;
    }

	@RequestMapping("specialUpdateShow")
    public ModelAndView specialUpdateShow(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
      
        Page page=new Page(); //分页类

        int special_id=Integer.parseInt(request.getParameter("special_id"));
        int is_show=Integer.parseInt(request.getParameter("is_show"));
        
        
        IndexSpecial sd = new IndexSpecial();
        sd.setId(special_id);
        sd.setIs_show(is_show);
        specialService.updateShow(sd);
        int count = specialService.total();
        
        
        System.out.print("每一个一级栏目下面二极栏目的数量"+count);
        Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数      
        String currentPage=request.getParameter("currentPage");
        Pattern pattern = Pattern.compile("[0-9]{1,9}");
        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
            page.setCurrentPage(1);
        } else {
            page.setCurrentPage(Integer.valueOf(currentPage));
        }
        page.setTotalNumber(count);
        page.count();
        System.out.println(page.getDbIndex());
        System.out.println(page.getDbNumber());
        map.put("page", page);
        List<IndexSpecial> specialByPage=specialService.getlist(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+specialByPage.size());
        mav.addObject("specialByPage", specialByPage);
        mav.addObject("page", page);       
        mav.setViewName("specialManage");
        return mav;
    }

	@RequestMapping("specialUpdate")
    public ModelAndView specialUpdate(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
      
        Page page=new Page(); //分页类

        String special_name=request.getParameter("special_name");      
        int special_id=Integer.parseInt(request.getParameter("special_id"));
      
        IndexSpecial sd = new IndexSpecial();
        sd.setId(special_id);
        sd.setTitle(special_name);
        specialService.update(sd);
        
        
        int count = specialService.total();
        System.out.print("每一个一级栏目下面二极栏目的数量"+count);
        Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数      
        String currentPage=request.getParameter("currentPage");
        Pattern pattern = Pattern.compile("[0-9]{1,9}");
        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
            page.setCurrentPage(1);
        } else {
            page.setCurrentPage(Integer.valueOf(currentPage));
        }
        page.setTotalNumber(count);
        page.count();
        System.out.println(page.getDbIndex());
        System.out.println(page.getDbNumber());
        map.put("page", page);
        List<IndexSpecial> specialByPage=specialService.getlist(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+specialByPage.size());
        mav.addObject("specialByPage", specialByPage);
        mav.addObject("page", page);    
     
        mav.setViewName("specialManage");
        return mav;
    }
	
	
}
