package com.wuhan_data.pojo;

import java.util.Date;
//后台管理员
public class PanelUser {
	Integer id; //管理员id
	String username;//管理员用户名
	String password;//管理员密码
	Integer status;//管理员状态 0正常 1禁封
	String role_list;//权限列表
	Date create_time;//创建时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRole_list() {
		return role_list;
	}
	public void setRole_list(String role_list) {
		this.role_list = role_list;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "PanelUser [id=" + id + ", username=" + username + ", password=" + password + ", status=" + status
				+ ", role_list=" + role_list + ", create_time=" + create_time + "]";
	}
	
	

}
