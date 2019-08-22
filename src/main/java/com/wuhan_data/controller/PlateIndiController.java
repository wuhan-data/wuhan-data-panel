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

import com.wuhan_data.pojo.ColPlate;
import com.wuhan_data.pojo.ColPlateIndi;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.service.ColPlateIndiService;
import com.wuhan_data.tools.Page;

@Controller
@RequestMapping("")
public class PlateIndiController {
	
	@Autowired
	ColPlateIndiService colPlateIndiService;
	
	@RequestMapping("plateIndiInit")
    public ModelAndView plateIndiInit(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();   
        Page page=new Page(); //分页类
        int plate_id=Integer.parseInt(request.getParameter("id"));
        int count = colPlateIndiService.total(plate_id);//每一个板块下面的指标数量
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
        map.put("plate_id",plate_id);
        List<ColPlateIndi> indicolumnByPage=colPlateIndiService.getlist(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+indicolumnByPage.size());

//        List<IndexManage> indexManageList = colPlateIndiService.getAllIndi();
//        System.out.print("@@@@@@@@@@:"+indexManageList.size());
//        List<IndexManage> InitIndexManageList = colPlateService.listIndi();
//        mav.addObject("InitIndexManageList", InitIndexManageList);     
        mav.addObject("indicolumnByPage", indicolumnByPage);
        mav.addObject("page", page);    
        mav.addObject("cid", plate_id);  
//        mav.addObject("indexManageList", indexManageList);
        mav.setViewName("plateIndiManage");
        return mav;
    }
	
	
	@RequestMapping("plateIndiAdd")//添加指标
    public ModelAndView plateIndiAdd(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8"); //防止乱码   	
        response.setCharacterEncoding("UTF-8");//防止乱码       
        ModelAndView mav = new ModelAndView();//返回视图类    
        Page page=new Page(); //分页类
        int pid=Integer.parseInt(request.getParameter("cid")); //板块id
        System.out.print("&&&&&&&&&&&&&&&:板块id"+pid);
        
        String search_indi_id=request.getParameter("indi_id");
        String indi_new_name=request.getParameter("indi_new_name");
        String indi_old_name=request.getParameter("indi_old_name");
        String show_type = request.getParameter("show_type");  
        String show_color = request.getParameter("show_color");  
        System.out.print("show_color:"+show_color);
        //pid,pname,indi_id,indi_name,show_type
        ColPlateIndi colPlateIndi = new ColPlateIndi();
        colPlateIndi.setPlate_id(pid);
        colPlateIndi.setSearch_indi_id(search_indi_id);
        colPlateIndi.setIndi_new_name(indi_new_name);
        colPlateIndi.setIndi_old_name(indi_old_name);
        colPlateIndi.setShow_type(show_type);
        colPlateIndi.setShow_color(show_color);
        colPlateIndiService.add(colPlateIndi);        
        int count = colPlateIndiService.total(pid);//每一个二级栏目下的板块数量
        Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数     
        String currentPage=request.getParameter("currentPage");//获取jsp页面的当前页的页码
        Pattern pattern = Pattern.compile("[0-9]{1,9}");//对跳转框内容的限制
        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
            page.setCurrentPage(1);//首次进入，初始化页码
        } else {
            page.setCurrentPage(Integer.valueOf(currentPage));//设置页码
        }
        page.setTotalNumber(count);//设置总条数
        page.count();//执行分页类设置分页参数，为sql查询做准备
        map.put("page", page);//设置page参数
        map.put("plate_id",pid);//设置二级栏目id参数，where条件
        List<ColPlateIndi> indicolumnByPage=colPlateIndiService.getlist(map);//获取分页

        mav.addObject("indicolumnByPage", indicolumnByPage);//查询结果，前端展示
        mav.addObject("page", page);    //前端获取参数
        mav.addObject("cid", pid); //前端获取二级栏目id，以便多次传参        
        mav.setViewName("plateIndiManage"); //返回到jsp页面
        return mav;
    }
	
	@RequestMapping("plateIndiDel")//删除板块下的指标
    public ModelAndView plateIndiDel(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8"); //防止乱码   	
        response.setCharacterEncoding("UTF-8");//防止乱码       
        ModelAndView mav = new ModelAndView();//返回视图类    
        Page page=new Page(); //分页类
        int pid=Integer.parseInt(request.getParameter("pid")); //板块id       
        int indi_id=Integer.parseInt(request.getParameter("id")); //板块id
        colPlateIndiService.delete(indi_id);                
        int count = colPlateIndiService.total(pid);//每一个二级栏目下的板块数量
        Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数     
        String currentPage=request.getParameter("currentPage");//获取jsp页面的当前页的页码
        Pattern pattern = Pattern.compile("[0-9]{1,9}");//对跳转框内容的限制
        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
            page.setCurrentPage(1);//首次进入，初始化页码
        } else {
            page.setCurrentPage(Integer.valueOf(currentPage));//设置页码
        }
        page.setTotalNumber(count);//设置总条数
        page.count();//执行分页类设置分页参数，为sql查询做准备
        map.put("page", page);//设置page参数
        map.put("plate_id",pid);//设置二级栏目id参数，where条件
        List<ColPlateIndi> indicolumnByPage=colPlateIndiService.getlist(map);//获取分页
        
//        List<IndexManage> InitIndexManageList = colPlateService.listIndi();
//        mav.addObject("InitIndexManageList", InitIndexManageList);     
        mav.addObject("indicolumnByPage", indicolumnByPage);//查询结果，前端展示
        mav.addObject("page", page);    //前端获取参数
        mav.addObject("cid", pid); //前端获取二级栏目id，以便多次传参        
        mav.setViewName("plateIndiManage"); //返回到jsp页面
        return mav;
    }
	
	@RequestMapping("plateIndiUpdateShow")//
    public ModelAndView plateIndiUpdateShow(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8"); //防止乱码   	
        response.setCharacterEncoding("UTF-8");//防止乱码       
        ModelAndView mav = new ModelAndView();//返回视图类    
        Page page=new Page(); //分页类
        int pid=Integer.parseInt(request.getParameter("pid")); //板块id       
        int indi_id=Integer.parseInt(request.getParameter("id")); //板块id
        int is_show=Integer.parseInt(request.getParameter("is_show")); //板块id
        ColPlateIndi colPlateIndi = new ColPlateIndi();
        colPlateIndi.setIndi_id(indi_id);
        colPlateIndi.setIs_show(is_show);
        colPlateIndiService.updateShow(colPlateIndi);            
        int count = colPlateIndiService.total(pid);//每一个二级栏目下的板块数量
        Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数     
        String currentPage=request.getParameter("currentPage");//获取jsp页面的当前页的页码
        Pattern pattern = Pattern.compile("[0-9]{1,9}");//对跳转框内容的限制
        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
            page.setCurrentPage(1);//首次进入，初始化页码
        } else {
            page.setCurrentPage(Integer.valueOf(currentPage));//设置页码
        }
        page.setTotalNumber(count);//设置总条数
        page.count();//执行分页类设置分页参数，为sql查询做准备
        map.put("page", page);//设置page参数
        map.put("plate_id",pid);//设置二级栏目id参数，where条件
        List<ColPlateIndi> indicolumnByPage=colPlateIndiService.getlist(map);//获取分页
        
//        List<IndexManage> InitIndexManageList = colPlateService.listIndi();
//        mav.addObject("InitIndexManageList", InitIndexManageList);     
        mav.addObject("indicolumnByPage", indicolumnByPage);//查询结果，前端展示
        mav.addObject("page", page);    //前端获取参数
        mav.addObject("cid", pid); //前端获取二级栏目id，以便多次传参        
        mav.setViewName("plateIndiManage"); //返回到jsp页面
        return mav;
    }

}
