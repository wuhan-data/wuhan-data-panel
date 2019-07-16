package com.wuhan_data.pojo;

import java.util.Date;
public class SysLog {
	private Integer id;
	private String operate_user_name;
	private String operate;
	private String method;
	private Date create_time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getOperate_user_name() {
		return operate_user_name;
	}
	public void setOperate_user_name(String operate_user_name) {
		this.operate_user_name = operate_user_name;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "SysLog [id=" + id + ", operate_user_id=" + operate_user_name + ", operate=" + operate + ", method=" + method
				+ ", create_time=" + create_time  + "]";
	
	}
	
	

}
