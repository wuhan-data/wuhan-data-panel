package com.wuhan_data.pojo;

import java.util.Date;

//收藏
public class Collect {
	Integer id;//收藏id
	Integer uid;//用户id
	String type;//收藏的类型 analysis/index
	Integer index_id;//指标数据id
	Date create_time;//收藏时间
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getIndex_id() {
		return index_id;
	}
	public void setIndex_id(Integer index_id) {
		this.index_id = index_id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "Collect [id=" + id + ", uid=" + uid + ", type=" + type + ", index_id=" + index_id + ", create_time="
				+ create_time + "]";
	}
	

}
