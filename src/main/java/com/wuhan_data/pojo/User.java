package com.wuhan_data.pojo;

import java.util.Date;

public class User {
    private Integer id;
    private String username;//用户名
    private String password;//密码
    private String status; //用户状态 0正常 1封禁
    private Integer gender;//性别 0女 1男 9未知
    private String tel;//电话
    private String real_name;//真实姓名
    private String role_list;//权限列表
    private String role_name;//权限名称
    private Date create_time;//创建时间
    private String role_id;
    private String department_id;
    private Date birthday;
    private String city;
    private String head;
    private String description;
    public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
    
	
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getRole_list() {
		return role_list;
	}
	public void setRole_list(String role_list) {
		this.role_list = role_list;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", status=" + status
				+ ", gender=" + gender + ", tel=" + tel + ", real_name=" + real_name + ", role_list=" + role_list
				+ ", role_name=" + role_name + ", create_time=" + create_time + ", role_id=" + role_id
				+ ", department_id=" + department_id + ", birthday=" + birthday + ", city=" + city + ", head=" + head
				+ ", description=" + description + "]";
	}
	
    
}