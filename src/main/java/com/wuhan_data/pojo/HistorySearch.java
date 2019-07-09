package com.wuhan_data.pojo;

import java.util.Date;

//历史搜索
public class HistorySearch {
	Integer id;//历史搜索id
	Integer uid;//用户id
	String keyword;//搜索关键字
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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "HistorySearch [id=" + id + ", uid=" + uid + ", keyword=" + keyword + ", create_time=" + create_time
				+ "]";
	}
	

}
