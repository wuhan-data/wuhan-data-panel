package com.wuhan_data.pojo;

public class Menu {
	Integer id;
	String level_one;
	String level_two;
	String role_name;
	String url;
	String perm;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLevel_one() {
		return level_one;
	}
	public void setLevel_one(String level_one) {
		this.level_one = level_one;
	}
	public String getLevel_two() {
		return level_two;
	}
	public void setLevel_two(String level_two) {
		this.level_two = level_two;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPerm() {
		return perm;
	}
	public void setPerm(String perm) {
		this.perm = perm;
	}
	
	@Override
	public String toString() {
		return "Menu [id=" + id + ", level_one=" + level_one + ", level_two=" + level_two + ", role_name=" + role_name
				+ ", url=" + url + ", perm=" + perm + "]";
	}
}
