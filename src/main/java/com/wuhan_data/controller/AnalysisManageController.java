package com.wuhan_data.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.AnalysisLabel;
import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.pojo.AnalysisType;
import com.wuhan_data.pojo.ColPlate;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.service.AnalysisManageService;
import com.wuhan_data.service.IndiColumnService;
import com.wuhan_data.tools.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("")
public class AnalysisManageController {
	
	private static String tname;
	private static int type_id;
//	private static String theme_name="PMI指数（全国）";
	
	@Autowired
	AnalysisManageService analysisManageService;
	@Autowired
	IndiColumnService indiColumnService;
//	  @RequestMapping("test")
//	    public String testM(HttpServletRequest request, 
//	            HttpServletResponse response){
//	        ModelAndView mav = new ModelAndView();
//	  String op = request.getParameter("op");
//	  System.out.println(op);
//	  request.setAttribute("result", 1);
//	  String json="[{result:1}";
//	  JSONArray success=JSONArray.fromObject(json);
//	  return "success";
//	  
//	    }
	
//    @RequestMapping("listAnalysisManage")
//    public ModelAndView listAnalysisManage(){
//        ModelAndView mav = new ModelAndView();
//        List<AnalysisManage> analysisManageList= analysisManageService.list();
//  
//        // 
//        mav.addObject("analysisManageList", analysisManageList);
//        // 
//        mav.setViewName("columnManageFrame");
//        return mav;
//    }
    
   @RequestMapping("init")
    public String init(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	HttpSession session = request.getSession(true);
    	request.setCharacterEncoding("UTF-8");
    	
        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
        List<AnalysisType> analysisListParent=analysisManageService.parentList();
        String jsondata = request.getParameter("jsondata");
        if(jsondata!=null) {
        	System.out.println("************************************");
        	System.out.println(jsondata);
        	System.out.println("************************************");
        	 JSONArray jsonArray = JSONArray.fromObject(jsondata);
             Object[] os = jsonArray .toArray();
             for(int i=0; i<os.length; i++) {
            	 AnalysisType o = new AnalysisType();
                 JSONObject jsonObj = JSONObject.fromObject(os[i]);
                 System.out.println("解析后："+jsonObj.get("name").toString()+":"+(jsonObj.getInt("num")));
                 o.setTypeName(jsonObj.get("name").toString());
                 o.setTypeWeight(jsonObj.getInt("num"));
                 analysisManageService.reOrderByTypename(o);
                
             } 
             analysisListParent=analysisManageService.parentList();
        }else {
        	System.out.print("无法获取jsondata");
        }
        Page page=new Page(); //分页类
        type_id = analysisManageService.getFirstWeight().getTypeId();
        tname=analysisManageService.getFirstWeight().getTypeName();
       
//        List<AnalysisManage> analysisManageList= analysisManageService.list();
        int count = analysisManageService.countByGroup(type_id);//每一个一级栏目下面二极栏目的数量
        System.out.println("每一个一级栏目下面二极栏目的数量"+count);
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
        map.put("type_id",type_id);
        List<AnalysisLabel> analysisListByPage= analysisManageService.groupList(map);//分页查询二极栏目

        
        List<AnalysisType> typenameOrder = analysisManageService.getOrderByTypename();//得到一级分类顺序
        session.setAttribute("typenameOrder", typenameOrder);
//        mav.addObject("typenameOrder", typenameOrder);
        for(AnalysisType a : typenameOrder){
            System.out.println(a.getTypeName()+":"+(a.getTypeWeight()+1));
        }
        JSONArray json = new JSONArray();
        for(AnalysisType a : typenameOrder){
            JSONObject jo = new JSONObject();
            jo.put("id", a.getTypeWeight());
            jo.put("name", a.getTypeName());
            jo.put("num", a.getTypeWeight());
            json.add(jo);
        }
        mav.addObject("json", json);
        
        
        PrintWriter out = response.getWriter();
        out.print(json);
        
        System.out.print(analysisListByPage.size());
//        mav.addObject("analysisListByPage", analysisListByPage);
//        mav.addObject("analysisListParent", analysisListParent);
//        mav.addObject("analysisManageList", analysisManageList);
        session.setAttribute("json", json);
        session.setAttribute("analysisListByPage", analysisListByPage);
        session.setAttribute("analysisListParent", analysisListParent);
//        session.setAttribute("analysisManageList", analysisManageList);
        
        session.setAttribute("type_id", type_id);
        session.setAttribute("tname", tname);
        session.setAttribute("controlURL", "selectAnalysisListByPage");
        session.setAttribute("page", page);
//        session.setAttribute("placeholder", theme_name);
        
        
//        mav.addObject("tname", tname);
//        mav.addObject("controlURL", "selectAnalysisListByPage");//控制页码传递URL
//        mav.addObject("page", page); 
//        mav.addObject("placeholder", theme_name);
       
        // placeholder
//        mav.setViewName("columnManage");
        
        return "columnManageFrame";
    }
    
    @RequestMapping("initAnalysisList")
    public ModelAndView initAnalysisList(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException{
     	request.setCharacterEncoding("UTF-8");
//    	
        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
              
        Page page=new Page(); //分页类
        tname= java.net.URLDecoder.decode(request.getParameter("op"),"UTF-8");
//        tname= request.getParameter("op");
        System.out.print("tname:"+tname);
        List<AnalysisType> analysisListParent=analysisManageService.parentList();
//        List<AnalysisManage> analysisManageList= analysisManageService.list();
        type_id = analysisManageService.getTypeId(tname);
        int count = analysisManageService.countByGroup(type_id);//每一个一级栏目下面二极栏目的数量
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
        map.put("type_id",type_id);
        
        List<AnalysisType> typenameOrder = analysisManageService.getOrderByTypename();//得到一级分类顺序
        mav.addObject("typenameOrder", typenameOrder);
        for(AnalysisType a : typenameOrder){
            System.out.println(a.getTypeName()+":"+(a.getTypeWeight()));
        }
        JSONArray json = new JSONArray();
        for(AnalysisType a : typenameOrder){
            JSONObject jo = new JSONObject();
            jo.put("id", a.getTypeWeight());
            jo.put("name", a.getTypeName());
            jo.put("num", a.getTypeWeight());
            json.add(jo);
        }
        mav.addObject("json", json);
        
        List<AnalysisLabel> analysisListByPage= analysisManageService.groupList(map);//分页查询二极栏目

        
        System.out.print(analysisListByPage.size());
        mav.addObject("analysisListByPage", analysisListByPage);
        mav.addObject("analysisListParent", analysisListParent);
//        mav.addObject("analysisManageList", analysisManageList);
        mav.addObject("tname", tname);
        mav.addObject("controlURL", "selectAnalysisListByPage");//控制页码传递URL
        mav.addObject("page", page);
//        mav.addObject("placeholder", theme_name);
        // 
        mav.setViewName("columnManageFrame");
        return mav;
    }

//    @RequestMapping("selectAnalysisListByPage")
//    public ModelAndView selectAnalysisListByPage(HttpServletRequest request, 
//            HttpServletResponse response) throws UnsupportedEncodingException{
////  	    request.setCharacterEncoding("UTF-8");	
////        response.setCharacterEncoding("UTF-8");
//        
//        ModelAndView mav = new ModelAndView();       
//        Page page=new Page(); //分页类
//        List<AnalysisManage> analysisListParent=analysisManageService.parentList();
//        List<AnalysisManage> analysisManageList= analysisManageService.list();
//        int count = analysisManageService.countByGroup(tname);//每一个一级栏目下面二极栏目的数量
//        Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数       
//        String currentPage=request.getParameter("currentPage");
//        Pattern pattern = Pattern.compile("[0-9]{1,9}");
//        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
//            page.setCurrentPage(1);
//        } else {
//            page.setCurrentPage(Integer.valueOf(currentPage));
//        }
//        page.setTotalNumber(count);
//        page.count();
//        map.put("page", page);
//        map.put("type_name",tname);
//        List<AnalysisManage> analysisListByPage= analysisManageService.groupList(map);//分页查询二极栏目
//        List<AnalysisManage> typenameOrder = analysisManageService.getOrderByTypename();//得到一级分类顺序
//        mav.addObject("typenameOrder", typenameOrder);
//        for(AnalysisManage a : typenameOrder){
//            System.out.println(a.getType_name()+":"+(a.getWeight()+1));
//        }
//        JSONArray json = new JSONArray();
//        for(AnalysisManage a : typenameOrder){
//            JSONObject jo = new JSONObject();
//            jo.put("id", a.getWeight());
//            jo.put("name", a.getType_name());
//            jo.put("num", a.getWeight());
//            json.add(jo);
//        }
//        mav.addObject("json", json);
//     
//        mav.addObject("analysisListByPage", analysisListByPage);
//        mav.addObject("analysisListParent", analysisListParent);
//        mav.addObject("analysisManageList", analysisManageList);
//        mav.addObject("tname", tname);      
//        mav.addObject("page", page);
//        mav.addObject("controlURL", "selectAnalysisListByPage");//控制页码传递URL
//        mav.addObject("placeholder", theme_name);
//        mav.setViewName("columnManageFrame");
//        
//        return mav;
//    }
//    
    @RequestMapping("deleteLabel")
    public ModelAndView deleteLabel(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView mav = new ModelAndView();
    	//int id = Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
    	int label_id=Integer.parseInt(request.getParameter("label_id"));
    	   System.out.print("label_id:"+label_id);
    	   analysisManageService.delete(label_id);
    	   Page page=new Page(); //分页类
           List<AnalysisType> analysisListParent=analysisManageService.parentList();

           int count = analysisManageService.countByGroup(type_id);//每一个一级栏目下面二极栏目的数量
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
           map.put("page", page);
           map.put("type_id",type_id);
           List<AnalysisLabel> analysisListByPage= analysisManageService.groupList(map);//分页查询二极栏目
           List<AnalysisType> typenameOrder = analysisManageService.getOrderByTypename();//得到一级分类顺序
           mav.addObject("typenameOrder", typenameOrder);
           for(AnalysisType a : typenameOrder){
               System.out.println(a.getTypeName()+":"+(a.getTypeWeight()));
           }
           JSONArray json = new JSONArray();
           for(AnalysisType a : typenameOrder){
               JSONObject jo = new JSONObject();
               jo.put("id", a.getTypeWeight());
               jo.put("name", a.getTypeName());
               jo.put("num", a.getTypeWeight());
               json.add(jo);
           }
           mav.addObject("json", json);
           mav.addObject("analysisListByPage", analysisListByPage);
           mav.addObject("analysisListParent", analysisListParent);
           mav.addObject("type_id", type_id); 
           mav.addObject("tname", tname);      
           mav.addObject("page", page);
//           mav.addObject("placeholder", theme_name);
           mav.addObject("controlURL", "selectAnalysisListByPage");//控制页码传递URL
           mav.setViewName("columnManageFrame");
           return mav;
    	
    }
//    
//    @RequestMapping("searchCol")
//    public ModelAndView searchCol(HttpServletRequest request, 
//            HttpServletResponse response) throws IOException{
////request.setCharacterEncoding("UTF-8");    	
////        response.setCharacterEncoding("UTF-8");
//    	ModelAndView mav = new ModelAndView();
//    	theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");
//    	System.out.println("theme_name"+theme_name);
////    	String theme_name="%"+search+"%";
//    	   Page page=new Page(); //分页类
//           List<AnalysisManage> analysisListParent=analysisManageService.parentList();
//           List<AnalysisManage> analysisManageList= analysisManageService.list();
//           Map<String,Object> mapSearch = new HashMap<String, Object>();
//           mapSearch.put("theme_name", theme_name);
//           mapSearch.put("type_name", tname);
//           int count = analysisManageService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
//           System.out.println("count:"+count);
//           Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数       
//           String currentPage=request.getParameter("currentPage");
//           Pattern pattern = Pattern.compile("[0-9]{1,9}");
//           if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
//               page.setCurrentPage(1);
//           } else {
//               page.setCurrentPage(Integer.valueOf(currentPage));
//           }
//           page.setTotalNumber(count);
//           page.count();
//           map.put("page", page);
//           map.put("type_name",tname);
//           map.put("theme_name", theme_name);
//           List<AnalysisManage> analysisListByPage= analysisManageService.search(map);//分页查询二极栏目
//           List<AnalysisManage> typenameOrder = analysisManageService.getOrderByTypename();//得到一级分类顺序
//           mav.addObject("typenameOrder", typenameOrder);
//           for(AnalysisManage a : typenameOrder){
//               System.out.println(a.getType_name()+":"+(a.getWeight()+1));
//           }
//           JSONArray json = new JSONArray();
//           for(AnalysisManage a : typenameOrder){
//               JSONObject jo = new JSONObject();
//               jo.put("id", a.getWeight());
//               jo.put("name", a.getType_name());
//               jo.put("num", a.getWeight());
//               json.add(jo);
//           }
//           mav.addObject("json", json);
//           mav.addObject("analysisListByPage", analysisListByPage);
//           mav.addObject("analysisListParent", analysisListParent);
//           mav.addObject("analysisManageList", analysisManageList);
//           mav.addObject("tname", tname);      
//           mav.addObject("page", page);
//           mav.addObject("placeholder", theme_name);
//           mav.addObject("controlURL", "searchPage");//控制页码传递URL
//           mav.setViewName("columnManageFrame");           
//           return mav;
//    }
//    
//    
//    @RequestMapping("searchPage")
//    public ModelAndView searchPage(HttpServletRequest request, 
//            HttpServletResponse response) throws IOException{
////request.setCharacterEncoding("UTF-8");    	
////        response.setCharacterEncoding("UTF-8");
//    	ModelAndView mav = new ModelAndView();
////    	String theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");
//    	System.out.println("theme_name"+theme_name);
////    	String theme_name="%"+search+"%";
//    	   Page page=new Page(); //分页类
//           List<AnalysisManage> analysisListParent=analysisManageService.parentList();
//           List<AnalysisManage> analysisManageList= analysisManageService.list();
//           Map<String,Object> mapSearch = new HashMap<String, Object>();
//           mapSearch.put("theme_name", theme_name);
//           mapSearch.put("type_name", tname);
//           int count = analysisManageService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
//           System.out.println("count:"+count);
//           Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数       
//           String currentPage=request.getParameter("currentPage");
//           Pattern pattern = Pattern.compile("[0-9]{1,9}");
//           if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
//               page.setCurrentPage(1);
//           } else {
//               page.setCurrentPage(Integer.valueOf(currentPage));
//           }
//           page.setTotalNumber(count);
//           page.count();
//           map.put("page", page);
//           map.put("type_name",tname);
//           map.put("theme_name", theme_name);
//           List<AnalysisManage> analysisListByPage= analysisManageService.searchGroupList(map);//分页查询二极栏目
//           List<AnalysisManage> typenameOrder = analysisManageService.getOrderByTypename();//得到一级分类顺序
//           mav.addObject("typenameOrder", typenameOrder);
//           for(AnalysisManage a : typenameOrder){
//               System.out.println(a.getType_name()+":"+(a.getWeight()+1));
//           }
//           JSONArray json = new JSONArray();
//           for(AnalysisManage a : typenameOrder){
//               JSONObject jo = new JSONObject();
//               jo.put("id", a.getWeight());
//               jo.put("name", a.getType_name());
//               jo.put("num", a.getWeight());
//               json.add(jo);
//           }
//           mav.addObject("json", json);
//           mav.addObject("analysisListByPage", analysisListByPage);
//           mav.addObject("analysisListParent", analysisListParent);
//           mav.addObject("analysisManageList", analysisManageList);
//           mav.addObject("tname", tname);      
//           mav.addObject("page", page);
//           mav.addObject("placeholder", theme_name);
//           mav.addObject("controlURL", "searchPage");//控制页码传递URL
//           mav.setViewName("columnManageFrame");           
//           return mav;
//    }
    
    @RequestMapping("addLabel")
    public ModelAndView addLabel(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{

    	ModelAndView mav = new ModelAndView();
    	
    	
    	int label_weight = analysisManageService.getMaxWeight(type_id);
    	AnalysisLabel al = new AnalysisLabel();
    	al.setTypeId(type_id);
    	al.setLabelName(request.getParameter("addLabelName"));
    	al.setLabelWeight(label_weight);
    	analysisManageService.addLabel(al);
    	
    	  Page page=new Page(); //分页类
          List<AnalysisType> analysisListParent=analysisManageService.parentList();
          int count = analysisManageService.countByGroup(Integer.parseInt(request.getParameter("type_id")));//每一个一级栏目下面二极栏目的数量
          System.out.println("count:"+count);
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
          map.put("page", page);
          map.put("type_id",type_id);
//          map.put("theme_name", theme_name);
          List<AnalysisLabel> analysisListByPage= analysisManageService.groupList(map);//分页查询二极栏目
          List<AnalysisType> typenameOrder = analysisManageService.getOrderByTypename();//得到一级分类顺序
          mav.addObject("typenameOrder", typenameOrder);
          for(AnalysisType a : typenameOrder){
              System.out.println(a.getTypeName()+":"+(a.getTypeWeight()));
          }
          JSONArray json = new JSONArray();
          for(AnalysisType a : typenameOrder){
              JSONObject jo = new JSONObject();
              jo.put("id", a.getTypeWeight());
              jo.put("name", a.getTypeName());
              jo.put("num", a.getTypeWeight());
              json.add(jo);
          }
          mav.addObject("json", json);
          mav.addObject("analysisListByPage", analysisListByPage);
          mav.addObject("analysisListParent", analysisListParent);
          mav.addObject("tname", tname);      
          mav.addObject("type_id", type_id); 
          mav.addObject("page", page);
//          mav.addObject("placeholder", theme_name);
          mav.addObject("controlURL", "selectAnalysisListByPage");//控制页码传递URL
          mav.setViewName("columnManageFrame");           
          return mav;
    }
//    
    @RequestMapping("editLabel")
    public ModelAndView editLabel(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	System.out.println("进入方法--------------");
//    	request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
//    	String theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");
    
    	
//    	String theme_name="%"+search+"%";
    	
    	AnalysisLabel al = new AnalysisLabel();
    	al.setLabelId(Integer.parseInt(request.getParameter("editLabelID")));
    	al.setLabelName(request.getParameter("editLabelName"));
    	analysisManageService.editLabel(al);
    	
    	
    	   Page page=new Page(); //分页类
           List<AnalysisType> analysisListParent=analysisManageService.parentList();
           int count = analysisManageService.countByGroup(type_id);//每一个一级栏目下面二极栏目的数量
           System.out.println("count:"+count);
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
           map.put("page", page);
           map.put("type_id",type_id);
//           map.put("theme_name", theme_name);
           List<AnalysisLabel> analysisListByPage= analysisManageService.groupList(map);//分页查询二极栏目
           List<AnalysisType> typenameOrder = analysisManageService.getOrderByTypename();//得到一级分类顺序
           mav.addObject("typenameOrder", typenameOrder);
           for(AnalysisType a : typenameOrder){
               System.out.println(a.getTypeName()+":"+(a.getTypeWeight()+1));
           }
           JSONArray json = new JSONArray();
           for(AnalysisType a : typenameOrder){
               JSONObject jo = new JSONObject();
               jo.put("id", a.getTypeWeight());
               jo.put("name", a.getTypeName());
               jo.put("num", a.getTypeWeight());
               json.add(jo);
           }
           mav.addObject("json", json);
           mav.addObject("analysisListByPage", analysisListByPage);
           mav.addObject("analysisListParent", analysisListParent);
           mav.addObject("tname", tname);     
           mav.addObject("type_id", type_id);    
           mav.addObject("page", page);
//           mav.addObject("placeholder", theme_name);
           mav.addObject("controlURL", "selectAnalysisListByPage");//控制页码传递URL
           mav.setViewName("columnManageFrame");           
           return mav;
    }
//    
//    
    @RequestMapping("updateIsShow")
    public ModelAndView updateIsShow(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	System.out.println("进入方法--------------");
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	
    	AnalysisLabel al = new AnalysisLabel();
    	al.setLabelId(Integer.parseInt(request.getParameter("label_id")));
    	al.setIsShow(Integer.parseInt(request.getParameter("is_show")));
    	analysisManageService.updateShow(al);
    	
    	   Page page=new Page(); //分页类
           List<AnalysisType> analysisListParent=analysisManageService.parentList();
           int count = analysisManageService.countByGroup(type_id);//每一个一级栏目下面二极栏目的数量
           System.out.println("count:"+count);
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
           map.put("page", page);
           map.put("type_id",type_id);
//           map.put("theme_name", theme_name);
           List<AnalysisLabel> analysisListByPage= analysisManageService.groupList(map);//分页查询二极栏目
           List<AnalysisType> typenameOrder = analysisManageService.getOrderByTypename();//得到一级分类顺序
           mav.addObject("typenameOrder", typenameOrder);
           for(AnalysisType a : typenameOrder){
               System.out.println(a.getTypeName()+":"+(a.getTypeWeight()));
           }
           JSONArray json = new JSONArray();
           for(AnalysisType a : typenameOrder){
               JSONObject jo = new JSONObject();
               jo.put("id", a.getTypeWeight());
               jo.put("name", a.getTypeName());
               jo.put("num", a.getTypeWeight());
               json.add(jo);
           }
           mav.addObject("json", json);
           mav.addObject("analysisListByPage", analysisListByPage);
           mav.addObject("analysisListParent", analysisListParent);
           mav.addObject("tname", tname);     
           mav.addObject("type_id", type_id);   
           mav.addObject("page", page);
//           mav.addObject("placeholder", theme_name);
           mav.addObject("controlURL", "selectAnalysisListByPage");//控制页码传递URL
           mav.setViewName("columnManageFrame");           
           return mav;
    }
    
    
	@RequestMapping("updateLabelWeight")//更新板块权重
    public ModelAndView updateLabelWeight(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8"); //防止乱码   	
        response.setCharacterEncoding("UTF-8");//防止乱码  
        
        System.out.println("type_id:"+type_id);
        
        ModelAndView mav = new ModelAndView();//返回视图类    
        Page page=new Page(); //分页类
        
        
//        int type_id = Integer.parseInt(request.getParameter("type_id"));
        String sort = request.getParameter("sort");

        String[] array = sort.split(",");
        int []labelIdArray= new int[array.length];
        
        System.out.println("arraylegth:"+array.length);
        
       
        for(int i=1;i<=array.length;i++) {
        	Map map = new HashMap();
        	System.out.println("array[i-1]"+array[i-1]);
        	map.put("type_id", type_id);
        	map.put("oldWeight", array[i-1]);
        	System.out.println("你是为啥咧？："+analysisManageService.getLabelId(map));
        	int label_id = analysisManageService.getLabelId(map);
        	labelIdArray[i-1]=label_id;       	
        }
        
        for(int i=1;i<=labelIdArray.length;i++) {
        	Map map = new HashMap();
        	map.put("label_id", labelIdArray[i-1]);
        	map.put("newWeight", i);
        	analysisManageService.updateWeight(map);
        }
             
        
        

        List<AnalysisType> analysisListParent=analysisManageService.parentList();
        int count = analysisManageService.countByGroup(type_id);//每一个一级栏目下面二极栏目的数量
        System.out.println("count:"+count);
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
        map.put("page", page);
        map.put("type_id",type_id);
//        map.put("theme_name", theme_name);
        List<AnalysisLabel> analysisListByPage= analysisManageService.groupList(map);//分页查询二极栏目
        List<AnalysisType> typenameOrder = analysisManageService.getOrderByTypename();//得到一级分类顺序
        mav.addObject("typenameOrder", typenameOrder);
        for(AnalysisType a : typenameOrder){
            System.out.println(a.getTypeName()+":"+(a.getTypeWeight()));
        }
        JSONArray json = new JSONArray();
        for(AnalysisType a : typenameOrder){
            JSONObject jo = new JSONObject();
            jo.put("id", a.getTypeWeight());
            jo.put("name", a.getTypeName());
            jo.put("num", a.getTypeWeight());
            json.add(jo);
        }
        mav.addObject("json", json);
        mav.addObject("analysisListByPage", analysisListByPage);
        mav.addObject("analysisListParent", analysisListParent);
        mav.addObject("tname", tname);     
        mav.addObject("type_id", type_id);   
        mav.addObject("page", page);
//        mav.addObject("placeholder", theme_name);
        mav.addObject("controlURL", "selectAnalysisListByPage");//控制页码传递URL
        mav.setViewName("columnManageFrame");           
        return mav;
    }
   

}
