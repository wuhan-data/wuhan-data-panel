package com.wuhan_data.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.pojo.ColPlate;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.service.ColPlateService;
import com.wuhan_data.tools.Page;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("")
public class ColPlateController { //栏目下的板块管理
	
	@Autowired
	ColPlateService colPlateService;
	
	@RequestMapping("colPlateInit")//初始化板块
    public ModelAndView colPlateInit(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8"); //防止乱码   	
        response.setCharacterEncoding("UTF-8");//防止乱码       
        ModelAndView mav = new ModelAndView();//返回视图类    
        Page page=new Page(); //分页类
        int cid=Integer.parseInt(request.getParameter("id")); //获取二级栏目id
        int count = colPlateService.total(cid);//每一个二级栏目下的板块数量
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
        map.put("cid",cid);//设置二级栏目id参数，where条件
        List<ColPlate> indicolumnByPage=colPlateService.getlist(map);//获取分页
//        List<IndexManage> InitIndexManageList = colPlateService.listIndi();
//        mav.addObject("InitIndexManageList", InitIndexManageList);     
        mav.addObject("indicolumnByPage", indicolumnByPage);//查询结果，前端展示
        mav.addObject("page", page);    //前端获取参数
        mav.addObject("cid", cid); //前端获取二级栏目id，以便多次传参        
        mav.setViewName("columnContentManage"); //返回到jsp页面
        return mav;
    }
	
	
	
	@RequestMapping("colPlateAdd")//添加板块
    public ModelAndView colPlateAdd(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8"); //防止乱码   	
        response.setCharacterEncoding("UTF-8");//防止乱码       
        ModelAndView mav = new ModelAndView();//返回视图类    
        Page page=new Page(); //分页类
        int cid=Integer.parseInt(request.getParameter("cid")); //获取二级栏目id
        String pname = request.getParameter("pname");//获取新板块名称
        String cname=colPlateService.getAddCname(cid);//获取添加板块所属栏目的名称
        String show_type=request.getParameter("show_type");//获取显示类型
        int term = Integer.parseInt(request.getParameter("term"));//获取期数
        ColPlate colPlate = new ColPlate();//新添加板块对象
        colPlate.setCid(cid);
        colPlate.setCname(cname);
        colPlate.setPname(pname);
        colPlate.setShow_type(show_type);
        colPlate.setTerm(term);
        colPlateService.add(colPlate);
        
        int count = colPlateService.total(cid);//每一个二级栏目下的板块数量
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
        map.put("cid",cid);//设置二级栏目id参数，where条件
        List<ColPlate> indicolumnByPage=colPlateService.getlist(map);//获取分页
//        List<IndexManage> InitIndexManageList = colPlateService.listIndi();
//        mav.addObject("InitIndexManageList", InitIndexManageList);     
        mav.addObject("indicolumnByPage", indicolumnByPage);//查询结果，前端展示
        mav.addObject("page", page);    //前端获取参数
        mav.addObject("cid", cid); //前端获取二级栏目id，以便多次传参        
        mav.setViewName("columnContentManage"); //返回到jsp页面
        return mav;
    }
	
	
	@RequestMapping("colPlateDel")//删除板块
    public ModelAndView colPlateDel(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8"); //防止乱码   	
        response.setCharacterEncoding("UTF-8");//防止乱码       
        ModelAndView mav = new ModelAndView();//返回视图类    
        Page page=new Page(); //分页类
        int id=Integer.parseInt(request.getParameter("id")); //获取删除id
        colPlateService.delete(id);
//        return "redirect:colPlateInit";
        
        int cid=Integer.parseInt(request.getParameter("cid")); //获取二级栏目id
        int count = colPlateService.total(cid);//每一个二级栏目下的板块数量
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
        map.put("cid",cid);//设置二级栏目id参数，where条件
        List<ColPlate> indicolumnByPage=colPlateService.getlist(map);//获取分页
//        List<IndexManage> InitIndexManageList = colPlateService.listIndi();
//        mav.addObject("InitIndexManageList", InitIndexManageList);     
        mav.addObject("indicolumnByPage", indicolumnByPage);//查询结果，前端展示
        mav.addObject("page", page);    //前端获取参数
        mav.addObject("cid", cid); //前端获取二级栏目id，以便多次传参        
        mav.setViewName("columnContentManage"); //返回到jsp页面
        return mav;
    }
	
	
	@RequestMapping("colPlateUpdateShow")//删除板块
    public ModelAndView colPlateUpdateShow(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8"); //防止乱码   	
        response.setCharacterEncoding("UTF-8");//防止乱码       
        ModelAndView mav = new ModelAndView();//返回视图类    
        Page page=new Page(); //分页类
        int id=Integer.parseInt(request.getParameter("id")); //获取更新显示与否id
        int is_show=Integer.parseInt(request.getParameter("is_show"));//获取显示与否

        ColPlate colPlate = new ColPlate();//新添加板块对象
        colPlate.setId(id);
        colPlate.setIs_show(is_show);
        colPlateService.updateShow(colPlate);
      
        
        int cid=Integer.parseInt(request.getParameter("cid")); //获取二级栏目id
        int count = colPlateService.total(cid);//每一个二级栏目下的板块数量
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
        map.put("cid",cid);//设置二级栏目id参数，where条件
        List<ColPlate> indicolumnByPage=colPlateService.getlist(map);//获取分页
//        List<IndexManage> InitIndexManageList = colPlateService.listIndi();
//        mav.addObject("InitIndexManageList", InitIndexManageList);     
        mav.addObject("indicolumnByPage", indicolumnByPage);//查询结果，前端展示
        mav.addObject("page", page);    //前端获取参数
        mav.addObject("cid", cid); //前端获取二级栏目id，以便多次传参        
        mav.setViewName("columnContentManage"); //返回到jsp页面
        return mav;
    }
	
	

	@RequestMapping("colPlateUpdate")//更新板块信息
    public ModelAndView colPlateUpdate(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8"); //防止乱码   	
        response.setCharacterEncoding("UTF-8");//防止乱码       
        ModelAndView mav = new ModelAndView();//返回视图类    
        Page page=new Page(); //分页类
        int cid=Integer.parseInt(request.getParameter("cid")); //获取二级栏目id
        int pid=Integer.parseInt(request.getParameter("pid"));//获取板块id
        String pname = request.getParameter("pname");
        String show_type=request.getParameter("show_type");//获取更新的显示类型
        int term=Integer.parseInt(request.getParameter("term"));//获取更新的期数
        
        

        ColPlate colPlate = new ColPlate();//新添加板块对象
        colPlate.setCid(cid);
        colPlate.setPid(pid);
        colPlate.setPname(pname);
        colPlate.setShow_type(show_type);
        colPlate.setTerm(term);
        
        colPlateService.update(colPlate);
      
        int count = colPlateService.total(cid);//每一个二级栏目下的板块数量
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
        map.put("cid",cid);//设置二级栏目id参数，where条件
        List<ColPlate> indicolumnByPage=colPlateService.getlist(map);//获取分页
//        List<IndexManage> InitIndexManageList = colPlateService.listIndi();
//        mav.addObject("InitIndexManageList", InitIndexManageList);     
        mav.addObject("indicolumnByPage", indicolumnByPage);//查询结果，前端展示
        mav.addObject("page", page);    //前端获取参数
        mav.addObject("cid", cid); //前端获取二级栏目id，以便多次传参        
        mav.setViewName("columnContentManage"); //返回到jsp页面
        return mav;
    }
	

	
}
