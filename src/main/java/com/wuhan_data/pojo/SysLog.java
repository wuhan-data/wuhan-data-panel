package com.wuhan_data.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;
public class SysLog {
	private Integer id;
	private Integer operate_user_id;//操作者id
	private String e_type;//操作者身份
	private String e_interface;//产生错误的接口
	private String e_parameter;
	private String e_msg;
	private String e_error;
	private Date create_time;
	private String timeString;//使用来规范时间的显示格式的
	public String getTimeString() {
		String strDateFormat = "yyyy-MM-dd HH:mm:ss";
	    SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
		return sdf.format(create_time);
	}
	public void setTimeString() {
		String strDateFormat = "yyyy-MM-dd HH:mm:ss";
	    SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
		this.timeString = sdf.format(create_time);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOperate_user_id() {
		return operate_user_id;
	}
	public void setOperate_user_id(Integer operate_user_id) {
		this.operate_user_id = operate_user_id;
	}
	public String getE_type() {
		return e_type;
	}
	public void setE_type(String e_type) {
		this.e_type = e_type;
	}
	public String getE_interface() {
		return e_interface;
	}
	public void setE_interface(String e_interface) {
		this.e_interface = e_interface;
	}
	public String getE_parameter() {
		return e_parameter;
	}
	public void setE_parameter(String e_parameter) {
		this.e_parameter = e_parameter;
	}
	public String getE_msg() {
		return e_msg;
	}
	public void setE_msg(String e_msg) {
		this.e_msg = e_msg;
	}
	public String getE_error() {
		return e_error;
	}
	public void setE_error(String e_error) {
		this.e_error = e_error;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "SysLog [id=" + id + ", operate_user_id=" + operate_user_id + ", e_type=" + e_type + ", e_interface="
				+ e_interface + ", e_parameter=" + e_parameter + ", e_msg=" + e_msg + ", e_error=" + e_error
				+ ", create_time=" + create_time + ", timeString=" + timeString + "]";
	}
	
	
	
	

}
