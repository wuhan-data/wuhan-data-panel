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
import com.wuhan_data.service.IndexManageService;
import com.wuhan_data.service.IndiColumnService;
import com.wuhan_data.tools.Page;

@Controller
@RequestMapping("")
public class IndiColumnController {
	
	@Autowired
	IndiColumnService indiColumnService;
	@Autowired
	IndexManageService indexManageService;
	@RequestMapping("indiColumnInit")
    public ModelAndView initIndi(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
      
        Page page=new Page(); //分页类

        int cid=Integer.parseInt(request.getParameter("id"));
        int count = indiColumnService.total(cid);//每一个一级栏目下面二极栏目的数量
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
        map.put("id",cid);
        List<IndiCorrelative> indicolumnByPage=indiColumnService.getlist(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+indicolumnByPage.size());

        List<IndexManage> InitIndexManageList = indexManageService.listIndi();
        mav.addObject("InitIndexManageList", InitIndexManageList);     
        mav.addObject("indicolumnByPage", indicolumnByPage);
        mav.addObject("page", page);    
        mav.addObject("cid", cid);    
        
        mav.setViewName("columnContentManage");
        return mav;
    }
	
	
	@RequestMapping("indiColumnAdd")
    public ModelAndView indiColumnAdd(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
//    	request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
      
        Page page=new Page(); //分页类

//        int indi_id=Integer.parseInt(request.getParameter("indi_id"));
        int column_id=Integer.parseInt(request.getParameter("cid1"));
        
        //获得指标名称和指标代码
        String indi=request.getParameter("indi");
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
       
        IndiCorrelative ic = new IndiCorrelative();
        ic.setCid(column_id);
        ic.setIndi_id(Integer.parseInt(indi_code));
        ic.setIndi_name(indi_name);
        indiColumnService.add(ic);

        int count = indiColumnService.total(column_id);//每一个一级栏目下面二极栏目的数量
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
        map.put("id",column_id);
        List<IndiCorrelative> indicolumnByPage=indiColumnService.getlist(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+indicolumnByPage.size());
        
        ////////
        List<IndexManage> InitIndexManageList = indexManageService.listIndi();
        mav.addObject("InitIndexManageList", InitIndexManageList);
        ////////
        mav.addObject("indicolumnByPage", indicolumnByPage);
        mav.addObject("page", page);    
        mav.addObject("cid", column_id);    
        
        
        
        mav.setViewName("columnContentManage");
        return mav;
    }
	

	@RequestMapping("indiColumnDel")
    public ModelAndView indiColumnDel(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
//    	request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
      
        Page page=new Page(); //分页类

        int id=Integer.parseInt(request.getParameter("id"));
        int column_id=Integer.parseInt(request.getParameter("column_id"));
        indiColumnService.delete(id);

        int count = indiColumnService.total(column_id);
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
        map.put("id",column_id);
        List<IndiCorrelative> indicolumnByPage=indiColumnService.getlist(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+indicolumnByPage.size());
        
        
        ////////
        List<IndexManage> InitIndexManageList = indexManageService.listIndi();
        mav.addObject("InitIndexManageList", InitIndexManageList);
        ////////
        
        mav.addObject("indicolumnByPage", indicolumnByPage);
        mav.addObject("page", page);    
        mav.addObject("cid", column_id);    
     
        mav.setViewName("columnContentManage");
        return mav;
    }

	@RequestMapping("indiColumnUpdate")
    public ModelAndView indiColumnUpdate(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
//    	request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
      
        Page page=new Page(); //分页类

        int id=Integer.parseInt(request.getParameter("id"));
        int column_id=Integer.parseInt(request.getParameter("column_id"));
        int is_show=Integer.parseInt(request.getParameter("is_show"));
        IndiCorrelative ic = new IndiCorrelative();
        ic.setId(id);
        ic.setIs_show(is_show);
        indiColumnService.updateShow(ic);
        int count = indiColumnService.total(column_id);
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
        map.put("id",column_id);
        List<IndiCorrelative> indicolumnByPage=indiColumnService.getlist(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+indicolumnByPage.size());
        
        ////////
        List<IndexManage> InitIndexManageList = indexManageService.listIndi();
        mav.addObject("InitIndexManageList", InitIndexManageList);
        ////////
        
        mav.addObject("indicolumnByPage", indicolumnByPage);
        mav.addObject("page", page);    
        mav.addObject("cid", column_id);    
     
        mav.setViewName("columnContentManage");
        return mav;
    }

	@RequestMapping("indiColumnUpdateOther")
    public ModelAndView indiColumnUpdateOther(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
//    	request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
      
        Page page=new Page(); //分页类

        int id=Integer.parseInt(request.getParameter("indi_id"));
        int column_id=Integer.parseInt(request.getParameter("cid2"));
        String indi_name=request.getParameter("indi_name");
        IndiCorrelative ic = new IndiCorrelative();
        ic.setId(id);
        ic.setIndi_name(indi_name);
        indiColumnService.update(ic);
        int count = indiColumnService.total(column_id);
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
        map.put("id",column_id);
        List<IndiCorrelative> indicolumnByPage=indiColumnService.getlist(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+indicolumnByPage.size());
        
        ////////
        List<IndexManage> InitIndexManageList = indexManageService.listIndi();
        mav.addObject("InitIndexManageList", InitIndexManageList);
        ////////
        
        mav.addObject("indicolumnByPage", indicolumnByPage);
        mav.addObject("page", page);    
        mav.addObject("cid", column_id);    
     
        mav.setViewName("columnContentManage");
        return mav;
    }
}
