package com.wuhan_data.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.indi_TF;
//import com.wuhan_data.pojo.Page;
import com.wuhan_data.service.IndexManageService;
import com.wuhan_data.tools.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("")
public class IndexManageController {
	
	@Autowired
	IndexManageService indexManageService;
	private static String keyword="PMI指数（全国）";
	private List<indi_TF> indi_TFList;
	//首页去元数据管理界面
	 @RequestMapping("toMetaDataManage")
		public String toMetaDataManage() {
//		 ModelAndView mav = new ModelAndView();
//		 mav.setViewName("metaDataManage");
	       return "redirect:listIndiCorrelative";
		}
	//指标数据维护页去首页界面
		 @RequestMapping("toIndex")
			public ModelAndView toIndex() {
			 ModelAndView mav = new ModelAndView();
			 mav.setViewName("index");
		        return mav;
			}
	
    @RequestMapping("listIndexManage")
    public ModelAndView listIndexManage(HttpServletRequest request, 
            HttpServletResponse response) throws ParseException{
        ModelAndView mav = new ModelAndView();
        
        Page page=new Page(); //分页类
        Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数
        String currentPage=request.getParameter("currentPage");
        Pattern pattern = Pattern.compile("[0-9]{1,9}");
        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
            page.setCurrentPage(1);
        } else {
            page.setCurrentPage(Integer.valueOf(currentPage));
        }
        int total=indexManageService.total();
        page.setTotalNumber(total);
        page.count();
        System.out.println(page.getDbIndex());
        System.out.println(page.getDbNumber());
        map.put("page", page);
        
        List<IndexManage> indexManageList= indexManageService.listAddPage(map);
        //System.out.println(indexManageList.get(0).getStart_time());
        
        //将时间格式转换为字符串
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        for(int i=0;i<indexManageList.size();i++)
//        {
//        	 System.out.println(indexManageList.size());
//        	Date date = new SimpleDateFormat("yyyy-MM-dd").parse(indexManageList.get(i).getStart_time());
//        	String str = format.format(date);
//        	indexManageList.get(i).setStart_time(str);
//        	date = new SimpleDateFormat("yyyy-MM-dd").parse(indexManageList.get(i).getEnd_time());
//        	str = format.format(date);
//        	indexManageList.get(i).setEnd_time(str);;
//        }
       
        mav.addObject("controlURL", "selectListIndexManageByPage");//控制页码传递URL
        mav.addObject("page", page);
        
        mav.addObject("indexManageList", indexManageList);
        // 
        mav.setViewName("indiDataManage");
        return mav;
    }
    
    @RequestMapping("selectListIndexManageByPage")
    public ModelAndView selectAnalysisListByPage(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException{
  	   
        ModelAndView mav = new ModelAndView();       
        Page page=new Page(); //分页类
       
        Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数       
        String currentPage=request.getParameter("currentPage");
        Pattern pattern = Pattern.compile("[0-9]{1,9}");
        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
            page.setCurrentPage(1);
        } else {
            page.setCurrentPage(Integer.valueOf(currentPage));
        }
        int total = indexManageService.total();
        page.setTotalNumber(total);
        page.count();
        map.put("page", page);
        List<IndexManage> indexManageList= indexManageService.listAddPage(map);
          
        mav.addObject("page", page);
        mav.addObject("controlURL", "selectListIndexManageByPage");//控制页码传递URL
        mav.addObject("indexManageList", indexManageList);
        mav.setViewName("indiDataManage");
        return mav;
    }
    
    @RequestMapping("updateIndiData")
    public String updateIndiData(@RequestParam("Id") int id,
  			@RequestParam("Code") String code,@RequestParam("Name") String name,@RequestParam("Status") int status,
  			Model model){
        //ModelAndView mav = new ModelAndView();
        IndexManage indexManage= new IndexManage();
        indexManage.setId(id);
        indexManage.setIndi_code(code);
        indexManage.setIndi_name(name);
//        indexManage.setSource(source);
//        indexManage.setStatus(status);
//        indexManage.setFreq_code(freqCode);
//        indexManage.setStart_time(startTime);
//        indexManage.setTime_point(timePoint);
//        indexManage.setEnd_time(endTime);
//        indexManage.setIndi_value(value);
        indexManage.setStatus(status);
        indexManageService.update(indexManage);
        model.addAttribute("updateSuccess", "更新成功");
        System.out.println("sssssss");
        // 
        //mav.addObject("updateSuccess", "更新成功");
        // 
        //mav.setViewName("indiDataManage");
        return "redirect:listIndexManage";
       
    }
    
    
    @RequestMapping("delete")
	public String questionDelete(int id) {
    	indexManageService.delete(id);
		return "redirect:listIndexManage";
	}
    
    @RequestMapping("per_show")
	public String per_show(int id) {
    	indexManageService.per_show(id);
		return "redirect:listIndexManage";
	}
    
    @RequestMapping("no_per_show")
   	public String no_per_show(int id) {
       	indexManageService.no_per_show(id);
   		return "redirect:listIndexManage";
   	}
    
    
    @RequestMapping("addIndiData")
    public String addIndiData(
  			@RequestParam("addCode") String code,@RequestParam("addName") String name,@RequestParam("addStatus") int status,
  			Model model){
        //ModelAndView mav = new ModelAndView();
        IndexManage indexManage= new IndexManage();
        //indexManage.setId(id);
        indexManage.setIndi_code(code);
        indexManage.setIndi_name(name);
//        indexManage.setSource(source);
        indexManage.setStatus(status);
//        indexManage.setFreq_code(freqCode);
//        indexManage.setStart_time(startTime);
//        indexManage.setTime_point(timePoint);
//        indexManage.setEnd_time(endTime);
//        indexManage.setIndi_value(value);
        indexManageService.add(indexManage);
        model.addAttribute("addSuccess", "添加成功");
        System.out.println("sssssss");
        // 
        //mav.addObject("updateSuccess", "更新成功");
        // 
        //mav.setViewName("indiDataManage");
        return "redirect:listIndexManage";
    }
    
    
//    @RequestMapping("IndiSearch")
//	public ModelAndView indiSearch(String SearchKeyWord,Page page,Model model) {
//    	 ModelAndView mav = new ModelAndView();
//    	 Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数
//    	 map.put("page", page);
//         map.put("SearchKeyWord",SearchKeyWord);
//         List<IndexManage> indexManageList= indexManageService.indiSearch(map);
//         System.out.println(indexManageList.get(0).getStart_time());
//         
//         int total = indexManageList.size();
//         //page.caculateLast(total);
//         // 
//         mav.addObject("indexManageList", indexManageList);
//         // 
//         mav.setViewName("indiDataManage");
//         return mav;
//	}
    
    @RequestMapping("IndiSearch")
    public ModelAndView searchCol(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView mav = new ModelAndView();
    	keyword = java.net.URLDecoder.decode(request.getParameter("keyWord"),"UTF-8");
    	System.out.println("keyword"+keyword);
//    	String theme_name="%"+search+"%";
    	   Page page=new Page(); //分页类
          
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("keyword", keyword);
         
//           int count = analysisManageService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
//           System.out.println("count:"+count);
           Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数       
           String currentPage=request.getParameter("currentPage");
           Pattern pattern = Pattern.compile("[0-9]{1,9}");
           if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
               page.setCurrentPage(1);
           } else {
               page.setCurrentPage(Integer.valueOf(currentPage));
           }
           int count = indexManageService.searchCount(mapSearch);
           page.setTotalNumber(count);
           page.count();
           map.put("page", page);
           map.put("keyword",keyword);
           
           List<IndexManage> indexManageList= indexManageService.indiSearch(map);
//           List<AnalysisManage> typenameOrder = analysisManageService.getOrderByTypename();//得到一级分类顺序
           mav.addObject("indexManageList", indexManageList);
           mav.addObject("page", page);
           mav.addObject("controlURL", "searchIndiPage");//控制页码传递URL
           mav.setViewName("indiDataManage");           
           return mav;
    }
    
    
    @RequestMapping("searchIndiPage")
    public ModelAndView searchPage(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView mav = new ModelAndView();
//    	String theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");
    	System.out.println("keyword"+keyword);
//    	String theme_name="%"+search+"%";
    	   Page page=new Page(); //分页类
          
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("keyword", keyword);
           int count = indexManageService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
           map.put("keyword", keyword);
           List<IndexManage> indexManageList= indexManageService.indiSearch(map);//分页查询二极栏目
          
        
          
           mav.addObject("indexManageList", indexManageList);
              
           mav.addObject("page", page);
          
           mav.addObject("controlURL", "searchIndiPage");//控制页码传递URL
           mav.setViewName("indiDataManage");           
           return mav;
    }
    
    
    
    @RequestMapping("test")
   	public String testSource(Model model) {
    	indi_TFList=indexManageService.listTF();
    	 System.out.println("长度："+indi_TFList.size());
//    	 model.addAttribute("indi_TFList", indi_TFList);
//    	 for(int i=0;i<1;i++)
//    	 {
//    		 String date="2017-02-04";
//    		 IndexManage indexManage= new IndexManage();
//    	        indexManage.setIndi_code(indi_TFList.get(i).getIndi_code());
//    	        System.out.println(indi_TFList.get(i).getIndi_code());
//    	        
//    	        indexManage.setIndi_name(indi_TFList.get(i).getIndi_name());
//    	        System.out.println(indi_TFList.get(i).getIndi_name());
//    	        
//    	        indexManage.setSource(indi_TFList.get(i).getSjly());
//    	        System.out.println(indi_TFList.get(i).getSjly());
//    	        
//    	        indexManage.setStart_time(date);
//    	        indexManage.setEnd_time(date);
//    	        
//    	        indexManage.setStatus(0);
//    	        
//    	        indexManage.setFreq_code(indi_TFList.get(i).getFreq_code());
//    	        System.out.println(indi_TFList.get(i).getFreq_code());
//    	        
//    	        indexManage.setTime_point(207);
//    	      
//    	        indexManage.setIndi_value(indi_TFList.get(i).getIndi_value());
//    	        System.out.println(indi_TFList.get(i).getIndi_value());
//    	        
//    	        System.out.println("插入0");
//    		 
//    	        indexManageService.add(indexManage);
//
//    	        System.out.println("插入");
//    	 }
//    	 
    	 
    	 
//    	 String ss=indi_TFList.get(0).getDate_code();
//		 int time =Integer.parseInt(ss.substring(0,4));
//    	 int min=time;
//    	 int max=time;
//    	 for(int i=1;i<indi_TFList.size();i++)
//    	 {
//    		 ss=indi_TFList.get(i).getDate_code();
//    		 time =Integer.parseInt(ss.substring(0,4));
//    		 String freq=ss.substring(4,6);
//    		 if(indi_TFList.get(i).getIndi_name().equals(indi_TFList.get(i-1).getIndi_name()))
//    		 {
//    			 if(time<min)
//        		 {
//        			 min=time;
//        		 }
//        		 if(time>max)
//        		 {
//        			 max=time;
//        		 }
//    		 }
//    		 else
//    		 {
//    			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
//    			   Date date = null; 
//    			   try { 
//    				ss=Integer.toString(time);
//    			    date = format.parse(ss); 
//    			    System.out.println("日期："+date);
//    			   } catch (ParseException e) { 
//    			    e.printStackTrace(); 
//    			   } 
//    		 }
//    		 
//    		 
//    		 
//    		 
//    	 }
   		return "redirect:test1";
   	}
    
    @RequestMapping("test1")
    public String test(Model model)
    {
    	System.out.println("test1长度："+indi_TFList.size());
   	 for(int i=0;i<indi_TFList.size();i++)
   	 {
   		 	IndexManage indexManage= new IndexManage();
   	        indexManage.setIndi_code(indi_TFList.get(i).getIndi_code());
   	        System.out.println(indi_TFList.get(i).getIndi_code());
   	        
   	        indexManage.setIndi_name(indi_TFList.get(i).getIndi_name());
   	        System.out.println(indi_TFList.get(i).getIndi_name());
   	        
   	        indexManage.setStatus(0);
   	        
   	        System.out.println("插入0");
   		 
   	        indexManageService.add(indexManage);

   	        System.out.println("插入");
        
        
    }
   	return "ok";
    }
}
