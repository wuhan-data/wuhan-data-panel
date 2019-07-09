package com.wuhan_data.pojo;

import java.util.Date;
public class SysLog {
	private Integer id;
	private Integer operate_user_id;
	private String operate;
	private String method;
	private Date create_time;
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
		return "SysLog [id=" + id + ", operate_user_id=" + operate_user_id + ", operate=" + operate + ", method=" + method
				+ ", create_time=" + create_time  + "]";
	
	}
	
	

}
