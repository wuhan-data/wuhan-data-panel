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

import com.wuhan_data.pojo.AnalysisTheme;
import com.wuhan_data.pojo.ColPlate;
import com.wuhan_data.service.AnalysisSecondService;
import com.wuhan_data.tools.Page;

@Controller
@RequestMapping("")
public class AnalysisSecondController {

	@Autowired
	AnalysisSecondService analysisSecondService;
	
	Integer label_id;
	String label_name;
	String type_name;
	
	
	
	
	
	@RequestMapping("analysisSecondInit")//初始化板块
    public ModelAndView analysisSecondInit(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8"); //防止乱码   	
        response.setCharacterEncoding("UTF-8");//防止乱码       
        ModelAndView mav = new ModelAndView();//返回视图类    
//        Page page=new Page(); //分页类
      if(label_id==null) {
    	  label_id=Integer.parseInt(request.getParameter("label_id")); //获取二级分类id
    	  
      }
      if(label_name==null) {
    	  label_name=java.net.URLDecoder.decode(request.getParameter("label_name"),"UTF-8"); //获取二级分类名称
          
      }
      if(type_name==null) {
    	  type_name=java.net.URLDecoder.decode(request.getParameter("type_name"),"UTF-8");  //获取一级名称
      }
        	      	
       System.out.print(label_id);
       System.out.print(label_name);
       System.out.print(type_name);
        
//        int count = analysisSecondService.total(label_id);//每一个二级栏目下的板块数量
        Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数     
//        String currentPage=request.getParameter("currentPage");//获取jsp页面的当前页的页码
//        Pattern pattern = Pattern.compile("[0-9]{1,9}");//对跳转框内容的限制
//        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
//            page.setCurrentPage(1);//首次进入，初始化页码
//        } else {
//            page.setCurrentPage(Integer.valueOf(currentPage));//设置页码
//        }
//        page.setTotalNumber(count);//设置总条数
//        page.count();//执行分页类设置分页参数，为sql查询做准备
//        map.put("page", page);//设置page参数
        map.put("label_id",label_id);//设置二级栏目id参数，where条件
        List<AnalysisTheme> indianalysisSecondByPage=analysisSecondService.getlist(map);//获取分页
//        List<IndexManage> InitIndexManageList = colPlateService.listIndi();
//        mav.addObject("InitIndexManageList", InitIndexManageList);     
        mav.addObject("indianalysisSecondByPage", indianalysisSecondByPage);//查询结果，前端展示
//        mav.addObject("page", page);    //前端获取参数
        mav.addObject("cid", label_id); //前端获取二级栏目id，以便多次传参 
        mav.addObject("label_id", label_id); //前端获取二级栏目id，以便多次传参 
        mav.addObject("label_name", label_name);
        mav.addObject("type_name", type_name);
        mav.setViewName("columnSecondManageFrame"); //返回到jsp页面
        return mav;
    }
	@RequestMapping("addTheme")//初始化板块
    public String addTheme(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8"); //防止乱码   	
        response.setCharacterEncoding("UTF-8");//防止乱码 
        
        String theme_name = request.getParameter("addThemeName");
        AnalysisTheme analysisTheme = new AnalysisTheme();
        analysisTheme.setLabel_id(label_id);
        analysisTheme.setTheme_name(theme_name);
        analysisTheme.setTheme_weight(analysisSecondService.getMaxWeight(label_id));
        analysisSecondService.add(analysisTheme);       
        return "redirect:analysisSecondInit";
		
	}
	
	@RequestMapping("delTheme")//初始化板块
    public String delTheme(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8"); //防止乱码   	
        response.setCharacterEncoding("UTF-8");//防止乱码        
        int theme_id = Integer.parseInt(request.getParameter("theme_id"));
        analysisSecondService.delete(theme_id);  
        return "redirect:analysisSecondInit";
		
	}
	
	@RequestMapping("editTheme")//初始化板块
    public String editTheme(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8"); //防止乱码   	
        response.setCharacterEncoding("UTF-8");//防止乱码        
        int theme_id = Integer.parseInt(request.getParameter("editThemeID"));
        String theme_name = request.getParameter("editThemeName");
        AnalysisTheme analysisTheme = new AnalysisTheme();
        analysisTheme.setTheme_id(theme_id);
        analysisTheme.setTheme_name(theme_name);
        analysisSecondService.update(analysisTheme);
        return "redirect:analysisSecondInit";		
	}
	
	@RequestMapping("updateThemeShow")//初始化板块
    public String updateThemeShow(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8"); //防止乱码   	
        response.setCharacterEncoding("UTF-8");//防止乱码        
        int theme_id = Integer.parseInt(request.getParameter("theme_id"));
        int is_show = Integer.parseInt(request.getParameter("is_show"));
        AnalysisTheme analysisTheme = new AnalysisTheme();
        analysisTheme.setTheme_id(theme_id);
        analysisTheme.setIs_show(is_show);
        analysisSecondService.updateShow(analysisTheme);
        return "redirect:analysisSecondInit";		
	}
	
	@RequestMapping("updateThemeWeight")//初始化板块
    public String updateThemeWeight(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8"); //防止乱码   	
        response.setCharacterEncoding("UTF-8");//防止乱码        
        String sort = request.getParameter("sort");

        String[] array = sort.split(",");
        int []themeIdArray= new int[array.length];
        
        System.out.println("arraylegth:"+array.length);
        
       
        for(int i=1;i<=array.length;i++) {
        	Map map = new HashMap();
        	System.out.println("array[i-1]"+array[i-1]);
        	map.put("label_id", label_id);
        	map.put("oldWeight", array[i-1]);
        	int label_id = analysisSecondService.getThemeId(map);
        	themeIdArray[i-1]=label_id;       	
        }
        
        for(int i=1;i<=themeIdArray.length;i++) {
        	Map map = new HashMap();
        	map.put("theme_id", themeIdArray[i-1]);
        	map.put("newWeight", i);
        	analysisSecondService.updateWeight(map);
        }
        return "redirect:analysisSecondInit";		
	}
	
	
	
	
}
