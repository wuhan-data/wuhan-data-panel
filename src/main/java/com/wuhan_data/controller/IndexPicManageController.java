package com.wuhan_data.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.IndexPic;
import com.wuhan_data.pojo.SpecialDetail;
import com.wuhan_data.service.IndexPicService;
import com.wuhan_data.tools.Page;

@Controller
@RequestMapping("")
public class IndexPicManageController {
	
	@Autowired
	IndexPicService indexPicService;
	
	@RequestMapping("indexPicInit")
    public ModelAndView indexPicInit(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");  	
        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();     
        Page page=new Page(); //分页类
        int count = indexPicService.total();//每一个一级栏目下面二极栏目的数量
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
        List<IndexPic> indexPicByPage=indexPicService.getlist(map);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+indexPicByPage.size());
        mav.addObject("specialByPage", indexPicByPage);
        mav.addObject("page", page);         
        mav.setViewName("indexPic");
        return mav;
    }
	
	@RequestMapping(value="indexPicAdd",produces = "text/plain;charset=utf-8",method = RequestMethod.POST)
	 public String indexPicAdd(IndexPic indexPic,MultipartFile pic1,HttpServletRequest request, HttpServletResponse response) throws IOException {
//       存到数据库的路径
       String sqlPath=null;
       
       String contextPath = request.getContextPath();
       String basePath = request.getScheme()+"://"+InetAddress.getLocalHost().getHostAddress()+":"+  
                     request.getServerPort()+contextPath+"/"; 
       
//       存到本地的路径
//       String localPath="/Users/in/uploads/";
       String localPath="C:\\wuhan_data_file\\image\\slideshow\\";      
//       文件名
       String filename=null;
       System.out.print(pic1);
       if(!pic1.isEmpty()){  //判断获取到的图片是否为空 不为空 进入到if
           //生成uuid作为文件名的一部分
           String uuid = UUID.randomUUID().toString().replaceAll("-","");
           //获得文件类型
           String contentType=pic1.getContentType();
           //获得文件后缀
           String suffixName=contentType.substring(contentType.indexOf("/")+1);
           //拼接文件名
           filename=uuid+"."+suffixName;
           System.out.println(filename);
           //文件保存路径
           pic1.transferTo(new File(localPath+filename));

       }
       //数据库中保存的是图片的相对路径
       sqlPath = basePath+"file_image_slideshow/"+filename;
       System.out.println(sqlPath);
       indexPic.setImage(sqlPath);
       indexPicService.add(indexPic);      
       return "redirect:indexPicInit";
   }
	
	@RequestMapping(value="indexPicUpdate",produces = "text/plain;charset=utf-8",method = RequestMethod.POST)
	 public String indexPicUpdate(IndexPic indexPic,MultipartFile pic1,HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
		//      存到数据库的路径
      String sqlPath=null;
      String contextPath = request.getContextPath();
      String basePath = request.getScheme()+"://"+InetAddress.getLocalHost().getHostAddress()+":"+  
                    request.getServerPort()+contextPath+"/"; 
      
//      存到本地的路径
//      String localPath="/Users/in/uploads/";
      String localPath="C:\\wuhan_data_file\\head\\";     
      
//      存到本地的路径
//      String localPath="/Users/in/uploads/";
//      文件名
      String filename=null;
      if(!pic1.isEmpty()){  //判断获取到的图片是否为空 不为空 进入到if
          //生成uuid作为文件名的一部分
          String uuid = UUID.randomUUID().toString().replaceAll("-","");
          //获得文件类型
          String contentType=pic1.getContentType();
          //获得文件后缀
          String suffixName=contentType.substring(contentType.indexOf("/")+1);
          //拼接文件名
          filename=uuid+"."+suffixName;
          System.out.println(filename);
          //文件保存路径
          pic1.transferTo(new File(localPath+filename));
          
          sqlPath = basePath+"file_head/"+filename;
          System.out.println(sqlPath);
          int id=Integer.parseInt(request.getParameter("picid"));
          indexPic.setId(id);
          indexPic.setImage(sqlPath);
          indexPicService.update(indexPic);  

      }else {
    	  int id=Integer.parseInt(request.getParameter("picid"));
          indexPic.setId(id);
          indexPicService.updateTitle(indexPic);  
  	  
      }
      //数据库中保存的是图片的相对路径
        
      return "redirect:indexPicInit";
  }
	
	@RequestMapping(value="indexPicDel",produces = "text/plain;charset=utf-8",method = RequestMethod.GET)
	 public String indexPicDel(HttpServletRequest request, HttpServletResponse response) throws IOException {
   
		int id=Integer.parseInt(request.getParameter("id"));
		indexPicService.delete(id);
		
      return "redirect:indexPicInit";
  }
	
	@RequestMapping(value="indexPicUpdateShow",produces = "text/plain;charset=utf-8",method = RequestMethod.GET)
	 public String indexPicUpdateShow(HttpServletRequest request, HttpServletResponse response) throws IOException {
  
		int id=Integer.parseInt(request.getParameter("id"));
		int is_show = Integer.parseInt(request.getParameter("is_show"));
		IndexPic indexPic = new IndexPic();
		indexPic.setId(id);
		indexPic.setIs_show(is_show);
		indexPicService.updateShow(indexPic);
		
     return "redirect:indexPicInit";
 }
	
	

//	@RequestMapping("specialDel")
//    public ModelAndView specialDel(HttpServletRequest request, 
//            HttpServletResponse response) throws IOException{
//    	request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
//        ModelAndView mav = new ModelAndView();
//      
//        Page page=new Page(); //分页类
//
//        int special_id=Integer.parseInt(request.getParameter("special_id"));
//        specialService.delete(special_id);
//
//        int count = specialService.total();
//        System.out.print("每一个一级栏目下面二极栏目的数量"+count);
//        Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数
//       
//        String currentPage=request.getParameter("currentPage");
//        Pattern pattern = Pattern.compile("[0-9]{1,9}");
//        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
//            page.setCurrentPage(1);
//        } else {
//            page.setCurrentPage(Integer.valueOf(currentPage));
//        }
//        page.setTotalNumber(count);
//        page.count();
//        System.out.println(page.getDbIndex());
//        System.out.println(page.getDbNumber());
//        map.put("page", page);
//        List<SpecialDetail> specialByPage=specialService.getlist(map);
//        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+specialByPage.size());
//        mav.addObject("specialByPage", specialByPage);
//        mav.addObject("page", page);    
//     
//        mav.setViewName("specialManage");
//        return mav;
//    }
//
//	@RequestMapping("specialUpdateShow")
//    public ModelAndView specialUpdateShow(HttpServletRequest request, 
//            HttpServletResponse response) throws IOException{
//    	request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
//        ModelAndView mav = new ModelAndView();
//      
//        Page page=new Page(); //分页类
//
//        int special_id=Integer.parseInt(request.getParameter("special_id"));
//        int is_show=Integer.parseInt(request.getParameter("is_show"));
//        SpecialDetail sd = new SpecialDetail();
//        sd.setSpecial_id(special_id);
//        sd.setIs_show(is_show);
//        specialService.updateShow(sd);
//        int count = specialService.total();
//        System.out.print("每一个一级栏目下面二极栏目的数量"+count);
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
//        System.out.println(page.getDbIndex());
//        System.out.println(page.getDbNumber());
//        map.put("page", page);
//        List<SpecialDetail> specialByPage=specialService.getlist(map);
//        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+specialByPage.size());
//        mav.addObject("specialByPage", specialByPage);
//        mav.addObject("page", page);       
//        mav.setViewName("specialManage");
//        return mav;
//    }
//
//	@RequestMapping("specialUpdate")
//    public ModelAndView specialUpdate(HttpServletRequest request, 
//            HttpServletResponse response) throws IOException{
//    	request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
//        ModelAndView mav = new ModelAndView();
//      
//        Page page=new Page(); //分页类
//
//        String special_name=request.getParameter("special_name");      
//        int special_id=Integer.parseInt(request.getParameter("special_id"));
//      
//        SpecialDetail sd = new SpecialDetail();
//        sd.setSpecial_id(special_id);
//        sd.setSpecial_name(special_name);
//        specialService.update(sd);
//        int count = specialService.total();
//        System.out.print("每一个一级栏目下面二极栏目的数量"+count);
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
//        System.out.println(page.getDbIndex());
//        System.out.println(page.getDbNumber());
//        map.put("page", page);
//        List<SpecialDetail> specialByPage=specialService.getlist(map);
//        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+specialByPage.size());
//        mav.addObject("specialByPage", specialByPage);
//        mav.addObject("page", page);    
//     
//        mav.setViewName("specialManage");
//        return mav;
//    }
	
	

}
