package com.wuhan_data.controller;

import java.io.IOException;
import java.util.Date;
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
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.Version;
import com.wuhan_data.service.VersionService;
import com.wuhan_data.service.SysLogService;
import com.wuhan_data.service.VersionService;
import com.wuhan_data.tools.Page;

@Controller
@RequestMapping("")
public class VersionController {

	@Autowired
	VersionService versionService;
	
	private static String version_name="";//用于模糊查询的名字
	@RequestMapping("listVersion")
	public ModelAndView listVersion() {
		ModelAndView mav=new ModelAndView();
		List<Version> versionList=versionService.List();
		mav.addObject("versionList",versionList);
		mav.setViewName("listVersion");
		return mav;
	}
	@RequestMapping("versionInit")
	public ModelAndView versionInit(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
    	ModelAndView maView=new ModelAndView();
    	Page page=new Page();
    	
    	int count=versionService.count();
    	System.out.println(count);
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
        List<Version> versionListByPage=versionService.listByPage(map);
        
        maView.addObject("versionListByPage", versionListByPage);
        maView.addObject("controlURL", "versionListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("version");
    	return maView;
    }
	@RequestMapping("versionListByPage")
	public ModelAndView  versionListByPage(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
		ModelAndView maView=new ModelAndView();
    	Page page=new Page();
    	
    	int count=versionService.count();
    	
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
        List<Version> versionListByPage=versionService.listByPage(map);
        
        maView.addObject("versionListByPage", versionListByPage);
        maView.addObject("controlURL", "versionListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("version");
    	return maView;
	}
	@RequestMapping("versionSearchByName")
    public ModelAndView versionSearchByName(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	version_name = java.net.URLDecoder.decode(request.getParameter("version_name"),"UTF-8");
    	System.out.println("versionname"+version_name);
//    	String theme_name="%"+search+"%";
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("version_name", version_name);
           int count = versionService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
           map.put("version_name",version_name);
           List<Version> versionListByPage= versionService.search(map);//分页查询二极栏目
           mav.addObject("versionListByPage", versionListByPage);  
           mav.addObject("page", page);
           mav.addObject("controlURL", "versionSearchPage");//控制页码传递URL
           mav.setViewName("version");   

           return mav;
    }
	@RequestMapping("versionSearchPage")
    public ModelAndView searchPage(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("version_name", version_name);
           int count = versionService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
           map.put("version_name",version_name);
           List<Version> versionListByPage= versionService.search(map);//分页查询二极栏目
           mav.addObject("versionListByPage", versionListByPage);  
           mav.addObject("page", page);
           mav.addObject("controlURL", "versionSearchPage");//控制页码传递URL
           mav.setViewName("version");           
           return mav;
    }
	//add version
	@RequestMapping("addVersion")
	public ModelAndView addVersion(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	Version version=new Version();
		/* user.setId(Integer.valueOf(request.getParameter("addUserId"))); */
    	version.setAppid(request.getParameter("addAppid"));
    	version.setPlatform(request.getParameter("addPlatform"));
    	version.setVersion(request.getParameter("addVersion"));
    	version.setText(request.getParameter("addText"));
    	version.setUrl(request.getParameter("addUrl"));
    	version.setCreate_time(new Date());
  
    	versionService.add(version);
    	
    	Page page=new Page();
    	int count=versionService.count();
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
        List<Version> versionListByPage=versionService.listByPage(map);
        
        maView.addObject("versionListByPage", versionListByPage);
        maView.addObject("controlURL", "versionListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("version");

    	return maView;
    }
	//delete version
	@RequestMapping("deleteVersion")
    public ModelAndView deleteVersion(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView maView = new ModelAndView();
    	int id = Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
    	System.out.print("id:"+id);
    	versionService.delete(id);
    	int count=versionService.count();
    	Page page=new Page();
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
        List<Version> versionListByPage=versionService.listByPage(map);
        
        maView.addObject("versionListByPage", versionListByPage);
        maView.addObject("controlURL", "versionListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("version");

    	return maView;
    }
	//edit version
	@RequestMapping("editVersion")
    public ModelAndView editVersion(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	Version version=new Version();
		/* user.setId(Integer.valueOf(request.getParameter("addUserId"))); */
    	version.setId(Integer.valueOf(request.getParameter("editID")));
    	version.setAppid(request.getParameter("editAppid"));
    	version.setPlatform(request.getParameter("editPlatform"));
    	version.setVersion(request.getParameter("editVersion"));
    	version.setText(request.getParameter("editText"));
    	version.setUrl(request.getParameter("editUrl"));
    	System.out.println(version.toString());
    	versionService.update(version);
    	Page page=new Page();
    	int count=versionService.count();
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
        List<Version> versionListByPage=versionService.listByPage(map);
        
        maView.addObject("versionListByPage", versionListByPage);
        maView.addObject("controlURL", "versionListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("version");

    	return maView;
    }

}
