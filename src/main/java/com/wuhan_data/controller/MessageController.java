//package com.wuhan_data.controller;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Pattern;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.wuhan_data.tools.Page;
//import com.wuhan_data.mapper.RoleMapper;
//import com.wuhan_data.pojo.Admin;
//import com.wuhan_data.pojo.Message;
//import com.wuhan_data.pojo.Message;
//import com.wuhan_data.pojo.Role;
//import com.wuhan_data.pojo.User;
//import com.wuhan_data.service.MessageService;
//import com.wuhan_data.service.RoleService;
//import com.wuhan_data.service.SysLogService;
//import com.wuhan_data.service.UserService;
//
//@Controller
//@RequestMapping("")
//
//public class MessageController {
//	@Autowired
//	MessageService messageService;
//	@Autowired
//	RoleService roleService;
//	@Autowired
//	UserService userService;
//	
//	private static String content="";//用于模糊查询的名字
//	
//	@RequestMapping("listMessage")
//	public ModelAndView listMessage() {
//		ModelAndView maView=new ModelAndView();
//		List<Message> cs=messageService.list();
//		maView.addObject("cs",cs);
//		maView.setViewName("message");
//		return maView;
//	}
//	@RequestMapping("messageInit")
//	public ModelAndView messageInit(HttpServletRequest request, 
//            HttpServletResponse response) throws UnsupportedEncodingException {
//    	request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        
//    	ModelAndView maView=new ModelAndView();
//    	Page page=new Page();
//    	
//    	int count=messageService.count();
//    	
//    	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
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
//        List<Message> messagesListByPage=messageService.listByPage(map);
//        List<Role> roleList=roleService.List();
//        maView.addObject("roleList", roleList);
//        maView.addObject("messagesListByPage",  messagesListByPage);
//        maView.addObject("controlURL", "messagesListByPage");//控制页码传递URL
//        maView.addObject("page", page); 
//    	maView.setViewName("message");
//    	return maView;
//    }
//	@RequestMapping("messagesListByPage")
//	public ModelAndView messagesListByPage(HttpServletRequest request, 
//            HttpServletResponse response) throws UnsupportedEncodingException {
//    	request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        
//    	ModelAndView maView=new ModelAndView();
//    	Page page=new Page();
//    	
//    	int count=messageService.count();
//    	
//    	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
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
//        List<Message> messagesListByPage=messageService.listByPage(map);
//        List<Role> roleList=roleService.List();
//        maView.addObject("roleList", roleList);
//        maView.addObject("messagesListByPage",  messagesListByPage);
//        maView.addObject("controlURL", "messagesListByPage");//控制页码传递URL
//        maView.addObject("page", page); 
//    	maView.setViewName("message");
//    	return maView;
//    }
//	@RequestMapping("addMessage")
//	 public ModelAndView addMessage(HttpServletRequest request, 
//	            HttpServletResponse response) throws IOException{
//	    	request.setCharacterEncoding("UTF-8");    	
//	        response.setCharacterEncoding("UTF-8");
//	    	ModelAndView maView = new ModelAndView();
//	    	Message message=new Message();
//	    	message.setSender_id(Integer.valueOf(request.getParameter("addsender_id")));
//	    	message.setReceiver_id(Integer.valueOf(request.getParameter("addreceiver_id")));
//	    	message.setUrl(request.getParameter("addContent"));
//	    	message.setCreate_time(new Date());
//	    	message.setTitle(request.getParameter("addTitle"));
//	    	message.setRemarks(request.getParameter("addRemark"));
//	    	message.setIs_read(0);
//	    	messageService.add(message);
//	    	
//	    	Page page=new Page();
//	    	
//	    	int count=messageService.count();
//	    	
//	    	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
//	        String currentPage=request.getParameter("currentPage");
//	        Pattern pattern = Pattern.compile("[0-9]{1,9}");
//	        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
//	            page.setCurrentPage(1);
//	        } else {
//	            page.setCurrentPage(Integer.valueOf(currentPage));
//	        }
//	        page.setTotalNumber(count);
//	        page.count();
//	        System.out.println(page.getDbIndex());
//	        System.out.println(page.getDbNumber());
//	        map.put("page", page);
//	        List<Message> messagesListByPage=messageService.listByPage(map);
//	        List<Role> roleList=roleService.List();
//	        maView.addObject("roleList", roleList);
//	        maView.addObject("messagesListByPage",  messagesListByPage);
//	        maView.addObject("controlURL", "messagesListByPage");//控制页码传递URL
//	        maView.addObject("page", page); 
//	    	maView.setViewName("message");
//	    	
//	    
//	    	return maView;
//	    }
//	@RequestMapping("addMessageByRole")
//	 public ModelAndView addMessageByRole(HttpServletRequest request, 
//	            HttpServletResponse response) throws IOException{
//	    	request.setCharacterEncoding("UTF-8");    	
//	        response.setCharacterEncoding("UTF-8");
//	    	ModelAndView maView = new ModelAndView();
//	    	
//	    	String role_name=(request.getParameter("roleListSelectMessage"));
//	    	//System.out.println("role_name:"+role_name);
//	    	int sender_id=Integer.valueOf((request.getParameter("addByRolesender_id")));
//	    	String content=request.getParameter("addByRoleContent");
//	    	String title=request.getParameter("addByRoleTitle");
//	    	String remarks=request.getParameter("addByRoleRemark");
//	    	//int role_id=roleService.getByName(role_name).getId();
//	    	//System.out.println("role_id"+role_id);
//	    	List<User> userByRoleList=userService.getByRole(role_name.trim());
//	    	//System.out.print("userByRoleList"+userByRoleList);
//	    	List<Message> messageList=new ArrayList<Message>();
//	    	for (int i=1;i<=userByRoleList.size();i++)
//	    	{
//	    		int  Re_id=userByRoleList.get(i-1).getId();
//	    		Message message=new Message();
//	    		message.setSender_id(sender_id);
//	    		message.setReceiver_id(Re_id);
//	    		message.setTitle(title);
//	    		message.setRemarks(remarks);
//	    		message.setUrl(content);
//	    		message.setCreate_time(new Date());
//	    		message.setIs_read(0);
//	    		messageList.add(message);
//	    	};
//	    	System.out.println("messageList"+messageList);
//	    	
//	    	messageService.addByRole(messageList);
//	    	
//		/*
//		 * Message message=new Message();
//		 * message.setsender_id(Integer.valueOf(request.getParameter("addsender_id")));
//		 * message.setreceiver_id(Integer.valueOf(request.getParameter("addreceiver_id"))
//		 * ); message.setContent(request.getParameter("addContent"));
//		 * message.setCreate_time(new Date()); messageService.add(message);
//		 */
//	    	
//	    	Page page=new Page();
//	    	
//	    	int count=messageService.count();
//	    	
//	    	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
//	        String currentPage=request.getParameter("currentPage");
//	        Pattern pattern = Pattern.compile("[0-9]{1,9}");
//	        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
//	            page.setCurrentPage(1);
//	        } else {
//	            page.setCurrentPage(Integer.valueOf(currentPage));
//	        }
//	        page.setTotalNumber(count);
//	        page.count();
//	        System.out.println(page.getDbIndex());
//	        System.out.println(page.getDbNumber());
//	        map.put("page", page);
//	        List<Message> messagesListByPage=messageService.listByPage(map);
//	        List<Role> roleList=roleService.List();
//	        maView.addObject("roleList", roleList);
//	        maView.addObject("messagesListByPage",  messagesListByPage);
//	        maView.addObject("controlURL", "messagesListByPage");//控制页码传递URL
//	        maView.addObject("page", page); 
//	    	maView.setViewName("message");
//	    	
//	    	
//	    	return maView;
//	    }
//	
//	
//	@RequestMapping("editMessage")
//	public ModelAndView editMessage(HttpServletRequest request, 
//           HttpServletResponse response) throws IOException{
//   	request.setCharacterEncoding("UTF-8");    	
//       response.setCharacterEncoding("UTF-8");
//   	ModelAndView maView = new ModelAndView();
//   	Message message=new Message();
//   	message.setId(Integer.valueOf(request.getParameter("editMessageID")));
//   	message.setSender_id(Integer.valueOf((request.getParameter("editMessagesender_id"))));
//   	message.setReceiver_id(Integer.valueOf((request.getParameter("editMessagereceiver_id"))));
//   	message.setUrl(request.getParameter("editMessageContent"));
//   	message.setCreate_time(new Date());
//   	message.setTitle(request.getParameter("editMessageTitle"));
//   	message.setRemarks(request.getParameter("editMessageRemarks"));
//   	String is_read=request.getParameter("editMessageIs_read");
//   	message.setIs_read(Integer.valueOf(is_read));
//   	System.out.println("message edit"+message.toString()+is_read);
//   	messageService.update(message);
//   	
//   	Page page=new Page();
//   	
//   	int count=messageService.count();
//   	
//   	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
//       String currentPage=request.getParameter("currentPage");
//       Pattern pattern = Pattern.compile("[0-9]{1,9}");
//       if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
//           page.setCurrentPage(1);
//       } else {
//           page.setCurrentPage(Integer.valueOf(currentPage));
//       }
//       page.setTotalNumber(count);
//       page.count();
//       System.out.println(page.getDbIndex());
//       System.out.println(page.getDbNumber());
//       map.put("page", page);
//       List<Message> messagesListByPage=messageService.listByPage(map);
//       List<Role> roleList=roleService.List();
//       maView.addObject("roleList", roleList);
//       maView.addObject("messagesListByPage",  messagesListByPage);
//       maView.addObject("controlURL", "messagesListByPage");//控制页码传递URL
//       maView.addObject("page", page); 
//   	maView.setViewName("message");
//   	
//   	 
//   	return maView;
//   }
//	@RequestMapping("deleteMessage")
//	public ModelAndView deleteMessage(HttpServletRequest request, 
//           HttpServletResponse response) throws IOException{
//		
//		ModelAndView maView = new ModelAndView();
//		int id = Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
//		System.out.print("id:"+id);
//		messageService.delete(id);
//   	
//   	Page page=new Page();
//   	
//   	int count=messageService.count();
//   	
//   	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
//       String currentPage=request.getParameter("currentPage");
//       Pattern pattern = Pattern.compile("[0-9]{1,9}");
//       if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
//           page.setCurrentPage(1);
//       } else {
//           page.setCurrentPage(Integer.valueOf(currentPage));
//       }
//       page.setTotalNumber(count);
//       page.count();
//       map.put("page", page);
//       List<Message> messagesListByPage=messageService.listByPage(map);
//       List<Role> roleList=roleService.List();
//       maView.addObject("roleList", roleList);
//       maView.addObject("messagesListByPage",  messagesListByPage);
//       maView.addObject("controlURL", "messagesListByPage");//控制页码传递URL
//       maView.addObject("page", page); 
//   	maView.setViewName("message");
//   	
//   	 
//   	return maView;
//   }
//	@RequestMapping("messageSearchByContent")
//   public ModelAndView messageSearchByContent(HttpServletRequest request, 
//           HttpServletResponse response) throws IOException{
//   	response.setCharacterEncoding("UTF-8");
//   	ModelAndView mav = new ModelAndView();
//   	content = java.net.URLDecoder.decode(request.getParameter("content"),"UTF-8");
//   	 System.out.println("content="+content);
//   	   Page page=new Page(); //分页类
//          Map<String,Object> mapSearch = new HashMap<String, Object>();
//          mapSearch.put("title", content);
//          int count = messageService.searchCountByContent(mapSearch);//每一个一级栏目下面二极栏目的数量
//         
//          System.out.println("count:"+count);
//          Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数       
//          String currentPage=request.getParameter("currentPage");
//          Pattern pattern = Pattern.compile("[0-9]{1,9}");
//          if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
//              page.setCurrentPage(1);
//          } else {
//              page.setCurrentPage(Integer.valueOf(currentPage));
//          }
//          page.setTotalNumber(count);
//          page.count();
//          map.put("page", page);
//          map.put("title",content);
//          List<Message> messagesListByPage=messageService.searchByContent(map);
//          List<Role> roleList=roleService.List();
//          mav.addObject("roleList", roleList);
//          mav.addObject("messagesListByPage",  messagesListByPage);
//          mav.addObject("controlURL", "messageSearchListByPage");//控制页码传递URL
//          mav.addObject("page", page); 
//          mav.setViewName("message");
//          
//         
//      	   return mav;
//   }
//	@RequestMapping("messageSearchListByPage")
//   public ModelAndView searchPage(HttpServletRequest request, 
//           HttpServletResponse response) throws IOException{
//		request.setCharacterEncoding("UTF-8");    	
//       response.setCharacterEncoding("UTF-8");
//   	ModelAndView mav = new ModelAndView();
//   	   Page page=new Page(); //分页类
//          Map<String,Object> mapSearch = new HashMap<String, Object>();
//          mapSearch.put("title", content);
//          int count = messageService.searchCountByContent(mapSearch);//每一个一级栏目下面二极栏目的数量
//          System.out.println("count:"+count);
//          Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数       
//          String currentPage=request.getParameter("currentPage");
//          Pattern pattern = Pattern.compile("[0-9]{1,9}");
//          if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
//              page.setCurrentPage(1);
//          } else {
//              page.setCurrentPage(Integer.valueOf(currentPage));
//          }
//          page.setTotalNumber(count);
//          page.count();
//          map.put("page", page);
//          map.put("title",content);
//          List<Message> messagesListByPage=messageService.searchByContent(map);
//          List<Role> roleList=roleService.List();
//          mav.addObject("roleList", roleList);
//          mav.addObject("messagesListByPage",  messagesListByPage);
//          mav.addObject("controlURL", "messageSearchListByPage");//控制页码传递URL
//          mav.addObject("page", page); 
//          mav.setViewName("message");
//      	   return mav;
//   }
//
//}
