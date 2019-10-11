package com.wuhan_data.pojo;

public class Role {
	Integer id;
	String role_code;
	String  role_name;
	String  role_description;
	String role_power_1;
	String role_power_2;
	String role_power_3;
	public String getRole_power_4() {
		return role_power_4;
	}
	public void setRole_power_4(String role_power_4) {
		this.role_power_4 = role_power_4;
	}
	String role_power_4;
	public String getRole_power_1() {
		return role_power_1;
	}
	public void setRole_power_1(String role_power_1) {
		this.role_power_1 = role_power_1;
	}
	public String getRole_power_2() {
		return role_power_2;
	}
	public void setRole_power_2(String role_power_2) {
		this.role_power_2 = role_power_2;
	}
	public String getRole_power_3() {
		return role_power_3;
	}
	public void setRole_power_3(String role_power_3) {
		this.role_power_3 = role_power_3;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRole_code() {
		return role_code;
	}
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getRole_description() {
		return role_description;
	}
	public void setRole_description(String role_description) {
		this.role_description = role_description;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", role_code=" + role_code + ", role_name=" + role_name + ", role_description="
				+ role_description + ", role_power_1=" + role_power_1 + ", role_power_2=" + role_power_2
				+ ", role_power_3=" + role_power_3 + "]";
	}

	

}
