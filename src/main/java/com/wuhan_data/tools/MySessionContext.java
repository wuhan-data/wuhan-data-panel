//package com.wuhan_data.tools;
//
//import com.workbgm.grid.interceptor.LoginInterceptor;
//import com.workbgm.grid.model.vo.data.LoginUserInfo;
//import java.util.HashMap;
//import java.util.Map;
// 
//import javax.servlet.http.HttpSession;
//public class MySessionContext {
//	 private static MySessionContext context;
//	 
//	    private Map<String, HttpSession> map;
//	 
//	    private MySessionContext() {
//	        map = new HashMap<>();
//	    }
//	 
//	    public static MySessionContext getSessionContext() {
//	        if(context == null) {
//	            context = new MySessionContext();
//	        }
//	        return context;
//	    }
//	 
//	   //添加
//	public synchronized void addSession(HttpSession session) {
//	    if(session!= null) {
//	        LoginUserInfo loginUserInfo = (LoginUserInfo) session.getAttribute(LoginInterceptor.SESSION_USER);
//	        if(loginUserInfo!=null) {
//	            //登录名-用户ID作为key，你可以定义你自己的
//	            String key=loginUserInfo.getSysUser().getUsername()+"-"+loginUserInfo.getSysUser().getId();
//	            HttpSession oldSession=this.getSession(key);
//	            if(oldSession!=null) {
//	                String newUUID=  session.getAttribute(LoginInterceptor.SESSION_CSRF_TOKEN).toString();
//	                String oldUUID = oldSession.getAttribute(LoginInterceptor.SESSION_CSRF_TOKEN).toString();
//	                if (!newUUID.equals(oldUUID)) {//不同浏览器才处理
//	                    this.clearSession(key);
//	                }
//	            }
//	            map.put(key, session);
//	        }
//	    }
//	}
//	 
//	    //清除
//	    public synchronized void clearSession(String key){
//	        HttpSession oldSession=this.getSession(key);
//	        if(oldSession!=null){
//	            oldSession.invalidate();//让session失效
//	            map.remove(key);
//	        }
//	    }
//	 
//	    //获取
//	    public synchronized HttpSession getSession(String key) {
//	        if(key == null)
//	            return null;
//	        return map.get(key);
//	    }
//	 
//	    //删除
//	    public synchronized void delSession(HttpSession session) {
//	        if(session!= null){
//	            LoginUserInfo loginUserInfo = (LoginUserInfo) session.getAttribute(LoginInterceptor.SESSION_USER);
//	            if(loginUserInfo!=null) {
//	                String key=loginUserInfo.getSysUser().getUsername()+"-"+loginUserInfo.getSysUser().getId();
//	                clearSession(key);
//	            }
//	        }
//	    }
//	 
//
//}
