package com.wuhan_data.pojo;

import java.util.Date;

//历史浏览记录
public class HistoryView {
	Integer id;//历史浏览id
	Integer uid;//用户id
	String func_name;//功能模块名称
	String title;//浏览具体页面标题
	String view_url;//浏览url
	Date create_time;//创建时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getFunc_name() {
		return func_name;
	}
	public void setFunc_name(String func_name) {
		this.func_name = func_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getView_url() {
		return view_url;
	}
	public void setView_url(String view_url) {
		this.view_url = view_url;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "HistoryView [id=" + id + ", uid=" + uid + ", func_name=" + func_name + ", title=" + title
				+ ", view_url=" + view_url + ", create_time=" + create_time + "]";
	}
	

}
