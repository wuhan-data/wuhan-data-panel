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

import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.SpecialDetail;
import com.wuhan_data.service.IndexManageService;
import com.wuhan_data.service.IndiColumnService;
import com.wuhan_data.service.SpecialService;
import com.wuhan_data.tools.Page;

@Controller
@RequestMapping("indiSpecial")
public class IndiSpecialController {
	
	@Autowired
	SpecialService specialService;
	@Autowired
	IndexManageService indexManageService;
	@RequestMapping("indiSpecialInit")
    public ModelAndView indiSpecialInit(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
//    	request.setCharacterEncoding("UTF-8");	
//        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();     
        Page page=new Page(); //分页类
        int special_id=Integer.parseInt(request.getParameter("special_id"));
        int count = specialService.totalIndi(special_id);//每一个一级栏目下面二极栏目的数量
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
        map.put("special_id",special_id);
        List<SpecialDetail> indiSpecialByPage=specialService.getIndiList(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+indiSpecialByPage.size());
        
        ////////
        List<IndexManage> InitIndexManageList = indexManageService.listIndi();
        mav.addObject("InitIndexManageList", InitIndexManageList);
        ////////
        
        
        mav.addObject("indiSpecialByPage", indiSpecialByPage);
        mav.addObject("page", page);    
        mav.addObject("special_id", special_id);      
        mav.setViewName("specialContentManage");
        return mav;
    }
	
	@RequestMapping("indiSpecialUpdateShow")
    public ModelAndView indiSpecialUpdateShow(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
      
        Page page=new Page(); //分页类

        int special_id=Integer.parseInt(request.getParameter("special_id"));
        int id=Integer.parseInt(request.getParameter("id"));
        int is_show=Integer.parseInt(request.getParameter("is_show"));
        SpecialDetail sd = new SpecialDetail();
        sd.setSpecial_id(special_id);
        sd.setIs_show(is_show);
        sd.setId(id);
        specialService.updateIndiShow(sd);
        int count = specialService.totalIndi(special_id);
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
        map.put("special_id",special_id);
        List<SpecialDetail> indiSpecialByPage=specialService.getIndiList(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+indiSpecialByPage.size());
        
        ////////
        List<IndexManage> InitIndexManageList = indexManageService.listIndi();
        mav.addObject("InitIndexManageList", InitIndexManageList);
        ////////
        
        mav.addObject("indiSpecialByPage", indiSpecialByPage);
        mav.addObject("page", page);    
        mav.addObject("special_id", special_id);      
        mav.setViewName("specialContentManage");
        return mav;
    }
	
	@RequestMapping("indiSpecialUpdate")
    public ModelAndView indiSpecialUpdate(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
      
        Page page=new Page(); //分页类

        int special_id=Integer.parseInt(request.getParameter("special_id1"));
        int id=Integer.parseInt(request.getParameter("id"));
        String indi_name=request.getParameter("indi_name");
        SpecialDetail sd = new SpecialDetail();
        sd.setIndi_name(indi_name);
        sd.setId(id);
        specialService.updateIndi(sd);
        int count = specialService.totalIndi(special_id);
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
        map.put("special_id",special_id);
        List<SpecialDetail> indiSpecialByPage=specialService.getIndiList(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+indiSpecialByPage.size());
        
        ////////
        List<IndexManage> InitIndexManageList = indexManageService.listIndi();
        mav.addObject("InitIndexManageList", InitIndexManageList);
        ////////
        
        mav.addObject("indiSpecialByPage", indiSpecialByPage);
        mav.addObject("page", page);    
        mav.addObject("special_id", special_id);      
        mav.setViewName("specialContentManage");
        return mav;
    }

	@RequestMapping("indiSpecialAdd")
    public ModelAndView indiSpecialAdd(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
      
        Page page=new Page(); //分页类

        int special_id=Integer.parseInt(request.getParameter("special_id2"));
        String special_name=specialService.getSname(special_id);
//        String indi_name=request.getParameter("indi_name");      
//        String indi_id=request.getParameter("indi_id");
        //获得指标名称和指标代码
        String indi=request.getParameter("indi1");
        String indi_code="";
        String indi_name="";
        boolean e=true;
        for(int i=0;i<indi.length();i++)
        {
        	if(e){
        		if(indi.charAt(i)!='-')
        		{
        			indi_code=indi_code+indi.charAt(i);
        			
        		}
        		else{
        			e=false;
        		}
        		
        	}
        	else{
        		indi_name=indi_name+indi.charAt(i);
        	}
        	
        }
        
        
        
        
        SpecialDetail sd = new SpecialDetail();
        sd.setSpecial_id(special_id);
        sd.setSpecial_name(special_name);
        sd.setIndi_name(indi_name);
        sd.setIndi_id(indi_code);
        specialService.addIndi(sd);
        int count = specialService.totalIndi(special_id);
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
        map.put("special_id",special_id);
        List<SpecialDetail> indiSpecialByPage=specialService.getIndiList(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+indiSpecialByPage.size());
        
        
        ////////
        List<IndexManage> InitIndexManageList = indexManageService.listIndi();
        mav.addObject("InitIndexManageList", InitIndexManageList);
        ////////
        
        mav.addObject("indiSpecialByPage", indiSpecialByPage);
        mav.addObject("page", page);    
        mav.addObject("special_id", special_id);      
        mav.setViewName("specialContentManage");
        return mav;
    }

	
	@RequestMapping("indiSpecialDel")
    public ModelAndView indiSpecialDel(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
      
        Page page=new Page(); //分页类

        
        int id =Integer.parseInt(request.getParameter("id"));
        int special_id =Integer.parseInt(request.getParameter("special_id"));
        specialService.deleteIndi(id);
        int count = specialService.totalIndi(special_id);
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
        map.put("special_id",special_id);
        List<SpecialDetail> indiSpecialByPage=specialService.getIndiList(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+indiSpecialByPage.size());
        
        ////////
        List<IndexManage> InitIndexManageList = indexManageService.listIndi();
        mav.addObject("InitIndexManageList", InitIndexManageList);
        ////////
        
        mav.addObject("indiSpecialByPage", indiSpecialByPage);
        mav.addObject("page", page);    
        mav.addObject("special_id", special_id);      
        mav.setViewName("specialContentManage");
        return mav;
    }
}
