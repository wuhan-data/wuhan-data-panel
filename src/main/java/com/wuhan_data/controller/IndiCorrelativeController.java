package com.wuhan_data.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.AnalysisManage;
//import com.wuhan_data.pojo.Page;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.service.AnalysisManageService;
import com.wuhan_data.service.IndexManageService;
import com.wuhan_data.service.IndiCorrelativeService;
import com.wuhan_data.tools.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("")
public class IndiCorrelativeController {
	@Autowired
	IndiCorrelativeService indiCorrelativeService;
	@Autowired
	IndexManageService indexManageService;
	@Autowired
	AnalysisManageService analysisManageService;
	 @RequestMapping("listIndiCorrelative")
	    public ModelAndView listHistorySearch(HttpServletRequest request, 
	            HttpServletResponse response){
	        ModelAndView mav = new ModelAndView();
	        //List<IndiCorrelative> IndiCorrelativeList= indiCorrelativeService.listAddPage();
	        int total = indiCorrelativeService.total();
	        Page page=new Page(); //分页类
	        Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数
	        String currentPage=request.getParameter("currentPage");
	        Pattern pattern = Pattern.compile("[0-9]{1,9}");
	        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
	            page.setCurrentPage(1);
	        } else {
	            page.setCurrentPage(Integer.valueOf(currentPage));
	        }
	        page.setTotalNumber(total);
	        page.count();
	        System.out.println(page.getDbIndex());
	        System.out.println(page.getDbNumber());
	        map.put("page", page);
	        List<IndiCorrelative> IndiCorrelativeList= indiCorrelativeService.listAddPage(map);
	        mav.addObject("controlURL", "selectIndiCorrelativeByPage");//控制页码传递URL
	        mav.addObject("page", page);
	        mav.addObject("IndiCorrelativeList", IndiCorrelativeList);
	        // 
	        List<AnalysisManage> InitAnalysisManageList= analysisManageService.list( );
	        List<IndexManage> InitIndexManageList = indexManageService.listIndi();
	        mav.addObject("InitAnalysisManageList", InitAnalysisManageList);
	        mav.addObject("InitIndexManageList", InitIndexManageList);
	       
	        mav.setViewName("metaDataManage");
	        return mav;
	    }
	 
	 @RequestMapping("selectIndiCorrelativeByPage")
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
	        int total = indiCorrelativeService.total();
	        page.setTotalNumber(total);
	        page.count();
	        map.put("page", page);
	        List<IndiCorrelative> IndiCorrelativeList= indiCorrelativeService.listAddPage(map);
	        List<AnalysisManage> InitAnalysisManageList= analysisManageService.list( );
	        List<IndexManage> InitIndexManageList = indexManageService.listIndi();
	        mav.addObject("InitAnalysisManageList", InitAnalysisManageList);
	        mav.addObject("InitIndexManageList", InitIndexManageList);
	        mav.addObject("page", page);
	        mav.addObject("controlURL", "selectIndiCorrelativeByPage");//控制页码传递URL
	        mav.addObject("IndiCorrelativeList", IndiCorrelativeList);
	        mav.setViewName("metaDataManage");
	        return mav;
	    }
	 
	 
	 
	 @RequestMapping("updateCorrelativeIndi")
	    public String updateIndiData(int Id,int ColumnId,String CorrelativeIndiCode,int indiIsshow,Model model){
	        ModelAndView mav = new ModelAndView();
		 	IndiCorrelative indiCorrelative= new IndiCorrelative();
		 	indiCorrelative.setId(Id);
		 	 System.out.println("ColumnId:0"+ColumnId);
		 	indiCorrelative.setCid(ColumnId);
		 	
		 	//根据指标代码查询指标名字
		 	String indi_name=indiCorrelativeService.searchIndiNameById(CorrelativeIndiCode);
		 	indiCorrelative.setIndi_id(Integer.parseInt(CorrelativeIndiCode));
		 	indiCorrelative.setIndi_name(indi_name);
		 	indiCorrelative.setIs_show(indiIsshow);
		 	indiCorrelativeService.update(indiCorrelative);
	        model.addAttribute("updateSuccess", "更新成功");
	        System.out.println("sssssss");
	        // 
	        //mav.addObject("updateSuccess", "更新成功");
	        // 
	        //mav.setViewName("indiDataManage");
	        
	        List<AnalysisManage> InitAnalysisManageList= analysisManageService.list( );
	        List<IndexManage> InitIndexManageList = indexManageService.listIndi();
	        mav.addObject("InitAnalysisManageList", InitAnalysisManageList);
	        mav.addObject("InitIndexManageList", InitIndexManageList);
	        
	        return "redirect:listIndiCorrelative";
	    }
	 
	 @RequestMapping("CorrelativeDelete")
		public String questionDelete(int id) {
		 indiCorrelativeService.delete(id);
		 
		 ModelAndView mav = new ModelAndView();
		 List<AnalysisManage> InitAnalysisManageList= analysisManageService.list( );
	        List<IndexManage> InitIndexManageList = indexManageService.listIndi();
	        mav.addObject("InitAnalysisManageList", InitAnalysisManageList);
	        mav.addObject("InitIndexManageList", InitIndexManageList);
	        
			return "redirect:listIndiCorrelative";
		}
	 
	 @RequestMapping("addCorrelativeIndiData")
	    public String addIndiData(int addCorrelativeCoumnId,String addCorrelativeIndiCode/*,String addCorrelativeIndiName,int addCorrelativeIndiIsshow*/, Model model){
	        ModelAndView mav = new ModelAndView();
		 	IndiCorrelative indiCorrelative= new IndiCorrelative();
		 	//根据指标id查询指标名字
		 	String indi_name=indiCorrelativeService.searchIndiNameById(addCorrelativeIndiCode);
		 	System.out.println("indi_name:"+indi_name);
		 	String indi_code = addCorrelativeIndiCode;
		 	indiCorrelative.setCid(addCorrelativeCoumnId);
		 	indiCorrelative.setIndi_id(Integer.parseInt(indi_code));
		 	indiCorrelative.setIndi_name(indi_name);
		 	indiCorrelative.setIs_show(0);
		 	indiCorrelativeService.add(indiCorrelative);
	        model.addAttribute("addCorrelativeIndiSuccess", "添加成功");
	        
	        List<AnalysisManage> InitAnalysisManageList= analysisManageService.list( );
	        List<IndexManage> InitIndexManageList = indexManageService.listIndi();
	        mav.addObject("InitAnalysisManageList", InitAnalysisManageList);
	        mav.addObject("InitIndexManageList", InitIndexManageList);
	        
	       // System.out.println("indi_name:"+indi_name);
	        return "redirect:listIndiCorrelative";
	    }
	 
	 
	 @RequestMapping("per_show_correIndi")
		public String per_show(int id) {
		 indiCorrelativeService.per_show_correIndi(id);
			return "redirect:listIndexManage";
		}
	    
	    @RequestMapping("no_per_show_correIndi")
	   	public String no_per_show(int id) {
	    	indiCorrelativeService.no_per_show_correIndi(id);
	   		return "redirect:listIndexManage";
	   	}
	 
	

}
