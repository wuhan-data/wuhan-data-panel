package com.wuhan_data.pojo;
import java.util.Date;
public class Message {
	private Integer id;
	private String sender_name;
	private String receiver_name;
	private String title;
	private String url;
	private String remarks;
	private Date create_time;
	private Integer is_read;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSender_name() {
		return sender_name;
	}
	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}
	public String getReceiver_name() {
		return receiver_name;
	}
	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Integer getIs_read() {
		return is_read;
	}
	public void setIs_read(Integer is_read) {
		this.is_read = is_read;
	}
	@Override
	public String toString() {
		return "message [id=" + id + ", sender_name=" + sender_name + ", receiver_name=" + receiver_name
				+ ", title=" + title + ", url=" + url + ", remarks=" + remarks + ", create_time=" + create_time
				+ ", is_read=" + is_read + "]";
	}
	

}
