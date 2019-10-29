package com.wuhan_data.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserOpLog {
	private Integer id;
	private Integer user_id;
	private String op_interface;
	private String op_parameter;
	private String op_msg;
	private Date op_create_time;
	private String op_create_timeString;
	
	public String getOp_create_timeString() {
		String strDateFormat = "yyyy-MM-dd HH:mm:ss";
	    SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
		return sdf.format(op_create_time);
	}
	public void setOp_create_timeString(String op_create_timeString) {
		String strDateFormat = "yyyy-MM-dd HH:mm:ss";
	    SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
		this.op_create_timeString = sdf.format(op_create_time);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getOp_interface() {
		return op_interface;
	}
	public void setOp_interface(String op_interface) {
		this.op_interface = op_interface;
	}
	public String getOp_parameter() {
		return op_parameter;
	}
	public void setOp_parameter(String op_parameter) {
		this.op_parameter = op_parameter;
	}
	public String getOp_msg() {
		return op_msg;
	}
	public void setOp_msg(String op_msg) {
		this.op_msg = op_msg;
	}
	public Date getOp_create_time() {
		return op_create_time;
	}
	public void setOp_create_time(Date op_create_time) {
		this.op_create_time = op_create_time;
	}
	@Override
	public String toString() {
		return "UserOpLog [id=" + id + ", user_id=" + user_id + ", op_interface=" + op_interface + ", op_parameter="
				+ op_parameter + ", op_msg=" + op_msg + ", op_create_time=" + op_create_time + "]";
	}
	

}
