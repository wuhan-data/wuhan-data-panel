package com.wuhan_data.tools;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wuhan_data.service.SysLogService;


public class SysFilter implements Filter{
	
	@Autowired
    SysLogService sysLogService;
	
	
	 @Override
	    public void destroy() {
	 
	    }
	 
	    @Override
	    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
	            throws IOException, ServletException {
	        HttpServletRequest request = (HttpServletRequest) req;
	        HttpServletResponse response = (HttpServletResponse) res;
	       // System.out.println(request.getRequestURL().toString()+request.getParameterMap().toString());
	        //sysLogService.add(1, "1", "2");
	        //sysLogService.add(-1,request.getRequestURL().toString(),request.getParameterMap().toString());
//	        if(request.getParameter("id")==null)
//	        {
//	        	sysLogService.add(-1,request.getRequestURL().toString(),request.getParameterMap().toString());
//	        }
//	        else {
//	        	sysLogService.add(Integer.valueOf(request.getParameter("id")),request.getRequestURL().toString(),request.getParameterMap().toString());
//				
//			}
	 
	
	        chain.doFilter(request, response);
	    }
	 
	    @Override
	    public void init(FilterConfig arg0) throws ServletException {
	 
	    }


}
