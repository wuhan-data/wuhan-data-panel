package com.wuhan_data.controller;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.strategy.StdInstantiatorStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.Message;
import com.wuhan_data.pojo.Message2;
import com.wuhan_data.pojo.Role;
import com.wuhan_data.pojo.User;
import com.wuhan_data.pojo.Version;
import com.wuhan_data.service.Message2Service;
import com.wuhan_data.service.RoleService;
import com.wuhan_data.service.UserService;
import com.wuhan_data.tools.ImageUtils;
import com.wuhan_data.tools.Page;

@Controller
@RequestMapping("")
public class Message2Controller {
	@Autowired
	Message2Service message2Service;
	@Autowired
	RoleService roleService;
	@Autowired
	UserService userService;
	private static String title="";//用于模糊查询的名字
	 String strDateFormat = "yyyy-MM-dd HH:mm:ss";
	 SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
     
	//message界面初始化需要的数据
	@RequestMapping("messageInit")
	public ModelAndView messageInit(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
    	ModelAndView maView=new ModelAndView();
    	String currentPage=null;
    	try {
			currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("messageInit:参数获取"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	try {
    		Page page=new Page();
        	int count=message2Service.count();
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
            List<Message2> messagesListByPage=message2Service.listByPage(map);
            List<Role> roleList=roleService.List();
            
            
            for (Message2 message2 : messagesListByPage) {
            	
				message2.setCreate_time(sdf.parse(sdf.format(message2.getCreate_time())));
			}
            //System.out.println("mess"+messagesListByPage);
            maView.addObject("roleList", roleList);
            maView.addObject("messagesListByPage",  messagesListByPage);
            maView.addObject("controlURL", "messagesListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("message");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("messageInit:数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    }
	@RequestMapping("messagesListByPage")
	public ModelAndView messagesListByPage(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView=new ModelAndView();
    	//参数获取
    	String currentPage=null;
    	try {
			currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("messagesListByPage:参数获取"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	//数据库操作
    	try {
    		Page page=new Page();
        	int count=message2Service.count();
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
            List<Message2> messagesListByPage=message2Service.listByPage(map);
            List<Role> roleList=roleService.List();
            maView.addObject("roleList", roleList);
            maView.addObject("messagesListByPage",  messagesListByPage);
            maView.addObject("controlURL", "messagesListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("message");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("messagesListByPage:数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	
    }
	//消息的添加
	@RequestMapping("addMessage")
	 public ModelAndView addMessage(HttpServletRequest request, 
	            HttpServletResponse response ,@RequestParam("messageAddFile")MultipartFile [] files) throws IOException{
	    	request.setCharacterEncoding("UTF-8");    	
	        response.setCharacterEncoding("UTF-8");
	        ModelAndView maView = new ModelAndView();
	        //需要接受的参数addSender_id,addReceiver_id,addTitle,addLabel,addContent,addM_text,addType,addPath,addCreate_time
	        //参数获取
	        int addSender_id=0;
	        String addReceiver_id="";
	        String addTitleString="";
	        String addLabelString="";
	        String addContentString="";
	        String addM_textString="";
	        String addTypeString="";
	       // String addPathString="";
	        String imgPath="";
	        Date addCreate_time=new Date();
	        try {
	        	addSender_id=Integer.valueOf(request.getParameter("addSender_id"));
	        	addReceiver_id=request.getParameter("addReceiver_id");
	        	addTitleString=request.getParameter("addTitle");
	        	addLabelString=request.getParameter("addLabel");
	        	addContentString=request.getParameter("addContent");
	        	addM_textString=request.getParameter("addM_text");
	        	addTypeString=request.getParameter("addType");
	        	//addPathString=request.getParameter("addPath");
	        	addReceiver_id="|"+addReceiver_id+"|";
	        	addReceiver_id=addReceiver_id.replace(",", "|");
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("addMessage:获取参数"+e.toString());
				maView.setViewName("login");
				return maView;
			}
	        //数据库操作
	        try {
	        	//new 对应的消息，并添加
	        	//上传文件
	        	
	    		 if (files.length!=1)
	    	 	 {
	        		 System.out.println("addVersion:上传文件数量不等于1");
	        		 maView.setViewName("login");
	        		 return maView;
	    	 	 }
	    	 	 else 
	    	 	 {
	    	 	    imgPath =ImageUtils.getURL(request)+"file_message/"+ ImageUtils.upload(request, files[0],"C:\\wuhan_data_file\\message");
	    	 	    if(imgPath==null ||imgPath.equals(""))
	    	 	    {
	    	 	    	System.out.println("addVersion:上传文件失败");
	    	 	    	maView.setViewName("login");
	    	 	    	return maView;
	    	 		 }
	    	 	  }
	    		 
	    	Message2 message2=new Message2();
	    	message2.setSender_id(addSender_id);
	    	message2.setReceiver_id(addReceiver_id);
	    	message2.setTitle(addTitleString);
	    	message2.setLabel(addLabelString);
	    	message2.setContent(addContentString);
	    	message2.setM_text(addM_textString);
	    	message2.setType(addTypeString);
	    	//message2.setPath(addPathString);
	    	message2.setPath(imgPath);
	    	message2.setCreate_time(addCreate_time);
	    	message2Service.add(message2);
	    	Page page=new Page();
	    	int count=message2Service.count();
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
	        List<Message2> messagesListByPage=message2Service.listByPage(map);
	        List<Role> roleList=roleService.List();
	        maView.addObject("roleList", roleList);
	        maView.addObject("messagesListByPage",  messagesListByPage);
	        maView.addObject("controlURL", "messagesListByPage");//控制页码传递URL
	        maView.addObject("page", page); 
	    	maView.setViewName("message");
	    	return maView;	
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("addMessage:数据库操作"+e.toString());
				maView.setViewName("login");
				return maView;
			}

	        
	    }
	//按角色添加消息
	@RequestMapping("addMessageByRole")
	 public ModelAndView addMessageByRole(HttpServletRequest request, 
	            HttpServletResponse response,@RequestParam("messageAddByRoleFile")MultipartFile [] files) throws IOException{
	    	request.setCharacterEncoding("UTF-8");    	
	        response.setCharacterEncoding("UTF-8");
	    	ModelAndView maView = new ModelAndView();
	    	//获取数据
	    	int addByRoleSender_id=0;
	        String addByRoleReceiver_id="";
	        String addByRoleTitleString="";
	        String addByRoleLabelString="";
	        String addByRoleContentString="";
	        String addByRoleM_textString="";
	        String addByRoleTypeString="";
	       // String addByRolePathString="";
	        String imgPath="";
	        Date addByRoleCreate_time=new Date();
	        try {
	        		addByRoleSender_id=Integer.valueOf(request.getParameter("addByRoleSender_id"));
		        	addByRoleReceiver_id=request.getParameter("addByRoleReceiver_id");//这是一个名字
		        	addByRoleTitleString=request.getParameter("addByRoleTitle");
		        	addByRoleLabelString=request.getParameter("addByRoleLabel");
		        	addByRoleContentString=request.getParameter("addByRoleContent");
		        	addByRoleM_textString=request.getParameter("addByRoleM_text");
		        	addByRoleTypeString=request.getParameter("addByRoleType");
		        	//addByRolePathString=request.getParameter("addByRolePath");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("addMessageByRole:获取参数"+e.toString());
				maView.setViewName("login");
				return maView;
			}
		      //数据库操作 
	        try {
	        	List<User>users=userService.getByRole(addByRoleReceiver_id);//这其实是一个名字
		        List<Integer> userIdList=new ArrayList<Integer>();
		        for (int i=0;i<users.size();i++)
		        {
		        	userIdList.add(users.get(i).getId());
		        }
		        String userIdListString=userIdList.toString();
		        userIdListString=userIdListString.replace(" ", "");
		        userIdListString=userIdListString.replace("[", "|");
		        userIdListString=userIdListString.replace("]", "|");
		        userIdListString=userIdListString.replace(",", "|");
		        addByRoleReceiver_id=userIdListString;
		        System.out.println("userIdListString"+userIdListString);
		        //上传文件
			   		 if (files.length!=1)
			   	 	 {
			       		 System.out.println("addVersion:上传文件数量不等于1");
			       		 maView.setViewName("login");
			       		 return maView;
			   	 	 }
			   	 	 else 
			   	 	 {
			   	 	    imgPath =ImageUtils.getURL(request)+"file_message/"+ ImageUtils.upload(request, files[0],"C:\\wuhan_data_file\\message");
			   	 	    if(imgPath==null ||imgPath.equals(""))
			   	 	    {
			   	 	    	System.out.println("addVersion:上传文件失败");
			   	 	    	maView.setViewName("login");
			   	 	    	return maView;
			   	 		 }
			   	 	  }
		    	Message2 message2=new Message2();
		    	message2.setSender_id(addByRoleSender_id);
		    	message2.setReceiver_id(addByRoleReceiver_id);
		    	message2.setTitle(addByRoleTitleString);
		    	message2.setLabel(addByRoleLabelString);
		    	message2.setContent(addByRoleContentString);
		    	message2.setM_text(addByRoleM_textString);
		    	message2.setType(addByRoleTypeString);
		    	//message2.setPath(addByRolePathString);
		    	message2.setPath(imgPath);
		    	message2.setCreate_time(addByRoleCreate_time);
		    	message2Service.add(message2);
		    	Page page=new Page();
		    	int count=message2Service.count();
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
		        List<Message2> messagesListByPage=message2Service.listByPage(map);
		        List<Role> roleList=roleService.List();
		        maView.addObject("roleList", roleList);
		        maView.addObject("messagesListByPage",  messagesListByPage);
		        maView.addObject("controlURL", "messagesListByPage");//控制页码传递URL
		        maView.addObject("page", page); 
		    	maView.setViewName("message");
		    	return maView;		
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("addMessageByRole:数据库操作"+e.toString());
				maView.setViewName("login");
				return maView;
			}
	    }
	//编辑消息
	@RequestMapping("editMessage")
	public ModelAndView editMessage(HttpServletRequest request,HttpServletResponse response
			,@RequestParam("messageEditFile")MultipartFile [] files) throws IOException{
			request.setCharacterEncoding("UTF-8");    	
			response.setCharacterEncoding("UTF-8");
			
		   	ModelAndView maView = new ModelAndView();
		   	int editMessageId=0;
		    int editSender_id=0;
	        String editReceiver_id="";
	        String editTitleString="";
	        String editLabelString="";
	        String editContentString="";
	        String editM_textString="";
	        String editTypeString="";
	        //String editPathString="";
	        String imgPath="";
	        Date editCreate_time=new Date();
	        //获取数据
	        try {
	        	
	        	
	        	
	        	editMessageId=Integer.valueOf(request.getParameter("editMessageID"));
	        	editSender_id=Integer.valueOf(request.getParameter("editSender_id"));
	        	editReceiver_id=request.getParameter("editReceiver_id");
	        	editTitleString=request.getParameter("editTitle");
	        	editLabelString=request.getParameter("editLabel");
	        	editContentString=request.getParameter("editContent");
	        	editM_textString=request.getParameter("editM_text");
	        	editTypeString=request.getParameter("editType");
	        	//editPathString=request.getParameter("editPath");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("editMessage:获取参数"+e.toString());
				maView.setViewName("login");
				return maView;
				
			}
	        //数据库操作
	        try {
	        	//上传文件
	        	 if (files.length!=1)
		   	 	 {
		       		 System.out.println("addVersion:上传文件数量不等于1");
		       		 maView.setViewName("login");
		       		 return maView;
		   	 	 }
		   	 	 else 
		   	 	 {
		   	 	    imgPath =ImageUtils.getURL(request)+"file_message/"+ ImageUtils.upload(request, files[0],"C:\\wuhan_data_file\\message");
		   	 	    if(imgPath==null ||imgPath.equals(""))
		   	 	    {
		   	 	    	System.out.println("addVersion:上传文件失败");
		   	 	    	maView.setViewName("login");
		   	 	    	return maView;
		   	 		 }
		   	 	  }
	        	
	        	Message2 message2=new Message2();
	 		    message2.setId(editMessageId);
	 		    message2.setSender_id(editSender_id);
	 		    message2.setReceiver_id(editReceiver_id);
	 		    message2.setTitle(editTitleString);
	 		    message2.setLabel(editLabelString);
	 		    message2.setContent(editContentString);
	 		    message2.setM_text(editM_textString);
	 		    message2.setType(editTypeString);
	 		   // message2.setPath(editPathString);
	 		    message2.setPath(imgPath);
	 		    message2.setCreate_time(editCreate_time);
	 		    message2Service.update(message2);
	 	        
	 		   	Page page=new Page();
	 		   	int count=message2Service.count();
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
	 		   	List<Message2> messagesListByPage=message2Service.listByPage(map);
	 		   	List<Role> roleList=roleService.List();
	 		   	maView.addObject("roleList", roleList);
	 		   	maView.addObject("messagesListByPage",  messagesListByPage);
	 		   	maView.addObject("controlURL", "messagesListByPage");//控制页码传递URL
	 		   	maView.addObject("page", page); 
	 		   	maView.setViewName("message");
	 		   	return maView;
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("editMessage:数据库操作"+e.toString());
				maView.setViewName("login");
				return maView;
			}

  		}
	@RequestMapping("deleteMessage")
	public ModelAndView deleteMessage(HttpServletRequest request, 
           HttpServletResponse response) throws IOException{
		
			ModelAndView maView = new ModelAndView();
			int id=0;
			String currentPage=null;
			try {
				id = Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
				currentPage=request.getParameter("currentPage");
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("deleteMessage:获取参数"+e.toString());
				maView.setViewName("login");
				return maView;
			}
			try {
				message2Service.delete(id);
				Page page=new Page();
				int count=message2Service.count();
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
				List<Message2> messagesListByPage=message2Service.listByPage(map);
				List<Role> roleList=roleService.List();
				maView.addObject("roleList",roleList);
				maView.addObject("messagesListByPage",messagesListByPage);
				maView.addObject("controlURL","messagesListByPage");//控制页码传递URL
				maView.addObject("page", page); 
				maView.setViewName("message");
				return maView;
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("deleteMessage:数据库操作"+e.toString());
				maView.setViewName("login");
				return maView;
			}
			
		}
	
	@RequestMapping("messageSearchByTitle")
	   public ModelAndView messageSearchByContent(HttpServletRequest request, 
	           HttpServletResponse response) throws IOException{
	   		response.setCharacterEncoding("UTF-8");
	   		ModelAndView mav = new ModelAndView();
	   		String currentPage=null;
	   		//获取数据
	   		try {
	   			title = java.net.URLDecoder.decode(request.getParameter("title"),"UTF-8");
	   			currentPage=request.getParameter("currentPage");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("messageSearchByTitle:获取参数"+e.toString());
				mav.setViewName("login");
				return mav;
			}
	   		//数据库操作
	   		try {
	   			Page page=new Page(); //分页类
		        Map<String,Object> mapSearch = new HashMap<String, Object>();
		        mapSearch.put("title",title );
		        int count = message2Service.searchCountByContent(mapSearch);//每一个一级栏目下面二极栏目的数量
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
		        map.put("title",title);
		        List<Message2> messagesListByPage=message2Service.searchByContent(map);
		        List<Role> roleList=roleService.List();
		        mav.addObject("roleList", roleList);
		        mav.addObject("messagesListByPage",  messagesListByPage);
		        mav.addObject("controlURL", "messageSearchListByPage");//控制页码传递URL
		        mav.addObject("page", page); 
		        mav.setViewName("message");
		      	return mav;
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("messageSearchByTitle:数据库操作"+e.toString());
				mav.setViewName("login");
				return mav;
			}
	   		
	   }
	@RequestMapping("messageSearchListByPage")
	   public ModelAndView searchPage(HttpServletRequest request, 
	           HttpServletResponse response) throws IOException{
			request.setCharacterEncoding("UTF-8");    	
			response.setCharacterEncoding("UTF-8");
			ModelAndView mav = new ModelAndView();
			String currentPage=null;
			try {
				currentPage=request.getParameter("currentPage");
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("messageSearchListByPage:获取参数"+e.toString());
				mav.setViewName("login");
				return mav;
			}
			//数据库操作
			try {
				Page page=new Page(); //分页类
		        Map<String,Object> mapSearch = new HashMap<String, Object>();
		        mapSearch.put("title", title);
		        int count = message2Service.searchCountByContent(mapSearch);//每一个一级栏目下面二极栏目的数
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
		        map.put("title",title);
		        List<Message2> messagesListByPage=message2Service.searchByContent(map);
		        List<Role> roleList=roleService.List();
		        mav.addObject("roleList", roleList);
		        mav.addObject("messagesListByPage",  messagesListByPage);
		        mav.addObject("controlURL", "messageSearchListByPage");//控制页码传递URL
		        mav.addObject("page", page); 
		        mav.setViewName("message");
		      	return mav;
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("messageSearchListByPage:数据库操作"+e.toString());
				mav.setViewName("login");
				return mav;
			}
	       	
	   }
}
