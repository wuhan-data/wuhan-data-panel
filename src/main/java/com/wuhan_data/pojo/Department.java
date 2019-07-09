package com.wuhan_data.pojo;

public class Department {
	Integer id;
	Integer department_code;//部门编号
	String department_name;//部门名字
	String department_description;//部门描述
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDepartment_code() {
		return department_code;
	}
	public void setDepartment_code(Integer department_code) {
		this.department_code = department_code;
	}
	public String getDepartment_name() {
		return department_name;
	}
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	public String getDepartment_description() {
		return department_description;
	}
	public void setDepartment_description(String department_description) {
		this.department_description = department_description;
	}
	@Override
	public String toString() {
		return "Department [id=" + id + ", department_code=" + department_code +", department_name="
				+ department_name +", department_description=" + department_description +"]";
	}
	

}
