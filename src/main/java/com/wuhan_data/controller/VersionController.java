package com.wuhan_data.controller;

import java.io.File;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.Version;
import com.wuhan_data.service.VersionService;
import com.wuhan_data.service.SysLogService;
import com.wuhan_data.service.VersionService;
import com.wuhan_data.tools.ImageUtils;
import com.wuhan_data.tools.Page;
import com.wuhan_data.tools.StringToMap;

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
    	String currentPage=null;
    	//参数获取
    	try {
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("versionInit,参数获取失败"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	//数据库操作
    	try {
    		Page page=new Page();
        	int count=versionService.count();
        	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
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
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("versionInit,数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    }
	@RequestMapping("versionListByPage")
	public ModelAndView  versionListByPage(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
		ModelAndView maView=new ModelAndView();
		
		String currentPage=null;
    	//参数获取
    	try {
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("versionListByPage,参数获取失败"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	//数据库操作
    	try {
    		Page page=new Page();
        	int count=versionService.count();
        	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
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
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("versionListByPage,参数获取失败"+e.toString());
			maView.setViewName("login");
			return maView;
		}
	}
	@RequestMapping("versionSearchByName")
    public ModelAndView versionSearchByName(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	String currentPage=null;
    	//参数获取
    	try {
    		version_name = java.net.URLDecoder.decode(request.getParameter("version_name"),"UTF-8");
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("versionListByPage,参数获取失败"+e.toString());
			mav.setViewName("login");
			return mav;
		}
    	//数据库操作
    	try {
    		Page page=new Page(); //分页类
            Map<String,Object> mapSearch = new HashMap<String, Object>();
            mapSearch.put("version_name", version_name);
            int count = versionService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
            System.out.println("count:"+count);
            Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数       
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
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("versionListByPage,数据库操作"+e.toString());
			mav.setViewName("login");
			return mav;
		}  
    }
	@RequestMapping("versionSearchPage")
    public ModelAndView searchPage(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	String currentPage=null;
    	//参数获取
    	try {
    		version_name = java.net.URLDecoder.decode(request.getParameter("version_name"),"UTF-8");
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("versionSearchPage,参数获取失败"+e.toString());
			mav.setViewName("login");
			return mav;
		}
    	//数据库操作
    	try {
    		Page page=new Page(); //分页类
            Map<String,Object> mapSearch = new HashMap<String, Object>();
            mapSearch.put("version_name", version_name);
            int count = versionService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
            Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数       
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
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("versionSearchPage,数据库操作"+e.toString());
			mav.setViewName("login");
			return mav;
		}
    }
	//add version
	@RequestMapping("addVersion")
	public ModelAndView addVersion(HttpServletRequest request, 
            HttpServletResponse response,@RequestParam("addFile")MultipartFile [] files) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	String addAppidString="";
    	String addPlatformString="";
    	String addVersionString="";
    	String addTextString="";
    	String imgPath="";
    	//参数获取
    	try {
    		addAppidString=request.getParameter("addAppid");
    		addPlatformString=request.getParameter("addPlatform");
    		addVersionString=request.getParameter("addVersion");
    		addTextString=request.getParameter("addText");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("addVersion,参数获取失败"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	//数据库操作
    	try {
    		Version version=new Version();
    		 if (files.length!=1)
    	 	 {
        		 System.out.println("addVersion:上传文件数量不等于1");
        		 maView.setViewName("login");
        		 return maView;
    	 	 }
    	 	 else 
    	 	 {
    	 	    imgPath =ImageUtils.getURL(request)+"file_version/"+ ImageUtils.upload(request, files[0],"C:\\wuhan_data_file\\version");
    	 	    if(imgPath==null ||imgPath.equals(""))
    	 	    {
    	 	    	System.out.println("addVersion:上传文件失败");
    	 	    	maView.setViewName("login");
    	 	    	return maView;
    	 		 }
    	 	  }
        	version.setUrl(imgPath);
        	version.setAppid(addAppidString);
        	version.setPlatform(addPlatformString);
        	version.setVersion(addVersionString);
        	version.setText(addTextString);
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
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("addVersion,数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}

    }
	//delete version
	@RequestMapping("deleteVersion")
    public ModelAndView deleteVersion(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView maView = new ModelAndView();
    	int id=0;
    	String currentPage=null;
    	//参数获取
    	try {
    		id = Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("deleteVersion,参数获取失败"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	//数据库操作
    	try {
    		versionService.delete(id);
        	int count=versionService.count();
        	Page page=new Page();
        	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
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
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("deleteVersion,数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	
    }
	//edit version
	@RequestMapping("editVersion")
    public ModelAndView editVersion(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	int editEditID=0;
    	String editAppidString="";
    	String editPlatformString="";
    	String editVersionString="";
    	String editTextString="";
    	String editUrl="";
    	//参数获取
    	try {
    		editEditID=Integer.valueOf(request.getParameter("editID"));
        	editAppidString=request.getParameter("editAppid");
        	editPlatformString=request.getParameter("editPlatform");
        	editVersionString=request.getParameter("editVersion");
        	editTextString=request.getParameter("editText");
        	editUrl=request.getParameter("editUrl");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("editVersion,参数获取失败"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	try {
    		Version version=new Version();
        	version.setId(editEditID);
        	version.setAppid(editAppidString);
        	version.setPlatform(editPlatformString);
        	version.setVersion(editVersionString);
        	version.setText(editTextString);
        	version.setUrl(editUrl);
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
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("editVersion,数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	
    }

}
