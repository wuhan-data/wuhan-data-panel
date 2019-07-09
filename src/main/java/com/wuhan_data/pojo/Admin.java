package com.wuhan_data.pojo;

import java.util.Date;

public class Admin {

	private Integer id;
	private String username;
	private String password;
	private Integer status;
	private String role_list;
	private Date create_date;
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

	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	@Override
	public String toString() {
		return "admin [id=" + id + ", username=" + username + ", password=" + password + ", status=" + status
				+ ", role_list=" + role_list + ", create_date=" + create_date + "]";
	}
	
}
