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
        int totalH=indexManageService.total();
        page.setTotalNumber(totalH);
        page.count();
        System.out.println(page.getDbIndex());
        System.out.println(page.getDbNumber());
        map.put("page", page);
        
        List<IndexManage> indexManageList= indexManageService.listAddPage(map);
        System.out.println("测试名称："+indexManageList.get(0).getIndi_name());
        mav.addObject("controlURL", "selectListIndexManageByPage");//控制页码传递URL
        mav.addObject("page", page);
        
        mav.addObject("indexManageList", indexManageList);
        // 
        mav.setViewName("indiDataManage");
        return mav;
    }
    
    @RequestMapping("listIndexManageG")
    public ModelAndView listIndexManageG(HttpServletRequest request, 
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
        int totalG=indexManageService.totalG();
        page.setTotalNumber(totalG);
        page.count();
        System.out.println(page.getDbIndex());
        System.out.println(page.getDbNumber());
        map.put("page", page);
        List<IndexManage> indexManageList= indexManageService.listAddPageG(map);
        System.out.println("测试是否展示："+indexManageList.get(0).getIs_show());
        mav.addObject("controlURL", "selectListIndexManageByPageG");//控制页码传递URL
        mav.addObject("page", page);
        mav.addObject("indexManageList", indexManageList);
        mav.setViewName("indiDataManageG");
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
    @RequestMapping("selectListIndexManageByPageG")
    public ModelAndView selectAnalysisListByPageG(HttpServletRequest request, 
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
        int total = indexManageService.totalG();
        page.setTotalNumber(total);
        page.count();
        map.put("page", page);
        List<IndexManage> indexManageList= indexManageService.listAddPageG(map);
          
        mav.addObject("page", page);
        mav.addObject("controlURL", "selectListIndexManageByPageG");//控制页码传递URL
        mav.addObject("indexManageList", indexManageList);
        mav.setViewName("indiDataManageG");
        return mav;
    }
    
    @RequestMapping("updateIndiData")
    public String updateIndiData(@RequestParam("Id") int id,
  			@RequestParam("Code") String code,@RequestParam("areaName") String areaName,@RequestParam("lj") String lj,@RequestParam("Name") String name,@RequestParam("Status") String status,
  			Model model){
        //ModelAndView mav = new ModelAndView();
        IndexManage indexManage= new IndexManage();
        indexManage.setId(id);
        indexManage.setIndi_code(code);
        indexManage.setIndi_name(name);
        indexManage.setArea_name(areaName);
        indexManage.setIs_show(status);
        indexManage.setLj(lj);
        indexManageService.update(indexManage);
        model.addAttribute("updateSuccess", "更新成功");
        System.out.println("sssssss");
        // 
        //mav.addObject("updateSuccess", "更新成功");
        // 
        //mav.setViewName("indiDataManage");
        return "redirect:listIndexManage";
       
    }
    
    @RequestMapping("updateIndiDataG")
    public String updateIndiDataG(@RequestParam("Id") int id,
  			@RequestParam("Code") String code,@RequestParam("lj") String lj,@RequestParam("Name") String name,@RequestParam("Status") String status,
  			Model model){
        //ModelAndView mav = new ModelAndView();
        IndexManage indexManage= new IndexManage();
        indexManage.setId(id);
        indexManage.setIndi_code(code);
        indexManage.setIndi_name(name);
//        indexManage.setArea_name(areaName);
        indexManage.setIs_show(status);
        indexManage.setLj(lj);
        indexManageService.updateG(indexManage);
        model.addAttribute("updateSuccess", "更新成功");
        System.out.println("sssssss");
        // 
        //mav.addObject("updateSuccess", "更新成功");
        // 
        //mav.setViewName("indiDataManage");
        return "redirect:listIndexManageG";
       
    }
    
    @RequestMapping("searchUpdateIndiData")
    public String searchUpdateIndiData(@RequestParam("Id") int id,
  			@RequestParam("Code") String code,@RequestParam("Name") String name,@RequestParam("Status") int status,
  			Model model){
        IndexManage indexManage= new IndexManage();
        indexManage.setId(id);
        indexManage.setIndi_code(code);
        indexManage.setIndi_name(name);
//        indexManage.setShow_type(showType);
        indexManage.setStatus(status);
        indexManageService.update(indexManage);
        model.addAttribute("updateSuccess", "更新成功");
        System.out.println("sssssss");
        return "redirect:IndiSearchPage";
       
    }
    
    
    @RequestMapping("delete")
	public String questionDelete(HttpServletRequest request, 
            HttpServletResponse response) {
    	int indi_id=Integer.parseInt(request.getParameter("indi_id"));
    	indexManageService.delete(indi_id);
		return "redirect:listIndexManage";
	}
    @RequestMapping("deleteG")
   	public String questionDeleteG(HttpServletRequest request, 
               HttpServletResponse response) {
       	int indi_id=Integer.parseInt(request.getParameter("indi_id"));
       	indexManageService.deleteG(indi_id);
   		return "redirect:listIndexManageG";
   	}
    
    @RequestMapping("searchDelete")
   	public String searchDelete(HttpServletRequest request, 
               HttpServletResponse response) {
       	int indi_id=Integer.parseInt(request.getParameter("indi_id"));
       	indexManageService.delete(indi_id);
   		return "redirect:IndiSearchPage";
   	}
    
    @RequestMapping("per_show")
	public String per_show(HttpServletRequest request, 
            HttpServletResponse response) {
    	int indi_id=Integer.parseInt(request.getParameter("indi_id"));
    	indexManageService.per_show(indi_id);
		return "redirect:listIndexManage";
	}
    
    @RequestMapping("per_showG")
   	public String per_showG(HttpServletRequest request, 
               HttpServletResponse response) {
       	int indi_id=Integer.parseInt(request.getParameter("indi_id"));
       	indexManageService.per_showG(indi_id);
   		return "redirect:listIndexManageG";
   	}
    
    @RequestMapping("search_per_show")
   	public String search_per_show(HttpServletRequest request, 
               HttpServletResponse response) {
       	int indi_id=Integer.parseInt(request.getParameter("indi_id"));
       	indexManageService.per_show(indi_id);
   		return "redirect:IndiSearchPage";
   	}
    
    @RequestMapping("no_per_show")
   	public String no_per_show(HttpServletRequest request, 
            HttpServletResponse response) {
    	int indi_id=Integer.parseInt(request.getParameter("indi_id"));
       	indexManageService.no_per_show(indi_id);
   		return "redirect:listIndexManage";
   	}
    
    @RequestMapping("no_per_showG")
   	public String no_per_showG(HttpServletRequest request, 
            HttpServletResponse response) {
    	int indi_id=Integer.parseInt(request.getParameter("indi_id"));
       	indexManageService.no_per_showG(indi_id);
   		return "redirect:listIndexManageG";
   	}
    
    @RequestMapping("search_no_per_show")
   	public String search_no_per_show(HttpServletRequest request, 
            HttpServletResponse response) {
    	int indi_id=Integer.parseInt(request.getParameter("indi_id"));
       	indexManageService.no_per_show(indi_id);
   		return "redirect:IndiSearchPage";
   	}
    
    
    @RequestMapping("addIndiData")
    public String addIndiData(
  			@RequestParam("addCode") String code,@RequestParam("addName") String name,@RequestParam("addStatus") String status,@RequestParam("addSource") String source,
  			@RequestParam("addAreaCode") String addAreaCode,@RequestParam("addAreaName") String addAreaName,@RequestParam("addLj") String addLj,Model model){
        IndexManage indexManage= new IndexManage();
        indexManage.setIndi_code(code);
        indexManage.setIndi_name(name);
        indexManage.setSource(source);
        indexManage.setIs_show(status);
        indexManage.setArea_code(addAreaCode);
        indexManage.setArea_name(addAreaName);
        indexManage.setLj(addLj);
        indexManageService.add(indexManage);
        model.addAttribute("addSuccess", "添加成功");
        System.out.println("sssssss");
        // 
        //mav.addObject("updateSuccess", "更新成功");
        // 
        //mav.setViewName("indiDataManage");
        return "redirect:listIndexManage";
    }
    
    @RequestMapping("addIndiDataG")
    public String addIndiDataG(
  			@RequestParam("addCode") String code,@RequestParam("addName") String name,@RequestParam("addStatus") String status,@RequestParam("addSource") String source,
  			@RequestParam("addLj") String addLj,Model model){
        IndexManage indexManage= new IndexManage();
        indexManage.setIndi_code(code);
        indexManage.setIndi_name(name);
        indexManage.setSource(source);
        indexManage.setIs_show(status);
        indexManage.setLj(addLj);
        indexManageService.addG(indexManage);
        model.addAttribute("addSuccess", "添加成功");
        System.out.println("sssssss");
        // 
        //mav.addObject("updateSuccess", "更新成功");
        // 
        //mav.setViewName("indiDataManage");
        return "redirect:listIndexManageG";
    }
    
    @RequestMapping("searchAddIndiData")
    public String searchAddIndiData(
  			@RequestParam("addCode") String code,@RequestParam("addName") String name,@RequestParam("addStatus") int status,@RequestParam("addSource") String source,
  			Model model){
        IndexManage indexManage= new IndexManage();
        indexManage.setIndi_code(code);
        indexManage.setIndi_name(name);
        indexManage.setStatus(status);
        indexManage.setSjly_name2(source);
        indexManageService.add(indexManage);
        model.addAttribute("addSuccess", "添加成功");
        System.out.println("sssssss");
        return "redirect:IndiSearchPage";
    }
    
    @RequestMapping("IndiSearch")
    public ModelAndView searchCol(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView mav = new ModelAndView();
    	keyword = java.net.URLDecoder.decode(request.getParameter("keyword"),"UTF-8");

    	System.out.println("IndiSearchkeyword"+keyword);
    	   Page page=new Page(); //分页类
          
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("keyword", keyword);
         
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
           mav.setViewName("indiSearchResult");           
           return mav;
    }
    
    @RequestMapping("IndiSearchG")
    public ModelAndView searchColG(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView mav = new ModelAndView();
    	keyword = java.net.URLDecoder.decode(request.getParameter("keyword"),"UTF-8");

    	System.out.println("IndiSearchkeyword"+keyword);
    	   Page page=new Page(); //分页类
          
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("keyword", keyword);
         
           Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数       
           String currentPage=request.getParameter("currentPage");
           Pattern pattern = Pattern.compile("[0-9]{1,9}");
           if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
               page.setCurrentPage(1);
           } else {
               page.setCurrentPage(Integer.valueOf(currentPage));
           }
           int count = indexManageService.searchCountG(mapSearch);
           page.setTotalNumber(count);
           page.count();
           map.put("page", page);
           map.put("keyword",keyword);
           
           List<IndexManage> indexManageList= indexManageService.indiSearchG(map);
//           List<AnalysisManage> typenameOrder = analysisManageService.getOrderByTypename();//得到一级分类顺序
           mav.addObject("indexManageList", indexManageList);
           mav.addObject("page", page);
           
           mav.addObject("controlURL", "searchIndiPageG");//控制页码传递URL
           mav.setViewName("indiSearchResultG");           
           return mav;
    }
    
    @RequestMapping("IndiSearchPage")
    public ModelAndView searchCol1(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView mav = new ModelAndView();
//    	keyword = java.net.URLDecoder.decode(request.getParameter("keyword"),"UTF-8");

    	System.out.println("IndiSearch1+keyword："+keyword);
    	   Page page=new Page(); //分页类
          
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("keyword", keyword);
         
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
           mav.setViewName("indiSearchResult");           
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
           mav.setViewName("indiSearchResult");           
           return mav;
    }
    
    @RequestMapping("searchIndiPageG")
    public ModelAndView searchPageG(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView mav = new ModelAndView();
//    	String theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");
    	System.out.println("keyword"+keyword);
//    	String theme_name="%"+search+"%";
    	   Page page=new Page(); //分页类
          
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("keyword", keyword);
           int count = indexManageService.searchCountG(mapSearch);//每一个一级栏目下面二极栏目的数量
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
           List<IndexManage> indexManageList= indexManageService.indiSearchG(map);//分页查询二极栏目
          
        
          
           mav.addObject("indexManageList", indexManageList);
              
           mav.addObject("page", page);
          
           mav.addObject("controlURL", "searchIndiPageG");//控制页码传递URL
           mav.setViewName("indiSearchResultG");           
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
   	        indexManage.setSjly_name2(indi_TFList.get(i).getSjly_name2());
   	        System.out.println("插入0");
   		 
   	        indexManageService.add(indexManage);

   	        System.out.println("插入");
        
        
    }
   	return "ok";
    }
}