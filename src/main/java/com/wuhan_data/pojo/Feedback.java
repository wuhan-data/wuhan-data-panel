package com.wuhan_data.pojo;

import java.util.Date;

public class Feedback {
	private Integer id;
	private Integer uid;
	private String title;
	private String text;
	private String img;
	private String contact;
	private Date create_time;
	private String state;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Feedback [id=" + id + ", uid=" + uid + ", title=" + title + ", text=" + text + ", img=" + img
				+ ", contact=" + contact + ", create_time=" + create_time + ", state=" + state + "]";
	}
	
	

}
