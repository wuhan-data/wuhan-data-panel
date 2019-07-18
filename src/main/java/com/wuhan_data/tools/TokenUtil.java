package com.wuhan_data.tools;

import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TokenUtil {
	
	public static String getToken(String string)
	{
		return md5Password(string);
	}
	public static boolean verify(HttpServletRequest request,HttpServletResponse response,String token)//可以吧token的验证放在过滤器中
	{
		 HttpSession session = request.getSession();//设置session
		 if(session.getAttribute(token)==null)
		 {
			 return false;
		 }
		
		return true;
	}
	public static String md5Password(String password) {
		 
		try {
			// 得到一个信息摘要器
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] result = digest.digest(password.getBytes());
			StringBuffer buffer = new StringBuffer();
			// 把每一个byte 做一个与运算 0xff;
			for (byte b : result) {
				// 与运算
				int number = b & 0xff;// 加盐
				String str = Integer.toHexString(number);
				if (str.length() == 1) {
					buffer.append("0");
				}
				buffer.append(str);
			}
			// 标准的md5加密后的结果
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}


}
